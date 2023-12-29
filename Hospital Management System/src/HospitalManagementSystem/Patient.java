package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    // Connection to the database
    private Connection connection;

    // Scanner for user input
    private Scanner scanner;

    // Constructor to initialize the connection and scanner
    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    // Method to add a new patient to the database
    public void addPatient(){
        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();
        System.out.println();

        try{
            // SQL query to insert a new patient into the 'patients' table
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            // Execute the update and check the affected rows
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient Added Successfully!!");
            }else{
                System.out.println("Failed to add Patient!!");
            }

        }catch (SQLException e){
            // Handle any SQL exceptions by printing the stack trace
            e.printStackTrace();
        }
    }

    // Method to view all patients in the database
    public void viewPatients(){
        // SQL query to select all patients from the 'patients' table
        String query = "Select * from patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display the header for the patient table
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");

            // Iterate through the result set and display each patient's information
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                // Display the patient information in a formatted manner
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }

        }catch (SQLException e){
            // Handle any SQL exceptions by printing the stack trace
            e.printStackTrace();
        }
    }

    // Method to check if a patient with a given ID exists in the database
    public boolean getPatientById(int id){
        // SQL query to select a patient with a specific ID from the 'patients' table
        String query = "Select * From patients Where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If there is a result, the patient with the given ID exists
            if(resultSet.next()){
                return true;
            }
            else{
                // If no result, the patient with the given ID does not exist
                return false;
            }
        }catch (SQLException e){
            // Handle any SQL exceptions by printing the stack trace
            e.printStackTrace();
        }

        // Default case, return false if there is an exception
        return false;
    }
}
