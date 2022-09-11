package entity;


public class Item implements Comparable, Cloneable{
    private String itemCode;
    private String itemName;
    private double unitPrice;
    private int quantity = 0;
    private static int noOfItem = 0;

    public Item(String itemName, double unitPrice){
        this.itemCode = "I" + String.format("%03d",++noOfItem);
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }
    public Item(String itemCode,String itemName, double unitPrice){
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double calcItemPrice(){
        return unitPrice * quantity;
    }

    public String toString(){
        return  String.format("%-5s",itemCode)
        + String.format("%-30s",itemName)
        + String.format("%12.2f",unitPrice)
        + (quantity==0?"":String.format("%5d",quantity))
        + (quantity==0?"":String.format("%12.2f",calcItemPrice()));
    }
    public int compareTo(Object anotherItem){
        if(anotherItem instanceof String){
            return itemCode.compareTo(((String)anotherItem));
        }
        return itemCode.compareTo(((Item)anotherItem).itemCode);
    }

    public boolean equals(Object anotherItem){
        return ((Item)anotherItem).getItemCode().equals(itemCode);
    }
    @Override
    public Item clone(){
        return new Item(itemCode,itemName,unitPrice);
    }
}
