package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class TrainProgram {
    public static String[] Coaches=new String[10]; //creating Coach array with 10 indexes
    // creating variable
    public static int CoachNumber;
    public static String CustomerName;
    public static String option;


    public static void main(String[] args) {
        try { //error checking to file handling
            File MyObject = new File("CoachDetails.txt"); //creating new file
            if (MyObject.createNewFile()) {
                System.out.println("File created: " + MyObject.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        Scanner input = new Scanner(System.in); //creating scanner build in function


        initialise(Coaches); //initialising Coach array

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
                System.out.println("\nEnter your option: ");
                option = input.next().toUpperCase(Locale.ROOT); //getting menu option and convert  it to upper case
                switch (option) {
                    case "V" -> ViewCoaches();//calling view Coach method
                    case "A" -> AddCustomer();//calling add  customer method
                    case "E" -> EmptyCoaches();//calling empty Coach method
                    case "D" -> DeleteCustomer();//calling delete customer method
                    case "F" -> FindCustomer();//calling find customer method
                    case "S" -> StoreFile();//calling store file method
                    case "L" -> LoadFile();//calling load file method
                    case "O" -> OrderedList();//calling ordered list method
                }
            } catch (Exception e) {
                System.out.println(e);  //to show what type of error
                System.out.println("!!!Error!!!"); //print the error message
            }
        }

        while (!option.equals("W"));// set the while loop condition
    }
    private static void initialise(String[] coachRef) {
        for (int x = 0; x < 10; x++ )
            coachRef[x] = null; //initialising all index with empty

    }
    private static void ViewCoaches(){
        for (int x = 0; x < 10; x++ )
            if ((Coaches[x]) == null){
                System.out.println("Coach " + (x+1) +" is EMPTY" );
            }
            else{
                System.out.println("Coach " + (x+1) + " is occupied by " + Coaches[x]);
            }
    }

    private static void EmptyCoaches() {
        int emptyCounter = 0;
        for (int x = 0; x < 10; x++) {
            if ((Coaches[x]) == null) {
                System.out.println("Coach " + (x + 1) + " is EMPTY");
                emptyCounter++;
            }
        }
        if (emptyCounter == 0) {
            System.out.println("All Coaches are occupied");
        }
        else {
            System.out.println("There are " + emptyCounter + " empty coaches. ");
        }
    }

    private static void AddCustomer(){
        Scanner input=new Scanner(System.in);
        System.out.println("What Coach number do you want? (1-10)");
        CoachNumber=input.nextInt();
        if (Coaches[CoachNumber-1] != null){
            System.out.println("Coach number " + CoachNumber+" is already occupied");

        }
        else{
            System.out.print("Enter your name: ");
            CustomerName=input.next().toUpperCase(Locale.ROOT);
            Coaches[CoachNumber-1]=CustomerName;
            System.out.println("Customer added successfully");

        }
    }
    private static void DeleteCustomer(){
        Scanner input=new Scanner(System.in);
        System.out.print("Enter Coach number(1-10) : ");
        CoachNumber= input.nextInt();
        if (CoachNumber >= 0 && CoachNumber < 10) {
            Coaches[CoachNumber-1] = null;
            System.out.println("Customer deleted  successfully");

        }
        else { System.out.println("Invalid Coach number \n you should enter between 1-10");
        }
    }
    private static void FindCustomer(){
        Scanner input=new Scanner(System.in);
        System.out.print("Enter Customer Name: ");
        CustomerName=input.next().toUpperCase(Locale.ROOT); //getting customer name and convert it to upper case
        for (int x=0;x<10;x++){
            if(Coaches[x]!=null){
                if (Coaches[x].equals(CustomerName))
                    System.out.println(CustomerName+"'s Coach number is " +(x+1));
        }
        }
    }
    private static void OrderedList(){
        String[] copyCoaches=new String[10];
        int size = copyCoaches.length;
        for (int z=0; z<size; z++){
            copyCoaches[z]=Coaches[z];
        }

        //logic for sorting
        for (int i = 0; i < size - 1; i++)
        {
            for (int j = i + 1; j < size; j++) {
                //compares each element of the array to all the remaining elements
                if (copyCoaches[i] != null && copyCoaches[j]!= null){
                    if (copyCoaches[i].compareTo(copyCoaches[j]) > 0) {
                        //swapping array elements
                        String temp = copyCoaches[i];
                        copyCoaches[i] = copyCoaches[j];
                        copyCoaches[j] = temp;
                    }
                }
            }
        } //prints the sorted array in ascending order
        for (int i=0; i < size - 1; i++){
            if (copyCoaches[i]!= null){
                System.out.println(copyCoaches[i]);
            }
        }
    }



    private static void StoreFile(){
        try {
            FileWriter myWriter = new FileWriter("CoachDetails.txt");
            for (int x = 0; x < 10; x++ ){
                if (Coaches[x]!=null) {
                    myWriter.write("Coach number " + (x + 1) + " is occupied by " + Coaches[x]+"\n");
                }
                else {
                    myWriter.write("Coach number " + (x + 1) + " is  " + Coaches[x]+"\n");
                }
            }
            myWriter.close(); //closing the file

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private static void LoadFile(){
        try {
            File MyObject = new File("CoachDetails.txt");
            Scanner myReader = new Scanner(MyObject);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); //reading from file
                System.out.println(data);
            }
            myReader.close(); //closing the file
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}




