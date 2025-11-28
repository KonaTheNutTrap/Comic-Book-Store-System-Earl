package managers;
import entities.Comic;
import entities.Order;
import entities.Stock;

import java.util.*;




public class PurchaseManager extends EntityManager<Order> {
    ArrayList<Order> cart = new ArrayList<>();
    ComicManager comicManager;
    InventoryManager stock;
   


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

    }




    public void addOrder(String comicInput, int quantity) {
        Comic comic;
    

        comic = comicManager.findByIdOrName(comicInput);

        if (comic == null) {
            System.out.println("Comic not found!");
            return;
        }

        try {
            Order newOrder = new Order(comic, quantity);
            cart.add(newOrder);
            System.out.println("Added to cart!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    } 
    






    public void viewOrders() {
        System.out.println("==Your Shopping Cart==");
        if (cart.isEmpty()) {
            System.out.println("Cart is currently empty");
            return;
        }

        for (Order order : cart) {
            System.out.println(order.toString());
            System.out.println("================================");
        }


    }




    public void removeOrder(String c) {

        if (cart.isEmpty()) {
            System.out.println("There's nothing to remove because the cart is empty.");
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
            System.out.println("Item removed from cart!");
        } else {
            System.out.println("Item not found in cart!");
        }

        }

        }


    public void checkout() {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to checkout.");
            return;
        }

        // First, check if all items have sufficient stock
        boolean canCheckout = true;
        for (Order order : cart) {
            int comicId = order.getComic().getId();
            if (!stock.hasSufficientStock(comicId, order.getQuantity())) {
                System.out.println("Insufficient stock for " + order.getComic().getTitle() + ". Available: " + stock.getStockQuantity(comicId));
                canCheckout = false;
            } else if (stock.getStockQuantity(comicId) == -1) {
                System.out.println("No stock record found for " + order.getComic().getTitle());
                canCheckout = false;
            }
        }

        if (!canCheckout) {
            System.out.println("Checkout failed due to insufficient stock.");
            return;
        }

        // Now, deduct stocks and process orders
        double cartTotal = 0.0;

        System.out.println("===Receipt===");
        for (Order order : cart) {
            int comicId = order.getComic().getId();
            stock.removeStockFromComic(comicId, order.getQuantity());

            System.out.println("Comic: " + order.getComic().getTitle() + ", Quantity: " + order.getQuantity() + ", Price: $" + order.getComic().getPrice() + ", Total: $" + order.getComicTotal());

            cartTotal += order.getComicTotal();
            add(order);  // Persist order
        }

        System.out.println("Grand Total: P" + cartTotal);
        System.out.println("Purchase completed successfully!");

        // Clear cart after successful checkout
        cart.clear();
    }
}
