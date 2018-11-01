package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.StringConverter;
import model.DBConnection;



public class PatientRegController implements Initializable
{

	@FXML
	private TextField nameField;
	@FXML
	private TextField surnameField;
	@FXML
	private TextField workField;
	@FXML
	private TextField placeOfStayField;
	@FXML
	private TextField amkaField;
	@FXML
	private TextField tilField;
	@FXML
	private TextArea famHistoryArea;
	@FXML
	private TextArea nososArea;
	@FXML
	private TextArea persHistoryArea;
	@FXML
	private TextArea diagnosiArea;
	@FXML
	private TextArea farmakaArea;
	@FXML
	private ChoiceBox<String> sexChooser;
	@FXML
	private ChoiceBox<String> farmakachooser;

	@FXML
	private DatePicker dobPicker;
	
	@FXML
	private Button farmakaBtn;
	@FXML
	private Button regBtn;
	@FXML
	private Button farmakaBtnadd;
	@FXML
	private Button editbtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

		
		dobPicker.setConverter(new StringConverter<LocalDate>() {
			 String pattern = "dd-MM-yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			 {
				 dobPicker.setPromptText(pattern.toLowerCase());
			 }

			 @Override public String toString(LocalDate date) {
			     if (date != null) {
			         return dateFormatter.format(date);
			     } else {
			         return "";
			     }
			 }

			 @Override public LocalDate fromString(String string) {
			     if (string != null && !string.isEmpty()) {
			         return LocalDate.parse(string, dateFormatter);
			     } else {
			         return null;
			     }
			 }
			});
		
		sexChooser.setValue("Γυναίκα");
		sexChooser.getItems().add("Άντρας");
		sexChooser.getItems().add("Γυναίκα");
		
		for(String str: getDataFromFarmakaToArray())
		{
			if(!farmakachooser.getItems().contains(str))
			{
				farmakachooser.getItems().add(str);
			}					
		}

		
		Image farmakaB = new Image(getClass().getResource("/view/addMedication.png").toExternalForm(), 25, 25, true, true);
		farmakaBtn.setGraphic(new ImageView(farmakaB));
		Image addPat = new Image(getClass().getResource("/view/add.png").toExternalForm(), 25, 25, true, true);
		regBtn.setGraphic(new ImageView(addPat));
		Image addfar = new Image(getClass().getResource("/view/add.png").toExternalForm(), 25, 25, true, true);
		farmakaBtnadd.setGraphic(new ImageView(addfar));	
		Image edit = new Image(getClass().getResource("/view/editfarmaka.png").toExternalForm(), 25, 25, true, true);
		editbtn.setGraphic(new ImageView(edit));
	    
	    
	}
	
	private Set<String> getDataFromFarmakaToArray()
	{
		
		Set<String> linkedHashSet = new LinkedHashSet<>();
		
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			
			rs = conn.createStatement().executeQuery("SELECT * FROM FARMAKA");
			while(rs.next())
			{
				linkedHashSet.add(rs.getString(1));
			}
						
		} catch (SQLException e) {
			System.out.println(e+"se piasa grami 156 patient controller");
		} finally {
	        try {
	        	rs.close();
	            conn.close();
	            
	        } catch (SQLException e){System.err.println("Error 162 pat controll " + e);}
	    }
		
		return linkedHashSet;	
	}
	
	
	@FXML
	private void handleEditFarmakaButton(ActionEvent event)
	{
		// Load new FXML file and save root Node as "root":
	    Parent root = null;
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("FarmakaTableEditWindow.fxml"));
		} catch (IOException e) {
			System.out.println(e + "se piasa grami 171 patient controller");

		}

	    // Create a new Scene to display the root node just loaded:
	    Scene scene = new Scene(root);

	    // Get a reference to the existing stage (the window containing the source of the event; the "show..." Button)
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	    scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
	    Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
	    stage.getIcons().add(applicationIcon);
	    stage.setTitle("Επεξεργασία φαρμάκων");
	    // Set the new scene in the existing stage:
	    stage.setScene(scene);
	    stage.setMinHeight(600);
	    stage.setMinWidth(600);
	    // Show the existing stage (though it is already showing, I think):
	    stage.show();
	}
	
	@FXML
	private void handleFarmakaAddButton(ActionEvent event)
	{
		String value = (String) farmakachooser.getValue();
		farmakaArea.appendText(value + ", ");
		farmakaArea.getText().toString();
	}
	
	
	@FXML
	private void handleFarmakaButton(ActionEvent event)
	{
		
		    Parent root = null;
			try {
				root = (Parent) FXMLLoader.load(getClass().getResource("addFarmako.fxml"));
			} catch (IOException e) {
				System.out.println(e+"se piasa grami 210 patient controller");

			}
		    Scene scene = new Scene(root);
		    Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
		    scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
		    // create a new stage:
		    Stage stage = new Stage();
		    // make it modal:
		    stage.initModality(Modality.APPLICATION_MODAL);
		    Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
		    stage.getIcons().add(applicationIcon);
		    stage.setTitle("Καταχώρηση φαρμάκων");
		    // make its owner the existing window:
		    stage.initOwner(existingWindow);

		    stage.setOnHidden(e->{		
		    	 
				for(String str: getDataFromFarmakaToArray())
				{
					if(!farmakachooser.getItems().contains(str))
					{
						farmakachooser.getItems().add(str);
					}					
				}
		    });
		    stage.setScene(scene);
		    stage.setMinHeight(400);
		    stage.setMinWidth(300);
		    stage.show();

	}
	
	@FXML
	private void handleRegButton(ActionEvent event)
	{
						
		try {
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(dobPicker.getValue() != null)
		{
			String sex = sexChooser.getSelectionModel().getSelectedItem().toString();
			LocalDate localDate = LocalDate.now();//For reference
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedString = localDate.format(formatter);

			UUID id = UUID.randomUUID();
		
			String sqlInsert = "INSERT INTO PATIENTS(name, surname, amka, dob, sex, topos, ergasia, oikistoriko, prosistoriko, nosos, diagnosi, regdate, tilefono, farmaka, id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Connection conn = null;
		    PreparedStatement pstmnt = null;
			try {
				conn = DBConnection.getConnection();
				pstmnt = conn.prepareStatement(sqlInsert);
							pstmnt.setString(1, this.nameField.getText());
							pstmnt.setString(2, this.surnameField.getText());
							pstmnt.setString(3, this.amkaField.getText());
							pstmnt.setString(4, dobPicker.getEditor().getText());
							pstmnt.setString(5, sex);
							pstmnt.setString(6, this.placeOfStayField.getText());
							pstmnt.setString(7, this.workField.getText());
							pstmnt.setString(8, this.famHistoryArea.getText());
							pstmnt.setString(9, this.persHistoryArea.getText());
							pstmnt.setString(10, this.nososArea.getText());
							pstmnt.setString(11, this.diagnosiArea.getText());
							pstmnt.setString(12, formattedString);
							pstmnt.setString(13, this.tilField.getText());
							pstmnt.setString(14, this.farmakaArea.getText());
							pstmnt.setString(15, id.toString());
							
							pstmnt.execute();
							
							
							
							Alert alert = new Alert(AlertType.INFORMATION);
							DialogPane dialogPane = alert.getDialogPane();
							dialogPane.getStylesheets().add(
							   getClass().getResource("/view/dark-theme.css").toExternalForm());
							alert.initStyle(StageStyle.UNDECORATED);
							alert.setTitle("Πληροφορίες");
							alert.setHeaderText(null);
							alert.setContentText("Επιτυχής καταχώρηση!");
							alert.showAndWait();

						

				
			} catch (SQLException e) {
				System.out.println(e+"se piasa grami 288 patient controller");

			} finally {
		        try {
		        	pstmnt.close();
		            conn.close();
		            
		        } catch (SQLException e){System.err.println("Error 259 patcontrol" + e);}
		    }	
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(
			   getClass().getResource("/view/dark-theme.css").toExternalForm());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.getDialogPane().setMaxWidth(300);
			alert.setTitle("Δεν έχετε συμπληρώσει ημερομηνία!");
			alert.setHeaderText(null);
			alert.setContentText("Δεν έχετε συμπληρώσει ημερομηνία!");
			alert.showAndWait();
		}

	}

}
