package com.TrainProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class train {

    public static void main(String[] args) {
//
        // write your code here
        Scanner input = new Scanner(System.in); //calling a scanner build in function
        coach.Initialise();//initialising Coach array
        //menu printing
        System.out.println("---------------------------------");
        System.out.println(" * * *    Train Program    * * * ");
        System.out.println("---------------------------------");
        System.out.println("Select an option to proceed");
        System.out.println("'V' : View all coaches ");
        System.out.println("'A' : Add a customer to coach ");
        System.out.println("'E' : Display empty coaches ");
        System.out.println("'D' : Delete customer from coach ");
        System.out.println("'F' : Find coach from customer name ");
        System.out.println("'S' : Store program data into file ");
        System.out.println("'L' : Load program data from file");
        System.out.println("'O' : View guests Ordered alphabetically by name ");
        System.out.println("'W' : Exit Program");


        do {
            try { //error handling
                System.out.print("\nEnter your option: ");
                coach.option = input.next().toUpperCase(Locale.ROOT);//getting menu option and convert  it to upper case
                switch (coach.option) {
                    case "V" -> coach.ViewCoaches(); //calling view Coach method
                    case "A" -> coach.AddCustomer(input); //calling add  customer method
                    case "E" -> coach.EmptyCoaches(); //calling empty Coach method
                    case "D" -> coach.DeleteCustomer(input); //calling delete customer method
                    case "F" -> coach.FindCustomer(input); //calling find customer method
                    case "S" -> coach.StoreFile(); //calling store file method
                    case "L" -> coach.LoadFile(); //calling load file method
                    case "O" -> coach.OrderedList(); //calling ordered list method

                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("!!!Error!!!");//print the error message
            }
        }
        while (!coach.option.equals("W")); // set the while loop condition


    }

}

class coach{
    public static person[] Person = new person[2];
    public static person[]queue=new person[10];
    public  static int numberOfGuest;
    public  static String fName;
    public  static String sName;
    public static int ticketNumber;
    public static person pass;
    public static String option;
    public static int totalCoach=10;
    public static int coachNumber;

    public static void details() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of guest: ");
        numberOfGuest = input.nextInt();       //getting first name from customer
        System.out.println("Enter customer first name to  coach: ");
        fName = input.next().toUpperCase(Locale.ROOT);       //getting first name from customer
        System.out.println("Enter customer sur name to  coach: ");
        sName = input.next().toUpperCase(Locale.ROOT);      //getting surname from customer
        System.out.println("Enter " + fName + " " + sName + "'s Ticket number: ");
        ticketNumber = input.nextInt(); //getting expense from customer
        pass = new person(numberOfGuest, fName, sName, ticketNumber); //
    }
    public static void Initialise(){
        for(int x=0;x< Person.length;x++){
            Person[x]=new person(0,"e","e",0); //initialise with Coach class
        }
    }
    public static void ViewCoaches(){
        for(int x=0;x< Person.length;x++){
            if(Person[x].numberOfGuest==0){
                System.out.println("coach "+(x+1)+" is Empty");

            }else if(Person[x].numberOfGuest!=0){
                System.out.println("coach "+(x+1)+" is occupied."+Person[x].fullDetails());
            }
        }
    }
    public static void AddCustomer(Scanner input) {
        System.out.println("Enter coach number(1-"+Person.length+"): ");
        coachNumber = input.nextInt();
        if (coachNumber > Person.length || coachNumber < 1) { //set interval for Coach number
            System.out.println("The coach number you added is wrong try again");
        }
        else if(Person[coachNumber-1].numberOfGuest==0){
            details();
            System.out.println("This customer add in to the coach");
            Person[coachNumber - 1]=pass;
        }
        else {
            int noEmpty=0;
            for(int x=0;x< Person.length;x++){
                if(Person[x].numberOfGuest!=0)
                    noEmpty++;
            }
            if(noEmpty!= Person.length){
                System.out.println("This coach number "+coachNumber+" is already occupied \n you can try " +
                        "with another caoch ");
            }
            else {
                details();
                for (int x=0;x< queue.length;x++){
                    if(queue[x]==null){
                        queue[x]=pass;
                        break;
                    }
                }
                System.out.println("Coach is full \n" +
                        "This customer added to the queue");
            }
        }
    }
    public static void EmptyCoaches(){
        int emptyCounter=0;
        for(int x=0;x< Person.length;x++){
            if(Person[x].numberOfGuest==0) {
                System.out.println("Coach " + (x + 1) + " is Empty");
                emptyCounter++;

            }

        }
        System.out.println("");
        System.out.println("There are "+emptyCounter+" coaches empty");
    }
    public static void DeleteCustomer(Scanner input){
        int deleteCounter=0;
        System.out.println("enter passenger first Name: "); //getting first name from customer
        String fName= input.next().toUpperCase(Locale.ROOT);
        System.out.println("enter passenger sur Name: "); //getting surname from customer
        String sName= input.next().toUpperCase(Locale.ROOT);
        for(int x=0;x< Person.length;x++){ //this is for each loop
            if (Person[x].firstName.equals(fName) && Person[x].surName.equals(sName)) { //checking if first name and surName are correct
                if(queue[0]==null){
                    Person[x].numberOfGuest=0;
                    Person[x].firstName="e";
                    Person[x].surName="e";
                    Person[x].ticketNumber=0;
                    System.out.println("Customer deleted successfully");
                    deleteCounter = 1;

                }
                else {
                    Person[x]=queue[0];
                    deleteCounter = 1;
                    System.out.println("Customer deleted successfully\n" +
                            "and "+queue[0].fullName()+" added to the Coach number "+(x+1)+" automatically");
                    for (int l = 0; l < queue.length-1; l++) {
                        queue[l] = queue[l + 1]; //shifting queue customers
                    }
                    queue[queue.length-1] = null;
                    break;
                }
            }
        }
        if(deleteCounter==0){
            System.out.println("The first Name or sur Name you entered is invalid");
        }

    }

    public static void FindCustomer(Scanner input){
        int count=0;
        System.out.println("enter passenger first Name: "); //getting first name from customer
        String fName= input.next().toUpperCase(Locale.ROOT);
        System.out.println("enter passenger sur Name: "); //getting surname from customer
        String sName= input.next().toUpperCase(Locale.ROOT);
        for(int x=0;x< Person.length;x++){
            if(Person[x].firstName.equals(fName) && Person[x].surName.equals(sName)){ //checking if first name and surName are correct
                System.out.println(Person[x].firstName+" "+Person[x].surName+" is in coach number  "+(x+1));
                count=1;
                break;
            }
        }
        if(count!=1)
            System.out.println("The first Name or sur Name you entered is invalid");

    }

    public static void StoreFile(){
        try {
            File File=new File("Task2_Coach.txt"); // creating file
            FileWriter myWriter=new FileWriter("Task2_Coach.txt");
            for(int x=0;x< Person.length;x++){

                if(Person[x].numberOfGuest==0){
                    myWriter.write(" Coach "+(x+1)+" is Empty\n"); //write from coach array

                }else if(Person[x].numberOfGuest!=0){
                    myWriter.write(Person[x].fullDetails()+"\n");
                }
            }
            myWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred");
        }
        System.out.println("Coaches data stored successfully");
    }

    public static void LoadFile(){
        try {  //file not found error handling
            File MyObject = new File("Task2_Coach.txt");
            Scanner myReader = new Scanner(MyObject);
            while(myReader.hasNextLine()){
                String data= myReader.nextLine();
                System.out.println(data);
            }
        }catch(FileNotFoundException e){
            System.out.println("there is no file");}
    }

    public static void OrderedList() {
        person[] copyCoach = new person[Person.length];
        int size=copyCoach.length;
        System.arraycopy(Person, 0, copyCoach, 0, size);

        for(int x=0;x< size;x++){
            for (int y=x+1;y<size;y++){
                if(copyCoach[x].numberOfGuest!=0 && copyCoach[y].numberOfGuest!=0){ //if current index and next index are not null
                    //compares each element of the array to all the remaining elements
                    if(copyCoach[x].firstName.compareTo(copyCoach[y].firstName)>0){
                        person temp =copyCoach[x] ;
                        copyCoach[x]=copyCoach[y];
                        copyCoach[y] = temp;
                    }
                }
            }
        }
        for(int x=0;x< size;x++){
            if(copyCoach[x].numberOfGuest!=0){
                System.out.println(copyCoach[x].fullDetails());
            }
        }
    }


}
class person{
    public int numberOfGuest;
    public String firstName;
    public String surName;
    public int ticketNumber;

    public person (int numberOfGuest, String firstName,String surName,int ticketNumber){
        this.numberOfGuest=numberOfGuest;
        this.firstName=firstName;
        this.surName=surName;
        this.ticketNumber=ticketNumber;
    }


    public String fullDetails(){
        return " "+firstName+" "+surName+"'s Ticket number is "+ticketNumber+" and total number of member in coach is "+numberOfGuest+".";
    }
    public String fullName(){
        return firstName+" "+surName;
    }
}
