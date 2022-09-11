/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import adt.DeLinkedList;
import adt.DeListInterface;
import adt.LinkedQueue;
import adt.LinkedStack;
import adt.QueueInterface;
import adt.StackInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Fong Suk Dien
 */
public class Order implements Comparable{
    private String orderCode;
    private Customer customer;
    private LocalDateTime orderDateTime;
    private DeListInterface<Item> itemList = new DeLinkedList<>();
    private int noOfOrderItem = 0;
    private double totalAmount;

    private static int noOfOrder = 0;
    private static QueueInterface<Order> orderQueue = new LinkedQueue<>();
    private static QueueInterface<Order> tempqueue = new LinkedQueue<>();
    private static QueueInterface<Order> newqueue = new LinkedQueue<>();
    private static StackInterface<Order> deletedstack = new LinkedStack<>();
    private static StackInterface<Order> tempstack = new LinkedStack<>();

    // Sample 
    public Order(){}
    public Order(Customer customer){
        this.orderCode = "OD"+String.format("%03d", noOfOrder++);
        this.customer = customer;
//        this.itemList = ItemList;
//        this.noOfOrderItem = ItemList.getNumberOfEntries();
        this.orderDateTime = LocalDateTime.now();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public DeLinkedList<Item> getItemList() {
        return (DeLinkedList)itemList;
    }

    public void setItemList(DeLinkedList<Item> ItemList) {
        this.itemList = ItemList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public static DeLinkedList<Order> getOrderList() {
        return (DeLinkedList)orderQueue;
    }
    public double calcTotalAmount(){
        totalAmount = 0;
        for(int i = 1 ;i <= noOfOrderItem; i++){
            totalAmount += itemList.retrieveByPosition(i).calcItemPrice();
        }
        return totalAmount;
    }

    public boolean addItem(Item newItem){
        if(itemList.insertLast(newItem)){
            noOfOrderItem = itemList.size();
            return true;
        }
        return false;
    }

    public boolean removeItem(String itemCode){
        if(itemList.delete(itemCode)){
            noOfOrderItem = itemList.size();
            return true;
        }
        return false;
    }

    public boolean editItem(String itemCode,int quantity){
        Item thisItem = retrieveItem(itemCode);
        if(thisItem == null){
            return false;
        }
        thisItem.setQuantity(quantity);
        return true;
    }
    
    public Item retrieveItem(String itemCode){
        return itemList.retrieve(itemCode);
    }
    
    public static boolean addOrder(Order newOrder){
        if(orderQueue.enqueue(newOrder)){
            return true;
        }
        return false;
    }
    
    public static void removeOrder(){
        deletedstack.push(orderQueue.dequeue());
        System.out.println("Order Removed");
    }
    
    public static void removeSpecificOrder(String orderCode){
        tempqueue.clear();
        newqueue.clear();
        
        boolean found = orderQueue.contains(orderCode);
        while (found){
            Order temp = orderQueue.getFront();
            if (temp.orderCode.compareTo(orderCode) != 0){
                tempqueue.enqueue(orderQueue.dequeue());
            }else{
                deletedstack.push(orderQueue.dequeue());
                System.out.println("Order Removed");
                break;
            } 
        }
        while (!tempqueue.isEmpty()){
            newqueue.enqueue(tempqueue.dequeue());
        }
        while (!orderQueue.isEmpty()){
            newqueue.enqueue(orderQueue.dequeue());
        }
        while(!newqueue.isEmpty()){
            orderQueue.enqueue(newqueue.dequeue());
        }
        
        if(!found){
            System.out.println("There's no such order exist in this queue");
        }
    }
    
    public static void restoreOrder(){
        if(deletedstack.isEmpty()){
            System.out.println("There's no any deleted order yet.");
        }else{
            orderQueue.enqueue(deletedstack.pop());
            System.out.println("Order restored");
        }
    }
    
    public static void restoreSpecificOrder(String orderCode){
        tempstack.clear();
        
        if(deletedstack.isEmpty()){
            System.out.println("There's no any deleted order yet.");
        }else{
            Order temp = deletedstack.pop();
            while(temp != null){
                if(temp.orderCode.compareTo(orderCode) != 0){
                    tempstack.push(temp);
                    temp = deletedstack.pop();
                }
                else{
                    orderQueue.enqueue(deletedstack.pop());
                    System.out.println("Order restored");
                    break;
                }
            }
            while(!tempstack.isEmpty()){
                deletedstack.push(tempstack.pop());
            }
        }
    }
    
    public static void viewOrderQueue(){
        tempqueue.clear();
        Order temp = orderQueue.getFront();
        
        if(temp == null){
            System.out.println("There's no any order yet.");
        }
        
        while(!orderQueue.isEmpty()){
            temp = orderQueue.dequeue();
            System.out.println(temp.toString());
            tempqueue.enqueue(temp);
        }
        while(!tempqueue.isEmpty()){
            orderQueue.enqueue(tempqueue.dequeue());
        }
    }
    
    public static void viewDeletedOrder(){
        tempstack.clear();
        if(deletedstack.isEmpty()){
            System.out.println("There's no any deleted order yet.");
        }else{
            while(!deletedstack.isEmpty()){
                Order temp = deletedstack.pop();
                System.out.println(temp.toString());
                tempstack.push(temp);
            }
            while(!tempstack.isEmpty()){
                deletedstack.push(tempstack.pop());
            }
        }
    }

    @Override
    public int compareTo(Object anotherOrder){
        if(anotherOrder instanceof String){
            return orderCode.compareTo(((String)anotherOrder));
        }
        return orderCode.compareTo(((Order)anotherOrder).orderCode);
    }
    @Override
    public String toString(){
        String str = "";
        str += "\nOrder Code : " + orderCode 
            + "\nDate : " + orderDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            + "\nCustomer Name : " + customer.getCustomerName()
            + "\nItem List : \n"
            + "=====================================================================\n"
            + String.format("%-5s","Item")
            + String.format("%-30s","Name")
            + String.format("%12s","Unit Price")
            + String.format("%5s","Qty")
            + String.format("%14s","Amount(RM)\n");
        for(int i = 1;i <= noOfOrderItem; i++){
            str += itemList.retrieveByPosition(i) + "\n";
        }
        str += "====================================================================="
            + "\nTotal Amount : " + String.format("%.2f", calcTotalAmount());
        return str;
    }
}

