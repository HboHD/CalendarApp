package pajh.calendar;

import pajh.calendar.model.*;
import pajh.calendar.view.*;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private ObservableList<Event> eventData = FXCollections.observableArrayList();
	public void addEvent(Event event) { eventData.add(event); }

	public MainApp() {
		eventData.add(new Event("£ódŸ", "Miliony rzeczy do zrobienia", LocalDate.of(1995, 03, 02)));
		eventData.add(new Event("Wawa", "#rzycie #jest #suabe", LocalDate.of(1995, 03, 02)));
		eventData.add(new Event("Kraków", "lubiê placuszki", LocalDate.of(1995, 03, 02)));
	}
	
    public ObservableList<Event> getEventData() {
        return eventData;
    }
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Organizer");

		initRootLayout();

		showEventOverview();
	}

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            RootLayoutController controller = loader.getController(); 
            controller.setMainApp(this);
            
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEventOverview() {
        try {
            // Load month overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MonthOverview.fxml"));
            AnchorPane monthOverview = (AnchorPane) loader.load();

            // Set month overview into the center of root layout.
            rootLayout.setCenter(monthOverview);
            
            // Give the controller access to the main app.
            MonthOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showEventEditDialog(Event event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EventEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja wydarzenia");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EventEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEvent(event);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showDeleteOlderThanDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DeleteOlderThanDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Usun wydarzenia starsze ni¿...");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            DeleteOlderThanDialogController controller = loader.getController();            
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
