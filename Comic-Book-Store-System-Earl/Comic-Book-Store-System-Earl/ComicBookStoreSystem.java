//import java.io.*;
import java.util.*;
import managers.*;
import entities.*;

/*
 * Comic Book Store System - Main Application Class
 *
 * This application provides a console-based interface for managing a comic book store.
 * It supports two types of users: Admin and Customer, with different levels of access.
 *
 * Features:
 * - Admin: Manage comics and inventory (CRUD operations)
 * - Customer: Browse comics and make purchases
 * - Data persistence using text files
 *
 * @author Comic Book Store System
 * @version 2.0
 */
public class ComicBookStoreSystem {
    // Scanner for user input throughout the application
    private static Scanner sc = new Scanner(System.in);

    // Manager instances for handling comic and inventory data
    private static ComicManager comicManager = new ComicManager("data/comics.txt");
    private static InventoryManager inventoryManager = new InventoryManager("data/stocks.txt");
    private static PurchaseManager purchaseManager = new PurchaseManager("data/orders.txt", comicManager);

    // Admin validation
    private static final String ADMIN_FILE = "data/admin.txt";


    public static void cls() {
        System.out.println("\033c");
    }

    public static void spc() {
         System.out.println("                                                                                     ");

    }

    public static void line() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");      
    }

    public static void ln() {
        System.out.println("─────────────────────────────────────────────────────────────────────────────────────── ");
    }





    private static boolean adminLogin() {
        System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                                                                                     ");
        System.out.println("  ██████╗ ██████╗ ███╗   ███╗██╗ ██████╗    ███████╗██╗  ██╗ █████╗  ██████╗██╗  ██╗\r\n" + //
                        " ██╔════╝██╔═══██╗████╗ ████║██║██╔════╝    ██╔════╝██║  ██║██╔══██╗██╔════╝██║ ██╔╝\r\n" + //
                        " ██║     ██║   ██║██╔████╔██║██║██║         ███████╗███████║███████║██║     █████╔╝ \r\n" + //
                        " ██║     ██║   ██║██║╚██╔╝██║██║██║         ╚════██║██╔══██║██╔══██║██║     ██╔═██╗ \r\n" + //
                        " ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║╚██████╗    ███████║██║  ██║██║  ██║╚██████╗██║  ██╗\r\n" + //
                        "  ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝ ╚═════╝    ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝\r\n" + //
                        "                                                                                   ");
         System.out.println("                                                                                     ");
         System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
         System.out.println("                                                                                     ");
         System.out.println("                                                                                     ");
         System.out.println("                                                                                     ");

        System.out.println("Please log in your Employee Account");
        System.out.println("                                                                                     ");
        System.out.print("Employee Username: ");
        String user = sc.nextLine();
        spc();
        spc();
        System.out.print("Account Password: ");
        String pass = sc.nextLine();


        try {
            java.io.File file = new java.io.File(ADMIN_FILE);
            if (!file.exists()) {
                System.out.println("Admin credentials file missing!");
                return false;
            }

            Scanner reader = new Scanner(file);
            if (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                reader.close();

                if (parts.length == 2) {
                    String savedUser = parts[0].trim();
                    String savedPass = parts[1].trim();

                    if (savedUser.equals(user) && savedPass.equals(pass)) {
                        System.out.println("Login Successful!\n");

                        return true;
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading admin file.");
        }

        System.out.println("Invalid username or password.\n");
        return false;
    }

    /**
     * Main entry point of the Comic Book Store System application.
     * Displays the main menu and handles user navigation between different modules.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        if (!adminLogin()) {
            System.out.println("Access denied. Exiting program...");
            return;
        }

        // Main application loop - continues until user chooses to exit
        
        while (true) {
            cls();
            line();
            spc();
            System.out.println("   ███╗   ███╗ █████╗ ██╗███╗   ██╗    ███╗   ███╗███████╗███╗   ██╗██╗   ██╗\r\n" + //
                                "   ████╗ ████║██╔══██╗██║████╗  ██║    ████╗ ████║██╔════╝████╗  ██║██║   ██║\r\n" + //
                                "   ██╔████╔██║███████║██║██╔██╗ ██║    ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║\r\n" + //
                                "   ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║\r\n" + //
                                "   ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝\r\n" + //
                                "   ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝    ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝ \r\n" + //
                                "                                                                          ");
            spc();
            ln();
            spc();
            System.out.println("    [1] Manage Inventory");
            spc();
            System.out.println("    [2] Manage Store Purchases");
            spc();
            System.out.println("    [3] Search Comics");
            spc();
            System.out.println("    [4] Exit");
            spc();
            ln();

            int choice = -1;
            while (true) {
               
                try {
                   
                    System.out.print("   What would you like to do?        ");
                   
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("    Invalid input. Please enter a valid number.    ");
                    sc.nextLine(); // clear invalid input
                }
             
            }

            // Route to appropriate menu based on user selection
            switch (choice) {
                case 1: adminMenu(); break;
                case 2: manageCart(); break;
                case 3: searchComics(); break;
                case 4: {
                    System.out.println("Exiting...");
                    return;
                }
                default: System.out.println("Invalid option!"); break;
            }
        }
    }

    /**
     * Admin Management Menu - Provides access to administrative functions.
     * Allows admin users to manage comic inventory and customer records.
     */
    private static void adminMenu() {
        // Admin menu loop - continues until user returns to main menu
        while (true) {
            cls();
            line();
            spc();
            System.out.println("   █████╗ ██████╗ ███╗   ███╗██╗███╗   ██╗    ███╗   ███╗███████╗███╗   ██╗██╗   ██╗\r\n" + //
                                "  ██╔══██╗██╔══██╗████╗ ████║██║████╗  ██║    ████╗ ████║██╔════╝████╗  ██║██║   ██║\r\n" + //
                                "  ███████║██║  ██║██╔████╔██║██║██╔██╗ ██║    ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║\r\n" + //
                                "  ██╔══██║██║  ██║██║╚██╔╝██║██║██║╚██╗██║    ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║\r\n" + //
                                "  ██║  ██║██████╔╝██║ ╚═╝ ██║██║██║ ╚████║    ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝\r\n" + //
                                "  ╚═╝  ╚═╝╚═════╝ ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝    ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝ \r\n" + //
                                "                                                                                  ");
            spc();
            ln();
            spc();
            System.out.println("    [1] Manage Comics");
            spc();
            System.out.println("    [2] Manage Stocks");
            spc();
            System.out.println("    [3] Back To Main Menu");
            spc();
            ln();
           

            int choice = -1;
            while (true) {
                try {
                    System.out.print("   What would you like to do?        ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("    Invalid input. Please enter a valid number.    ");
                    sc.nextLine(); // clear invalid input
                }
            }

            // Route to appropriate management module
            switch (choice) {
                case 1 : manageComics(); break;      // Navigate to comic management
                case 2 : manageInventory(); break;   // Navigate to inventory management
                case 3 : { return; }          // Return to main menu
                default : System.out.println("Invalid option!"); break;
            }
        }
    }

    /**
     * Comic Management Module - Handles all comic-related operations.
     */
    private static void manageComics() {
        // Comic management loop - continues until user returns to admin menu
        while (true) {
            cls();
            line();
            spc();
            System.out.println("   ██████╗ ██████╗ ███╗   ███╗██╗ ██████╗███████╗\r\n" + //
                                "  ██╔════╝██╔═══██╗████╗ ████║██║██╔════╝██╔════╝\r\n" + //
                                "  ██║     ██║   ██║██╔████╔██║██║██║     ███████╗\r\n" + //
                                "  ██║     ██║   ██║██║╚██╔╝██║██║██║     ╚════██║\r\n" + //
                                "  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║╚██████╗███████║\r\n" + //
                                "   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝ ╚═════╝╚══════╝\r\n" + //
                                "                                               ");
            spc();
            ln();
            spc();
            System.out.println("    [1] Add New Comic");
            spc();
            System.out.println("    [2] Display All Comics");
            spc();
            System.out.println("    [3] Update Comic Info");
            spc();
            System.out.println("    [4] Delete Comic");
            spc();
            System.out.println("    [5] Return");
            spc();
            ln();
           

            int choice = -1;
            while (true) {
                try {
                    System.out.print("      What would you like to do?        ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine(); // clear invalid input
                }
            
            }
             
                
            

            // Execute comic operation based on user selection
            
            switch (choice) {
                case 1 : 
                while(true) {
                comicManager.addComic(sc);
                spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                }

                
                }
                break; // Add new comic to inventory
                
                case 2 : 
                while(true) {
                spc();
                spc();
                line();
                System.out.println("                  COMIC CURRENTLY AVAILABLE        ");
                spc();
                ln();
                comicManager.displayAll(Comic::display);  // Display all comics using method reference
                spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                }
                } break;
                case 3 : {
                    while (true) {
                        
                    // Update existing comic - first display all, then select by ID or title
                    comicManager.displayAll(Comic::display);
                    spc();
                    spc();
                    ln();
                    System.out.print("Enter ID or title to update: ");
                    String input = sc.nextLine();
                    Comic comic = comicManager.findByIdOrName(input);
                    
                    if (comic != null) {
                        comicManager.update(comic.getId(), sc);
                    } else {
                        System.out.println("Comic cannot be found");
                    }
                    
                   spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                }
            } }break;
                case 4 : {
                    while (true) {
                        
                    
                
                    // Delete comic - first display all, then select by ID or title
                    System.out.println("              AVAILABLE COMICS                ");
                    comicManager.displayAll(Comic::display);
                    System.out.print("          What Comic would you like to delete?   ");
                    String input = sc.nextLine();
                    Comic comic = comicManager.findByIdOrName(input);
                    spc();
                    spc();
            
                    if (comic != null) {
                        comicManager.delete(comic.getId());
                        System.out.println("Deleted successfully!");
                    } else {
                        System.out.println("Comic not found!");
                    }
                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                } }} break;


                case 5 : { return; } // Return to admin menu
                default : System.out.println("Invalid option!"); break;
            }
        }
    
    } 
    /**
     * Inventory Management Module - Handles all inventory-related operations.
     */
    private static void manageInventory() {
        // Inventory management loop - continues until user returns to admin menu
        while (true) {
            cls();
            

            line();
            spc();
            System.out.println("  ██╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗████████╗ ██████╗ ██████╗ ██╗   ██╗\r\n" + //
                                "  ██║████╗  ██║██║   ██║██╔════╝████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗╚██╗ ██╔╝\r\n" + //
                                "  ██║██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║   ██║   ██║   ██║██████╔╝ ╚████╔╝ \r\n" + //
                                "  ██║██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ██║   ██║██╔══██╗  ╚██╔╝  \r\n" + //
                                "  ██║██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║   ██║   ╚██████╔╝██║  ██║   ██║   \r\n" + //
                                "  ╚═╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝   \r\n" + //
                                "                                                                           ");
            spc();
            ln();
            // Display low stock dashboard
            inventoryManager.displayLowStockDashboard(comicManager);
            spc();
            System.out.println("    [1] Add Stock Record For Comic");
            spc();
            System.out.println("    [2] Display all Stock Records");
            spc();
            System.out.println("    [3] Update Stock Records");
            spc();
            System.out.println("    [4] Delete Stock Record");
            spc();
            System.out.println("    [5] Comic Stock Checker");
            spc();
            System.out.println("    [6] Return");
            spc();
            ln();
        
            int choice = -1;
            while (true) {
                try {
                    System.out.print(   "     What would you like to do?        ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine(); // clear invalid input
                }
            }

            switch (choice) {
                case 1:
                    while (true) {
                        
                    
                    inventoryManager.addStock(sc, comicManager);
                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                }
            
            } break;

                case 2:
                    while (true) {
                        
                    
                    inventoryManager.displayAll(comicManager);
                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                } } break;

                case 3:
                    while (true) {
                        
                    
                    inventoryManager.displayAll(comicManager);
                    int id = -1;
                    while (true) {
                        try {
                            spc();
                            spc();
                            ln();
                            
                            System.out.print("      Select the ID of the Comic you would like to update:    ");
                            id = sc.nextInt();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            sc.nextLine(); // clear invalid input
                        }
                    }
                    inventoryManager.update(id, sc);


                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                } 
            } break;
                case 4:
                    while (true) {
                        
                    
                    inventoryManager.displayAll(comicManager);
                    int deleteId = -1;
                    while (true) {
                        try {
                            System.out.print("Enter Stock ID to delete: ");
                            deleteId = sc.nextInt();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            sc.nextLine(); // clear invalid input
                        }
                    }
                    inventoryManager.delete(deleteId);
                    System.out.println("Deleted successfully!");
                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                } } break;
                    
                case 5:
                    while (true) {
                        
                    
                    System.out.print("Enter Comic ID or title to check stock: ");
                    String input = sc.nextLine();
                    Comic comic = comicManager.findByIdOrName(input);
                    if (comic != null) {
                        int quantity = inventoryManager.getStockQuantity(comic.getId());
                        if (quantity >= 0) {
                            System.out.println("Current stock: " + quantity);
                        } else {
                            System.out.println("No stock record found for comic: " + comic.getTitle());
                        }
                    } else {
                        System.out.println("Comic not found!");
                    } 
                    spc();
                System.out.println("            [1] Return        ");
                spc();
                System.out.println("          What would you like to do?      ");
                spc();
                choice = sc.nextInt();

                if (choice <= 1) {
                    break;   
                } else {
                    break;
                } }  break;
            
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
                    spc();
                 
            }
        }
    }

    private static void manageCart() {
        while (true) {
            
            line();
            spc();
            System.out.println( "   ██████╗ █████╗ ██████╗ ████████╗\r\n" + //
                                "  ██╔════╝██╔══██╗██╔══██╗╚══██╔══╝\r\n" + //
                                "  ██║     ███████║██████╔╝   ██║   \r\n" + //
                                "  ██║     ██╔══██║██╔══██╗   ██║   \r\n" + //
                                "  ╚██████╗██║  ██║██║  ██║   ██║   \r\n" + //
                                "   ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   \r\n" + //
                                "                                 ");
            spc();
            ln();
            spc();
            System.out.println("    [1] Add to Cart");
            spc();
            System.out.println("    [2] View Cart");
            spc();
            System.out.println("    [3] Remove Item From Cart");
            spc();
            System.out.println("    [4] Checkout Cart");
            spc();
            System.out.println("    [5] Return");
            spc();

            ln();


            int choice = -1;
            while (true) {
                try {
                    System.out.print("   What would you like to do?        ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine(); // clear invalid input
                }
            }

            switch(choice) {
                case 1:
                    System.out.print("Enter Comic ID or Title: ");
                    String comicInput = sc.nextLine().trim();

                    int qty = -1;
                    while (true) {
                        try {
                            System.out.print("Enter the Amount/Quantity: ");
                            qty = sc.nextInt();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            sc.nextLine(); // clear invalid input
                        }
                    }

                    purchaseManager.addOrder(comicInput, qty);
                    break;

                case 2: purchaseManager.viewOrders();
                    break;

                case 3:
                    purchaseManager.viewOrders();
                    System.out.print("Enter the ID or title of the comic you would like to remove: ");
                    String c = sc.nextLine();

                    purchaseManager.removeOrder(c);
                    break;

                case 4: purchaseManager.checkout();
                    break;

                case 5: return;

                default : System.out.println("Please pick from one of the choices!");

            }
        }
    }

    /**
     * Search Comics - Allows users to search for comics by ID or name.
     * Displays the details of the found comic or shows a "not found" message.
     */
    private static void searchComics() {
        while (true) {
        cls();
    
        line();
        spc();
        spc();
        System.out.println("     ███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗\r\n" + //
                        "     ██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║\r\n" + //
                        "     ███████╗█████╗  ███████║██████╔╝██║     ███████║\r\n" + //
                        "     ╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║\r\n" + //
                        "     ███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║\r\n" + //
                        "     ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝\r\n" + //
                        "                                                ");
        spc();
        spc();
        ln();
        spc();
        spc();
        int choice = -1;

        System.out.print("     What Comic are you looking for?:  ");
        String input = sc.nextLine().trim();
        spc();
        line();
        spc();
        spc();


        Comic comic = comicManager.findByIdOrName(input);
        if (comic != null) {
            System.out.println("            Results:              ");
            line();
            System.out.println(comic.display());
            line();
        } else {
            System.out.println("Comic not found!");
        }

        spc();
        spc();
        System.out.println("          [1] Search again    ");
        System.out.println(  "          [2] Exit Menu    ");
        choice = sc.nextInt();
        sc.nextLine();


        switch (choice) {
            case 1:
                searchComics();
                
                break;
        
        case 2: 
                return;
            
        
            default:
                break;
        }






    }
}
}