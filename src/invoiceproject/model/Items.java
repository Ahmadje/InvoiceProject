
package invoiceproject.model;


public class Items {
    private int num;
    private String item;
    private double price;
    private int count;
    private Invoice invoice;

    public Items() {
    }

        
    public Items(int num, String item, double price, int count, Invoice invoice) {
        this.num = num;
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
        
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getItems() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getItemTotal(){
    return price*count;
    }

    @Override
    public String toString() {
        return "Items{" + "num=" + num + ", item=" + item + ", price=" + price + ", count=" + count + '}';
    }
    
    public String getAsCSV() {
        return num + "," + item + "," + price + "," + count;
    }
}
