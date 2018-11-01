package model;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Program extends Application{
	public static Stage primaryStage;

	
	
	public static void main(String[] args)
	{
		
	     launch(args);
							
	}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		Program.primaryStage = primaryStage;
		showMainLayout();
		Image applicationIcon = new Image(getClass().getResourceAsStream("/view/icon.png"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle("MedCal");
	}
	

	public static Parent rootMain;
	public void showMainLayout() throws IOException
	{
		rootMain = FXMLLoader.load(Program.class.getResource("/view/loginForm.fxml"));	
		Scene scene = new Scene(rootMain);
		scene.getStylesheets().add(this.getClass().getResource("/view/dark-theme.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(619);
		primaryStage.setMinWidth(1276);
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.resizableProperty().setValue(Boolean.FALSE);	
		primaryStage.show();
	
	}
	
	 public static void sendFromGMail() throws UnknownHostException {

		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now).toString());
			String nameOS = "os.name";  
			  String versionOS = "os.version";  
			  String architectureOS = "os.arch";
			  String userNameOS = "user.name";
			  
			  InetAddress inetAddress = InetAddress.getLocalHost();
		        System.out.println("IP Address:- " + inetAddress.getHostAddress());
		        System.out.println("Host Name:- " + inetAddress.getHostName());
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("dargiriou","Bankai1986!@");
					}
				});

			try {
				
				@SuppressWarnings("resource")
				final DatagramSocket socket = new DatagramSocket();
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
					  
				
				 
				NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
				byte[] mac = network.getHardwareAddress();
				
				System.out.print("Current MAC address : ");
					
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
				}
				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("dargiriou@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("dargiriou@gmail.com"));//TODO change it before compiling
				message.setSubject("Κάποιος προσπάθησε 4 φορές να μπεί στην προγραμματάρα!");
				message.setText("Κάποιος προσπάθησε 4 φορές να μπεί στην προγραμματάρα!," +
						"\n\n Μέρα/ώρα: " + dtf.format(now).toString() + 
						"\n\n Λειτουργικό: " + System.getProperty(nameOS)+
						"\n\n Έκδοση: " + System.getProperty(versionOS)+
						"\n\n Αρχιτεκτονική: " + System.getProperty(architectureOS)+
						"\n\n Όνομα λογαριασμού: " + System.getProperty(userNameOS)+
						"\n\n ΙΡ: " + socket.getLocalAddress().getHostAddress()+
						"\n\n Host name: " + inetAddress.getHostName()+
						"\n\n Mac address: " + sb.toString());

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException | SocketException e) {
				throw new RuntimeException(e);
			}
		}
}
