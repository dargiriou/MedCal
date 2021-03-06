package application;

import application.model.Appointment;
import application.model.Day;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Program;
import java.io.IOException;

public class DayComponent extends VBox {

    @FXML
    private final ObjectProperty<Day> day = new SimpleObjectProperty<>();
    @FXML
    private ListView<Appointment> appointmentsListView;

    public DayComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("day_component.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        appointmentsListView.setCellFactory(l -> {
            ListCell<Appointment> cell = new AppointmentCell();
            cell.setOnMouseClicked(this::handleAppointmentClick);
            return cell;
        });
    }

    @FXML
    public void handleAppointmentsClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                openCreateAppointmentWindow();
            }
        }
    }

    private void handleAppointmentClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                AppointmentCell cell = (AppointmentCell) event.getSource();
                Appointment appointment = cell.getItem();
                if (appointment != null) {
                    openEditAppointmentWindow(appointment);
                    event.consume();
                }
            }
        }
    }

    private void openCreateAppointmentWindow() {
        openAppointmentWindow(null, "Create appointment");
    }

    private void openEditAppointmentWindow(Appointment appointment) {
        openAppointmentWindow(appointment, "Edit appointment");
    }

    private void openAppointmentWindow(Appointment appointment, String windowTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment_details.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(FxCalendar.class
                    .getResource("/view/dark-theme.css").toExternalForm());
            AppointmentDetails appointmentDetails = loader.getController();
            appointmentDetails.setDay(day.get());
            if (appointment != null) {
                appointmentDetails.setAppointment(appointment);
            }
            
            Stage stage = new Stage();
            stage.setOnHidden(e -> {
            	try {
					FxCalendar.start(Program.primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root, 450, 300));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Day getDay() {
        return day.get();
    }

    public void setDay(Day day) {
        this.day.set(day);
    }

    public ObjectProperty<Day> dayProperty() {
        return day;
    }
}
