package com.efrei.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private javafx.scene.control.TableView<Train> table_train;

    ObservableList<Train> listM;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void initialize() {
        listM = MySQLConnect.getDataUsers();
        table_train.setItems(listM);
        System.out.println(listM);
		
		table_train.parentProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                double parentWidth = ((javafx.scene.Parent)newValue).getLayoutBounds().getWidth();
                table_train.setPrefWidth(parentWidth * 0.7);
            }
        });

        col_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        col_capacite.setCellValueFactory(cellData -> cellData.getValue().capaciteProperty().asObject());
        col_modele.setCellValueFactory(cellData -> cellData.getValue().modeleProperty());
    }
}
