package test.java.com.efrei.models;

import static org.junit.Assert.*;

import org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import com.efrei.MySQLConnect;
import com.efrei.models.Train;

import javafx.collections.ObservableList;

class TrainTest {

	@Test
	void testAddTrain() {
		
		int Id = 8;
		int capacite = 80;
		String modele = "DER78";
		
		
		Train a = new Train(Id,modele,capacite);
		
		assertEquals(a.idProperty().get(), Id);	
		assertEquals(a.capaciteProperty().get(), capacite);
		assertEquals(a.modeleProperty().get(), modele);
		
	}
}
