package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import application.FxCalendar;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.CreateDatabase;
import model.DBConnection;
import model.PatientsData;
import model.Program;


public class MainMedCalController implements Initializable{
	Connection c = null;
	

	/**
	 * PATIENTS TABLE
	 */
	@FXML
	private TableView<PatientsData> patientsTable;
	
	@FXML
	private TableColumn<PatientsData, String> pnameColumn;
	
	@FXML
	private TableColumn<PatientsData, String> psurnameColumn;
	
	@FXML
	private TableColumn<PatientsData, String> pdobColumn;
	
	@FXML
	private TableColumn<PatientsData, String> pamkaColumn;
	
	@FXML
	private TableColumn<PatientsData, String> psexColumn;
	
	@FXML
	private TableColumn<PatientsData, String> pworkColumn;
	
	@FXML
	private TableColumn<PatientsData, String> pplaceOfStayColumn;
	
	@FXML
	private TableColumn<PatientsData, String> pdiagnosiColumn;
	
	@FXML
	private TableColumn<PatientsData, String> ptilColumn;
	
	
	/**
	 * BUTTONS
	 */
	@FXML
	private Button calendar;
	
	@FXML
	public static Pane anchor;
	
	@FXML
	private Button goBtn;
	
	@FXML
	private Button addPatientBtn;
	
	@FXML
	private Button removePatientBtn;
	
	@FXML
	private Button loadDBbtn;
	
	@FXML
	private Button reloadBtn;
	
	@FXML
	private Button calendarButton;
	
	@FXML
	private TextField search;
	
	@FXML 
	private MenuItem exitMenuItem;
	
	@FXML 
	private MenuItem crtdb;
	
	@FXML 
	private MenuItem about;
	
	@FXML 
	private MenuItem loadDbmenuBtn;
	
	@FXML 
	private MenuItem saveDbBtn;
	
	@FXML 
	private MenuItem refreshBtn;
	
	@FXML 
	private MenuItem restartBtn;
	
	@FXML 
	private MenuBar menu;
	
	@FXML
	private MenuItem mi1;
	
	@FXML
	private MenuItem mi2;
	
	@FXML
	private MenuItem mi3;
	
	@FXML
	private ContextMenu cm;
	
	private ObservableList<PatientsData>pData;
	
	private DBConnection dbc;	
	
	private ArrayList<PatientsData> rowList = new ArrayList<PatientsData>();

	public static PatientsData pRowData;
	
	private boolean isClicked = false;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		setDbc(new DBConnection());

		Image reloadImg = new Image(getClass().getResource("/view/reload.png").toExternalForm(), 25, 25, true, true);
		reloadBtn.setGraphic(new ImageView(reloadImg));
		Image addPat = new Image(getClass().getResource("/view/addPatientIcon.png").toExternalForm(), 25, 25, true, true);
		addPatientBtn.setGraphic(new ImageView(addPat));
		Image removePat = new Image(getClass().getResource("/view/removePatientIcon.png").toExternalForm(), 25, 25, true, true);
		removePatientBtn.setGraphic(new ImageView(removePat));
		Image loadDatabase = new Image(getClass().getResource("/view/loadDBIcon.png").toExternalForm(), 25, 25, true, true);
		loadDBbtn.setGraphic(new ImageView(loadDatabase));
		Image searchicon = new Image(getClass().getResource("/view/searchIcon.png").toExternalForm(), 25, 25, true, true);
		goBtn.setGraphic(new ImageView(searchicon));
		Image calendar = new Image(getClass().getResource("/view/calendar.png").toExternalForm(), 25, 25, true, true);
		calendarButton.setGraphic(new ImageView(calendar));
		
		
		pnameColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		psurnameColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		pdobColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(9));
		pamkaColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		psexColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(8));
		pworkColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		pplaceOfStayColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		pdiagnosiColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));
		ptilColumn.prefWidthProperty().bind(patientsTable.widthProperty().divide(7));

		
		patientsTable.setRowFactory( tv -> {
		    TableRow<PatientsData> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        pRowData = row.getItem();		           
		        	
		    	    try {
						Parent root = (Parent) FXMLLoader.load(getClass().getResource("/view/patientRegEdit.fxml"));

						Scene scene = new Scene(root);
						Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
						scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
						// create a new stage:
						Stage stage = new Stage();

						// make it modal:
						stage.initModality(Modality.APPLICATION_MODAL);
						Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
						stage.getIcons().add(applicationIcon);
						stage.setTitle("Επεξεργασία ασθενών");
						// make its owner the existing window:
						stage.initOwner(existingWindow);
								    	
						stage.setScene(scene);
						stage.setMinHeight(400);
						stage.setMinWidth(300);
						stage.show();
						stage.setOnHidden(e->{
							loadAll();
						});
					} catch (IOException e) {
						e.printStackTrace();
					}	    	  		    	    
		        }
		    });
		    return row ;
	});
			
		
		
		patientsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent t) {
		        if(t.getButton() == MouseButton.SECONDARY) {
		            cm.show(patientsTable, t.getScreenX(), t.getScreenY());
		        }		     
		    }
		});
		


	}

	@FXML
	private void handleClicked(MouseEvent event)
	{
		if(!isClicked)
		{
			loadAll();
			isClicked = true;			
		}
	}
	@FXML
	private void handleCalendarButton(ActionEvent event) throws Exception
	{
		FxCalendar.start(Program.primaryStage);
	}
		
	
	private void CreateDatabase()
	{
		CreateDatabase cd = null;
		cd = new CreateDatabase(DBConnection.getConnection());
		cd.createTables();
	}

	@FXML
	private void handleloadDbmenuBtn(ActionEvent event)
	{
		Stage stage = (Stage) menu.getScene().getWindow();
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
		File file = chooser.showOpenDialog(stage);
		String workingDir = System.getProperty("user.dir");
        Path target = FileSystems.getDefault().getPath(workingDir);
         
        	 try {
       		 Files.move(file.toPath(), target.resolve (file.getName ()), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	 loadAll();
	}
	
	@FXML
	private void handlesaveDbBtn(ActionEvent event) throws SQLException
	{
		Connection conn = null;
        String filename ="exportedPatients.txt";
        String filename2 ="exportedVisits.txt";
        try {
            FileWriter fw = new FileWriter(filename);
            FileWriter fw2 = new FileWriter(filename2);
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM PATIENTS";
            String query2 = "SELECT * FROM VISITS";
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs2 = stmt2.executeQuery(query2);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.append(',');
                fw.append(rs.getString(9));
                fw.append(',');
                fw.append(rs.getString(10));
                fw.append(',');
                fw.append(rs.getString(11));
                fw.append(',');
                fw.append(rs.getString(12));
                fw.append(',');
                fw.append(rs.getString(13));
                fw.append(',');
                fw.append(rs.getString(14));
                fw.append(',');
                fw.append(rs.getString(15));
                fw.append('\n');
               }
            fw.flush();
            fw.close();

            while(rs2.next())
            {
                fw2.append(rs2.getString(1));
                fw2.append(',');
                fw2.append(rs2.getString(2));
                fw2.append(',');
                fw2.append(rs2.getString(3));
                fw2.append(',');
                fw2.append(rs2.getString(4));
                fw2.append(',');
                fw2.append(rs2.getString(5));
                fw2.append(',');
                fw2.append(rs2.getString(6));
                fw2.append(',');
                fw2.append(rs2.getString(7));
                fw2.append('\n');
            }
            fw2.flush();
            fw2.close();
            stmt.close();
            stmt2.close();
            rs.close();
            rs2.close();
            
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            conn.close();
        }
        
	}
	
	@FXML
	private void handlerefreshBtn(ActionEvent event)
	{
		loadAll();
	}

	
	@FXML
	private void handleCrtdb(ActionEvent event)
	{
		CreateDatabase();
		Alert alert = new Alert(AlertType.INFORMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("/view/dark-theme.css").toExternalForm());
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setTitle("Πληροφορίες");
		alert.setHeaderText(null);
		alert.setContentText("Δημιουργήσατε με επιτυχία μια βάση δεδομένων!");
		alert.showAndWait();	
	}
	
	@FXML
	private void reloadButton(ActionEvent event)
	{
		loadAll();
	}
	
	@FXML
	private void handleQuit(ActionEvent event)
	{
		Platform.exit();
	}
	
	@FXML
	private void handleLoadDBbtn(ActionEvent event)
	{
		
		Node node = (Node) event.getSource();
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
		//chooser.showOpenDialog(node.getScene().getWindow());
		File file = chooser.showOpenDialog(node.getScene().getWindow());
		String workingDir = System.getProperty("user.dir");
        Path target = FileSystems.getDefault().getPath(workingDir);
         
        	 try {
       		 Files.move(file.toPath(), target.resolve (file.getName ()), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception ignore) {}
        	 loadAll();
	}

	@FXML
	private void handleAbout(ActionEvent event)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainMedCalController.class.getResource("/view/about.fxml"));
		BorderPane aboutPane = null;
		try {
			aboutPane = loader.load();
		} catch (IOException e) {
			System.out.println(e +"se piasa grami 202 main controller");

		}
		Stage s1 = new Stage();
		s1.initModality(Modality.WINDOW_MODAL);
		Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
		s1.getIcons().add(applicationIcon);
		s1.setTitle("About MedCal");
		Scene scene = new Scene(aboutPane);
		scene.setOnKeyPressed(evt -> {           
			s1.close();
        });
		s1.setScene(scene);
		s1.setResizable(false);
		s1.initStyle(StageStyle.UNDECORATED);
		s1.show();
		
		
	}
	
	private void loadPatientsData()
	{
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			this.pData = FXCollections.observableArrayList();
			
			rs = conn.createStatement().executeQuery("SELECT * FROM PATIENTS");
			while(rs.next())
			{
				this.pData.add(new PatientsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15)));			
			}
			
		} catch (SQLException e) {
			System.out.println(e +"se piasa grami 263 main controller");

		} finally {
	        try {
	        	 rs.close();
	            conn.close();
	           
	        } catch (SQLException e){System.err.println("Error 262 mainmed " + e);}
	    }	
		
		this.pnameColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("name"));
		this.psurnameColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("surname"));
		this.pamkaColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("amka"));
		this.psexColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("sex"));
		this.pdobColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("dob"));
		this.pworkColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("ergasia"));
		this.pplaceOfStayColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("topos"));
		this.pdiagnosiColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("diagnosi"));
		this.ptilColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("tilefono"));
		
		this.patientsTable.setItems(null);
		this.patientsTable.setItems(this.pData);
	}
	
	@FXML
	private void okToSearch()
	{
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			this.pData = FXCollections.observableArrayList();
			

				rs = conn.createStatement().executeQuery("SELECT * FROM PATIENTS WHERE name LIKE '%" + this.search.getText() + "%' OR "
						+ "surname LIKE '%" + this.search.getText() + "%' OR "
								+ "amka LIKE '%" + this.search.getText() + "%' OR "
										+ "topos LIKE '%" + this.search.getText() + "%'");
				while(rs.next())
				{
					this.pData.add(new PatientsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)
							, rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)
							, rs.getString(14),rs.getString(1)));
				}		
			
		} catch (SQLException e) {
			System.out.println(e +"se piasa grami 309 main controller");

		} finally {
	        try {
	        	rs.close();
	            conn.close();
	            
	        } catch (SQLException e){System.err.println("Error 304 main med" + e);}
	    }
		
		this.pnameColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("name"));
		this.psurnameColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("surname"));
		this.pamkaColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("amka"));
		this.psexColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("sex"));
		this.pdobColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("dob"));
		this.pworkColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("ergasia"));
		this.pplaceOfStayColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("topos"));
		this.pdiagnosiColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("diagnosi"));
		this.ptilColumn.setCellValueFactory(new PropertyValueFactory<PatientsData, String>("tilefono"));
		
		
		if(pData.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(
			   getClass().getResource("/view/dark-theme.css").toExternalForm());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.setTitle("Δεν βρέθηκαν αποτελέσματα!");
			alert.setHeaderText(null);
			alert.setContentText("Η αναζήτηση ολοκληρώθηκε χωρίς αποτελέσματα!");
			alert.showAndWait();
			loadAll();
		}else{
			this.patientsTable.setItems(null);
			this.patientsTable.setItems(this.pData);
		}
	}
	
	public void loadAll()
	{
			loadPatientsData();
	}
		
	@FXML
	private void handleDeleteButton(ActionEvent event)
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmnt = null;
		PatientsData pdata = patientsTable.getSelectionModel().getSelectedItem();
		Alert alertC = new Alert(AlertType.CONFIRMATION);
		DialogPane dialogPane = alertC.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("/view/dark-theme.css").toExternalForm());
		alertC.initStyle(StageStyle.UNDECORATED);
		alertC.setTitle("Eπιβεβαίωση!");
		alertC.setHeaderText("Διαγραφή Καταχώρησης");
		alertC.setContentText("Είστε βέβαιοι ότι θέλετε να διαγράψετε αυτή την καταχώρηση?");

		Optional<ButtonType> result = alertC.showAndWait();
		if (result.get() == ButtonType.OK){
			String sqlDelete = "DELETE FROM PATIENTS WHERE surname = ?";
			try {
				conn = DBConnection.getConnection();
				if(rowList.size()>1)
				{
					for(@SuppressWarnings("unused") PatientsData item : rowList)
					{
						pstmnt = conn.prepareStatement(sqlDelete);						
						pstmnt.setString(1, pdata.getSurname());						
						pstmnt.executeUpdate();
					}
					
				}
				else
				{
					pstmnt = conn.prepareStatement(sqlDelete);					
					pstmnt.setString(1, pdata.getSurname());				
					pstmnt.executeUpdate();
				}

				
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane1 = alert.getDialogPane();
				dialogPane1.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Πληροφορίες");
				alert.setHeaderText(null);
				alert.setContentText("Έχετε διαγράψει με επιτυχία μια καταχώρηση από τη βάση δεδομένων!");
				alert.showAndWait();
				loadAll();
				
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
		        try {
		        	pstmnt.close();
		        	rs.close();
		            conn.close();
		            
		        } catch (SQLException e){System.err.println("Error 412 main med" + e);}
		    }
		} 
	}

	@FXML
	private void handleAddPatientButton(ActionEvent event)
	{
		try {
			File inputFile = new File("MedCalDB.db");
			if(inputFile.exists())
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainMedCalController.class.getResource("/view/patientReg.fxml"));
				

				BorderPane patientReg = loader.load();

				Stage s1 = new Stage();
				s1.initModality(Modality.WINDOW_MODAL);
				Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
				s1.getIcons().add(applicationIcon);
				s1.setTitle("Καταχώρηση ασθενούς");
				Scene scene = new Scene(patientReg);
				scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
				s1.setScene(scene);
				s1.setOnHidden(e -> {
					try {
					
						loadAll();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				s1.setMinHeight(600);
				s1.setMinWidth(600);
				s1.setResizable(false);
				s1.show();
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Δεν βρέθηκε βάση δεδομένων");
				alert.setHeaderText(null);
				alert.setContentText("Δημιουργησε βάση!");
				alert.showAndWait();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public DBConnection getDbc() {
		return dbc;
	}

	public void setDbc(DBConnection dbc) {
		this.dbc = dbc;
	}
	
	public static int calculateAge(String date, LocalDate currentDate)
	    {
	    	DateTimeFormatter formatter_1=DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    	   LocalDate birthDate= LocalDate.parse(date,formatter_1);
	    	   
	           if ((birthDate != null) && (currentDate != null)) {
	               return Period.between(birthDate, currentDate).getYears();
	           } else {
	               return 0;
	           } 	  
	    }
}
