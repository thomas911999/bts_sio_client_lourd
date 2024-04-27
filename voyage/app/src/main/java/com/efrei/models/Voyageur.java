package com.efrei.models;

import com.efrei.MySQLConnect;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Voyageur {
	
    private IntegerProperty Id_voyageur;
    private StringProperty Login;
    private StringProperty Nom;
	private StringProperty Prenom;
    private StringProperty Adresse;
    
    
	public Voyageur(Integer id_voyageur, String login, String nom, String prenom,
			String adresse) {
		Id_voyageur = new SimpleIntegerProperty(id_voyageur); 
		Login = new SimpleStringProperty(login);
		Nom = new SimpleStringProperty(nom);
		Prenom = new SimpleStringProperty(prenom);
		Adresse = new SimpleStringProperty(adresse);
	}


	public IntegerProperty getId_voyageur() {
		return Id_voyageur;
	}


	public void setId_voyageur(Integer id_voyageur) {
		Id_voyageur.set(id_voyageur);
	}


	public StringProperty getLogin() {
		return Login;
	}


	public void setLogin(String login) {
		Login.set(login);
	}


	public StringProperty getNom() {
		return Nom;
	}


	public void setNom(String nom) {
		Nom.set(nom);
	}


	public StringProperty getPrenom() {
		return Prenom;
	}


	public void setPrenom(String prenom) {
		Prenom.set(prenom);
	}


	public StringProperty getAdresse() {
		return Adresse;
	}


	public void setAdresse(String adresse) {
		Adresse.set(adresse);
	}

    public static Voyageur GetVoyageur(Integer Id_voyageur)
	{
		 ObservableList<Voyageur> getDataVoyageur = MySQLConnect.getDataVoyageur();
		    
		    for (Voyageur v : getDataVoyageur) {
		        Integer v_id = v.getId_voyageur().getValue(); // Getting the Integer value from IntegerProperty
		        if (v_id.equals(Id_voyageur)) // Using equals method for comparison
		            return v;
		    }
		    return null;
	}
}
