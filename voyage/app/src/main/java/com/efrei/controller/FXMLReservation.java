package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.efrei.MySQLConnect;
import com.efrei.models.Billet;
import com.efrei.models.Reservation;
import com.efrei.models.Train;
import com.efrei.models.Ville;
import com.efrei.models.Voyageur;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.util.StringConverter;

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
    ObservableList<Voyageur> listVoyageur;

    
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
    private ChoiceBox<Ville> ville_arrivée_filtre;

    @FXML
    private ChoiceBox<Ville> ville_depart_filtre;
    
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
    	
    	Billet b = table_billet.getSelectionModel().getSelectedItem();
    	Integer enfant = nb_enfant_gestion.getValue();
    	Integer adulte = nb_adulte_gestion.getValue();
    	Integer senior = nb_senior_gestion.getValue();
    	Timestamp time = Timestamp.valueOf(LocalDateTime.now());
    	Voyageur v = login_gestion.getValue();
    	
    	String sql = "INSERT INTO RESERVATION(ID_VOYAGEUR, NB_ADULTE, NB_ENFANT, NB_SENIOR, DATE_RESERVATION, ID_BILLET)"
    			+ "VALUES(?, ?, ?, ?, ?, ?)";

        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1,  v.getId_voyageur().getValue()); // ID_VOYAGEUR
            pst.setInt(2, adulte); // NB_ADULTE
            pst.setInt(3, enfant); // NB_ENFANTS
            pst.setInt(4, senior); // NB_SENIOR
            pst.setTimestamp(5,  time); // H_RESERVATION
            pst.setFloat(6,  b.getID_BILLET().getValue()); // ID_BILLET
            pst.execute();
            listReservation = MySQLConnect.getDataReservation();
			table_reservation.setItems(listReservation);
			initialize();

            JOptionPane.showMessageDialog(null, "AJOUT RESERVATION");
        } 
        catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs valides pour la réservation");
	    }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Remplir tous les champs \n" + e);
        }
    }

    @FXML
    void Del_Reservation(ActionEvent event) {

    	Reservation selectedItem = table_reservation.getSelectionModel().getSelectedItem();
	    if (selectedItem == null) {
	        JOptionPane.showMessageDialog(null, "Aucune réservation sélectionné.");
	        return;
	    }
	    
    	String sql = "DELETE FROM reservation WHERE ID_BILLET = ? AND ID_VOYAGEUR = ?";
	    
	    try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedItem.getBillet().getValue().getID_BILLET().getValue());
            pst.setInt(2, selectedItem.getVoyageur().getValue().getId_voyageur().getValue());
            pst.execute();
            listReservation.remove(selectedItem);

            JOptionPane.showMessageDialog(null, "SUPRESSION RESERVATION");
        } 
        
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERREUR" + e);
        }
    }


    @FXML
    void Update_Reservation(ActionEvent event) {
    	
    	Reservation selectedItem = table_reservation.getSelectionModel().getSelectedItem();
	    if (selectedItem == null) {
	        JOptionPane.showMessageDialog(null, "Aucune réservation sélectionné.");
	        return;
	    }

	    Voyageur v = login_gestion.getValue();
    	Integer enfant = nb_enfant_gestion.getValue();
    	Integer adulte = nb_adulte_gestion.getValue();
    	Integer senior = nb_senior_gestion.getValue();
	    

    	String sql = "UPDATE Reservation SET ID_VOYAGEUR = ?, NB_ADULTE = ?, NB_ENFANT = ?, NB_SENIOR = ? WHERE ID_BILLET = ? AND ID_VOYAGEUR = ?";
	    
	    try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
			pst.setInt(1,  v.getId_voyageur().getValue()); // ID_VOYAGEUR
            pst.setInt(2, enfant); // NB_ENFANT
            pst.setInt(3, adulte); // NB_ADULTE
            pst.setInt(4,  senior); // NB_SENIOR
            pst.setInt(5,  selectedItem.getBillet().getValue().getID_BILLET().getValue());// ID_BILLET
            pst.setInt(6,  selectedItem.getVoyageur().getValue().getId_voyageur().getValue());// ID_VOYAGEUR
            pst.executeUpdate();

	        int affectedRows = pst.executeUpdate();
	        if (affectedRows > 0) {
	        	
	        	selectedItem.setNb_adulte(adulte);
	        	selectedItem.setNb_enfant(enfant);
	        	selectedItem.setNb_senor(senior);
	        	selectedItem.setVoyageur(v);
	    	  
	            table_reservation.refresh();
	            
	            JOptionPane.showMessageDialog(null, "Mise à jour des billets réussie.");
	        } 
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du billet : " + e.getMessage());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs valides pour le prix du billet et remplir tous les champs");
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

    }
    
    @FXML
    void Search(ActionEvent event) {

    	
    	FilteredList<Reservation> filteredData = new FilteredList<>(listReservation, p -> true);

    	// Appliquez le prédicat de filtrage en fonction de vos critères
    	filteredData.setPredicate(reservation -> {

        	Billet b = table_billet.getSelectionModel().getSelectedItem();

        	var log = login_filtre.getText();
        	
    		
    		String text_login = login_filtre.getText();
    	    LocalDate selectedDate_reservation = Date_reservation_filtre.getValue();
    	    
    	    LocalDateTime startOfDay_reservation = null;
    	    SimpleStringProperty formated_date_reservation = new SimpleStringProperty("");

    	    // Vérifiez si la date sélectionnée n'est pas null
    	  /*  if(selectedDate != null) {
    	        startOfDay = selectedDate.atStartOfDay();
    	        formated_date.set(Billet.Date_to_string_day(startOfDay).get());
    	    }*/
    	    
    	    if(selectedDate_reservation != null) {
    	    	startOfDay_reservation = selectedDate_reservation.atStartOfDay();
    	    	formated_date_reservation.set(Billet.Date_to_string_day(startOfDay_reservation).get());
    	    }
        	
        	
        	boolean dateMatch_Reservation = selectedDate_reservation == null || Billet.Date_to_string_day(reservation.getH_RESERVATION().getValue()).get().equals(formated_date_reservation.get());

        	boolean get_billet = b != null && b.getID_BILLET().getValue() == reservation.getBillet().getValue().getID_BILLET().getValue();
        			
        	boolean getLog = log == null || reservation.getVoyageur().getValue().getLogin().getValue().contains(log);
        	
        	return  get_billet && getLog && dateMatch_Reservation;
        	

    	});

    	table_reservation.setItems(filteredData);
    	
    }
    
    @FXML
    void Search_billet(ActionEvent event) {

    	
    	FilteredList<Billet> filteredData = new FilteredList<>(listBillet, p -> true);

    	// Appliquez le prédicat de filtrage en fonction de vos critères
    		filteredData.setPredicate(billet -> {

    			
    		
    		Ville v_dep = ville_depart_filtre.getValue();
    		Ville v_arr = ville_arrivée_filtre.getValue();
    		
    		
    	    LocalDate selectedDate = Date_filtre.getValue();
    	    
    	    LocalDateTime startOfDay_reservation = null;
    	    SimpleStringProperty formated_date_reservation = new SimpleStringProperty("");
    	   
    	    
    	    if(selectedDate != null) {
    	    	startOfDay_reservation = selectedDate.atStartOfDay();
    	    	formated_date_reservation.set(Billet.Date_to_string_day(startOfDay_reservation).get());
    	    }
        	
        	
        	boolean dateMatch_voyage = selectedDate == null || Billet.Date_to_string_day(billet.getH_DEB().getValue()).get().equals(formated_date_reservation.get());

        	boolean v_depMatch = v_dep == null || billet.getV_DEPART().getVille().get().equals(v_dep.getVille().get());
        	
        	boolean v_arrMatch = v_arr == null || billet.getV_ARRIVE().getVille().get().equals(v_arr.getVille().get());
                			
        	return  dateMatch_voyage && v_depMatch && v_arrMatch;

    	});

    	/*if (filteredData.equals(null))
    		table_reservation.setItems(null);*/
    	table_billet.setItems(filteredData);
    	Search(event);
    	
    }


    @FXML
    void reset_all(ActionEvent event) {

    	Date_reservation_filtre.setValue(null);
    	login_filtre.setText(null);
    	ville_depart_filtre.setValue(null);
    	Date_filtre.setValue(null);
    	
    	
    	table_billet.refresh();
    	table_reservation.refresh();
    	initialize();
    	
    }

    @FXML
    void reset_date_reservation(ActionEvent event) {
    	Date_reservation_filtre.setValue(null);
    	table_reservation.refresh();
    	Search(event);
    }

    @FXML
    void reset_date_voyage(ActionEvent event) {
    	Date_filtre.setValue(null);
    	table_billet.refresh();
    	Search_billet(event);
    }
    
    public void Item_voyageur(ChoiceBox<Voyageur> choicebox_voyageur)
    {
    	listVoyageur = MySQLConnect.getDataVoyageur();
		
		ObservableList<Voyageur> voyageur_items = FXCollections.observableArrayList();
					
		for (Voyageur  v : listVoyageur) {
			voyageur_items.add(v);
		}
	
		choicebox_voyageur.setItems(voyageur_items);
		
		choicebox_voyageur.setConverter(new StringConverter<Voyageur>() {
			
            @Override
            public String toString(Voyageur v) {
                return (v == null) ? "Aucun" : v.getLogin().getValue().toString();
            }

            @Override
            public Voyageur fromString(String string) {
                return null; // Pas nécessaire pour une ChoiceBox non éditable
            }
        });
		
		choicebox_voyageur.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        
		        	var v = choicebox_voyageur.getValue();

		        		if (choicebox_voyageur.getItems().size() >1 && choicebox_voyageur.getItems().remove(v))
		        			{
		        				choicebox_voyageur.getItems().add(0, newValue);
		        				choicebox_voyageur.setValue(newValue); // Sélectionner la nouvelle valeur
		        			}
		        
		    });
    }

    
public void initialize() {
	
    listBillet = MySQLConnect.getDataBillet();
    table_billet.setItems(listBillet);
    
    Item_voyageur(login_gestion);
    FXMLBillet fxml_b = new FXMLBillet();
    
    login_gestion.setItems(listVoyageur);    
    
    fxml_b.Item_gestion_ville(ville_arrivée_filtre); 
    FXMLBillet.addNullValue(ville_arrivée_filtre);
    fxml_b.Item_gestion_ville(ville_depart_filtre);
    FXMLBillet.addNullValue(ville_depart_filtre);
    
    listReservation = MySQLConnect.getDataReservation();
    
    table_billet.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
        	FilteredList<Reservation> filteredData = new FilteredList<>(listReservation, p -> true);
        	filteredData.setPredicate(reservation -> {
        		
        	
        		var b = newSelection.getID_BILLET().getValue();
        		
            	var log = login_filtre.getText();
            	
        		
        		String text_login = login_filtre.getText();
        	    LocalDate selectedDate_reservation = Date_reservation_filtre.getValue();
        	    
        	    LocalDateTime startOfDay_reservation = null;
        	    SimpleStringProperty formated_date_reservation = new SimpleStringProperty("");
        	    
        	    if(selectedDate_reservation != null) {
        	    	startOfDay_reservation = selectedDate_reservation.atStartOfDay();
        	    	formated_date_reservation.set(Billet.Date_to_string_day(startOfDay_reservation).get());
        	    }
            	
            	boolean dateMatch_Reservation = selectedDate_reservation == null || Billet.Date_to_string_day(reservation.getH_RESERVATION().getValue()).get().equals(formated_date_reservation.get());

            	boolean get_billet = b  == reservation.getBillet().getValue().getID_BILLET().getValue();
            			
            	boolean getLog = log == null || reservation.getVoyageur().getValue().getLogin().getValue().contains(log);
            	
            	return  get_billet && getLog && dateMatch_Reservation;
            	
        	    // Retourne vrai si le billet correspond aux critères de ville de départ et d'arrivée
        	});
        	
            table_reservation.setItems(filteredData);
        }
    });

    table_reservation.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
        	
        	login_gestion.setValue(newSelection.getVoyageur().getValue());
        	nb_enfant_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, newSelection.getNb_enfant().getValue()));
        	nb_adulte_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, newSelection.getNb_adulte().getValue()));
        	nb_senior_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, newSelection.getNb_senor().getValue()));
      	
        	}
    });
    
    col_modele.setCellValueFactory(cellData -> cellData.getValue().getID_TRAIN().get().modeleProperty());
    col_prix.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
    col_Ville_depart.setCellValueFactory(cellData -> cellData.getValue().getV_DEPART().getVille());
    col_ville_arrive.setCellValueFactory(cellData -> cellData.getValue().getV_ARRIVE().getVille());
    col_voyage.setCellValueFactory(cellData ->  Billet.Date_to_string_day(cellData.getValue().getH_DEB().getValue()));
    col_H_depart.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_DEB()));
    col_H_arrive.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_FIN()));

    
    //table reservation 
    
    col_login.setCellValueFactory(cellData ->  cellData.getValue().getVoyageur().get().getLogin());
    
    col_date_reservation.setCellValueFactory(cellData ->  Billet.Date_to_string_day_hour(cellData.getValue().getH_RESERVATION()));
    
    col_adulte.setCellValueFactory(cellData ->  cellData.getValue().getNb_adulte().asObject());

    col_enfant.setCellValueFactory(cellData ->  cellData.getValue().getNb_enfant().asObject());

    col_senor.setCellValueFactory(cellData ->  cellData.getValue().getNb_senor().asObject());
   
	nb_enfant_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // enfant: 0-100, initial value: 0
	nb_adulte_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // adulte: 0-100, initial value: 0
	nb_senior_gestion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)); // senior: 0-100, initial value: 0

	Date_reservation_filtre.setOnAction(event -> Search(event));
	login_filtre.setOnAction(event -> Search(event));
	
	ville_depart_filtre.setOnAction(event -> Search_billet(event));
	ville_arrivée_filtre.setOnAction(event -> Search_billet(event));
	Date_filtre.setOnAction(event -> Search_billet(event));
	
    }


}
