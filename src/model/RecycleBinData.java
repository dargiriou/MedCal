package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecycleBinData {

	private final StringProperty bname;
	private final StringProperty bsurname;
	private final StringProperty bamka;
	private final StringProperty bdob;
	private final StringProperty bperioxi;
	private final StringProperty bsex;
	private final StringProperty btopos;
	private final StringProperty bergasia;
	private final StringProperty boikistoriko;
	private final StringProperty bprosistoriko;
	private final StringProperty bnosos;
	private final StringProperty bdiagnosi;
	private final StringProperty bregdate;
	private final StringProperty bepiskepseis;
	private final StringProperty btilefono;
	private final StringProperty bfarmaka;
	
	public RecycleBinData(String bname, String bsurname, String bamka, String bdob, String bperioxi, String bsex, String btopos
			, String bergasia, String boikistoriko, String bprosistoriko, String bnosos, String bdiagnosi, String bregdate
			, String bepiskepseis, String btilefono, String bfarmaka)
	{
		this.bname = new SimpleStringProperty(bname);
		this.bsurname = new SimpleStringProperty(bsurname);
		this.bamka = new SimpleStringProperty(bamka);
		this.bdob = new SimpleStringProperty(bdob);
		this.bperioxi = new SimpleStringProperty(bperioxi);
		this.bsex = new SimpleStringProperty(bsex);
		this.btopos = new SimpleStringProperty(btopos);
		this.bergasia = new SimpleStringProperty(bergasia);
		this.boikistoriko = new SimpleStringProperty(boikistoriko);
		this.bprosistoriko = new SimpleStringProperty(bprosistoriko);
		this.bnosos = new SimpleStringProperty(bnosos);
		this.bdiagnosi = new SimpleStringProperty(bdiagnosi);
		this.bregdate = new SimpleStringProperty(bregdate);
		this.bepiskepseis = new SimpleStringProperty(bepiskepseis);
		this.btilefono = new SimpleStringProperty(btilefono);
		this.bfarmaka = new SimpleStringProperty(bfarmaka);
	}
	
	public String getBname(){
		return this.bname.get();
		}
	
	public String getBsurname(){
		return this.bsurname.get();
		}
	public String getBamka(){
		return this.bamka.get();
		}
	
	public String getBdob(){
		return this.bdob.get();
		}
	public String getBperioxi(){
		return this.bperioxi.get();
		}
	public String getBsex(){
		return this.bsex.get();
		}
	
	public String getBtops(){
		return this.btopos.get();
		}
	public String getBergasia(){
		return this.bergasia.get();
		}
	
	public String getBoikistoriko(){
		return this.boikistoriko.get();
		}
	public String getBprosistoriko(){
		return this.bprosistoriko.get();
		}
	
	public String getBnosos(){
		return this.bnosos.get();
		}
	public String getBdiagnosi(){
		return this.bdiagnosi.get();
		}
	
	public String getBregDate(){
		return this.bregdate.get();
		}
	public String getBepiskepseis(){
		return this.bepiskepseis.get();
		}
	
	public String getBtilefono(){
		return this.btilefono.get();
		}
	public String getBfarmaka(){
		return this.bfarmaka.get();
		}
	

	

	public void setBName(String value){
		this.bname.set(value);
	}
	
	public void setBSurname(String value){
		this.bsurname.set(value);
	}
	
	public void setBAmka(String value){
		this.bamka.set(value);
	}
	
	public void setBDob(String value){
		this.bdob.set(value);
	}
	
	public void setBPerioxi(String value){
		this.bperioxi.set(value);
	}
	
	public void setBSex(String value){
		this.bsex.set(value);
	}
	public void setBTopos(String value){
		this.btopos.set(value);
	}
	
	public void setBErgasia(String value){
		this.bergasia.set(value);
	}
	
	public void setBOikistoriko(String value){
		this.boikistoriko.set(value);
	}
	
	public void setBProsistoriko(String value){
		this.bprosistoriko.set(value);
	}
	
	public void setBNosos(String value){
		this.bnosos.set(value);
	}
	public void setBDiagnosi(String value){
		this.bdiagnosi.set(value);
	}
	
	public void setBRegdate(String value){
		this.bregdate.set(value);
	}
	
	public void setBepiskepseis(String value){
		this.bepiskepseis.set(value);
	}
	
	public void setBtilefono(String value){
		this.btilefono.set(value);
	}
	
	public void setBfarmaka(String value){
		this.bfarmaka.set(value);
	}
	
	
	public StringProperty bnameProperty(){
		return this.bname;
	}
	
	public StringProperty bperioxiProperty(){
		return this.bperioxi;
	}
	
	public StringProperty bsurnameProperty(){
		return this.bsurname;
	}
	
	public StringProperty bamkaProperty(){
		return this.bamka;
	}
	
	public StringProperty bdobProperty(){
		return this.bdob;
	}
	public StringProperty bsexProperty(){
		return this.bsex;
	}
	public StringProperty btoposProperty(){
		return this.btopos;
	}
	public StringProperty bergasiaProperty(){
		return this.bergasia;
	}
	
	public StringProperty boikistorikoProperty(){
		return this.boikistoriko;
	}
	
	public StringProperty bprosistorikoProperty(){
		return this.bprosistoriko;
	}
	
	public StringProperty bnososProperty(){
		return this.bnosos;
	}
	public StringProperty bdiagnosiProperty(){
		return this.bdiagnosi;
	}
	public StringProperty bregdateProperty(){
		return this.bregdate;
	}
	public StringProperty bepiskepseisProperty(){
		return this.bepiskepseis;
	}
	
	public StringProperty btilefonoProperty(){
		return this.btilefono;
	}
	
	public StringProperty bfarmakaProperty(){
		return this.bfarmaka;
	}
	
}
