package com.efrei;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.efrei.models.Billet;
import com.efrei.models.Train;
import com.efrei.models.Ville;

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

    public static ObservableList<Train> getDataTrain() {
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
    
    public static ObservableList<Ville> getDataVille() {
        ObservableList<Ville> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Ville");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("Id_Ville");
                String libelle = rs.getString("Libelle");
                Ville ville = new Ville(id, libelle);
                list.add(ville);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Better than showing a message dialog for debugging purposes
        }
        return list;
    }

	public static ObservableList<Billet> getDataBillet() {
		ObservableList<Billet> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Billet");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id_reservation = rs.getInt("ID_RESERVATION");
                int id_train = rs.getInt("ID_TRAIN");
                int id_ville_dep = rs.getInt("V_DEPART");
                int id_ville_arrive = rs.getInt("V_ARRIVEE");
                int prix = rs.getInt("PRIX_BILLET");
                Ville v_dep = Ville.get_ville(id_ville_dep);
                Ville v_arr = Ville.get_ville(id_ville_arrive);
                Train train = Train.get_Train(id_train);
                
                LocalDateTime H_DEB = rs.getTimestamp("H_DEPART").toLocalDateTime();
                LocalDateTime H_FIN = rs.getTimestamp("H_FIN").toLocalDateTime();
                //int capacite = rs.getInt("CAPACITE");
                Billet billet = new Billet(train, id_reservation, v_dep, v_arr , prix, H_DEB, H_FIN);
               list.add(billet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Better than showing a message dialog for debugging purposes
        }
        return list;
	}
    
}
