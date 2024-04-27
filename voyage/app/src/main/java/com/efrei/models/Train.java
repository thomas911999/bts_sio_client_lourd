package com.efrei.models;

import java.util.Objects;

import com.efrei.MySQLConnect;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

public class Train {
    
    private IntegerProperty ID_TRAIN;
    private IntegerProperty CAPACITE;
    private StringProperty MODELE;

    public Train(int id_train, String modele, int capacite)
    {
        this.ID_TRAIN = new SimpleIntegerProperty(id_train);
        this.CAPACITE = new SimpleIntegerProperty(capacite);
        this.MODELE = new SimpleStringProperty(modele);
    }

    public IntegerProperty idProperty() {
        return ID_TRAIN;
    }

    public IntegerProperty capaciteProperty() {
        return CAPACITE;
    }

    public StringProperty modeleProperty() {
        return MODELE;
    }
    
    public void SetId(Integer id) {
        this.ID_TRAIN = new SimpleIntegerProperty(id);
    }

    public void SetCapacite(Integer capacite) {
        this.CAPACITE = new SimpleIntegerProperty(capacite);
    }
    
    public void SetModele(String modele) {
        this.MODELE = new SimpleStringProperty(modele);
    }

	public static Train get_Train(Integer id_train) {
	    ObservableList<Train> getDataTrain = MySQLConnect.getDataTrain();
	    
	    for (Train t : getDataTrain) {
	        Integer t_train = t.idProperty().getValue(); // Getting the Integer value from IntegerProperty
	        if (t_train.equals(id_train)) // Using equals method for comparison
	            return t;
	    }
	    return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(CAPACITE, ID_TRAIN, MODELE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Train other = (Train) obj;
		return Objects.equals(CAPACITE.getValue(), other.CAPACITE.getValue()) && Objects.equals(ID_TRAIN.getValue(), other.ID_TRAIN.getValue())
				&& Objects.equals(MODELE.getValue(), other.MODELE.getValue());
	}

    // Other methods remain the same
}

