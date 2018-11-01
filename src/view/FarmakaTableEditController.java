package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DBConnection;
import model.FarmakaData;


public class FarmakaTableEditController implements Initializable
{

	@FXML
	private TableView<FarmakaData> farmakaTable;
	@FXML
	private TableColumn<FarmakaData,String> fnameColumn;
	@FXML
	private TableColumn<FarmakaData,String> fcodeColumn;
	@FXML
	private TableColumn<FarmakaData,String> finfoColumn;
	
	@FXML
	private TextField nameEditField;
	@FXML
	private TextField infoEditField;
	@FXML
	private TextField codeEditField;
	
	@FXML
	private Button backButton;
	@FXML
	private Button okButton;
	@FXML
	private Button dbtn;
	
	private ArrayList<FarmakaData> rowList = new ArrayList<FarmakaData>();
	private ObservableList<FarmakaData>fData;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCellValueFromTableToTextField();
		Image addfar = new Image(getClass().getResource("/view/backIcon.png").toExternalForm(), 25, 25, true, true);
		backButton.setGraphic(new ImageView(addfar));	
		Image edit = new Image(getClass().getResource("/view/okButton.png").toExternalForm(), 25, 25, true, true);
		okButton.setGraphic(new ImageView(edit));
		Image deletebtn = new Image(getClass().getResource("/view/deleteEntry.png").toExternalForm(), 25, 25, true, true);
		dbtn.setGraphic(new ImageView(deletebtn));
		loadFarmakaData();
		codeEditField.setEditable(false);
		
	}
	private void setCellValueFromTableToTextField()
	{
		farmakaTable.setOnMouseClicked(new EventHandler<MouseEvent>(){			
			@Override
			public void handle(MouseEvent event) {
				farmakaTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					ObservableList<FarmakaData> selectedItems = farmakaTable.getSelectionModel().getSelectedItems();
		      
			        if(selectedItems.size() >1)
			        {
				        for (FarmakaData row : selectedItems) 
				        {
				        	rowList.add(row);
					    }
			        }
			        else
			        {
			        	FarmakaData fd = farmakaTable.getItems().get(farmakaTable.getSelectionModel().getSelectedIndex());
			        	nameEditField.setText(fd.getName());
			        	infoEditField.setText(fd.getNote());
			        	codeEditField.setText(fd.getCode());
			        }
					
				}		
		});
	}
	
	private void loadFarmakaData()
	{
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			this.fData = FXCollections.observableArrayList();
			
			rs = conn.createStatement().executeQuery("SELECT * FROM FARMAKA");
			while(rs.next())
			{
				this.fData.add(new FarmakaData(rs.getString(1), rs.getString(2), rs.getString(3)));			
			}
						
		} catch (SQLException e) {
			System.out.println("se piasa grami 127 farmaka edit");
		} finally {
	        try {
	        	rs.close();
	            conn.close();
	            
	        } catch (SQLException e){System.err.println("Error 352 main med " + e);}
	    }
		
		this.fnameColumn.setCellValueFactory(new PropertyValueFactory<FarmakaData, String>("name"));
		this.fcodeColumn.setCellValueFactory(new PropertyValueFactory<FarmakaData, String>("code"));
		this.finfoColumn.setCellValueFactory(new PropertyValueFactory<FarmakaData, String>("note"));

		
		
		this.farmakaTable.setItems(null);
		this.farmakaTable.setItems(this.fData);
	}
	
	
	@FXML
	private void handledbtn(ActionEvent event)
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmnt = null;
		FarmakaData ffdata = farmakaTable.getSelectionModel().getSelectedItem();
		Alert alertC = new Alert(AlertType.CONFIRMATION);
		DialogPane dialogPane = alertC.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("/view/dark-theme.css").toExternalForm());
		alertC.initStyle(StageStyle.UNDECORATED);
		alertC.setTitle("Επιβεβαίωση");
		alertC.setHeaderText("Διαγραφή καταχώρησης");
		alertC.setContentText("Είστε βέβαιοι ότι θέλετε να διαγράψετε αυτήν την καταχώρηση;");

		Optional<ButtonType> result = alertC.showAndWait();
		if (result.get() == ButtonType.OK){
			String sqlDelete = "DELETE FROM FARMAKA WHERE code = ?";
			try {
				conn = DBConnection.getConnection();
				if(rowList.size()>1)
				{
					for(@SuppressWarnings("unused") FarmakaData item : rowList)
					{
						pstmnt = conn.prepareStatement(sqlDelete);						
						pstmnt.setString(1, ffdata.getCode());						
						rs = pstmnt.executeQuery();
					}
					
				}
				else
				{
					pstmnt = conn.prepareStatement(sqlDelete);					
					pstmnt.setString(1, ffdata.getCode());				
					rs = pstmnt.executeQuery();
				}

				
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane5 = alert.getDialogPane();
				dialogPane5.getStylesheets().add(
						getClass().getResource("/view/dark-theme.css").toExternalForm());
						alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Πληροφορίες");
				alert.setHeaderText(null);
				alert.setContentText("Έχετε ενημερώσει με επιτυχία μια καταχώρηση της βάσης δεδομένων!");
				alert.showAndWait();
				loadFarmakaData();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
		        try {
		        	pstmnt.close();
		        	rs.close();	        			
		            conn.close();
		            
		        } catch (SQLException e){e.printStackTrace();}
		    }
		} 
	}
	
	@FXML
	private void handleOkButton(ActionEvent event)
	{
			Connection conn = null;
			PreparedStatement pstmnt= null;
			FarmakaData farmakaData = farmakaTable.getSelectionModel().getSelectedItem();
			
			if(farmakaData!=null)
			{
				try {
					conn = DBConnection.getConnection();

					String sqlUpdate = "UPDATE FARMAKA SET name=?, code=?, note=? WHERE code LIKE '"+this.codeEditField.getText()+"'";
					pstmnt = conn.prepareStatement(sqlUpdate);
					
								
					pstmnt.setString(1, this.nameEditField.getText());
					pstmnt.setString(2, this.codeEditField.getText());
					pstmnt.setString(3, this.infoEditField.getText());
								
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
								loadFarmakaData();

								
				} catch (SQLException e) {
					System.out.println(e+"336 pat update");
					e.printStackTrace();

				} finally {
			        try {
			        	pstmnt.close();
			            conn.close();
			            
			        } catch (SQLException e){System.err.println("Error 343 pat update" + e);}
			    }	
			}
	}
	@FXML
	private void handleBackButton (ActionEvent event)
	{
		// Load new FXML file and save root Node as "root":
	    Parent root = null;
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("patientReg.fxml"));
		} catch (IOException e) {
			System.out.println("se piasa grammi 238 farmaka edit");
		}

	    // Create a new Scene to display the root node just loaded:
	    Scene scene = new Scene(root);

	    // Get a reference to the existing stage (the window containing the source of the event; the "show..." Button)
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	    scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
	    Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
	    stage.getIcons().add(applicationIcon);
	    stage.setTitle("Καταχώρηση ασθενούς");
	    // Set the new scene in the existing stage:
	    stage.setScene(scene);	    
	    stage.setMinHeight(600);
	    stage.setMinWidth(600);
	    // Show the existing stage (though it is already showing, I think):
	    stage.show();
	}
	
	
}
