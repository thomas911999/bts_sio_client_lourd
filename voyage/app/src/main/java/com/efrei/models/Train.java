package main.java.com.efrei.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
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

    // Other methods remain the same
}

