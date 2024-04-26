package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    @FXML
    private TableColumn<?, ?> col_H_arrive;

    @FXML
    private TableColumn<?, ?> col_H_arrive1;

    @FXML
    private TableColumn<?, ?> col_H_depart;

    @FXML
    private TableColumn<?, ?> col_H_depart1;

    @FXML
    private TableColumn<?, ?> col_Ville_depart;

    @FXML
    private TableColumn<?, ?> col_modele;

    @FXML
    private TableColumn<?, ?> col_modele1;

    @FXML
    private TableColumn<?, ?> col_prix;

    @FXML
    private TableColumn<?, ?> col_prix1;

    @FXML
    private TableColumn<?, ?> col_ville_arrive;

    @FXML
    private TableColumn<?, ?> col_voyage;

    @FXML
    private TableColumn<?, ?> col_voyage1;

    @FXML
    private TextField login_filtre;

    @FXML
    private ChoiceBox<?> login_gestion;

    @FXML
    private MenuItem modif_menu;

    @FXML
    private Spinner<?> nb_adulte_gestion;

    @FXML
    private Spinner<?> nb_enfant_gestion;

    @FXML
    private Spinner<?> nb_senior_gestion;

    @FXML
    private MenuItem supprimer_menu;

    @FXML
    private TableView<?> table_billet;

    @FXML
    private TableView<?> table_billet1;

    @FXML
    private ChoiceBox<?> ville_arrivée_filtre;

    @FXML
    private ChoiceBox<?> ville_depart_filtre;

    @FXML
    void Add_Billet(ActionEvent event) {

    }

    @FXML
    void Del_Billet(ActionEvent event) {

    }

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
    void Update_Billet(ActionEvent event) {

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

}
