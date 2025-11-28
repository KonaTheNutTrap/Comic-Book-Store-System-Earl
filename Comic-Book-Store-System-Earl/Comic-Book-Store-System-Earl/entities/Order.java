package entities;



public class Order {
    private Comic comic;
    private int quantity;
    private double comicTotal;


    public Order (Comic comic, int quantity) {
        this.comic = comic;
        setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }


    public Comic getComic() {
        return comic;
    }

    public double getComicTotal() {
        return comicTotal = quantity * comic.getPrice();
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Override
    public String toString() {
        return "Order{" + "comic=" + comic + ", quantity=" + quantity + ",}";
    }

}
