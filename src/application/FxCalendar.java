package application;

import application.model.store.FileStore;
import application.model.store.Store;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.MainMedCalController;

import java.lang.reflect.Constructor;

public class FxCalendar{


    public static Stage stage;

	@SuppressWarnings("deprecation")
	public static void start(Stage primaryStage) throws Exception {

    	stage = primaryStage;
    	

    	stage.setMaxWidth(1300);
    	stage.setMaxHeight(650);

    	stage.setMinWidth(1300);
    	stage.setMinHeight(650);
        FXMLLoader loader = new FXMLLoader(FxCalendar.class.getResource("fxcalendar.fxml"));
        Store store = new FileStore();

        loader.setControllerFactory((Class<?> type) -> {
            try {
                // look for constructor taking MyService as a parameter
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1) {
                        if (c.getParameterTypes()[0] == Store.class) {
                            return c.newInstance(store);
                        }
                    }
                }
                // didn't find appropriate constructor, just use default constructor:
                return type.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MainMedCalController.anchor = loader.load();
        
        Scene scene = new Scene(MainMedCalController.anchor, 512, 256);
        
        
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
        () {

              @Override
              public void handle(KeyEvent t) {
                if(t.getCode()==KeyCode.ESCAPE)
                {
                	stage.close();
                }
              }
          });
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 1.8); 
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 1.45);
        scene.getStylesheets().add(FxCalendar.class
                .getResource("/view/dark-theme.css").toExternalForm());

        stage.setTitle("Ημερολόγιο");
        stage.setScene(scene);
        stage.show();

        ReadOnlyDoubleProperty width = scene.widthProperty();
        MainMedCalController.anchor.styleProperty().bind(Bindings.createStringBinding(() -> String.format("-fx-font-size: %dpx", (int) width.get() / 80), width));

    }

}
