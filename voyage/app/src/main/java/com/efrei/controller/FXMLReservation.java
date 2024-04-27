package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.efrei.MySQLConnect;
import com.efrei.models.Billet;
import com.efrei.models.Reservation;
import com.efrei.models.Train;
import com.efrei.models.Ville;
import com.efrei.models.Voyageur;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLReservation {

    @FXML
    private DatePicker Date_filtre;

    @FXML
    private DatePicker Date_reservation_filtre;

    @FXML
    private Button Reset_Date_reservation;

    @FXML
    private Button Reset_all;

    @FXML
    private Button Reset_date;

    @FXML
    private MenuItem ajout_menu;

    @FXML
    private Button button_ajouter;

    @FXML
    private Button button_modifier;

    @FXML
    private Button button_supprimer;
    
    /// TABLE CONTROLLER BILLET ///////////////////////////////////////////
    
    
    @FXML
    private TableView<Billet> table_billet;
    
    @FXML
    private TableColumn<Billet, String> col_voyage;
    
    @FXML
    private TableColumn<Billet, String> col_H_arrive;

    @FXML
    private TableColumn<Billet, String> col_H_depart; // Change TableColumn type to String
    @FXML
    private TableColumn<Billet, String> col_Ville_depart;
    
    @FXML
    private TableColumn<Billet, String> col_ville_arrive;

    @FXML
    private TableColumn<Billet, String> col_modele;

    @FXML
    private TableColumn<Billet, Float> col_prix;
    
    ObservableList<Billet> listBillet;
    ObservableList<Reservation> listReservation;
    
    ///////////// TABLE CONTROLLER RESERVATION //////////////////////////////
    
    @FXML
    private TableView<Reservation> table_reservation;

    @FXML
    private TableColumn<Reservation, String> col_date_reservation;
    
    @FXML
    private TableColumn<Reservation, Integer> col_adulte;

    @FXML
    private TableColumn<Reservation, Integer> col_enfant;

    @FXML
    private TableColumn<Reservation, String> col_login;


    @FXML
    private TableColumn<Reservation, Integer> col_senor;
    
    /// FILTRE CONTROLLER ////////////////////////////////

    @FXML
    private TextField login_filtre;

    @FXML
    private ChoiceBox<?> ville_arrivée_filtre;

    @FXML
    private ChoiceBox<?> ville_depart_filtre;
    
    //// MENU CONTROLLER /////////////////////

    @FXML
    private MenuItem modif_menu;
    
    @FXML
    private MenuItem supprimer_menu;
    
    
    //// GESTION RESERVATION //////////////////
    
    @FXML
    private ChoiceBox<Voyageur> login_gestion;

    @FXML
    private Spinner<Integer> nb_adulte_gestion;

    @FXML
    private Spinner<Integer> nb_enfant_gestion;

    @FXML
    private Spinner<Integer> nb_senior_gestion;

    

    @FXML
    void Exit_App(ActionEvent event) {

    }
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    private void changeScene(String fxml) {
        try {
            Stage stage = (Stage) button_ajouter.getScene().getWindow();
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void Switch_Billet(ActionEvent event) {
    	changeScene("/com/efrei/Billet.fxml");
    }

    @FXML
    void Switch_Train(ActionEvent event) {
    	changeScene("/com/efrei/Train.fxml");
    }

    @FXML
    void Switch_Voyageur(ActionEvent event) {
    	changeScene("/com/efrei/Voyageur.fxml");
    }

    @FXML
    void Switch_reservation(ActionEvent event) {
    	changeScene("/com/efrei/Reservation.fxml");
    }
    
    @FXML
    void Add_Reservation(ActionEvent event) {

    }

    @FXML
    void Del_Reservation(ActionEvent event) {

    }


    @FXML
    void Update_Reservation(ActionEvent event) {

    }

    @FXML
    void reset_all(ActionEvent event) {

    }

    @FXML
    void reset_date_reservation(ActionEvent event) {

    }

    @FXML
    void reset_date_voyage(ActionEvent event) {

    }
    
public void initialize() {
	
    listBillet = MySQLConnect.getDataBillet();
    table_billet.setItems(listBillet);
    
    listReservation = MySQLConnect.getDataReservation();
    
    table_billet.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
        	FilteredList<Reservation> filteredData = new FilteredList<>(listReservation, p -> true);
        	filteredData.setPredicate(reservation -> {
        		
        		
        		var b_reserv = reservation.getBillet().getValue();
        		var b_train = b_reserv.getID_TRAIN().getValue();
        		
        		var a_train = newSelection.getID_TRAIN().getValue();
        		
        		var b_id_train = b_train.idProperty().getValue();
        		var a_id_train = a_train.idProperty().getValue();
        		
        		
        		
        		System.out.println("ID_train billet :" + a_id_train);
        		System.out.println("ID_train reserv :" + b_id_train);
        		
            	boolean BilletMatch = a_id_train == b_id_train;
            	
            	System.out.println(BilletMatch);

        	    // Retourne vrai si le billet correspond aux critères de ville de départ et d'arrivée
        	    return BilletMatch;//  && modeleMatch && departMatch && arriveeMatch;
        	});
            table_reservation.setItems(filteredData);
        }
    });
    
    
    col_modele.setCellValueFactory(cellData -> cellData.getValue().getID_TRAIN().get().modeleProperty());
    col_prix.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
    col_Ville_depart.setCellValueFactory(cellData -> cellData.getValue().getV_DEPART().getVille());
    col_ville_arrive.setCellValueFactory(cellData -> cellData.getValue().getV_ARRIVE().getVille());
    col_voyage.setCellValueFactory(cellData ->  Billet.Date_to_string_day(cellData.getValue().getH_DEB().getValue()));
    col_H_depart.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_DEB()));
    col_H_arrive.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_FIN()));

    	
	nb_enfant_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // enfant: 0-100, initial value: 0
	nb_adulte_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // adulte: 0-100, initial value: 0
	nb_senior_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // senior: 0-100, initial value: 0

    }


}
