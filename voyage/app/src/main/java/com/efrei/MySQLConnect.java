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
import com.efrei.models.Reservation;
import com.efrei.models.Train;
import com.efrei.models.Ville;
import com.efrei.models.Voyageur;

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
    
    public static ObservableList<Voyageur> getDataVoyageur() {
        ObservableList<Voyageur> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Voyageur");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id_voyageur = rs.getInt("ID_VOYAGEUR");
                String login = rs.getString("LOGIN");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String adresse = rs.getString("ADRESSE");
                Voyageur voyageur = new Voyageur(id_voyageur, login, nom, prenom, adresse);
                list.add(voyageur);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            e.getMessage(); 
            System.out.println("TABLE VOYAGEUR INTROUVALBLE ");
            System.out.println("");// Better than showing a message dialog for debugging purposes
        }
        return list;
    }

	public static ObservableList<Billet> getDataBillet() {
		ObservableList<Billet> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Billet");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id_train = rs.getInt("ID_TRAIN");
                int id_ville_dep = rs.getInt("V_DEPART");
                int id_ville_arrive = rs.getInt("V_ARRIVEE");
                int prix = rs.getInt("PRIX_BILLET");
                Ville v_dep = Ville.get_Ville(id_ville_dep);
                Ville v_arr = Ville.get_Ville(id_ville_arrive);
                Train train = Train.get_Train(id_train);
                
                LocalDateTime H_DEB = rs.getTimestamp("H_DEPART").toLocalDateTime();
                LocalDateTime H_FIN = rs.getTimestamp("H_FIN").toLocalDateTime();
                Billet billet = new Billet(train, v_dep, v_arr , prix, H_DEB, H_FIN);
               list.add(billet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Better than showing a message dialog for debugging purposes
        }
        return list;
	}
	
	public static ObservableList<Reservation> getDataReservation() {
		ObservableList<Reservation> list = FXCollections.observableArrayList();
        try (Connection conn = connectDb();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Reservation");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id_voyageur = rs.getInt("ID_VOYAGEUR");
                int nb_adultes = rs.getInt("NB_ADULTE");
                int nb_enfants = rs.getInt("NB_ENFANT");
                int nb_senior = rs.getInt("NB_SENIOR");
                int id_train = rs.getInt("id_train");
                int id_ville_dep = rs.getInt("v_depart");
                int id_ville_arrive = rs.getInt("v_arrivee");
                
                Ville v_dep = Ville.get_Ville(id_ville_dep);
                Ville v_arr = Ville.get_Ville(id_ville_arrive);
                Train train = Train.get_Train(id_train);

                LocalDateTime H_Reservation = rs.getTimestamp("DATE_RESERVATION").toLocalDateTime();
                LocalDateTime H_DEB = rs.getTimestamp("H_DEPART").toLocalDateTime();

                Billet billet = Billet.get_Billet(train,H_DEB,v_dep,v_arr);
                System.out.println(billet.getID_TRAIN().getValue().idProperty().get());
                Voyageur voyageur = Voyageur.GetVoyageur(id_voyageur);
                Reservation reservation = new Reservation(billet, voyageur, H_Reservation,nb_enfants,nb_adultes,nb_senior);
            	list.add(reservation);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("TABLE RESERVATION INTROUVALBLE ");
            System.out.println("");// Better than showing a message dialog for debugging purposes// Better than showing a message dialog for debugging purposes
        }
        return list;
	}
    
}
