package model;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

	
	private Connection c;
	public CreateDatabase(Connection c)
	{
		this.c = c;
	}
	
	private String PATIENTS = "PATIENTS";
	private String VISITS = "VISITS";
	private String FARMAKA = "FARMAKA";
	
	public void createTables() {  
        Statement stmnt;
        String query;
      
        try {
        	stmnt = c.createStatement();
        	query = "CREATE TABLE IF NOT EXISTS " + PATIENTS +
               /*1*/"( name TEXT NOT NULL, " +
               /*2*/"surname TEXT PRIMARY KEY ," +
               /*3*/"amka TEXT NOT NULL ," +
               /*4*/"dob TEXT NOT NULL,"+
               /*5*/"sex TEXT NOT NULL,"+
               /*6*/"topos TEXT NOT NULL,"+
               /*7*/"ergasia TEXT NOT NULL ," +
               /*8*/"oikistoriko TEXT NOT NULL ," +
               /*9*/"prosistoriko TEXT NOT NULL ," +
               /*10*/"nosos TEXT NOT NULL ," +
               /*11*/"diagnosi TEXT NOT NULL ," +
               /*12*/"regdate TEXT ," +
               /*13*/"tilefono TEXT NOT NULL ," +
               /*15*/"farmaka TEXT NOT NULL ," +
               /*15*/"id TEXT NOT NULL)";          		
            stmnt.executeUpdate(query);
            stmnt.close();

            stmnt = c.createStatement();
            query = "CREATE TABLE IF NOT EXISTS " + VISITS +
            /*1*/" (date TEXT NOT NULL ," +
            /*2*/"pname TEXT NOT NULL ," +
            /*3*/"psurname TEXT NOT NULL ," +               
            /*4*/"note TEXT ," +
            /*5*/"sxediasmos TEXT ," +
            /*6*/ "perioxi TEXT ," +
            /*7*/"id TEXT NOT NULL )";
            stmnt.executeUpdate(query);
            stmnt.close();
            
            stmnt = c.createStatement();
            query = "CREATE TABLE IF NOT EXISTS " + FARMAKA +
                    " (name TEXT NOT NULL ," +
            		"code TEXT NOT NULL ," +
                    "note TEXT NOT NULL )";
            stmnt.executeUpdate(query);
            stmnt.close();          

        } catch (SQLException e) {
            e.printStackTrace();
        }
}
}
