package dto;

public class Car {
    public int registrationNo;
    public String color;
    public int allotedSlot;

    public Car(int registration_no,String color,int allotedSlot) { // constructor initialization
        this.registrationNo=registration_no;
        this.color=color;
        this.allotedSlot=allotedSlot;
    }

    public int getRegistrationNo(){
        return this.registrationNo;
    }

    public String getColor(){
        return this.color;
    }

    public int getSlot(){
        return this.allotedSlot;
    }
}
