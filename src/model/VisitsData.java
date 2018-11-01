package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VisitsData {

	private final StringProperty date;
	private final StringProperty pname;
	private final StringProperty psurname;
	private final StringProperty note;
	private final StringProperty perioxi;
	private final StringProperty sxediasmos;
	private final StringProperty id;
	
	public VisitsData(String date, String pname, String psurname, String note, String sxediasmos, String perioxi, String id)
	{
		this.date = new SimpleStringProperty(date);
		this.pname = new SimpleStringProperty(pname);
		this.psurname = new SimpleStringProperty(psurname);
		this.note = new SimpleStringProperty(note);
		this.sxediasmos = new SimpleStringProperty(sxediasmos);
		this.perioxi = new SimpleStringProperty(perioxi);
		this.id = new SimpleStringProperty(id);		
	}
	
	public String getDate(){
		return this.date.get();
		}
	public String getPname(){
		return this.pname.get();
		}
	public String getPsurname(){
		return this.psurname.get();
		}
	
	public String getNote(){
		return this.note.get();
		}
	public String getPerioxi(){
		return this.perioxi.get();
		}
	public String getSxediasmos(){
		return this.sxediasmos.get();
		}
	public String getID(){
		return this.id.get();
		}

	
	public void setDate(String value){
		this.date.set(value);
	}
	public void setPname(String value){
		this.pname.set(value);
	}
	public void setPsurname(String value){
		this.psurname.set(value);
	}
	
	public void setNote(String value){
		this.note.set(value);
	}
	
	public void setPerioxi(String value){
		this.perioxi.set(value);
	}
	public void setSxediasmos(String value){
		this.sxediasmos.set(value);
	}
	public void setID(String value){
		this.id.set(value);
	}

	
	public StringProperty dateProperty(){
		return this.date;
	}
	public StringProperty pnameProperty(){
		return this.pname;
	}
	public StringProperty psurnameProperty(){
		return this.psurname;
	}
	
	public StringProperty noteProperty(){
		return this.note;
	}
	
	public StringProperty perioxiProperty(){
		return this.perioxi;
	}
	public StringProperty sxediasmosProperty(){
		return this.sxediasmos;
	}
	public StringProperty idProperty(){
		return this.id;
	}
}
