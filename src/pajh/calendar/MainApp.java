package pajh.calendar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pajh.calendar.model.Event;
import pajh.calendar.model.EventListWrapper;
import pajh.calendar.util.iCalendarFormatUtil;
import pajh.calendar.view.DeleteOlderThanDialogController;
import pajh.calendar.view.EventEditDialogController;
import pajh.calendar.view.EventsOverviewController;
import pajh.calendar.view.RootLayoutController;
import pajh.calendar.view.SetAlarmDialogController;
import pajh.calendar.view.SettingsDialogController;

/**
 * Main app class.
 */
public class MainApp extends Application {

	/**
	 * Observable list of type Event collecting all saved events.
	 */
	private ObservableList<Event> eventData = FXCollections.observableArrayList();

	/**
	 * Method adding one Event to Observable list of Events.
	 *
	 * @param event Event object
	 */
	public void addEvent(Event event) { eventData.add(event); }

	/**
	 * MainApp construct.
	 */
	public MainApp() {
		eventData.add(new Event("��d�", "Miliony rzeczy do zrobienia", LocalDate.of(1995, 03, 02), LocalTime.of(12, 00)));
		eventData.add(new Event("Wawa", "#rzycie #jest #suabe", LocalDate.of(1995, 03, 02), LocalTime.of(12, 00)));
		eventData.add(new Event("Krak�w", "lubi� placuszki", LocalDate.of(2016, 06, 19), LocalTime.of(00, 30)));
	}

	/**
	 * Method returning Observable List of Event objects.
	 *
	 * @return eventData Observable list of Event type
	 */
    public ObservableList<Event> getEventData() {
        return eventData;
    }

	/**
	 * Primary stage of app.
	 */
	private Stage primaryStage;
	/**
	 * Border pane for bordering app with RootLayout.
	 */
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Organizer");

		initRootLayout();

		showEventOverview();
		setEventFilePath(null);
	}

	/**
	 * Method initializing @RootLayout
	 */
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

	/**
	 * Initialize and show @EventsOverview.
	 */
    public void showEventOverview() {
        try {
            // Load month overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EventsOverview.fxml"));
            AnchorPane eventsOverview = (AnchorPane) loader.load();

            // Set month overview into the center of root layout.
            rootLayout.setCenter(eventsOverview);

            // Give the controller access to the main app.
            EventsOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Method initializing @SettingsDialog. Returns true if OK button was clicked,
	 * else returns false if exception was thrown.
	 *
	 */
    public boolean showSettingsDialog() {
        try {
            // Load month overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SettingsDialog.fxml"));
            AnchorPane settingsDialog = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ustawienia");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(settingsDialog);
            dialogStage.setScene(scene);

            // Give the controller access to the main app.
            SettingsDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	/**
	 * Initialize and show EventEditDialog. Returns true if OK button was clicked,
	 * else returns false if exception was thrown.
	 *
	 * @param event Event we are editing
	 */
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

	/**
	 * Initialize and show SetAlarmDialog. Returns true if OK button was clicked,
	 * else returns false if exception was thrown.
	 *
	 * @param event Event that we are setting alarm for
	 */
    public boolean showSetAlarmDialog(Event event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SetAlarmDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Przypomnienie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SetAlarmDialogController controller = loader.getController();
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

	/**
	 * Initialize and show DeletingOlderThanDialog. Returns true if OK button was clicked,
	 * else returns false if exception was thrown.
	 *
	 */
    public boolean showDeleteOlderThanDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DeleteOlderThanDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Usun wydarzenia starsze ni�...");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DeleteOlderThanDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the events file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     */
    public File getEventFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setEventFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Organizer - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Organizer");
        }
    }

    /**
     * Loads xml file and unmarshal it to @EventListWrapper wrapper, then add events
     * to {@link MainApp#eventData}.
     *
     * @param file the file we are opening
     */
    public void loadEventDataFromFileXML(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(EventListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            EventListWrapper wrapper = (EventListWrapper) um.unmarshal(file);

            eventData.clear();
            eventData.addAll(wrapper.getEvents());

            // Save the file path to the registry.
            setEventFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("B��d");
            alert.setHeaderText("Nie mo�na wczyta� danych");
            alert.setContentText("Nie mo�na wczyta� danych z pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Wraps {@link MainApp#eventData} into @EventListWrapper wrapper, then marshal it to a file.
     *
     * @param file the file we are saving to
     */
    public void saveEventDataToFileXML(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(EventListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            EventListWrapper wrapper = new EventListWrapper();
            wrapper.setEvents(eventData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setEventFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("B��d");
            alert.setHeaderText("Nie mo�na zapisa� danych");
            alert.setContentText("Nie mo�na zapisa� danych do pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Using @iCalendarFormatUtil convert eventData to .ics format.
     *
     * @param file the file we are saving to
     */
    public void saveEventDataToFileICS(File file){

        try {

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(iCalendarFormatUtil.ConvertToiCalendarFormat(eventData));
            bw.close();

        } catch (Exception e) {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("B��d");
            alert.setHeaderText("Nie mo�na zapisa� danych");
            alert.setContentText("Nie mo�na zapisa� danych do pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Returns {@link MainApp#primaryStage}.
     * 
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main of the whole app, launches an application.
     */
	public static void main(String[] args) {
		launch(args);
	}
}
