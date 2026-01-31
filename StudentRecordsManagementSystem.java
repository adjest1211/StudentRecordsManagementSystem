/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentrecordsmanagementsystem;
import java.util.*;
import java.io.*;

public class StudentRecordsManagementSystem {

    static final String fileName = "studentRecords.txt";
    
    static void pl(Object text){
        System.out.println(text);
    }
    static void p(Object text){
        System.out.print(text);
    }
    static void pf(String text, Object... args){
        System.out.printf(text, args);
    }
    
   
    
   
    static void addStudent(Scanner scan){
        try{
            pl("");
            pl("~~Add Menu~~");
            p("Enter student name: ");
            String name = scan.nextLine();
            p("Enter age: ");
            int age = Integer.parseInt(scan.nextLine());
            p("Enter gender: ");
            String gender = scan.nextLine();
            p("Enter course: ");
            String course = scan.nextLine();
            p("Enter address: ");
            String address = scan.nextLine();
            int id = 0;
            
            try(BufferedWriter write = new BufferedWriter(new FileWriter(fileName, true))){
                String nameData = name.replace(",", ";");
                String addressData = address.replace(",", ";");
                write.write((id++) + "," + nameData + "," + age + "," + gender + "," + course + "," + addressData);
                write.newLine();
            }
            pl("Record added successfully");
        }
        catch(NumberFormatException e){
            pl("Please enter valid age...");
        }
        catch(IOException e){
            pl("Could not save the record to file :(");
        }
    }
    
    static void viewRecord(){
        List<String> recordList = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String data;
            while((data = read.readLine()) != null ){
                String[] records = data.split(",");
                if(records.length == 6 || (records.length == 7 && !records[6].equals("archived"))){
                    recordList.add(data);
                }
            }
            
            Collections.sort(recordList, new Comparator<String>() {
                public int compare(String a, String b){
                    String nameA = a.split(",")[1].toLowerCase();
                    String nameB = b.split(",")[1].toLowerCase();
                    return nameA.compareTo(nameB);
                }
            });
            for(int i = 0; i < recordList.size(); i++){
                String[] dataList = recordList.get(i).split(",");
                dataList[0] = String.valueOf(i + 1);
                recordList.set(i, String.join(",", dataList));
            }
        }
        catch(FileNotFoundException e){
            pl("No record file found... :(");
        }
        catch(IOException e){
            pl("Could not load the file");
        }
        
        pl("");
        pf("%-5s %-30s %-5s %-7s %-7s %-40s%n", "No.", "NAME", "AGE", "GENDER", "COURSE", "ADDRESS");
            for(String record : recordList){
                String[] list = record.split(",");
                String name = list[1].replace(";", ",");
                String address = list[5].replace(";", ",");
                pf("%-5s %-30s %-5s %-7s %-7s %-40s%n", list[0], name, list[2], list[3], list[4], address);      
            }
    }
    
    static void updateInfo(Scanner scan){
        List<String> records = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String data;
            while((data = read.readLine()) != null){
                String[] list = data.split(",");
                if(list.length == 6 || (list.length == 7 && !list[6].equals("archived"))){
                    records.add(data);
                }
            }
        }
        catch(FileNotFoundException e){
            pl("FIle not found... ");
            return;
        }
        catch(IOException e){
            pl("Could not load the record file...");
            return;
        }
        
        pl("");
        viewRecord();
        p("Enter student number: ");
        int number;
        
        try{
            number = scan.nextInt();
            scan.nextLine();
        }catch(Exception e){
            pl("Invalid input..");
            scan.nextLine();
            return;
        }
       
        boolean found = false;
        for(int i = 0; i < records.size(); i++){
            String[] data = records.get(i).split(",");
            if(data.length == 6 || Integer.parseInt(data[0]) == number 
                    && (data.length < 7 || !data[6].equals("archived"))){
                found = true;
                pl("");
                pl("~~Update Menu~~");
                pl("Choose What to update");
                pl("1. Name");
                pl("2. Age");
                pl("3. Gender");
                pl("4. Course");
                pl("5. Address");
                p("Enter option: ");
        
                int option;
                try{
                    option = scan.nextInt();
                    scan.nextLine();
                }catch(Exception e){
                    pl("Invalid input..");
                    scan.nextLine();
                    return;
                }
                
                switch(option){
                    case 1:
                        p("Enter new name: ");
                        String name = scan.nextLine();
                        data[1] = name.replace(",", ";");
                        break;
                    case 2:
                        p("Enter new age: ");
                        data[2] = scan.nextLine();
                        break;
                    case 3:
                        p("Enter new gender: ");
                        data[3] = scan.nextLine();
                        break;
                    case 4:
                        p("Enter new course: ");
                        data[4] = scan.nextLine();
                        break;
                    case 5:
                        p("Enter new Address: ");
                        String address = scan.nextLine();
                        data[5] = address.replace(",", ";");
                        break;
                    default:
                        pl("Invalid input..");
                        return;
                }
                
                records.set(i, String.join(",", data));
                break;
            }
        }
        

        
        try(BufferedWriter write = new BufferedWriter(new FileWriter(fileName))){
            for(String data : records){
                write.write(data);
                write.newLine();
            }
        }catch(IOException e){
            pl("Could not update the record... :(");
            return;
        }
        
        pl("Student informatioin updated successfully!!");
    }
    
    static void archiveStudent(Scanner scan){
        List<String> records = new ArrayList<>();
        try(BufferedReader read = new BufferedReader(new FileReader(fileName))){
            String data;
            while((data = read.readLine()) != null){
                records.add(data);
            }
        }
        catch(FileNotFoundException e){
            pl("File not found...");
            return;
        }
        catch(IOException e){
            pl("Could not load the record file..");
            return;
        }
        
        pl("");
        viewRecord();
        p("Enter student number: ");
        int number;
        
        try{
            number = scan.nextInt();
            scan.nextLine();
        }catch(Exception e){
            pl("Invalid input.");
            scan.nextLine();
            return;
        }
        
        boolean found = false;
        for(int i = 0; i < records.size(); i++){
            String[] data = records.get(i).split(",");
            if(data.length == 6 && Integer.parseInt(data[0]) == number){
                found = true;
                records.set(i, data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + ",archived");
                break;
            }
        }
        
        try(BufferedWriter write = new BufferedWriter(new FileWriter(fileName))){
            for(String data : records){
                write.write(data);
                write.newLine();
            }
        }
        catch(IOException e){
            pl("Could not archive the record... :(");
            return;
        }
        pl("Student archived successfully!!");

    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        while(true){
            pl("");
            pl("Student Records Management System");
            pl("1. Add Student");
            pl("2. View Record");
            pl("3. Update Record");
            pl("4. Archive Student");
            pl("5. Exit");
            p("Enter option: ");
            
            int option;
            try{
                option = scan.nextInt();
                scan.nextLine();
            }catch(Exception e){
                pl("Invalid input..");
                scan.nextLine();
                continue;
            }
            
            switch(option){
                case 1:
                    addStudent(scan);
                    break;
                case 2:
                    viewRecord();
                    break;
                case 3:
                    updateInfo(scan);
                    break;
                case 4:
                    archiveStudent(scan);
                    break;
                case 5:
                    pl("Exiting.... Thank You!");
                    break;
            }
            if(option == 5){
                break;
            }
        }
    }
}
