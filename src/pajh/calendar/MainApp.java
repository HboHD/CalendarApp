package pajh.calendar;

import pajh.calendar.model.*;
import pajh.calendar.view.*;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private ObservableList<Event> eventData = FXCollections.observableArrayList();

	public MainApp() {
		eventData.add(new Event("��d�", "chujow sto do zrobienia mam"));
		eventData.add(new Event("Wawa", "chujow dwiescie do zrobienia mam"));
		eventData.add(new Event("Krak�w", "lubie placuszki i male cycuszki"));
	}
	
    public ObservableList<Event> getPersonData() {
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


    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
