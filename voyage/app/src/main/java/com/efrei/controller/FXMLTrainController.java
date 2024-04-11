package com.efrei.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.efrei.MySQLConnect;
import main.java.com.efrei.models.Train;

public class FXMLTrainController {

    @FXML
    private TableColumn<Train, Integer> col_capacite;

    @FXML
    private TableColumn<Train, Integer> col_id;

    @FXML
    private TableColumn<Train, String> col_modele;

    @FXML
    private TableView<Train> table_train;

    ObservableList<Train> listM;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void initialize() {

		listM = MySQLConnect.getDataUsers();
        table_train.setItems(listM);
		System.out.println(listM);	
		
		col_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        col_capacite.setCellValueFactory(cellData -> cellData.getValue().capaciteProperty().asObject());
        col_modele.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());
		
	}
}
