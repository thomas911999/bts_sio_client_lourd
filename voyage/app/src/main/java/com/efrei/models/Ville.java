package com.efrei.models;

import com.efrei.MySQLConnect;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Ville {
	
    private IntegerProperty Id_Ville;
    private StringProperty Ville;
    
    
    public Ville(int id_ville, String ville)
    {
        this.setId_Ville(new SimpleIntegerProperty(id_ville));
        this.setVille(new SimpleStringProperty(ville));
    }


	public IntegerProperty getId_Ville() {
		return Id_Ville;
	}


	public void setId_Ville(IntegerProperty id_Ville) {
		Id_Ville = id_Ville;
	}


	public StringProperty getVille() {
		return Ville;
	}


	public void setVille(StringProperty ville) {
		Ville = ville;
	}
    
	
	public static Ville get_ville(Integer id_ville) {
	    ObservableList<Ville> getDataVille = MySQLConnect.getDataVille();
	    
	    for (Ville v : getDataVille) {
	        Integer v_id = v.getId_Ville().getValue(); // Getting the Integer value from IntegerProperty
	        if (v_id.equals(id_ville)) // Using equals method for comparison
	            return v;
	    }
	    return null;
	}
}
