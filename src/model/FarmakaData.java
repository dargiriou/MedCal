package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FarmakaData {

	private final StringProperty name;
	private final StringProperty code;
	private final StringProperty note;
	
	public FarmakaData(String name, String code, String note)
	{
		this.name = new SimpleStringProperty(name);
		this.code = new SimpleStringProperty(code);
		this.note = new SimpleStringProperty(note);
	}
	
	public String getName(){
		return this.name.get();
		}
	
	public String getCode(){
		return this.code.get();
		}
	public String getNote(){
		return this.note.get();
		}
	
	public void setName(String value){
		this.name.set(value);
	}
	
	public void setCode(String value){
		this.code.set(value);
	}
	
	public void setNote(String value){
		this.note.set(value);
	}
	
	public StringProperty nameProperty(){
		return this.name;
	}
	
	public StringProperty codeProperty(){
		return this.code;
	}
	
	public StringProperty noteProperty(){
		return this.note;
	}
}
