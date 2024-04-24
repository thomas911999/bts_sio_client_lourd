package com.efrei.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Billet {
	
    private ObjectProperty<Train> ID_TRAIN;
    private IntegerProperty ID_RESERVATION;
    private ObjectProperty<Ville> V_DEPART;
    private ObjectProperty<Ville> V_ARRIVE;
    private FloatProperty prix;
    private ObjectProperty<LocalDateTime> H_DEB;        
    private ObjectProperty<LocalDateTime> H_FIN;
    
    public Billet(Train TRAIN, int ID_RESERVATION, Ville villeDepart, Ville villeArrivee, float prix, LocalDateTime H_deb, LocalDateTime H_Fin) {
        this.ID_TRAIN = new SimpleObjectProperty<>(TRAIN);
        this.ID_RESERVATION = new SimpleIntegerProperty(ID_RESERVATION);
        this.V_DEPART= new SimpleObjectProperty<>(villeDepart);
        this.V_ARRIVE = new SimpleObjectProperty<>(villeArrivee);
        this.prix = new SimpleFloatProperty(prix);
        this.H_DEB = new SimpleObjectProperty<>(H_deb);
        this.H_FIN = new SimpleObjectProperty<>(H_Fin);
    }
    
    public ObjectProperty<Train> getID_TRAIN() {
        return ID_TRAIN;
    }

    public void setID_TRAIN(Train ID_TRAIN) {
        this.ID_TRAIN.set(ID_TRAIN);
    }

    public IntegerProperty getID_RESERVATION() {
        return ID_RESERVATION;
    }

    public void setID_RESERVATION(int ID_RESERVATION) {
        this.ID_RESERVATION.set(ID_RESERVATION);
    }

    public FloatProperty getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix.set(prix);
    }

    public ObjectProperty<LocalDateTime> getH_DEB() {
        return H_DEB;
    }

    public void setH_DEB(LocalDateTime H_DEB) {
        this.H_DEB.set(H_DEB);
    }

    public ObjectProperty<LocalDateTime> getH_FIN() {
        return H_FIN;
    }

    public void setH_FIN(LocalDateTime H_FIN) {
        this.H_FIN.set(H_FIN);
    }
    

    public Ville getV_DEPART() {
        return V_DEPART.get();
    }

    public void setV_DEPART(Ville V_DEPART) {
        this.V_DEPART.set(V_DEPART);
    }

    public ObjectProperty<Ville> V_DEPARTProperty() {
        return V_DEPART;
    }
    
    public Ville getV_ARRIVE() {
        return V_ARRIVE.get();
    }

    public void setV_ARRIVE(Ville V_ARRIVE) {
        this.V_ARRIVE.set(V_ARRIVE);
    }

    public ObjectProperty<Ville> V_ARRIVEProperty() {
        return V_ARRIVE;
    }
    
    public static StringProperty Date_to_string_hour(ObjectProperty<LocalDateTime> Time)
    {
    	if (Time != null && Time.get() != null) {
            LocalDateTime dateTime = Time.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedDateTime = dateTime.format(formatter);
            return new SimpleStringProperty(formattedDateTime);
        } else {
            return new SimpleStringProperty("");
        }
    }
    
    public static StringProperty Date_to_string_day(LocalDateTime objectProperty)
    {
    	if (objectProperty != null ) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDateTime = objectProperty.format(formatter);
            return new SimpleStringProperty(formattedDateTime);
        } else {
            return new SimpleStringProperty("");
        }
    }
}
