package com.efrei.controller;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import com.efrei.MySQLConnect;
import com.efrei.models.Train;


public class FXMLTrainController {
	
	@FXML
    private Button button_ajouter;

    @FXML
    private Button button_modifier;

    @FXML
    private Button button_supprimer;

    @FXML
    private TextField capacite_field;

	@FXML
    private TextField id_field;

    @FXML
    private TextField modele_field;

    @FXML
    private TableColumn<Train, Integer> col_capacite;

    @FXML
    private TableColumn<Train, Integer> col_id;

    @FXML
    private TableColumn<Train, String> col_modele;

    @FXML
    private javafx.scene.control.TableView<Train> table_train;

    ObservableList<Train> listM;

    int index = -1;

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

    // Method to switch to the reservation scene
    @FXML
    public void Switch_reservation() {
        changeScene("/com/efrei/scene.fxml"); // Adjust the path as needed
    }
    
	public void Add_Train()
    {
        
        String sql = "insert into train (ID_TRAIN, MODELE, CAPACITE) VALUES (?,?,?)";
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1,  Integer.parseInt(id_field.getText()));
            pst.setInt(3, Integer.parseInt( capacite_field.getText()));
            pst.setString(2, modele_field.getText());
			pst.execute();
			listM = MySQLConnect.getDataUsers();
			table_train.setItems(listM);

            JOptionPane.showMessageDialog(null, "AJOUT TRAIN");
        } 
        catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs valides pour l'ID et la capacité du train.");
	    }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "l'ID du train est déja dans ta table , veuillez en chosir un autre");
        }
    }
	
	public void Del_Train()
    {
        Train selectedItem = table_train.getSelectionModel().getSelectedItem();
        String sql = "delete from train where ID_TRAIN = ?";
	    if (selectedItem == null) {
	        JOptionPane.showMessageDialog(null, "Aucun train sélectionné.");
	        return;
	    }
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedItem.idProperty().getValue().intValue());
			pst.execute();
			listM.remove(selectedItem);

            JOptionPane.showMessageDialog(null, "SUPRESSION TRAIN");
        } 
        
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERREUR");
        }
    }

	public void Update_Train()
    {
		 Train selectedItem = table_train.getSelectionModel().getSelectedItem();
		    if (selectedItem == null) {
		        JOptionPane.showMessageDialog(null, "Aucun train sélectionné.");
		        return;
		    }

		    String sql = "UPDATE train SET ID_Train = ?, Modele = ?, CAPACITE = ? WHERE ID_Train = ?";
		    
		    try {
				conn = MySQLConnect.connectDb();
	            pst = conn.prepareStatement(sql);
	            pst.setInt(1,  Integer.parseInt(id_field.getText()));
	            pst.setString(2, modele_field.getText());
	            pst.setInt(3, Integer.parseInt( capacite_field.getText()));
	            pst.setInt(4, selectedItem.idProperty().getValue().intValue());
	            pst.executeUpdate();

		        int affectedRows = pst.executeUpdate();
		        if (affectedRows > 0) {
		            // Update the item in the listM
		            selectedItem.SetId(Integer.parseInt(id_field.getText()));
		            selectedItem.SetModele(modele_field.getText());
		            selectedItem.SetCapacite(Integer.parseInt(capacite_field.getText()));
		            table_train.refresh();
		            
		            JOptionPane.showMessageDialog(null, "Mise à jour du train réussie.");
		        } else {
		            JOptionPane.showMessageDialog(null, "Échec de la mise à jour du train.");
		        }
		    } catch (SQLException e) {
		        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du train : " + e.getMessage());
		    } catch (NumberFormatException e) {
		        JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs valides pour l'ID et la capacité du train.");
		    } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    }
	
    public void initialize() {
    	
        listM = MySQLConnect.getDataUsers();
        table_train.setItems(listM);
        System.out.println(listM);

        table_train.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	id_field.setText(newSelection.idProperty().getValue().toString());
            	capacite_field.setText(newSelection.capaciteProperty().getValue().toString());
            	modele_field.setText(newSelection.modeleProperty().getValue().toString());
            }
        });
		
        col_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        col_capacite.setCellValueFactory(cellData -> cellData.getValue().capaciteProperty().asObject());
        col_modele.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());

        
    }
}
