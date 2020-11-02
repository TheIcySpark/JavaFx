import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stagePrincipal;

    @Override
    public void init() throws Exception {
        System.out.println("INIT");
    }

    @Override
    public void start(Stage stage) throws Exception{
        System.out.println("START");
        stagePrincipal = stage;

        Parent fxml = FXMLLoader.load(getClass().getResource(
                "resources/aplicacion.fxml"));
        stagePrincipal.setTitle("Aplicacion");
        Scene scene = new Scene(fxml);
        scene.getStylesheets().add("stylesheets/style.css");
        stagePrincipal.setScene(scene);
        stagePrincipal.setResizable(false);
        stagePrincipal.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("STOP");
    }

    public static void main(String[] args){
        System.out.println("MAIN");
        launch(args);
    }

}
