package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientsData {

	private final StringProperty name;
	private final StringProperty surname;
	private final StringProperty amka;
	private final StringProperty dob;
	private final StringProperty sex;
	private final StringProperty topos;
	private final StringProperty ergasia;
	private final StringProperty oikistoriko;
	private final StringProperty prosistoriko;
	private final StringProperty nosos;
	private final StringProperty diagnosi;
	private final StringProperty regdate;
	private final StringProperty tilefono;
	private final StringProperty farmaka;
	private final StringProperty id;
	
	public PatientsData(String name, String surname, String amka, String dob, String sex, String topos
			, String ergasia, String oikistoriko, String prosistoriko, String nosos, String diagnosi, String regdate
			, String tilefono, String farmaka, String id)
	{
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.amka = new SimpleStringProperty(amka);
		this.dob = new SimpleStringProperty(dob);
		this.sex = new SimpleStringProperty(sex);
		this.topos = new SimpleStringProperty(topos);
		this.ergasia = new SimpleStringProperty(ergasia);
		this.oikistoriko = new SimpleStringProperty(oikistoriko);
		this.prosistoriko = new SimpleStringProperty(prosistoriko);
		this.nosos = new SimpleStringProperty(nosos);
		this.diagnosi = new SimpleStringProperty(diagnosi);
		this.regdate = new SimpleStringProperty(regdate);
		this.tilefono = new SimpleStringProperty(tilefono);
		this.farmaka = new SimpleStringProperty(farmaka);
		this.id = new SimpleStringProperty(id);
	}
	
	public String getName(){
		return this.name.get();
		}
	
	public String getSurname(){
		return this.surname.get();
		}
	public String getAmka(){
		return this.amka.get();
		}
	
	public String getDob(){
		return this.dob.get();
		}
	public String getSex(){
		return this.sex.get();
		}
	
	public String getTops(){
		return this.topos.get();
		}
	public String getErgasia(){
		return this.ergasia.get();
		}
	
	public String getOikistoriko(){
		return this.oikistoriko.get();
		}
	public String getProsistoriko(){
		return this.prosistoriko.get();
		}
	
	public String getNosos(){
		return this.nosos.get();
		}
	public String getDiagnosi(){
		return this.diagnosi.get();
		}
	
	public String getRegDate(){
		return this.regdate.get();
		}
	
	public String getTilefono(){
		return this.tilefono.get();
		}
	public String getFarmaka(){
		return this.farmaka.get();
		}
	public String getID(){
		return this.id.get();
		}
	

	

	public void setName(String value){
		this.name.set(value);
	}
	
	public void setSurname(String value){
		this.surname.set(value);
	}
	
	public void setAmka(String value){
		this.amka.set(value);
	}
	
	public void setDob(String value){
		this.dob.set(value);
	}
	
	public void setSex(String value){
		this.sex.set(value);
	}
	public void setTopos(String value){
		this.topos.set(value);
	}
	
	public void setErgasia(String value){
		this.ergasia.set(value);
	}
	
	public void setOikistoriko(String value){
		this.oikistoriko.set(value);
	}
	
	public void setProsistoriko(String value){
		this.prosistoriko.set(value);
	}
	
	public void setNosos(String value){
		this.nosos.set(value);
	}
	public void setDiagnosi(String value){
		this.diagnosi.set(value);
	}
	
	public void setRegdate(String value){
		this.regdate.set(value);
	}
	
	public void setTilefono(String value){
		this.tilefono.set(value);
	}
	
	public void setFarmaka(String value){
		this.farmaka.set(value);
	}
	public void setID(String value){
		this.id.set(value);
	}
	
	
	public StringProperty nameProperty(){
		return this.name;
	}
	
	public StringProperty surnameProperty(){
		return this.surname;
	}
	
	public StringProperty amkaProperty(){
		return this.amka;
	}
	
	public StringProperty dobProperty(){
		return this.dob;
	}
	public StringProperty sexProperty(){
		return this.sex;
	}
	public StringProperty toposProperty(){
		return this.topos;
	}
	public StringProperty ergasiaProperty(){
		return this.ergasia;
	}
	
	public StringProperty oikistorikoProperty(){
		return this.oikistoriko;
	}
	
	public StringProperty prosistorikoProperty(){
		return this.prosistoriko;
	}
	
	public StringProperty nososProperty(){
		return this.nosos;
	}
	public StringProperty diagnosiProperty(){
		return this.diagnosi;
	}
	public StringProperty regdateProperty(){
		return this.regdate;
	}
	
	public StringProperty tilefonoProperty(){
		return this.tilefono;
	}
	
	public StringProperty farmakaProperty(){
		return this.farmaka;
	}
	
	public StringProperty idProperty(){
		return this.id;
	}
	
}
