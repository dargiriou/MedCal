package view;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import model.DBConnection;

public class farmakaController implements Initializable{
	
	@FXML
	private TextField nameFarmakoField;
	@FXML
	private TextArea pliroforiesFarmakoField;
	@FXML
	private Button addfarmakoButton;
	@FXML
	private Button cancelfarmakoButton;
	
	
	private DBConnection dbc;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image add = new Image(getClass().getResource("/view/add.png").toExternalForm(), 25, 25, true, true);
		addfarmakoButton.setGraphic(new ImageView(add));
		Image dontAdd = new Image(getClass().getResource("/view/dontadd.png").toExternalForm(), 25, 25, true, true);
		cancelfarmakoButton.setGraphic(new ImageView(dontAdd));
		
	}
	
	@FXML
	private void handleCancelfarmakoButton(ActionEvent event)
	{
		
	}

	private String codeGenerator() throws SQLException
	{
		Random r = new Random();
		int low = 1;
		int high = 1000;
		int result = r.nextInt(high-low) + low;
		
		    String code;
		    do {
		        code = String.valueOf(result);
		    }
		    while(codeExists(code));
		    return code;
		}
		private boolean codeExists(String code) throws SQLException {
		    Connection conn = null;
		    Statement statement = null;
		    ResultSet rs = null;
		    try{
		    	conn = DBConnection.getConnection();
		        statement = conn.createStatement();
		        rs = statement.executeQuery("SELECT code FROM FARMAKA WHERE code = '" +code+ "'");
		        return rs.next();
		    }
		    finally {
		        try {
		            conn.close();
		            statement.close();
		            rs.close();
		        } catch (SQLException ignored){}
		    }		
	}

public DBConnection getDbc() {
	return dbc;
}

public void setDbc(DBConnection dbc) {
	this.dbc = dbc;
}
	@FXML
	private void handleAddfarmakoButton(ActionEvent event) throws SQLException
	{
		String sqlInsert = "INSERT INTO FARMAKA(name, code, note) VALUES (?,?,?)";
		Connection conn = null;
	    PreparedStatement pstmnt = null;
		try {
			conn = DBConnection.getConnection();
			pstmnt = conn.prepareStatement(sqlInsert);
						
						pstmnt.setString(1, this.nameFarmakoField.getText());
						pstmnt.setString(2, this.codeGenerator());
						pstmnt.setString(3, this.pliroforiesFarmakoField.getText());					
						pstmnt.execute();

						Alert alert = new Alert(AlertType.INFORMATION);
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.getStylesheets().add(
						   getClass().getResource("/view/dark-theme.css").toExternalForm());
						alert.initStyle(StageStyle.UNDECORATED);
						alert.setTitle("Πληροφορίες");
						alert.setHeaderText(null);
						alert.setContentText("Έχετε προσθέσει επιτυχώς μια καταχώρηση στη βάση δεδομένων!");
						nameFarmakoField.clear();
						pliroforiesFarmakoField.clear();
						alert.showAndWait();	
		}finally {
	        try {	      
	            pstmnt.close();
				conn.close();
	        } catch (SQLException e){System.err.println("Error 1 " + e);}
	    }	
	}	


}
