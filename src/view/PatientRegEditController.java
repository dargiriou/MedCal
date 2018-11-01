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
import java.util.regex.Pattern;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.StringConverter;
import model.DBConnection;
import model.VisitsData;

public class PatientRegEditController implements Initializable
{

	@FXML
	private TextField enameField;
	@FXML
	private TextField esurnameField;
	@FXML
	private TextField eworkField;
	@FXML
	private TextField eplaceOfStayField;
	@FXML
	private TextField eamkaField;
	@FXML
	private TextField etilField;
	@FXML
	private TextField evisitAreaField;
	@FXML
	private TextArea efamHistoryArea;
	@FXML
	private TextArea enososArea;
	@FXML
	private TextArea epersHistoryArea;
	@FXML
	private TextArea ediagnosiArea;
	@FXML
	private TextArea efarmakaArea;
	@FXML
	private ChoiceBox<String> esexChooser;
	@FXML
	private ChoiceBox<String> efarmakachooser;

	@FXML
	private DatePicker edobPicker;
	
	@FXML
	private Button efarmakaBtn;
	@FXML
	private Button eregBtn;
	@FXML
	private Button efarmakaBtnadd;
	@FXML
	private Button eaddVisit;
	@FXML
	private Button tickBtn;
	@FXML
	private Button Xbtn;
	
	@FXML
	private TableView<VisitsData> eVisitsTable;
	@FXML
	private TableColumn<VisitsData, String> eVisitPlaceCol;	
	@FXML
	private TableColumn<VisitsData, String> eDateCol;
	
	@FXML
	private HTMLEditor htmlNotes;
	@FXML
	private HTMLEditor htmlSxediasmos;
	
	private ObservableList<VisitsData>vData;
	private  VisitsData vRowData;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

		edobPicker.setConverter(new StringConverter<LocalDate>() {
			 String pattern = "dd-MM-yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			 {
				 edobPicker.setPromptText(pattern.toLowerCase());
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
		
		eVisitsTable.setRowFactory( tv -> {
		    TableRow<VisitsData> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
		        	vRowData = row.getItem();
		        	Connection conn = null;
		    		ResultSet rs = null;

		    		try {
		    			conn = DBConnection.getConnection();
		    					    			
		    				rs = conn.createStatement().executeQuery("SELECT * FROM VISITS WHERE date LIKE '%" + vRowData.getDate() +"%'");
		    				while(rs.next())
		    				{	    			    	   
		    					htmlNotes.setHtmlText(rs.getString(4));
		    					htmlSxediasmos.setHtmlText(rs.getString(5));		    					
		    				}		
		    			
		    		} catch (SQLException e) {
		    			System.out.println(e +"se piasa grami 177 patregedit controller");
		    			e.printStackTrace();

		    		} finally {
		    	        try {
		    	        	rs.close();
		    	            conn.close();
		    	            
		    	        } catch (SQLException e){System.err.println("Error 184 patregedit" + e);
		    	        e.printStackTrace();
		    	        }
		    	    }		        
		        }
		    });
		    return row ;
		});
		
		esexChooser.setValue("Γυναίκα");
		esexChooser.getItems().add("Άντρας");
		esexChooser.getItems().add("Γυναίκα");
		
		
		for(String str: getDataFromFarmakaToArray())
		{
			if(!efarmakachooser.getItems().contains(str))
			{
				efarmakachooser.getItems().add(str);
			}					
		}
		
		Image farmakaB = new Image(getClass().getResource("/view/addMedication.png").toExternalForm(), 25, 25, true, true);
		efarmakaBtn.setGraphic(new ImageView(farmakaB));
		Image addPat = new Image(getClass().getResource("/view/add.png").toExternalForm(), 25, 25, true, true);
		eregBtn.setGraphic(new ImageView(addPat));
		Image addfar = new Image(getClass().getResource("/view/add.png").toExternalForm(), 25, 25, true, true);
		efarmakaBtnadd.setGraphic(new ImageView(addfar));	
		Image visits = new Image(getClass().getResource("/view/visitsCreateIcon.png").toExternalForm(), 25, 25, true, true);
		eaddVisit.setGraphic(new ImageView(visits));
		Image x = new Image(getClass().getResource("/view/xIcon.png").toExternalForm(), 25, 25, true, true);
		Xbtn.setGraphic(new ImageView(x));	
		Image tick = new Image(getClass().getResource("/view/tickIcon.png").toExternalForm(), 25, 25, true, true);
		tickBtn.setGraphic(new ImageView(tick));
		LoadSelectedPatientFromPatientsTable();

	    
	}
	
	@FXML
	private void handleXbtn(ActionEvent event)
	{
		VisitsData visit = eVisitsTable.getSelectionModel().getSelectedItem();
		Connection conn = null;
		PreparedStatement pstmnt = null;
		if(visit != null)
		{
			String sqlDelete = "DELETE FROM VISITS WHERE date = ?";
			try {
				conn = DBConnection.getConnection();

				pstmnt = conn.prepareStatement(sqlDelete);						
				pstmnt.setString(1, visit.getDate());						
				pstmnt.executeUpdate();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane1 = alert.getDialogPane();
				dialogPane1.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Πληροφορίες");
				alert.setHeaderText(null);
				alert.setContentText("Έχετε διαγράψει με επιτυχία μια επίσκεψη από τη βάση δεδομένων!");
				alert.showAndWait();
				loadVisits();
				
			} catch (SQLException e) {
				System.out.println(e +"se piasa grami 425 main controller");

			} finally {
		        try {
		        	pstmnt.close();
		            conn.close();
		            
		        } catch (SQLException e){System.err.println("Error 412 main med" + e);}
		    }
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			DialogPane dialogPane1 = alert.getDialogPane();
			dialogPane1.getStylesheets().add(
			   getClass().getResource("/view/dark-theme.css").toExternalForm());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.setTitle("Πρόβλημα");
			alert.setHeaderText(null);
			alert.setContentText("Δεν διαλέξατε ποια επίσκεψη θέλετε να διαγράψετε!");
			alert.showAndWait();
		}
		
	}
	
	@FXML
	private void handletickBtn(ActionEvent event)
	{
		if(htmlNotes.getHtmlText().toString().trim().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(
			   getClass().getResource("/view/dark-theme.css").toExternalForm());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.setTitle("Πληροφορίες");
			alert.setHeaderText(null);
			alert.setContentText("Τίποτα δεν καταχωρήθηκε, άδειο πεδίο!");
			alert.showAndWait();
			loadVisits();

		}
		else
		{
			Connection conn = null;
			PreparedStatement pstmnt= null;
			VisitsData visit = eVisitsTable.getSelectionModel().getSelectedItem();
			
			if(visit!=null)
			{
				try {
					conn = DBConnection.getConnection();
		

					

					String sqlUpdate = "UPDATE VISITS SET date=?, pname=?, psurname=?, note=?, sxediasmos=?, perioxi=?, id=? WHERE date LIKE '"+ visit.getDate() +"'";
					pstmnt = conn.prepareStatement(sqlUpdate);
					
								
								pstmnt.setString(1, visit.getDate());
								pstmnt.setString(2, visit.getPname());
								pstmnt.setString(3, visit.getPsurname());
								pstmnt.setString(4, htmlNotes.getHtmlText());
								pstmnt.setString(5, htmlSxediasmos.getHtmlText());
								pstmnt.setString(6, visit.getPerioxi());
								pstmnt.setString(7, visit.getID());
								
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
								loadVisits();

								
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
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				DialogPane dialogPane1 = alert.getDialogPane();
				dialogPane1.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Πρόβλημα");
				alert.setHeaderText(null);
				alert.setContentText("Δεν διαλέξατε ποια επίσκεψη θέλετε να επεξεργαστείτε!");
				alert.showAndWait();
			}
			
		}
		
	}
	private String id;
	private void LoadSelectedPatientFromPatientsTable()
	{
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			
			

				rs = conn.createStatement().executeQuery("SELECT * FROM PATIENTS WHERE id LIKE '%" + MainMedCalController.pRowData.getID() +"%'");
				while(rs.next())
				{
		
					DateTimeFormatter formatter_1=DateTimeFormatter.ofPattern("dd-MM-yyyy");
			    	   LocalDate ld= LocalDate.parse(rs.getString(4),formatter_1);
			    	   
			    	id = rs.getString(15);
					enameField.setText(rs.getString(1));
					esurnameField.setText(rs.getString(2));
					edobPicker.setValue(ld);
					eworkField.setText(rs.getString(7));
					eplaceOfStayField.setText(rs.getString(6));
					eamkaField.setText(rs.getString(3));
					etilField.setText(rs.getString(13));
					efamHistoryArea.setText(rs.getString(8));
					enososArea.setText(rs.getString(10));
					epersHistoryArea.setText(rs.getString(9));
					ediagnosiArea.setText(rs.getString(11));
					efarmakaArea.setText(rs.getString(14));
					
					loadVisits();
				}		
			
		} catch (SQLException e) {
			System.out.println(e +"se piasa grami 177 patregedit controller");
			e.printStackTrace();

		} finally {
	        try {
	        	rs.close();
	            conn.close();
	            
	        } catch (SQLException e){System.err.println("Error 184 patregedit" + e);
	        e.printStackTrace();
	        }
	    }	
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
	

	
	private void loadVisits()
	{
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			this.vData = FXCollections.observableArrayList();
			
			rs = conn.createStatement().executeQuery("SELECT * FROM VISITS WHERE id LIKE '%"+ MainMedCalController.pRowData.getID() +"%'");
			while(rs.next())
			{
				this.vData.add(new VisitsData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));			
			}
			
		} catch (SQLException e) {
			System.out.println(e +"se piasa grami 263 main controller");

		} finally {
	        try {
	        	 rs.close();
	            conn.close();
	           
	        } catch (SQLException e){System.err.println("Error 262 mainmed " + e);}
	    }	
		
		this.eVisitPlaceCol.setCellValueFactory(new PropertyValueFactory<VisitsData, String>("perioxi"));
		this.eDateCol.setCellValueFactory(new PropertyValueFactory<VisitsData, String>("date"));

		
		this.eVisitsTable.setItems(null);
		this.eVisitsTable.setItems(this.vData);
	}
	
	
	@FXML
	private void ehandleAddVisit(ActionEvent event)
	{
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(PatientRegEditController.class.getResource("/view/perioxiPane.fxml"));		 
				BorderPane patientRegedit = loader.load();

				Stage s1 = new Stage();
				s1.initModality(Modality.WINDOW_MODAL);
				Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
				s1.getIcons().add(applicationIcon);
				s1.setTitle("Καταχώρηση περιοχής επίσκεψης");
				Scene scene = new Scene(patientRegedit);
				
				scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
				  () {

				        @Override
				        public void handle(KeyEvent t) {
				          if(t.getCode()==KeyCode.ESCAPE)
				          {
				             s1.close();
				          }
				        }
				    });
				
				scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
				s1.setScene(scene);
				s1.setMinHeight(100);
				s1.setMinWidth(200);
				s1.setResizable(false);
				s1.setOnHidden(e->{
					loadVisits();
				});
				s1.showAndWait();
				
					if(PerioxiControllerLmaoIsuck.perioxi == null)
					{
						Alert alert = new Alert(AlertType.WARNING);
						DialogPane dialogPane = alert.getDialogPane();
						dialogPane.getStylesheets().add(
						   getClass().getResource("/view/dark-theme.css").toExternalForm());
						alert.initStyle(StageStyle.UNDECORATED);
						alert.setTitle("Αδύνατη η Δημιουργία επίσκεψης");
						alert.setHeaderText(null);
						alert.setContentText("Αδύνατη η Δημιουργία επίσκεψης χωρίς να δηλωθεί περιοχή!!");
						alert.showAndWait();
						loadVisits();
					}
							else {
								String sqlInsert = "INSERT INTO VISITS(date, pname, psurname, note, sxediasmos, perioxi, id) VALUES (?,?,?,?,?,?,?)";
								Connection conn = null;
							    PreparedStatement pstmnt = null;
								try {
									conn = DBConnection.getConnection();
									pstmnt = conn.prepareStatement(sqlInsert);

									pstmnt.setString(1, PerioxiControllerLmaoIsuck.visitDatePickerDate);
									pstmnt.setString(2, this.enameField.getText());
									pstmnt.setString(3, this.esurnameField.getText());
									pstmnt.setString(4, "");
									pstmnt.setString(5, "");
									pstmnt.setString(6, PerioxiControllerLmaoIsuck.perioxi);
									pstmnt.setString(7, id);
												
												
									pstmnt.execute();											
												
									Alert alert5 = new Alert(AlertType.INFORMATION);
									DialogPane dialogPane5 = alert5.getDialogPane();
									dialogPane5.getStylesheets().add(
											getClass().getResource("/view/dark-theme.css").toExternalForm());
											alert5.initStyle(StageStyle.UNDECORATED);
											alert5.setTitle("Πληροφορίες");
											alert5.setHeaderText(null);
											alert5.setContentText("Επιτυχής καταχώρηση!");
											alert5.showAndWait();
											loadVisits();

								
								} catch (SQLException e2) {
									System.out.println(e2+"se piasa grami 288 patient controller");

								} finally {
							        try {
							        	pstmnt.close();
							            conn.close();
							            
							        } catch (SQLException e4){System.err.println("Error 259 patcontrol" + e4);}
							    }
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	private void ehandleEditFarmakaButton(ActionEvent event)
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
	    stage.setTitle("Εγγραφή Ασθενούς");
	    // Set the new scene in the existing stage:
	    stage.setScene(scene);
	    stage.setMinHeight(600);
	    stage.setMinWidth(600);
	    stage.setOnHidden(e->{
			loadVisits();
	    });
	    // Show the existing stage (though it is already showing, I think):
	    stage.show();	    
	}
	
	@FXML
	private void ehandleFarmakaAddButton(ActionEvent event)
	{
		String value = (String) efarmakachooser.getValue();
		efarmakaArea.appendText(value + ", ");
		efarmakaArea.getText().toString();
	}
	
	
	@FXML
	private void ehandleFarmakaButton(ActionEvent event)
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
				loadVisits();
				for(String str: getDataFromFarmakaToArray())
				{
					if(!efarmakachooser.getItems().contains(str))
					{
						efarmakachooser.getItems().add(str);
					}					
				}
		    });
		    stage.setScene(scene);
		    stage.setMinHeight(400);
		    stage.setMinWidth(300);
		    stage.show();

	}
	
	/**
	 * 		
	 * @param event
	 */
	
	@FXML
	private void ehandleRegButton(ActionEvent event)
	{
		
		String sex = esexChooser.getSelectionModel().getSelectedItem().toString();

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmnt= null;


			try {
				conn = DBConnection.getConnection();
				rs = conn.createStatement().executeQuery("SELECT * FROM PATIENTS WHERE surname LIKE '%" + MainMedCalController.pRowData.getSurname() + "%' AND "
						+ "name LIKE '%" + MainMedCalController.pRowData.getName() +"%'");
				
				String regDate = "";
				String id = "";
				while(rs.next())
				{
					regDate = rs.getString(12);
					id = rs.getString(15);
				}	
				
				String sqlUpdate = "UPDATE PATIENTS SET name=?, surname=?, amka=?, dob=?, sex=?, topos=?, ergasia=?, oikistoriko=?, prosistoriko=?, nosos=?, diagnosi=?, regdate=?, tilefono=?, farmaka=?, id=?"
						+ " WHERE id LIKE '"+ id +"'";
				pstmnt = conn.prepareStatement(sqlUpdate);
				
							
							pstmnt.setString(1, this.enameField.getText());
							pstmnt.setString(2, this.esurnameField.getText());
							pstmnt.setString(3, this.eamkaField.getText());
							pstmnt.setString(4, edobPicker.getEditor().getText());
							pstmnt.setString(5, sex);
							pstmnt.setString(6, this.eplaceOfStayField.getText());
							pstmnt.setString(7, this.eworkField.getText());
							pstmnt.setString(8, this.efamHistoryArea.getText());
							pstmnt.setString(9, this.epersHistoryArea.getText());
							pstmnt.setString(10, this.enososArea.getText());
							pstmnt.setString(11, this.ediagnosiArea.getText());
							pstmnt.setString(12, regDate);
							pstmnt.setString(13, this.etilField.getText());
							pstmnt.setString(14, this.efarmakaArea.getText());
							pstmnt.setString(15, id);
							
							if(!areFieldsEmpty())
							{
								if(isNumerical(eamkaField.getText()) && isNumerical(etilField.getText()))
								{
									if(isCorrectDate(edobPicker.getEditor().getText()))
									{
										pstmnt.execute();
										Alert alert4 = new Alert(AlertType.INFORMATION);
										DialogPane dialogPane4 = alert4.getDialogPane();
										dialogPane4.getStylesheets().add(
										   getClass().getResource("/view/dark-theme.css").toExternalForm());
										alert4.initStyle(StageStyle.UNDECORATED);
										alert4.setTitle("Πληροφορίες");
										alert4.setHeaderText(null);
										alert4.setContentText("Επιτυχής καταχώρηση");
										alert4.showAndWait();
										loadVisits();
									}
									else
									{	
										Alert alert2 = new Alert(AlertType.WARNING);
										DialogPane dialogPane2 = alert2.getDialogPane();
										dialogPane2.getStylesheets().add(
										   getClass().getResource("/view/dark-theme.css").toExternalForm());
										alert2.initStyle(StageStyle.UNDECORATED);
										alert2.setTitle("Πρόβλημα");
										alert2.setHeaderText(null);
										alert2.setContentText("Λάθος μορφή ημερομηνίας, παρακαλώ διαλέξτε ημερομηνία με το σχετικό κουμπί!");
										alert2.showAndWait();
									}
									
								}
								else
								{
									Alert alert3 = new Alert(AlertType.WARNING);
									DialogPane dialogPane3 = alert3.getDialogPane();
									dialogPane3.getStylesheets().add(
									   getClass().getResource("/view/dark-theme.css").toExternalForm());
									alert3.initStyle(StageStyle.UNDECORATED);
									alert3.setTitle("Πρόβλημα");
									alert3.setHeaderText(null);
									alert3.setContentText("Το ΑΜΚΑ ή/και το τηλέφωνο είναι λάθος!");
									alert3.showAndWait();
								}
							}else
							{
								Alert alert = new Alert(AlertType.WARNING);
								DialogPane dialogPane = alert.getDialogPane();
								dialogPane.getStylesheets().add(
								   getClass().getResource("/view/dark-theme.css").toExternalForm());
								alert.initStyle(StageStyle.UNDECORATED);
								alert.setTitle("Πρόβλημα");
								alert.setHeaderText(null);
								alert.setContentText("Παρακαλώ συμπληρώστε τα πεδία με τον αστερίσκο! ('Ονομα, Ημερομηνία, Επώμυμο)");
								alert.showAndWait();
							}

							
																												
							

							
			} catch (SQLException e) {
				System.out.println(e+"336 pat update");
				e.printStackTrace();

			} finally {
		        try {
		        	rs.close();
		        	pstmnt.close();
		            conn.close();
		            
		        } catch (SQLException e){System.err.println("Error 343 pat update" + e);}
		    }
		}
	
	
	
	/**
	 *===================================   V A L I D A T I O N    ===========================*
	 */
	


	private boolean areFieldsEmpty()
	{
		if(esurnameField.getText().isEmpty() || esurnameField.getText().isEmpty() || edobPicker.getEditor().getText().isEmpty())
		{
			return true;
		}
		else {
			return false;
		}				
	}
	
	private boolean isCorrectDate(String date)
	{
	    final Pattern pattern = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
	    if (!pattern.matcher(date.toString()).matches()) 
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }		
	}
	
	private boolean isNumerical(String text)
	{
		if (!text.matches("[0-9]+") && text.length() < 5)
		{
			return false;
		}
		return true;
	}

}
