package entity;

import adt.ArrayDeque;
import adt.DeLinkedList;
import adt.DequeInterface;
import adt.DeListInterface;



/**
 *
 * @author Fong Suk Dien
 */
public class Menu {
    private static int noOfMenuItem = 0; 
    private static DeListInterface<Item> menuList = new DeLinkedList<>();
    private static DequeInterface<Item> newItemDeque = new ArrayDeque<>();

    public Menu(){}

    public static int getNoOfMenuItem(){
        return noOfMenuItem;
    }

    public static DeLinkedList<Item> getMenuList() {
        return (DeLinkedList)menuList;
    }

    public static void setMenuList(DeLinkedList<Item> menuList) {
        Menu.menuList = menuList;
    }

    public static DequeInterface<Item> getNewItemDeque() {
        return (DequeInterface)newItemDeque;
    }

    public static void setNewItemList(DequeInterface<Item> newItemDeque) {
        Menu.newItemDeque = newItemDeque;
    }

    public static boolean addMenu(Item newItem){
        if(newItemDeque.isFull()){
            newItemDeque.deleteLast();
        }
        newItemDeque.insertFirst(newItem);
        boolean result = menuList.insertLast(newItem);
        noOfMenuItem = menuList.size();
        return result;
    }
    public static boolean removeMenu(String itemCode){
        if(menuList.delete(itemCode)){
            noOfMenuItem = menuList.size();
            if(newItemDeque.contains(new Item(itemCode,null,0.00))){
                int dequeCapacity = newItemDeque.capacity();
                for(int i=noOfMenuItem;i>(noOfMenuItem-dequeCapacity);i--){
                    if(newItemDeque.isFull()){
                        newItemDeque.deleteLast();
                    }
                    newItemDeque.insertFirst(menuList.retrieveByPosition(i));
                }
            }
            return true;
        }
        return false;
    }
    public static boolean editMenu(String itemCode, String itemName, double unitPrice){
        Item thisItem = retrieveMenu(itemCode);
        if(thisItem == null){
            Item newItem = new Item(itemName, unitPrice);
            addMenu(newItem);
        }else{
            thisItem.setItemName(itemName);
            thisItem.setUnitPrice(unitPrice);
        }
        return true;
    }
    public static Item retrieveMenu(String itemCode){
        return menuList.retrieve(itemCode);
    }
    public static void viewNewItemDeque(){
        ArrayDeque<Item> newDeque = new ArrayDeque<>();
        while(!newItemDeque.isEmpty()){
            Item deletedItem = newItemDeque.deleteFirst();
            System.out.println("               "+deletedItem);
            newDeque.insertLast(deletedItem);
        }
        while(!newDeque.isEmpty()){
            newItemDeque.insertLast(newDeque.deleteFirst());
        }
    }
    public String toString(){
        String str 
            = "=====================================================================\n"
            + String.format("%-5s","Item")
            + String.format("%-30s","Name")
            + String.format("%12s","Unit Price");
        for(int i=0;i<noOfMenuItem;i++){
            str += menuList.retrieveByPosition(i) + "\n";
        }
        return str;
    }
}

//    public Menu(DeLinkedList<Item> MenuList){
//        this.menuList = MenuList;
//        this.noOfMenuItem = MenuList.size();
//        for(int i=noOfMenuItem-1;i>=noOfMenuItem-newItemDeque.size();i--){
//            newItemDeque.insertLast(menuList.retrieveByPosition(i));
//        }
//    }