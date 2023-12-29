package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    // Connection to the database
    private Connection connection;

    // Constructor to initialize the connection
    public Doctor(Connection connection){
        this.connection = connection;
    }

    // Method to view all doctors in the database
    public void viewDoctors(){
        // SQL query to select all doctors from the 'doctors' table
        String query = "Select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display the header for the doctor table
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");

            // Iterate through the result set and display each doctor's information
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                // Display the doctor information in a formatted manner
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }

        }catch (SQLException e){
            // Handle any SQL exceptions by printing the stack trace
            e.printStackTrace();
        }
    }

    // Method to check if a doctor with a given ID exists in the database
    public boolean getDoctorById(int id){
        // SQL query to select a doctor with a specific ID from the 'doctors' table
        String query = "Select * From doctors Where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If there is a result, the doctor with the given ID exists
            if(resultSet.next()){
                return true;
            }
            else{
                // If no result, the doctor with the given ID does not exist
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
