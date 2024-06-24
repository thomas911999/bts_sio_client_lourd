package com.efrei.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.efrei.MySQLConnect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLConnexion {

    @FXML
    private Button connexion_bouton;

    @FXML
    private Text error_text;

    @FXML
    private TextField login_input;

    @FXML
    private PasswordField mdp_input;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    private void changeScene(String fxml) {
        try {
            Stage stage = (Stage) connexion_bouton.getScene().getWindow();
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
	private void connect(KeyEvent event) {
		
		if (event.getCode() != KeyCode.ENTER)
			return;
		var login = login_input.getText();
    	var mdp = mdp_input.getText();
    	
        String sql = "SELECT * FROM voyageur WHERE login = ? AND mdp = ? AND isadmin = TRUE";
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, mdp);
			rs = pst.executeQuery();
			int countrow = 0;
			
			while(rs.next())
				countrow++;
			
			if (countrow == 0)
			{
				error_text.setVisible(true);
			}
			else
			{
				changeScene("/com/efrei/Train.fxml");
			}
				        } 
        catch (NumberFormatException e) {
        	  JOptionPane.showMessageDialog(null, "erreur : " + e);
	    }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur : " + e);
        }
		
	}

    @FXML
    void connect(ActionEvent event) {
    	var login = login_input.getText();
    	var mdp = mdp_input.getText();
    	
        String sql = "SELECT * FROM voyageur WHERE login = ? AND mdp = ? AND isadmin = TRUE";
        try {
			conn = MySQLConnect.connectDb();
            pst = conn.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, mdp);
			rs = pst.executeQuery();
			int countrow = 0;
			
			while(rs.next())
				countrow++;
			
			if (countrow == 0)
			{
				error_text.setVisible(true);
			}
			else
			{
				changeScene("/com/efrei/Train.fxml");
			}
				        } 
        catch (NumberFormatException e) {
        	  JOptionPane.showMessageDialog(null, "erreur : " + e);
	    }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur : " + e);
        }
    }

}