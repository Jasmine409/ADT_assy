/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.DeLinkedList;
import adt.DeListInterface;
import java.util.Objects;

/**
 *
 * @author JASON
 */
public class Account implements Comparable{
    private Long password;
    private static DeListInterface<Account> accountList = new DeLinkedList<>();
    
    public Account(){};
    
    public Account(Long password){
        this.password = password;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public static DeListInterface<Account> getAccountList() {
        return accountList;
    }

    public static void setAccountList(DeListInterface<Account> accountList) {
        Account.accountList = accountList;
    }
    
    public static boolean addAccountList(Account newAcc) throws InterruptedException{
        if(accountList.contains(newAcc)){
            System.out.println("Password is already existed");
            Thread.sleep(3000);
            return false;
        }
        else{
            accountList.insertLast(newAcc);
            return true;
        }
    }
    
    public static boolean removeAccountList(Long password){
        return accountList.delete(password);
    }
    
    public static Account retrievePassword(Long password){
        //System.out.print(password);

        return accountList.retrieve(password);
    }
    
    
    @Override
    public int compareTo(Object anotherPassword){
        Long password1 = (Long)anotherPassword;
        return password.compareTo(password1);
    }
    
    @Override
    public boolean equals(Object obj) {
        final Account other = (Account) obj;
        return Objects.equals(this.password, other.password);
    }

    @Override
    public String toString() {
        return "Account{" + "password=" + password + '}';
    }
    
    
    
}
