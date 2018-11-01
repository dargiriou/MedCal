package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

public class PerioxiControllerLmaoIsuck implements Initializable{

	@FXML
	private TextField perioxiTxtField;
	@FXML
	private DatePicker visitDatePicker;
	@FXML
	public BorderPane borderPaneBipolar;
	public static String perioxi;
	public static String visitDatePickerDate;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void handleKeysPresses(KeyEvent t)
	{
		if(t.getCode()==KeyCode.ENTER)
		{
			if((perioxiTxtField.getText().trim().isEmpty() || visitDatePicker.getValue() == null))
			{
				Alert alert = new Alert(AlertType.WARNING);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/view/dark-theme.css").toExternalForm());
				alert.initStyle(StageStyle.UNDECORATED);
				alert.setTitle("Άδειο πεδίο");
				alert.setHeaderText(null);
				alert.setContentText("Δήλωσε περιοχή ή/και ημερομηνία!!");
				alert.showAndWait();
			}
			else
			{
				perioxi = perioxiTxtField.getText();
				visitDatePickerDate = visitDatePicker.getEditor().getText();
				borderPaneBipolar.getScene().getWindow().hide();
			}

		}
		else if(t.getCode()==KeyCode.ESCAPE)
		{
			borderPaneBipolar.getScene().getWindow().hide();
		}
	}
		
}
