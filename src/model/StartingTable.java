package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StartingTable {
	private final StringProperty name;
	private final StringProperty surname;
	private final StringProperty dob;
	private final StringProperty amka;
	private final StringProperty region;

	public StartingTable(String name, String surname, String dob, String amka, String region)
	{
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.dob = new SimpleStringProperty(dob);
		this.amka = new SimpleStringProperty(amka);
		this.region = new SimpleStringProperty(region);
	}
	
	public String getName(){
		return this.name.get();
		}
	
	public String getSurname() {
		return this.surname.get();
		}
	
	public String getDob(){
		return this.dob.get();
		}
	
	public String getAmka(){
		return this.amka.get();
		}
	
	public String getRegion(){
		return this.region.get();
		}
	

	public void setName(String value){
		this.name.set(value);
	}
	
	public void setSurname(String value){
		this.surname.set(value);
	}
	
	public void setDob(String value){
		this.dob.set(value);
	}
	
	public void setAmka(String value){
		this.amka.set(value);
	}
	
	public void setRegion(String value){
		this.region.set(value);
	}
	
	
	public StringProperty nameProperty(){
		return this.name;
	}
	
	public StringProperty surnameProperty(){
		return this.surname;
	}
	
	public StringProperty dobProperty(){
		return this.dob;
	}
	
	public StringProperty amkaProperty(){
		return this.amka;
	}
	public StringProperty regionProperty(){
		return this.region;
	}
}

