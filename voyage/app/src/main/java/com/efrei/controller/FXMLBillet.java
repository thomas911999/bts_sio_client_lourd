package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javax.swing.JOptionPane;

import com.efrei.MySQLConnect;
import com.efrei.models.Billet;
import com.efrei.models.Train;
import com.efrei.models.Ville;

import javafx.util.StringConverter;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLBillet {
	
	
	
	/////// MENU CONTROLLER /////////////////////
	
    @FXML
    private MenuItem modif_menu;

    @FXML
    private MenuItem supprimer_menu;

    
    @FXML
    private MenuItem ajout_menu;
    
    
    ///// TABLE CONTROLLER ///////////////////////
    
    
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
    
    ObservableList<Train> listTrain;

    ObservableList<Ville> listVille;

    
    Map<String, Integer> modeleIdMap = new HashMap<>();	////// FILTRE CONTROLLER ////////////////////
	
    @FXML
    private ComboBox<Ville> ville_depart_filtre;
    
    @FXML
    private ComboBox<Ville> ville_arrivée_filtre;
    
    @FXML
    private ComboBox<String> modele_filtre;
	
	
	////// GESTION CONTROLLER ///////////////////

    @FXML
    private DatePicker Date_gestion;
    

    @FXML
    private Spinner<Integer> H_deb;

    @FXML
    private Spinner<Integer> H_fin;

    @FXML
    private Spinner<Integer> M_deb;

    @FXML
    private Spinner<Integer> M_fin;
    
    @FXML
    private TextField prix_gestion;


    @FXML
    private Button button_ajouter;

    @FXML
    private Button button_modifier;

    @FXML
    private Button button_supprimer;

    @FXML
    private ChoiceBox<Map<Integer,String>> modele_gestion;

    @FXML
    private ChoiceBox<Ville> ville_arrivée_gestion;

    @FXML
    private ChoiceBox<Ville> ville_depart_gestion;
    
    
    //////////////////////////// FUNCTION MENU ET GESTION //////////////////////////////////////////////
    
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
    void Add_Billet(ActionEvent event) {
    	
    	Integer id_train = modele_gestion.getValue().keySet().iterator().next();
    	System.out.print(id_train);
    	LocalDate selectedDate = Date_gestion.getValue();

    	// Get the selected hour and minute from the Spinners
    	int hour_deb = H_deb.getValue();
    	int minute_deb = M_deb.getValue();
    	
    	int hour_fin = H_fin.getValue();
    	int minute_fin = M_fin.getValue();
    	
    	Ville v_depart = ville_depart_gestion.getValue();
    	Ville v_arrive = ville_arrivée_gestion.getValue();
    	
    	int price = Integer.parseInt(prix_gestion.getText());

    	// Create a Timestamp object with the selected date, hour, and minute
    	Timestamp time_dep = Timestamp.valueOf(selectedDate.atTime(hour_deb, minute_deb));
    	Timestamp time_fin = Timestamp.valueOf(selectedDate.atTime(hour_fin, minute_fin));
    	
    	
    	System.out.println(time_dep);
    	System.out.println(time_fin);
    	
    	String sql = "INSERT into billet (ID_TRAIN, H_DEPART, H_FIN, V_DEPART, V_ARRIVEE, PRIX_BILLET) "
    			+ "VALUES(?, ?, ?, ?, ?, ?)";

    			
    	
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1,  id_train); // ID_TRAIN
            pst.setTimestamp(2, time_dep); // H_DEPART
            pst.setTimestamp(3, time_fin); // H_FIN
            pst.setInt(4,  v_depart.getId_Ville().getValue()); // V_DEPART
            pst.setInt(5,  v_arrive.getId_Ville().getValue()); // V_ARRIVE
            pst.setInt(6,  price); // PRIX_BILLET
            pst.execute();
			
			listBillet = MySQLConnect.getDataBillet();
			table_billet.setItems(listBillet);

            JOptionPane.showMessageDialog(null, "AJOUT BILLET");
        } 
        catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs valides pour le prix");
	    }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Remplir tous les champs" + e);
        }
    	
    	
    }

    @FXML
    void Del_Billet(ActionEvent event) {
    	
    	/// TODO //
    }

    @FXML
    void Exit_App(ActionEvent event) {
    	
    	/// TODO //
    }
    
    ///////////////////////////////// FUNCTION FILTRE /////////////////////////////////////////////////


    @FXML
    void Search_Date(ActionEvent event) {
    	
    	/// TODO //

    }

    @FXML
    void Search_modele(ActionEvent event) {
    	
    	/// TODO //

    }


    @FXML
    void Update_Billet(ActionEvent event) {
    	
    	/// TODO //

    }
    
    @FXML
    void Search_VIlle_Depart(ActionEvent event) {

    }

    @FXML
    void Search_Ville_Arrivée(ActionEvent event) {

    }

    ObservableList<String> modele_gestion_items;
    
    public void Item_gestion_modele()
    {
	listTrain = MySQLConnect.getDataTrain();
	
	ObservableList<Map<Integer, String>> modele_gestion_items = FXCollections.observableArrayList();

	for (Train train : listTrain) {
	    String modele = train.modeleProperty().getValue();
	    Integer id_train = train.idProperty().getValue();

	    Map<Integer, String> modeleIdMap = new HashMap<>();
	    modeleIdMap.put(id_train, modele);

	    modele_gestion_items.add(modeleIdMap);
	}

	modele_gestion.setItems(modele_gestion_items);

	modele_gestion.setConverter(new StringConverter<Map<Integer, String>>() {
	    @Override
	    public String toString(Map<Integer, String> map) {
	        if (map == null) {
	            return "";
	        } else {
	            Integer id = map.keySet().iterator().next(); // Supposons qu'il y a toujours un seul ID dans la Map
	            String modele = map.get(id);
	            return modele + "\nID: " + id + "\t\t\t"; // Afficher l'ID avec le modèle
	        }
	    }

	    @Override
	    public Map<Integer, String> fromString(String string) {
	        return null; // Conversion inverse, pas nécessaire pour une ChoiceBox non éditable
	    }
	});
    
    }
    
    
    public void Item_gestion_ville(ChoiceBox<Ville> v)
    {
	listVille = MySQLConnect.getDataVille();
	
	ObservableList<Ville> ville_gestion_items = FXCollections.observableArrayList();

	for (Ville ville : listVille) {

	    ville_gestion_items.add(ville);
	}

	v.setItems(ville_gestion_items);

    v.setConverter(new StringConverter<Ville>() {
        @Override
        public String toString(Ville ville) {
            if (ville == null) {
                return "";
            } else {
                return ville.getVille().getValue();
            }
        }

        @Override
        public Ville fromString(String string) {
            return null; // Conversion inverse, pas nécessaire pour une ChoiceBox non éditable
        }
    });
    
    v.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            v.setValue(newValue); // Setter la nouvelle valeur uniquement si elle n'est pas nulle
        }
    });
    
    }
    
    
    
    //////////////////////// INIT Billet ////////////////////////////////////////////////////////////////////////
    
	public void initialize() {
    	
		Item_gestion_modele();
		Item_gestion_ville(ville_arrivée_gestion);
		Item_gestion_ville(ville_depart_gestion);
		H_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Hours: 0-23, initial value: 0
  	    M_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Minutes: 0-59, initial value: 0
  	    H_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Hours: 0-23, initial value: 0
	    M_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Minutes: 0-59, initial value: 0

	    listBillet = MySQLConnect.getDataBillet();
        table_billet.setItems(listBillet);
	    
        col_modele.setCellValueFactory(cellData -> cellData.getValue().getID_TRAIN().get().modeleProperty());
        col_prix.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
        col_Ville_depart.setCellValueFactory(cellData -> cellData.getValue().getV_DEPART().getVille());
        col_ville_arrive.setCellValueFactory(cellData -> cellData.getValue().getV_ARRIVE().getVille());
        col_voyage.setCellValueFactory(cellData ->  Billet.Date_to_string_day(cellData.getValue().getH_DEB()));
        col_H_depart.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_DEB()));
        col_H_arrive.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_FIN()));

    }

}
