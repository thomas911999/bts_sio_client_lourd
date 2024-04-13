package com.efrei;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.efrei.models.Train;

public class MySQLConnect {

    public static Connection connectDb() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/train", "root", "");
            System.out.println("Database connection successful");
        } catch (SQLException e) {
            System.out.println("Error connecting to database:");
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found");
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    public static ObservableList<Train> getDataUsers() {
        ObservableList<Train> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM train");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("ID_TRAIN");
                String modele = rs.getString("MODELE");
                int capacite = rs.getInt("CAPACITE");
                Train train = new Train(id, modele, capacite);
                list.add(train);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Better than showing a message dialog for debugging purposes
        }
        return list;
    }
    
}
