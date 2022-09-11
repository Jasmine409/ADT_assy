package main;

import adt.DeLinkedList;
import adt.LinkedStack;
import adt.StackInterface;
import entity.Account;
import entity.Customer;
import entity.Item;
import entity.Menu;
import entity.Order;
import entity.Staff;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 *
 * @author 
 */
public class CateringService {
    static Scanner input = new Scanner(System.in);
    
    public static void signup() throws InterruptedException{
        System.out.println("Sign Up");
        Scanner obj1 = new Scanner (System.in);
        System.out.print("Enter password : ");
        Long password = obj1.nextLong();
        obj1.nextLine();
        System.out.print("Enter Name (same as NRIC) : ");
        String name = obj1.nextLine();
        System.out.print("Enter contact no. : ");
        String telno = obj1.nextLine();
        
        System.out.println("Sign up as staff       [1]");
        System.out.println("Sign up as customer    [2]");
        System.out.print("Enter option : ");
        int opt = obj1.nextInt();
        switch (opt) {
            case 1:
                Account stafffacc = new Staff(password,name,telno);
                if(Account.addAccountList(stafffacc)){
                    Staff.addStaff((Staff)stafffacc);
                    mainmenu();
                }
                else{
                    System.out.println("Sign up failed. Please try again");
                    signup();
                }   break;
            case 2:
                Account custacc = new Customer(password,name,telno);
                if(Account.addAccountList(custacc)){
                    Customer.addCustomer((Customer)custacc);
                    mainmenu();
                }
                else{
                    System.out.println("Sign up failed. Please try again");
                    signup();
                }   break;
            default:
                System.out.println("Invalid option");
                signup();
                break;
        }
    }
    
    public static void login() throws InterruptedException{
        System.out.println("Login");
        Scanner obj1 = new Scanner (System.in);
        System.out.print("Enter password : ");
        Long password = obj1.nextLong();
        obj1.nextLine();
        Account thisacc = Account.retrievePassword(password);

        if (thisacc == null){
            System.out.println("Wrong Password");
            mainmenu();
        }
        else{
            System.out.println("Access granted");
            Thread.sleep(3000);
            if (thisacc instanceof Staff){
                StaffUI((Staff)thisacc);
            }else{
                CustomerUI((Customer)thisacc);
            }
        }
    }
    
    public static void mainmenu() throws InterruptedException{
        System.out.println("Main Menu");
        Scanner obj1 = new Scanner (System.in);
        System.out.println("Login    [1]");
        System.out.println("Sign up  [2]");
        System.out.println("Exit     [3]");
        System.out.print("Enter option : ");
        int opt = obj1.nextInt();
        
        switch (opt) {
            case 1:
                login();
                break;
            case 2:
                signup();
                break;
            default:
                System.exit(0);
        }
    }
    
    public static void menuMaintenanceMenu(Staff thisStaff) throws InterruptedException{
        String menucode;
        String menuname;
        double price;
         
        System.out.println("==========================");
        System.out.println("Menu Maintenance");
        System.out.println("==========================");
        System.out.println("View Menu            [1]");
        System.out.println("Add Menu             [2]");
        System.out.println("Edit Menu            [3]");
        System.out.println("Delete Menu          [4]");
        System.out.println("Exit                 [5]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                System.out.println(Menu.getMenuList());
                String enter = obj1.nextLine();
                menuMaintenanceMenu(thisStaff);
                break;
            case 2:
                System.out.print("Please enter new menu : ");
                menuname = obj1.nextLine();
                System.out.print("Please enter new menu price (RM): ");
                price = obj1.nextDouble();
                Menu.addMenu(new Item(menuname, price));
                menuMaintenanceMenu(thisStaff);
                break;
            case 3:
                System.out.print("Please enter menu code : ");
                menucode = obj1.nextLine();
                System.out.print("Please enter menu name: ");
                menuname = obj1.nextLine();
                System.out.print("Please enter menu price (RM): ");
                price = obj1.nextDouble();
                Menu.editMenu(menucode, menuname, price);
                menuMaintenanceMenu(thisStaff);
                break;
            case 4:
                System.out.print("Please enter menu code you wish deleted : ");
                menucode = obj1.nextLine();
                Menu.removeMenu(menucode);
                menuMaintenanceMenu(thisStaff);
                break;
            default:
                StaffUI(thisStaff);
                break;
        }
    }
    
    public static void orderMaintenanceMenu(Staff thisStaff) throws InterruptedException{
        System.out.println("==========================");
        System.out.println("Order Maintenance");
        System.out.println("==========================");
        System.out.println("View Order           [1]");
        System.out.println("View Deleted Order   [2]");
        System.out.println("Delete Order         [3]");
        System.out.println("Restore Order        [4]");
        System.out.println("Exit                 [5]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                {
                    Order.viewOrderQueue();
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            case 2:
                {
                    Order.viewDeletedOrder();
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            case 3:
                deleteOrderMaintenance(thisStaff);
                break;
            case 4:
                restoreOrderMaintenance(thisStaff);
                break;
            default:
                StaffUI(thisStaff);
                break;
        }
    }
    
    public static void deleteOrderMaintenance(Staff thisStaff) throws InterruptedException{
        System.out.println("==========================");
        System.out.println("Delete Order");
        System.out.println("==========================");
        System.out.println("Delete Specific Order   [1]");
        System.out.println("Delete First Order      [2]");
        System.out.println("Back                    [3]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                {
                    System.out.print("Enter the order code you wish to delete: ");
                    Order.removeSpecificOrder(obj1.nextLine());
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            case 2:
                {
                    Order.removeOrder();
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            default:
                orderMaintenanceMenu(thisStaff);
                break;
        }
    }
        
    public static void restoreOrderMaintenance(Staff thisStaff) throws InterruptedException{
        System.out.println("==========================");
        System.out.println("Restore Order");
        System.out.println("==========================");
        System.out.println("Restore Specific Order   [1]");
        System.out.println("Restore Latest Order    [2]");
        System.out.println("Back                     [3]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                {
                    System.out.print("Enter the order code you wish to restore: ");
                    Order.restoreSpecificOrder(obj1.nextLine());
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            case 2:
                {
                    Order.restoreOrder();
                    String enter = obj1.nextLine();
                    orderMaintenanceMenu(thisStaff);
                    break;
                }
            default:
                orderMaintenanceMenu(thisStaff);
                break;
        }
    }
    
    public static void staffMaintenanceMenu(Staff thisStaff) throws InterruptedException{
        System.out.println("==========================");
        System.out.println("Staff Maintenance");
        System.out.println("==========================");
        System.out.println("View All Staff       [1]");
        System.out.println("View Profile         [2]");
        System.out.println("Edit Profile         [3]");
        System.out.println("Delete Profile       [4]");
        System.out.println("Exit                 [5]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                System.out.println(Staff.getStaffList());
                String enter = obj1.nextLine();
                staffMaintenanceMenu(thisStaff);
                break;
            case 2:
                System.out.println(thisStaff.toString());
                enter = obj1.nextLine();
                staffMaintenanceMenu(thisStaff);
                break;
                
            case 3:
                editStaffProfileMaintenance(thisStaff);
                staffMaintenanceMenu(thisStaff);
                break;
            case 4:
                if (Warning() == 1){
                    Account staffacc = thisStaff;
                    if(Staff.removeStaff(thisStaff.getStaffCode())){
                        Account.removeAccountList(staffacc.getPassword());
                        System.out.print("Staff " + thisStaff.getStaffCode() + " deleted");
                        Thread.sleep(3000);
                        mainmenu();
                    }else{
                        System.out.print("Staff " + thisStaff.getStaffCode() + " not found");
                        Thread.sleep(3000);
                        staffMaintenanceMenu(thisStaff);
                    }
                }else{
                    staffMaintenanceMenu(thisStaff);
                }   
                break;
            case 5:
                StaffUI(thisStaff);
                break;
            default:
                StaffUI(thisStaff);
                break;
        }
    }
    
    public static void customerMaintenanceMenu(Staff thisStaff) throws InterruptedException{
        System.out.println("==========================");
        System.out.println("Customer Maintenance");
        System.out.println("==========================");
        System.out.println("View All Customer    [1]");
        System.out.println("Delete Customer      [2]");
        System.out.println("Exit                 [3]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                System.out.println(Customer.getCustomerList());
                String enter = obj1.nextLine();
                customerMaintenanceMenu(thisStaff);
                break;
            case 2:
                System.out.print("Please enter the customer code you wish to delete : ");
                String custcode = obj1.nextLine();
                Account custacc = Customer.retrieveCustomer(custcode);
                if (Warning() == 1){
                    if(Customer.removeCustomer(custcode)){
                        Account.removeAccountList(custacc.getPassword());
                        System.out.println("Customer " + custcode + " deleted");
                        System.out.println(Customer.getCustomerList());
                        Thread.sleep(3000);
                    }
                    else{
                        System.out.print("Customer " + custcode + " not found");
                    }
                }
                else{
                    customerMaintenanceMenu(thisStaff);
                }   break;
            default:
                StaffUI(thisStaff);
                break;
        }
    }
    
    public static int Warning(){
            System.out.println("Are you sure to delete this account?(Warning : The data will permanently gone)");
            System.out.println("YES     [1]");
            System.out.println("NO      [2]");
            System.out.print("Enter option :"); 
            Scanner obj2 = new Scanner(System.in);
            return obj2.nextInt();
    }
    
    public static void editStaffProfileMaintenance(Staff thisStaff) throws InterruptedException{
         String newdata;
         
        System.out.println("==========================");
        System.out.println("Edit Profile");
        System.out.println("==========================");
        System.out.println("Edit Name               [1]");
        System.out.println("Edit Contact Number     [2]");
        System.out.println("Back                    [3]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                System.out.print("Enter the new name: ");
                newdata = obj1.nextLine();
                Staff.editStaff(thisStaff.getStaffCode(), newdata, opt - 1);
                break;
            case 2:
                System.out.print("Enter the new contact no: ");
                newdata = obj1.nextLine();
                Staff.editStaff(thisStaff.getStaffCode(), newdata, opt - 1);
                break;
            default:
                staffMaintenanceMenu(thisStaff);
                break;
        }
    }
    
    public static void editCustomerProfileMaintenance(Customer thisCustomer) throws InterruptedException{
        String newdata;
         
        System.out.println("==========================");
        System.out.println("Edit Profile");
        System.out.println("==========================");
        System.out.println("Edit Name               [1]");
        System.out.println("Edit Contact Number     [2]");
        System.out.println("Back                    [3]");
        System.out.print("Enter option : ");
        Scanner obj1 = new Scanner (System.in);
        int opt = obj1.nextInt();
        obj1.nextLine();
        
        switch (opt) {
            case 1:
                System.out.print("Enter the new name: ");
                newdata = obj1.nextLine();
                Customer.editCustomer(thisCustomer.getCustomerCode(), newdata, opt - 1);
                break;
            case 2:
                System.out.print("Enter the new contact no: ");
                newdata = obj1.nextLine();
                Customer.editCustomer(thisCustomer.getCustomerCode(), newdata, opt - 1);
                break;
            default:
                CustomerUI(thisCustomer);
                break;
        }
    }
    
    public static void stepUpMenu(){
        Menu.addMenu(new Item("Ayam Goreng Regular",12.59));
        Menu.addMenu(new Item("Ayam Goreng Spicy",12.59));
        Menu.addMenu(new Item("Ayam Goreng Mixed",12.59));
        Menu.addMenu(new Item("Ice Blended Lychee Berry",8.96));
        Menu.addMenu(new Item("Ice Blended Strawberry",8.96));
        Menu.addMenu(new Item("Ice Blended Mango Peach",12.59));
        Menu.addMenu(new Item("Apple Pie",4.34));
        Menu.addMenu(new Item("Oreo McFlurry",12.59));
        Menu.addMenu(new Item("Nasi Lemak Set A",12.59));
        Menu.addMenu(new Item("Nasi Lemak Set B",12.59));
        Menu.addMenu(new Item("Nasi Lemak Set C",12.59));
        Menu.addMenu(new Item("Nasi Lemak Set D",12.59));
        Menu.addMenu(new Item("Creamy Butter Chicken Burger",12.59));
    }
    
    public static void viewCart(Customer thisCustomer){
        String header = ( "     ===========================================================     \n"
                        + String.format("%35s", "Cart\n")          
                        + "     ===========================================================     \n");
        if(thisCustomer.getCurrentCart() == null){
            System.out.println(header+""+String.format("%37s","Empty Cart"));
        }else{
            DeLinkedList<Item> cart = thisCustomer.getCurrentCart().getItemList();
            if(cart.isEmpty()){
                System.out.println(header+"\n\n"+String.format("%37s","Empty Cart"));

            }else{
                System.out.print(header+cart
                            +"     -----------------------------------------------------------     "
                            +"\n     Subtotal :"+String.format("%49.2f", thisCustomer.getCurrentCart().calcTotalAmount()));
            }
        }
        System.out.println( "\n     ===========================================================     \n");

    }
    
    public static void startCart(Customer thisCustomer)throws InterruptedException{
        if(thisCustomer == null){System.out.println("Customer not exist");}
        Order thisOrder = new Order(thisCustomer);

        while(true){
            try{
                

                System.out.println(   "===============================================\n"
                                    + String.format("%25s", "Menu\n")          
                                    + "===============================================\n"
                                    + String.format("%-5s","Item")
                                    + String.format("%-30s","Name")
                                    + String.format("%13s","Price(RM)\n")
                                    + "-----------------------------------------------\n"
                                    + Menu.getMenuList()
                                    + "===============================================\n"
                                    + "[-1] ------- BACK TO PREVIOUS PAGE"
                                    + "\n===============================================\n");
                System.out.print("Enter item code : ");
                String itemCode = input.nextLine();
                if(itemCode.equals("-1")){break;}
                Item menuItem = Menu.retrieveMenu(itemCode.toUpperCase());
                if(menuItem == null){throw new InputMismatchException();}
                Item thisItem = menuItem.clone();

                if(thisCustomer.getCurrentCart().retrieveItem(itemCode)!= null){
                    System.out.println("Item has already been ordered.");
                    continue;
                }
                System.out.print("Quantity(1 - 20) : ");    
                int qty = input.nextInt();input.nextLine();
                if(qty <= 0 || qty > 20){throw new InputMismatchException();}
                thisItem.setQuantity(qty);

                if(thisOrder.addItem(thisItem)){
                    System.out.println("*————————————————————————————————————*");
                    System.out.printf("| %30s x %2d added.               |\n",thisItem.getItemName() , thisItem.getQuantity());
                    System.out.println("*————————————————————————————————————*");
                }else{
                    System.out.println("Order denied. Please try again.");
                }
                System.out.printf("Subtotal : RM %.2f \n\n" , thisOrder.calcTotalAmount());
                Thread.sleep(2000);
                thisCustomer.setCurrentCart(thisOrder);
                
            }catch(InputMismatchException ex){
                input.nextLine();
                System.out.println("Make sure your input is VALID");
                Thread.sleep(2000);
            }
            
        }
        System.out.println("Back to previous page ...");
        Thread.sleep(2000);
    }
    
    public static void removeCart(Customer thisCustomer)throws InterruptedException{   
        System.out.print("               Enter item code : ");
        String itemCode = input.nextLine();

        Order thisOrder = thisCustomer.getCurrentCart();
        if(thisOrder != null){
            Item thisItem = thisOrder.retrieveItem(itemCode);

            if(thisItem==null){
                System.out.println("               No such item in cart");
            }else{
                System.out.println("               Item Name  : " + thisItem.getItemName()
                                +  "\n               Unit Price : " + thisItem.getUnitPrice()
                                +  "\n               Quantity   : " + thisItem.getQuantity());
                System.out.print("     Confirm remove this item ? [Y] for Yes : ");
                if(input.nextLine().toUpperCase().equals("Y")){  
                    if(thisOrder.removeItem(itemCode)){
                        System.out.println("*————————————————————————————————————*");
                        System.out.printf("| %30s x %2d removeed.            |\n", 
                                        thisOrder.retrieveItem(itemCode).getItemName() , 
                                        thisOrder.retrieveItem(itemCode).getQuantity());
                        System.out.println("*————————————————————————————————————*");
                    }else{
                        System.out.println("               Request denied. Please try again.");
                    }
                }
            }
        }else{System.out.println("               Your cart is empty.");}
        Thread.sleep(2000);
    }
    
    public static void editCart(Customer thisCustomer)throws InterruptedException{
        try{
            System.out.print("               Enter item code : ");
            String itemCode = input.nextLine();
            Order thisOrder = thisCustomer.getCurrentCart();

            if(thisOrder != null){
                Item thisItem = thisOrder.retrieveItem(itemCode);
                if(thisItem==null){
                    System.out.println("               No such item in cart");
                }else{
                    System.out.println("               Item Name  : " + thisItem.getItemName()
                                    +  "\n               Unit Price : " + thisItem.getUnitPrice()
                                    +  "\n               Quantity   : " + thisItem.getQuantity());

                    System.out.print("               Enter new quantity (1-20) : ");
                    int qty = input.nextInt();
                    if(qty == 0){
                        if(thisOrder.removeItem(itemCode)){
                            System.out.println("*————————————————————————————————————*");
                            System.out.printf("| %30s x %2d removeed.            |\n", 
                                            thisOrder.retrieveItem(itemCode).getItemName() , 
                                            thisOrder.retrieveItem(itemCode).getQuantity());
                            System.out.println("*————————————————————————————————————*");
                        }else{
                            System.out.println("               Request denied. Please try again.");
                        }
                    }else if(qty <= 20 && qty > 0){
                        if(thisOrder.editItem(itemCode, qty)){
                            System.out.println("               Item Name    : " + thisItem.getItemName()
                                            +  "\n               Unit Price   : " + thisItem.getUnitPrice()
                                            +  "\n               New Quantity : " + thisItem.getQuantity());
                        }else{
                            System.out.println("               Request denied. Please try again.");
                        }
                    }else{
                            System.out.println("               Request denied. Invalid quantity.");
                    }
                }
            }else{System.out.println("               Your cart is empty.");}
        }catch(InputMismatchException ex){
                input.nextLine(); 
                System.out.println("Make sure your input is VALID");
        }
        Thread.sleep(2000);
    }
    
    public static void payment(Customer thisCustomer)throws InterruptedException{
        Order thisOrder = thisCustomer.getCurrentCart();
        if(thisOrder != null){
            viewCart(thisCustomer);
            String str = "Subtotal = RM " + String.format("%.2f",thisOrder.calcTotalAmount()) + "\nConfirm Transfer ? ";
            int input = JOptionPane.showConfirmDialog(null,str,"Virtual Wallet",JOptionPane.YES_NO_OPTION);
            System.out.println("               Payment proccessing ...");
            Thread.sleep(2000);
            if(input == JOptionPane.YES_OPTION){
                if(Order.addOrder(thisOrder) && thisCustomer.addPurchasedOrder(thisOrder)){
                    System.out.println("               Your order has been received.");
                }else{
                    System.out.println("               Some error occur");
                }
                thisCustomer.clearCart();
            }
        }else{System.out.println("               Your cast is empty");}
        Thread.sleep(2000);
    }
    
    public static void CustomerUI(Customer thisCustomer)throws InterruptedException{
        boolean stay = true;
        while(stay){
            try{

                System.out.println(   "*********************************************************************\n"
                                    + String.format("%40s", "Customer UI\n")
                                    + "*********************************************************************\n"
                                    + "Welcome, " + thisCustomer.getCustomerName());


                System.out.println("     ~~~~~~~~~~~~~~~~~~~~~ LET'S HAVE A TRY ~~~~~~~~~~~~~~~~~~");
                Menu.viewNewItemDeque();
                System.out.println("     ~~~~~~~~~~~~~~~~~~~~~ LET'S HAVE A TRY ~~~~~~~~~~~~~~~~~~");

                viewCart(thisCustomer);

                System.out.println("               *—————————————————————*\n"
                                +  "               |  Add Item ----------------- [1]  |\n"
                                +  "               |  Remove Item -------------- [2]  |\n"
                                +  "               |  Edit Item ---------------- [3]  |\n"
                                +  "               |  Check Out ---------------- [4]  |\n"
                                +  "               |  Clear Cart --------------- [5]  |\n"
                                +  "               |  View History ------------- [6]  |\n"
                                +  "               |  Edit Profile ------------- [7]  |\n"
                                +  "               |  Sign Out ----------------- [8]  |\n"
                                +  "               *—————————————————————*\n");
                System.out.print("               Enter your option : ");
                int option = input.nextInt();
                input.nextLine(); 

                switch(option){
                    case 1 : startCart(thisCustomer); break;
                    case 2 : removeCart(thisCustomer); break;
                    case 3 : editCart(thisCustomer); break;
                    case 4 : payment(thisCustomer); break;
                    case 5 : thisCustomer.clearCart(); break;
                    case 6 : {  StackInterface<Order> history = thisCustomer.getOrderHistory();
                                StackInterface<Order> tempHistory = new LinkedStack<>();
                                for(int i=0;i<=history.size();i++){
                                    Order order = history.pop();
                                    System.out.println(order);
                                    tempHistory.push(order);
                                }   
                                thisCustomer.setOrderHistory(tempHistory);                
                                Thread.sleep(2000);
                                break;
                                }
                    case 7 : editCustomerProfileMaintenance(thisCustomer); break;
                    case 8 : mainmenu(); break;
                    default : throw new InputMismatchException();
                }
            }catch(InputMismatchException ex){
input.nextLine(); 
                    System.out.println("               Make sure your input is VALID");
                    Thread.sleep(2000);
            }
        }
        System.out.println("               Back to previous page ...");
        Thread.sleep(2000);
}
    
    public static void StaffUI(Staff thisStaff) throws InterruptedException{
        boolean stay = true;
        while(stay){
            try{

                System.out.println(   "*********************************************************************\n"
                                    + String.format("%40s", "Staff UI\n")
                                    + "*********************************************************************\n"
                                    + "Welcome, " + thisStaff.getStaffName());

                System.out.println("               *———————————————————————————*\n"
                                +  "               |  Customer Maintenance ----------------- [1]  |\n"
                                +  "               |  Staff Maintenance -------------------- [2]  |\n"
                                +  "               |  Menu Maintenance --------------------- [3]  |\n"
                                +  "               |  Order Maintenance -------------------- [4]  |\n"
                                +  "               |  Sign Out ----------------------------- [5]  |\n"
                                +  "               *———————————————————————————*\n");
                System.out.print("               Enter your option : ");
                int option = input.nextInt();
                input.nextLine(); 

                switch(option){
                    case 1 : customerMaintenanceMenu(thisStaff); break;
                    case 2 : staffMaintenanceMenu(thisStaff); break;
                    case 3 : menuMaintenanceMenu(thisStaff); break;
                    case 4 : orderMaintenanceMenu(thisStaff); break;
                    case 5 : mainmenu(); break;
                    default : throw new InputMismatchException();
                }
            }catch(InputMismatchException ex){
                    input.nextLine(); 
                    System.out.println("               Make sure your input is VALID");
                    Thread.sleep(2000);
            }
        }
        System.out.println("               Back to previous page ...");
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException{     
        stepUpMenu();
        mainmenu();
    }
}
