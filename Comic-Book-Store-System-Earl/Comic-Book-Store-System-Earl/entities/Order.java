package entities;


// ORDER ENTITY CLASS REPRESENTS A CART ITEM IN A CART<>
//This class models the core product of the cart item stored inside a cart, containing attributes
//for identification, description, and total price. CART management is now
//handled separately by the oRDER entity and pURCHASEManager.
public class Order {
    private Comic comic; //calls on Comic attribute
    private int quantity; //Amount of how many of the comic the customer would like to order
    private double comicTotal; //Total of of Comic price and order quantity

    //Constructor For the Order Entity
    public Order (Comic comic, int quantity) {
        this.comic = comic;
        setQuantity(quantity);
    }
    //getters
    public int getQuantity() {
        return quantity;
    }


    public Comic getComic() {
        return comic;
    }
    
    public double getComicTotal() {
        return comicTotal = quantity * comic.getPrice(); //this returns the total 
    }
    //SETTERS
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Override //Overrides conversion from Object to String
    public String toString() {
        return  comic + " Quantity: " + quantity;
    }

}
