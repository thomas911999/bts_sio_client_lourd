package com.efrei.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.efrei.MySQLConnect;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Billet {
	
	
	private IntegerProperty ID_BILLET;
    private ObjectProperty<Train> ID_TRAIN;
    private ObjectProperty<Ville> V_DEPART;
    private ObjectProperty<Ville> V_ARRIVE;
    private FloatProperty prix;
    private ObjectProperty<LocalDateTime> H_DEB;        
    private ObjectProperty<LocalDateTime> H_FIN;
    
    public Billet(Integer ID_BILLET, Train TRAIN, Ville villeDepart, Ville villeArrivee, float prix, LocalDateTime H_deb, LocalDateTime H_Fin) {
        this.ID_BILLET = new SimpleIntegerProperty(ID_BILLET);
    	this.ID_TRAIN = new SimpleObjectProperty<>(TRAIN);
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
    
    public static StringProperty Date_to_string_day_hour(ObjectProperty<LocalDateTime> Time)
    {
    	if (Time != null && Time.get() != null) {
            LocalDateTime dateTime = Time.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
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

	@Override
	public int hashCode() {
		return Objects.hash(H_DEB, H_FIN, ID_TRAIN, V_ARRIVE, V_DEPART, prix);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Billet other = (Billet) obj;
		return Objects.equals(H_DEB.getValue(), other.H_DEB.getValue())
				&& Objects.equals(ID_TRAIN.getValue(), other.ID_TRAIN.getValue()) && Objects.equals(V_ARRIVE.getValue(), other.V_ARRIVE.getValue())
				&& Objects.equals(V_DEPART.getValue(), other.V_DEPART.getValue());
	}

	public static Billet get_Billet(Train train, LocalDateTime h_DEB, Ville v_dep, Ville v_arr) {
		ObservableList<Billet> getDataBillet = MySQLConnect.getDataBillet();
	    
	    for (Billet b : getDataBillet) {
	    	
	    	Train b_train = b.getID_TRAIN().getValue();
	    	LocalDateTime b_h_deb = b.getH_DEB().getValue();
	    	Ville b_v_dep = b.getV_DEPART();
	    	Ville b_v_arr = b.getV_ARRIVE();
	    	
	    	if((b_train.idProperty().get() == (train.idProperty().get())) && b_v_dep.equals(v_dep) && b_v_arr.equals(v_arr) && b_h_deb.equals(h_DEB) ) 
	    		return b;
	    }
	    return null;
	}
	
	public static Billet get_Billet(Integer id_billet) {
		ObservableList<Billet> getDataBillet = MySQLConnect.getDataBillet();
	    
	    for (Billet b : getDataBillet) {
	    	
	    	if(id_billet == (b.getID_BILLET().getValue()))
	    		return b;
	    }
	    return null;
	}

	public IntegerProperty getID_BILLET() {
		return ID_BILLET;
	}

	public void setID_BILLET(IntegerProperty iD_BILLET) {
		ID_BILLET = iD_BILLET;
	}

}
