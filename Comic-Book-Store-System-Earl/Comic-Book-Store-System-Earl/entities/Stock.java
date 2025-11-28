package entities;

/**
 * Stock entity class representing inventory stock for a comic book in the Comic Book Store System.
 * 
 * This class models the inventory management aspect separately from the comic product details,
 * allowing for better separation of concerns and more flexible inventory operations.
 * 
 * @author Comic Book Store System
 * @version 1.0
 */
public class Stock {
   // Comic c;


    // Unique identifier for the stock record
    private int id;
    
    // Reference to the comic ID this stock belongs to
    private int comicId;
    
    // Current quantity in stock
    private int quantity;

   // private String comicStockName;

    /**
     * Constructor for creating a Stock instance.
     * Stock ID must equal comic ID 
     *
     * @param comicId The ID of the comic this stock belongs to (also becomes stock ID)
     * @param quantity The current quantity in stock
     */
    public Stock(int comicId, int quantity) {
        this.id = comicId;
        this.comicId = comicId;
        setQuantity(quantity);
    }

    /**
     * Gets the unique identifier of the stock record.
     * 
     * @return The stock record's ID
     */
    public int getId() { return id; }
    
    /**
     * Gets the comic ID this stock belongs to.
     *
     * @return The comic ID
     */
    public int getComicId() { return comicId; }

    /**
     * Gets the current quantity in stock.
     *
     * @return The current stock quantity
     */
    public int getQuantity() { return quantity; }
    
    /**
     * Sets the current quantity in stock with validation.
     * 
     * @param quantity The new stock quantity
     * @throws IllegalArgumentException if quantity is negative
     */
    public void setQuantity(int quantity) { 
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity; 
    }

   // public void setComicStockName(Comic )

    

    /**
     * Increases the stock quantity by the specified amount.
     * 
     * @param amount The amount to add to stock
     * @throws IllegalArgumentException if amount is negative
     */
    public void addStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative");
        }
        quantity += amount;
    }

    /**
     * Decreases the stock quantity by the specified amount.
     * 
     * @param amount The amount to remove from stock
     * @throws IllegalArgumentException if amount is negative or exceeds current stock
     */
    public void removeStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to remove cannot be negative");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + quantity);
        }
        quantity -= amount;
    }

    /**
     * Converts the stock to a string representation for file storage.
     * Uses comma-separated format: id,comicId,quantity
     * 
     * @return String representation suitable for file storage
     */
    @Override
    public String toString() {
        return id + "," + comicId + "," + quantity;
    }

    /**
     * Static factory method to create a Stock instance from a string.
     * This method parses a comma-separated string to reconstruct a Stock object.
     * Used for loading stock data from text files.
     * Note: Stock ID must equal comic ID, so the second parameter (comicId) is used for both.
     *
     * @param line A comma-separated string in the format: id,comicId,quantity
     * @return Stock object if parsing is successful, null otherwise
     */
    public static Stock fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            int id = Integer.parseInt(parts[0]);
            int comicId = Integer.parseInt(parts[1]);
            int quantity = Integer.parseInt(parts[2]);

            // Validate that ID equals comic ID
            if (id != comicId) {
                System.err.println("Warning: Stock ID (" + id + ") does not match comic ID (" + comicId +
                                 "). Using comic ID as stock ID.");
            }

            return new Stock(comicId, quantity);
        } else {
            return null; // Invalid format
        }
    }

    /**
     * Formats the stock details in a readable string for console output.
     * 
     * @return Formatted string for display purposes
     */
    public String display() {
        return "Stock ID: " + id + "  Comic ID: " + comicId + "  Quantity: " + quantity;
    }
}
