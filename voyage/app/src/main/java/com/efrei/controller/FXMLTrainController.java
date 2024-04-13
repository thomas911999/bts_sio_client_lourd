package com.efrei.controller;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERREUR");
        }
    }
	
	public void Del_Train()
    {
        Train selectedItem = table_train.getSelectionModel().getSelectedItem();
        String sql = "delete from train where ID_TRAIN = ?";
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, selectedItem.idProperty().getValue().intValue());
			pst.execute();
			listM.remove(selectedItem);

            JOptionPane.showMessageDialog(null, "SUPRESSION TRAIN");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERREUR");
        }
    }

    public void initialize() {
        listM = MySQLConnect.getDataUsers();
        table_train.setItems(listM);
        System.out.println(listM);
		
	/*	table_train.parentProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                double parentWidth = ((javafx.scene.Parent)newValue).getLayoutBounds().getWidth();
                table_train.setPrefWidth(parentWidth * 0.7);
            }
        });*/
		
		
		Train selectedItem = table_train.getSelectionModel().getSelectedItem();

        col_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        col_capacite.setCellValueFactory(cellData -> cellData.getValue().capaciteProperty().asObject());
        col_modele.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());

        
    }
}
