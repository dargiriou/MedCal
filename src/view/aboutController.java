package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class aboutController implements Initializable{

    
	@FXML
	private ImageView gif;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gif.setImage(new Image(getClass().getResource("/view/giphy.gif").toExternalForm()));		
	
	}
	


	
}
