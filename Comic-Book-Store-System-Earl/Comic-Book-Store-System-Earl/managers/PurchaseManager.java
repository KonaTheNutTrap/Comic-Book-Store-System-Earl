package managers;
import entities.Comic;
import entities.Order;
import entities.Stock;
import utils.FileHandler;

import java.util.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




public class PurchaseManager extends EntityManager<Order> {
    ArrayList<Order> cart = new ArrayList<>();
    ComicManager comicManager;
    InventoryManager stock;
    private int orderIdCounter;
   


    //Abstract methods that must be implemented because of inheriting from a abstract class
    @Override
    protected Order parse(String line) { return null; }

    @Override
    protected String serialize(Order entity) { return entity.toString(); }

    @Override
    protected int getId(Order entity) { return entity.getComic().getId(); }

    @Override
    protected void updateEntity(Order entity, Scanner sc) { }

    @Override
    public void add(Order entity) {
        entities.add(entity);
        save(); // Persist changes to file
    }
    

    public PurchaseManager(String filename, ComicManager comicManager, InventoryManager stock) {

        super(filename);
        this.comicManager = comicManager;
        this.stock = stock;

        // Initialize orderIdCounter based on existing order files
        File dataDir = new File("data");
        File[] orderFiles = dataDir.listFiles((dir, name) -> name.startsWith("order_") && name.endsWith(".txt"));
        if (orderFiles != null && orderFiles.length > 0) {
            int maxId = 0;
            for (File file : orderFiles) {
                String name = file.getName(); // order_N.txt
                try {
                    int id = Integer.parseInt(name.substring(6, name.length() - 4));
                    if (id > maxId) maxId = id;
                } catch (NumberFormatException e) {
                    // Skip invalid filename
                }
            }
            orderIdCounter = maxId + 1;
        } else {
            orderIdCounter = 1;
        }

    }




    public void addOrder(String comicInput, int quantity) {
        Comic comic;


        comic = comicManager.findByIdOrName(comicInput);

        if (comic == null) {
            System.out.println("          Comic not found!");
            return;
        }

        try {
            Order newOrder = new Order(comic, quantity);
            cart.add(newOrder);
            System.out.println("          Item was successfully added to cart!");
        } catch (IllegalArgumentException e) {
            System.out.println("              Error: " + e.getMessage());
        }
    }

    public void displayAvailableComics() {
         System.out.println("───────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("                                   AVAILABLE COMICS               " + 
                     "\n ─────────────────────────────────────────────────────────────────────────────────────");
        List<Comic> comics = comicManager.getAll();
        boolean found = false;
        for (Comic comic : comics) {
            int stockQty = stock.getStockQuantity(comic.getId());
            if (stockQty > 0) {
                found = true;
                System.out.println(comic.display());
                System.out.println("Stock available: " + stockQty);
                System.out.println();
            }
        }
        if (!found) {
            System.out.println("No comics with available stock found.");
        }
    }
    






    public void viewOrders() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                                    Shopping Cart");
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        if (cart.isEmpty()) {
            System.out.println("                             Cart is currently empty");
            return;
        }

        for (Order order : cart) {
            System.out.println(order.toString());
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────");
        }


    }




    public void removeOrder(String c) {

        if (cart.isEmpty()) {
            System.out.println("          There's nothing to remove because the cart is empty.      ");
            return;
        } else {

        // Try to parse as ID, else remove by title
        boolean removed = false;
        try {
            int id = Integer.parseInt(c.trim());
            removed = cart.removeIf(o -> o.getComic().getId() == id);
        } catch (NumberFormatException e) {
            removed = cart.removeIf(o -> o.getComic().getTitle().equalsIgnoreCase(c.trim()));
        }

        if (removed) {
            System.out.println("                        Item has succesfully been removed from Cart!              ");
        } else {
            System.out.println("                                    Item not found in cart!                        ");
        }

        }

        }


    public void checkout() {

        if (cart.isEmpty()) {
            System.out.println("                                  Cart is empty. Nothing to checkout.");
            return;
        }

        // First, check if all items have sufficient stock
        boolean canCheckout = true;
        for (Order order : cart) {
            int comicId = order.getComic().getId();
            if (!stock.hasSufficientStock(comicId, order.getQuantity())) {
                System.out.println("                                    Insufficient stock for " + order.getComic().getTitle() + ". Available: " + stock.getStockQuantity(comicId));
                canCheckout = false;
            } else if (stock.getStockQuantity(comicId) == -1) {
                System.out.println("                                    No stock record found for " + order.getComic().getTitle());
                canCheckout = false;
            }
        }

        if (!canCheckout) {
            System.out.println("                                    Checkout failed due to insufficient stock.");
            return;
        }

        // Assign order ID
        int orderId = orderIdCounter++;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Now, deduct stocks and process orders
        double cartTotal = 0.0;
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("                                      OFFICIAL RECEIPT");
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────");
        
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order ID: ").append(orderId).append("\n");
        receipt.append("Date: ").append(now.format(formatter)).append("\n");
        receipt.append("Items:\n");
        for (Order order : cart) {
            int comicId = order.getComic().getId();
            stock.removeStockFromComic(comicId, order.getQuantity());

            String itemLine = "                           Comic: " + order.getComic().getTitle() + " Quantity: " + order.getQuantity() + " Price: P" + order.getComic().getPrice() + " Total: P" + order.getComicTotal() + "\n                                                                                      ";
            System.out.println(itemLine.trim());
            receipt.append(itemLine);

            cartTotal += order.getComicTotal();
        }

        System.out.println("                           Your Total is: P" + cartTotal);
        receipt.append("              Total: P").append(cartTotal).append("\n");

        // Save to file
        String filename = "data/order_" + orderId + ".txt";
        List<String> lines = Arrays.asList(receipt.toString().split("\n"));
        FileHandler.writeFile(filename, lines);

        System.out.println("              " + "\n Purchase completed successfully! Receipt saved as order_" + orderId + ".txt");

        // Clear cart after successful checkout
        cart.clear();
    }
}
