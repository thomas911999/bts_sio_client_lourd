package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXMLBillet {
	
	
	
	/////// MENU CONTROLLER /////////////////////
	
    @FXML
    private MenuItem modif_menu;

    @FXML
    private MenuItem supprimer_menu;

    
    @FXML
    private MenuItem ajout_menu;
    

    @FXML
    private MenuItem deconnexion_menu;
    
    @FXML
    private MenuItem quitter_menu;

    
    
    
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

    
  //  Map<String, Integer> modeleIdMap = new HashMap<>();	
    
    ////// FILTRE CONTROLLER ////////////////////
	
    @FXML
    private ChoiceBox<Ville> ville_depart_filtre;
    
    @FXML
    private ChoiceBox<Ville> ville_arrivée_filtre;
    
    @FXML
    private ChoiceBox<Train> modele_filtre;
    
    @FXML
    private DatePicker Date_filtre;
	
    @FXML
    private Button Reset_date;
    
    @FXML
    private Button Reset_all;
	
	////// GESTION CONTROLLER /////////////////// 

    
    @FXML
    private ChoiceBox<Train> modele_gestion;
    
    
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
    private ChoiceBox<Ville> ville_arrivée_gestion;

    @FXML
    private ChoiceBox<Ville> ville_depart_gestion;
    

    
    
    //////////////////////////// FUNCTION MENU ET GESTION //////////////////////////////////////////////
    
    @FXML
    private void handleExitMenuItemAction(ActionEvent event) {
        Platform.exit(); // Close JavaFX application
        System.exit(0); // Ensure JVM terminates
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
    void switch_connexion(ActionEvent event) {
    	changeScene("/com/efrei/Connexion.fxml");
    }

    
    
    @FXML
    void Add_Billet(ActionEvent event) {
    	
    	Train train = modele_gestion.getValue();
    	LocalDate selectedDate = Date_gestion.getValue();

    	// Get the selected hour and minute from the Spinners
    	int hour_deb = H_deb.getValue();
    	int minute_deb = M_deb.getValue();
    	
    	int hour_fin = H_fin.getValue();
    	int minute_fin = M_fin.getValue();
    	
    	Ville v_depart = ville_depart_gestion.getValue();
    	Ville v_arrive = ville_arrivée_gestion.getValue();
    	
    	float price = Float.parseFloat(prix_gestion.getText());

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
            pst.setInt(1,  train.idProperty().getValue()); // ID_TRAIN
            pst.setTimestamp(2, time_dep); // H_DEPART
            pst.setTimestamp(3, time_fin); // H_FIN
            pst.setInt(4,  v_depart.getId_Ville().getValue()); // V_DEPART
            pst.setInt(5,  v_arrive.getId_Ville().getValue()); // V_ARRIVE
            pst.setFloat(6,  price); // PRIX_BILLET
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
    	
    	Billet selectedItem = table_billet.getSelectionModel().getSelectedItem();
	    if (selectedItem == null) {
	        JOptionPane.showMessageDialog(null, "Aucun billet sélectionné.");
	        return;
	    }
	    
    	String sql = "DELETE FROM billet WHERE ID_BILLET = ?";
	    
	    try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedItem.getID_BILLET().getValue());
			pst.execute();
			listBillet.remove(selectedItem);

            JOptionPane.showMessageDialog(null, "SUPRESSION BILLET");
        } 
        
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERREUR" + e);
        }
	    
	}
    
    @FXML
    void Update_Billet(ActionEvent event) {
    	
		 Billet selectedItem = table_billet.getSelectionModel().getSelectedItem();
		    if (selectedItem == null) {
		        JOptionPane.showMessageDialog(null, "Aucun billet sélectionné.");
		        return;
		    }
		    
	    	Train train = modele_gestion.getValue();
	    	LocalDate selectedDate = Date_gestion.getValue();

	    	// Get the selected hour and minute from the Spinners
	    	int hour_deb = H_deb.getValue();
	    	int minute_deb = M_deb.getValue();
	    	
	    	int hour_fin = H_fin.getValue();
	    	int minute_fin = M_fin.getValue();
	    	
	    	Ville v_depart = ville_depart_gestion.getValue();
	    	Ville v_arrive = ville_arrivée_gestion.getValue();
	    	
	    	float price = Float.parseFloat(prix_gestion.getText());

	    	// Create a Timestamp object with the selected date, hour, and minute
	    	Timestamp time_dep = Timestamp.valueOf(selectedDate.atTime(hour_deb, minute_deb));
	    	Timestamp time_fin = Timestamp.valueOf(selectedDate.atTime(hour_fin, minute_fin));
		    

	    	String sql = "UPDATE billet SET ID_Train = ?, H_DEPART = ?, H_FIN = ?, V_DEPART = ?, V_ARRIVEE = ?, PRIX_BILLET = ? WHERE ID_BILLET = ?";
		    
		    try {
				conn = MySQLConnect.connectDb();
	            pst = conn.prepareStatement(sql);
				pst.setInt(1,  train.idProperty().getValue()); // ID_TRAIN
	            pst.setTimestamp(2, time_dep); // H_DEPART
	            pst.setTimestamp(3, time_fin); // H_FIN
	            pst.setInt(4,  v_depart.getId_Ville().getValue()); // V_DEPART
	            pst.setInt(5,  v_arrive.getId_Ville().getValue()); // V_ARRIVE
	            pst.setFloat(6,  price); // PRIX_BILLET
	            pst.setInt(7, selectedItem.getID_BILLET().getValue());
	            pst.executeUpdate();

		        int affectedRows = pst.executeUpdate();
		        if (affectedRows > 0) {
		        	
			    	time_dep = Timestamp.valueOf(selectedDate.atTime(hour_deb, minute_deb));
			    	time_fin = Timestamp.valueOf(selectedDate.atTime(hour_fin, minute_fin));
		            // Update the item in the listM
		            selectedItem.setID_TRAIN(train);
		            selectedItem.setH_DEB(time_dep.toLocalDateTime());
		            selectedItem.setH_FIN(time_fin.toLocalDateTime());
		            selectedItem.setV_ARRIVE(v_arrive);
		            selectedItem.setV_DEPART(v_depart);
		            selectedItem.setPrix(price);
		            table_billet.refresh();
		            
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
    void Exit_App(ActionEvent event) {
    	
    	/// TODO //
    }
    

    
    ///////////////////////////////// FUNCTION FILTRE /////////////////////////////////////////////////

    
    @FXML
    void Search(ActionEvent event) {

    	
    	FilteredList<Billet> filteredData = new FilteredList<>(listBillet, p -> true);

    	// Appliquez le prédicat de filtrage en fonction de vos critères
    	filteredData.setPredicate(billet -> {
    	    Ville selectedVilleDepart = ville_depart_filtre.getValue();
    	    Ville selectedVilleArrivee = ville_arrivée_filtre.getValue();
    	    Train selectedModel = modele_filtre.getValue();
    	    LocalDate selectedDate = Date_filtre.getValue();
        	
    	    LocalDateTime startOfDay = null;
    	    SimpleStringProperty formated_date = new SimpleStringProperty("");

    	    // Vérifiez si la date sélectionnée n'est pas null
    	    if(selectedDate != null) {
    	        startOfDay = selectedDate.atStartOfDay();
    	        formated_date.set(Billet.Date_to_string_day(startOfDay).get());
    	    }
        	
        	boolean dateMatch = selectedDate == null || Billet.Date_to_string_day(billet.getH_DEB().getValue()).get().equals(formated_date.get());

    	    
    	    boolean modeleMatch = selectedModel == null || billet.getID_TRAIN().getValue().modeleProperty().get()
    	    																	.equals(selectedModel.modeleProperty().get()) ;
    	    // Filtre par la ville de départ si elle est sélectionnée
    	    boolean departMatch = selectedVilleDepart == null || billet.getV_DEPART().getVille().get()
    	    																		.equals(selectedVilleDepart.getVille().get());

    	    // Filtre par la ville d'arrivée si elle est sélectionnée
    	    boolean arriveeMatch = selectedVilleArrivee == null || billet.getV_ARRIVE().getVille().get()
    	    																		.equals(selectedVilleArrivee.getVille().get());

    	    // Retourne vrai si le billet correspond aux critères de ville de départ et d'arrivée
    	    return dateMatch && modeleMatch && departMatch && arriveeMatch;
    	});

    	table_billet.setItems(filteredData);
    	
    }

    

    @FXML
    void reset(ActionEvent event) {
    	
    	Date_filtre.setValue(null);
    	table_billet.refresh();
    	Search(event);
    
    }
    

    @FXML
    void reset_all(ActionEvent event) {
        ville_depart_filtre.setValue(null);
        ville_arrivée_filtre.setValue(null);
        modele_filtre.setValue(null);
        Date_filtre.setValue(null);
    }
    
    ObservableList<String> modele_gestion_items;
    
    public void Item__modele(ChoiceBox<Train> choicebox_modele)
    {
		listTrain = MySQLConnect.getDataTrain();
		
		ObservableList<Train> modele_items = FXCollections.observableArrayList();
					
		for (Train train : listTrain) {
		    modele_items.add(train);
		}
	
		choicebox_modele.setItems(modele_items);
	
		choicebox_modele.setConverter(new StringConverter<Train>() {
		    @Override
		    public String toString(Train train) {
		        if (train == null) {
		            return "Aucun";
		        } else {
		            Integer id = train.idProperty().get(); // Supposons qu'il y a toujours un seul ID dans la Map
		            String modele = train.modeleProperty().getValue();
		            return modele + "\nID: " + id + ""; // Afficher l'ID avec le modèle
		        }
		    }
		    @Override
		    public Train fromString(String string) {
		        return null; // Conversion inverse, pas nécessaire pour une ChoiceBox non éditable
		    }
		});
		
		choicebox_modele.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		        {
		        	//int selectedIndex = choicebox_modele.getSelectionModel().getSelectedIndex();
		        	var train = choicebox_modele.getValue();

		        		if (choicebox_modele.getItems().remove(train))
		        			{
		        				choicebox_modele.getItems().add(0, newValue);
		        				choicebox_modele.setValue(newValue); // Sélectionner la nouvelle valeur
		        			}
		        }
		    });
    }

    
    public void Item_gestion_ville(ChoiceBox<Ville> v) {
        // Récupérer toutes les villes depuis la base de données
        listVille = MySQLConnect.getDataVille();
        
        // Créer une liste observable à partir de la liste des villes
        ObservableList<Ville> villeItems = FXCollections.observableArrayList(listVille);

        // Mettre à jour les éléments de la ChoiceBox avec les villes
        v.setItems(villeItems);

        // Définir le convertisseur pour afficher le nom de la ville dans la ChoiceBox
        v.setConverter(new StringConverter<Ville>() {
            @Override
            public String toString(Ville ville) {
                return (ville == null) ? "Aucun" : ville.getVille().get();
            }

            @Override
            public Ville fromString(String string) {
                return null; // Pas nécessaire pour une ChoiceBox non éditable
            }
        });

        // Écouter les changements de sélection dans la ChoiceBox
        v.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	var ville = v.getValue();
        	//if (selectedIndex != -1) {
        	   if( v.getItems().remove(ville))
        	   {
        	    v.getItems().add(0, ville);
        	    v.setValue(ville); // Sélectionner la nouvelle valeur
        	   }
        });
    }

    
    public static <T> void addNullValue(ChoiceBox<T> choiceBox) {
        ObservableList<T> items = choiceBox.getItems();
        items.add(0, null);
        choiceBox.setItems(items);
    }
    
    
    //////////////////////// INIT Billet ////////////////////////////////////////////////////////////////////////
    
	public void initialize() {
		
		quitter_menu.setOnAction(null);
    	
		Item__modele(modele_gestion);
		
		Item__modele(modele_filtre);
		addNullValue(modele_filtre);
		
		Item_gestion_ville(ville_depart_filtre);
		addNullValue(ville_depart_filtre);
		
		Item_gestion_ville(ville_arrivée_filtre);
		addNullValue(ville_arrivée_filtre);		
		
		
		H_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Hours: 0-23, initial value: 0
  	    M_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Minutes: 0-59, initial value: 0
  	    H_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12)); // Hours: 0-23, initial value: 0
	    M_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)); // Minutes: 0-59, initial value: 0

	    listBillet = MySQLConnect.getDataBillet();
        table_billet.setItems(listBillet);
	    
        table_billet.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	modele_gestion.setValue(newSelection.getID_TRAIN().getValue());
            	Date_gestion.setValue(newSelection.getH_DEB().get().toLocalDate());
            	var  initialTime = newSelection.getH_DEB().get();
            	H_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialTime.getHour()));
            	M_deb.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialTime.getMinute()));
            	var  initialTime_Fin = newSelection.getH_FIN().get();
            	H_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialTime_Fin.getHour()));
            	M_fin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, initialTime_Fin.getMinute()));
            	prix_gestion.setText(newSelection.getPrix().getValue().toString());
            	ville_depart_gestion.setValue(newSelection.getV_DEPART());
         	    ville_arrivée_gestion.setValue(newSelection.getV_ARRIVE());

            }
        });
        
		Item_gestion_ville(ville_arrivée_gestion);
		Item_gestion_ville(ville_depart_gestion);
        
        
        col_modele.setCellValueFactory(cellData -> cellData.getValue().getID_TRAIN().get().modeleProperty());
        col_prix.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
        col_Ville_depart.setCellValueFactory(cellData -> cellData.getValue().getV_DEPART().getVille());
        col_ville_arrive.setCellValueFactory(cellData -> cellData.getValue().getV_ARRIVE().getVille());
        col_voyage.setCellValueFactory(cellData ->  Billet.Date_to_string_day(cellData.getValue().getH_DEB().getValue()));
        col_H_depart.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_DEB()));
        col_H_arrive.setCellValueFactory(cellData ->  Billet.Date_to_string_hour(cellData.getValue().getH_FIN()));
        
        ville_depart_filtre.setOnAction(event -> Search(event));
        ville_arrivée_filtre.setOnAction(event -> Search(event));
        modele_filtre.setOnAction(event -> Search(event));
        Date_filtre.setOnAction(event -> Search(event));
        
       // quitter_menu.setOnAction(event -> handleExitMenuItemAction(event));

    }

}
