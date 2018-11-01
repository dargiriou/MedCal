package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.AES_DB_Encrypt;
import model.BCrypt;
import model.Program;

public class loginController implements Initializable {

	@FXML
	private Button loginBtn;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private BorderPane loginPane;
	
	private static  String kodikos;
	Cipher cipher = null;
	private int loginAttemptsCounter;

    private double xOffset = 0; 
    private double yOffset = 0;
    public static Stage stage = new Stage();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		loginPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
		loginPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Program.primaryStage.setX(event.getScreenX() - xOffset);
                Program.primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
	}
   
	public void setKodiko(String value)
	{
		kodikos = value;
	}

	private void encrypt()
	{
		File inputFile = new File("MedCalDB.db");
		File encryptedFile = new File("MedCalDB.db");
		String key = "lp0230441lp0230441lp0230441lp023";
		if(inputFile.exists())
		{			
			AES_DB_Encrypt.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);			
			System.out.println("Encryption succes!!");					     
		}
		else {
			System.out.println("Database not found!");
			Platform.exit();
		}
	}
	
	private void decrypt()
	{
		File encryptedFile = new File("MedCalDB.db");
		String key = "lp0230441lp0230441lp0230441lp023";	
			if(encryptedFile.exists())
			{
				File decryptedFile = new File("MedCalDB.db");
				AES_DB_Encrypt.fileProcessor(Cipher.DECRYPT_MODE,key,encryptedFile,decryptedFile);
				System.out.println("Decryption succes!!");	
			}
			else
			{
				System.out.println("Database not found!");
			}		
	}		
	/**
	 * PRESSING ENTER
	 * @param t
	 */
	@FXML
	private void handleKeysPresses(KeyEvent t)
	{
		if(t.getCode()==KeyCode.ESCAPE)
		{
			Platform.exit();
		}
		else if(t.getCode()==KeyCode.ENTER)
		{
			kodikos=passwordField.getText();
			String originalPassword = "lp0230441";
			String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
			boolean matched = BCrypt.checkpw(kodikos, generatedSecuredPasswordHash);
			
		     
		if(usernameField.getText().equals("lef")&& matched)
		{
			try {
				FXMLLoader loader = new FXMLLoader();				
				FadeTransition ftt = new FadeTransition();
				ftt.setDuration(Duration.seconds(1));
				ftt.setNode(Program.primaryStage.getScene().getRoot());
				ftt.setFromValue(1);
				ftt.setToValue(0);
				ftt.play();
		        
				loader.setLocation(MainMedCalController.class.getResource("/view/MainMedCal.fxml"));
				VBox main = loader.load();
				stage = new Stage();			
				stage.setMinHeight(619);
				stage.setMinWidth(1300);
					
				FadeTransition ft = new FadeTransition();					
				ft.setDuration(Duration.seconds(1));
				ft.setNode(main);
				ft.setFromValue(0);
				ft.setToValue(1);
				ft.play();

				stage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(main);
				scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
			    stage.setScene(scene);
			    stage.setOnShown(e ->{
			    	Program.primaryStage.close();			    	
			    	decrypt();		    	
					    });


			    stage.setOnHidden(e -> {
			    	encrypt();
			    	Platform.exit();
					});
				    				    
				Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
				stage.getIcons().add(applicationIcon);
				stage.setTitle("MedCal");
			    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			    Program.primaryStage.setX((primScreenBounds.getWidth() - Program.primaryStage.getWidth()) / 2);
			    Program.primaryStage.setY((primScreenBounds.getHeight() - Program.primaryStage.getHeight()) / 2);
			    stage.show();
				    
				} catch (IOException e) {
					System.out.println(e + "146 login");

				}
	 
			}
			else
			{
				loginAttemptsCounter++;
				if(loginAttemptsCounter >= 3)
				{
					try {
						Program.sendFromGMail();
					} catch (UnknownHostException e) {
						
						e.printStackTrace();
					}
				}
				Alert alert = new Alert(AlertType.WARNING);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.getDialogPane().setMaxWidth(300);
				alert.setTitle("Λάθος κωδικός");
				alert.setHeaderText(null);
				alert.setContentText("Λάθος κωδικος!");
				alert.showAndWait();			
				}
			}
		}
	
	/**
	 * LOGIN BUTTON
	 * @param event
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	@FXML
	private void handleLoginBtn(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		kodikos=passwordField.getText();
		String originalPassword = "lp0230441";
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		boolean matched = BCrypt.checkpw(kodikos, generatedSecuredPasswordHash);
		     
		if(usernameField.getText().equals("lef")&& matched)
		{
			
			FadeTransition ftt = new FadeTransition();
			ftt.setDuration(Duration.seconds(1));
			ftt.setNode(Program.primaryStage.getScene().getRoot());
			ftt.setFromValue(1);
			ftt.setToValue(0);
			ftt.play();
			
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainMedCalController.class.getResource("/view/MainMedCal.fxml"));				
				VBox main = loader.load();
				stage = new Stage();
				stage.setMinHeight(619);
			    stage.setMinWidth(1300);

				FadeTransition ft = new FadeTransition();				
				ft.setDuration(Duration.seconds(3));
				ft.setNode(main);
				ft.setFromValue(0);
				ft.setToValue(1);
				ft.play();
				
				stage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(main);
				scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
			    stage.setScene(scene);
			    stage.setOnShown(e ->{
			
			    	decrypt();
			    	Program.primaryStage.close();
			    });
			    stage.setOnHidden(e -> {
			    	encrypt();
			    	Platform.exit();
				});
			   
			    Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
			    stage.getIcons().add(applicationIcon);
			    stage.setTitle("MedCal");
		        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		        Program.primaryStage.setX((primScreenBounds.getWidth() - Program.primaryStage.getWidth()) / 2);
		        Program.primaryStage.setY((primScreenBounds.getHeight() - Program.primaryStage.getHeight()) / 2);
			    stage.show();

			} catch (IOException e) {
				System.out.println(e + "243 login");
			} 
		}
		else
		{
			loginAttemptsCounter++;
			if(loginAttemptsCounter >= 3)
			{
				try {
					Program.sendFromGMail();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			Alert alert = new Alert(AlertType.WARNING);
			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(
			getClass().getResource("/view/dark-theme.css").toExternalForm());
			alert.initStyle(StageStyle.UNDECORATED);
			alert.getDialogPane().setMaxWidth(300);
			alert.setTitle("Λάθος κωδικός");
			alert.setHeaderText(null);
			alert.setContentText("Λάθος κωδικος!");
			alert.showAndWait();	
		}
	}	
}
