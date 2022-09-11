package entity;

import adt.DoublyLinkedList;
import adt.LinkedStack;
import adt.ListInterface;
import adt.StackInterface;
import java.time.LocalDateTime;

public class Customer extends Account implements Comparable{
    private String customerCode;
    private String customerName;
    private String customerContact;
    private double customerWallet = 10.00;
    private Order currentCart;
    private LocalDateTime joinDateTime;
    private static int noOfCustomer=0;
    private StackInterface<Order> orderHistory = new LinkedStack<>();
    private static ListInterface<Customer> customerList = new DoublyLinkedList<>();

    public Customer(){
        this.customerCode = "C000";
        this.customerName = "Sample";
        this.customerContact = "012-234 2345";
        this.joinDateTime = LocalDateTime.now();
        this.currentCart = new Order();
    }
    public Customer(Long password, String customerName,String customerContact){
        super(password);
        this.customerCode = "C"+String.format("%03d", noOfCustomer++);
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.joinDateTime = LocalDateTime.now();
        this.currentCart = new Order();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public double getCustomerWallet() {
        return customerWallet;
    }

    public Order getCurrentCart() {
        return currentCart;
    }

    public void setCurrentCart(Order currentCart) {
        this.currentCart = currentCart;
    }

    public LocalDateTime getJoinDateTime() {
        return joinDateTime;
    }

    public void setOrderHistory(StackInterface<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public LinkedStack<Order> getOrderHistory() {
        return (LinkedStack)orderHistory;
    }

    public static DoublyLinkedList<Customer> getCustomerList() {
        return (DoublyLinkedList)customerList;
    }

    public boolean addPurchasedOrder(Order newOrder){
        return orderHistory.push(newOrder);
    }
    public void clearCart(){
        currentCart = null;
    }
    public static boolean addCustomer(Customer newCustomer){
        if(customerList.add(newCustomer)){
            noOfCustomer = customerList.getNumberOfEntries();
            return true;
        }
        return false;
    }
    public static boolean removeCustomer(String customerCode){
        if(customerList.delete(customerCode)){
            noOfCustomer = customerList.getNumberOfEntries();
            return true;
        }
        return false;
    }
    public static boolean editCustomer(String customerCode, String newData, int command){
        Customer thisCustomer = retrieveCustomer(customerCode);
        if(thisCustomer == null){
            return false;
        }
        switch(command){
            case 0 : thisCustomer.setCustomerName(newData); break;
            case 1 : thisCustomer.setCustomerContact(newData); break;
            default : break;
        }
        
        return true;
    }

    public static Customer retrieveCustomer(String customerCode){
        return customerList.retrieve(customerCode);
    }
    @Override
    public int compareTo(Object anotherCustomer){
        if(anotherCustomer instanceof String){
            return customerCode.compareTo(((String)anotherCustomer));
        }
        else if(anotherCustomer instanceof Long){
            return super.compareTo(anotherCustomer);
        }else{
            return customerCode.compareTo(((Customer)anotherCustomer).customerCode);
        }
    }

    @Override
    public String toString(){
        return "Customer Code : " + customerCode
                + "\nCustomer Name : " + customerName
                + "\nContact Number : " + customerContact
                + "\n";
    }
}
