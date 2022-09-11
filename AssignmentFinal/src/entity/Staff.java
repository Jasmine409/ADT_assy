package entity;

import adt.DeLinkedList;

public class Staff extends Account implements Comparable{
    private String staffCode;
    private String staffName;
    private String staffContact;
    private static int noOfStaff = 0;
    private static DeLinkedList<Staff> staffList = new DeLinkedList<>();

    public Staff(){
        this.staffCode = "S000";
        this.staffName = "Sample";
        this.staffContact = "012-234 2345";
    }
    public Staff(Long password, String staffName,String staffContact){
        super(password);
        this.staffCode = "S"+String.format("%03d", noOfStaff++);
        this.staffName = staffName;
        this.staffContact = staffContact;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffContact(String staffContact) {
        this.staffContact = staffContact;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffContact() {
        return staffContact;
    }

    public static int getNoOfStaff() {
        return noOfStaff;
    }

    public static DeLinkedList<Staff> getStaffList() {
        return (DeLinkedList)staffList;
    }

    public static boolean addStaff(Staff newStaff){
        if(staffList.insertLast(newStaff)){
            noOfStaff = staffList.size();
            return true;
        }
        return false;
    }

    public static boolean removeStaff(String staffCode){
        if(staffList.delete(staffCode)){
            noOfStaff = staffList.size();
            return true;
        }
        return false;
    }

    public static boolean editStaff(String staffCode, String newData, int command){
        Staff thisStaff = staffList.retrieve(staffCode);
        if(thisStaff == null){return false;}
        switch(command){
            case 0 : thisStaff.setStaffName(newData);break;
            case 1 : thisStaff.setStaffContact(newData);break;
            default : break;
        }
        return true;
    }
    public static Staff retrieveStaff(String staffCode){
        return staffList.retrieve(staffCode);
    }

    @Override
    public int compareTo(Object anotherStaff){
        if(anotherStaff instanceof String){
            return staffCode.compareTo(((String)anotherStaff));
        }
        else if(anotherStaff instanceof Long){
            return super.compareTo(anotherStaff);
        }
        return staffCode.compareTo(((Staff)anotherStaff).staffCode);
    }

    @Override
    public String toString(){
        return "\nStaff Code : " + staffCode
                + "\nStaff Name : " + staffName
                + "\nContact Number : " + staffContact
                + "\n";
    }
}
