package com.efrei.models;

import java.time.LocalDateTime;

import com.efrei.MySQLConnect;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class Reservation {

    private ObjectProperty<Billet> billet;
    private ObjectProperty<Voyageur> ID_VOYAGEUR;
    private ObjectProperty<LocalDateTime> H_RESERVATION;   
    private IntegerProperty nb_enfant;
    private IntegerProperty nb_adulte;
    private IntegerProperty nb_senor;
    
    public Reservation(Billet billet,Voyageur ID_VOYAGEUR, LocalDateTime H_RESERVATION, Integer nb_enfant, Integer nb_adulte, Integer nb_senor )
    {
    	this.billet = new SimpleObjectProperty<>(billet);
    	this.ID_VOYAGEUR = new SimpleObjectProperty<>(ID_VOYAGEUR);
    	this.H_RESERVATION = new SimpleObjectProperty<>(H_RESERVATION);
    	this.nb_enfant = new SimpleIntegerProperty(nb_enfant);
    	this.nb_adulte = new SimpleIntegerProperty(nb_adulte);
    	this.nb_senor = new SimpleIntegerProperty(nb_senor);
    	
    }
    
	public ObjectProperty<Billet> getBillet() { 
		return billet;
	}

	public void setBillet(Billet billet) {
		this.billet.set(billet);
	}
	
	public ObjectProperty<Voyageur> getVoyageur() {
		return ID_VOYAGEUR;
	}
	
	public void setVoyageur(Voyageur ID_VOYAGEUR) {
		this.setVoyageur(ID_VOYAGEUR);
	}
	
	public ObjectProperty<LocalDateTime> getH_RESERVATION() {
		return H_RESERVATION;
	}
	
	public void setH_RESERVATION(LocalDateTime H_RESERVATION) {
		this.H_RESERVATION.set(H_RESERVATION);
	}
	
	
	public IntegerProperty getNb_enfant() {
		return nb_enfant;
	}

	public void setNb_enfant(Integer nb_enfant) {
		this.nb_enfant.set(nb_enfant);
	}

	public IntegerProperty getNb_adulte() {
		return nb_adulte;
	}

	public void setNb_adulte(Integer nb_adulte) {
		this.nb_adulte.set(nb_adulte);
	}

	public IntegerProperty getNb_senor() {
		return nb_senor;
	}

	public void setNb_senor(Integer nb_senor) {
		this.nb_senor.set(nb_senor);
	}
	
}
