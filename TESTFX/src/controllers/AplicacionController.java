package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AplicacionController {
    private FileChooser seleccionadorArchivo = new FileChooser();
    private File archivoSeleccionado = null;
    private DirectoryChooser seleccionadorDirectorio = new DirectoryChooser();
    private File directorioSeleccionado = null;

    public ChoiceBox formato;
    public TextField rutaArchivo;
    public TextField rutaDirectorio;

    public ChoiceBox extension;
    public TextField nombreArchivo;
    public RadioButton eliminarLineasSinCodigo;
    public TextField cerosIzquierda;




    public void initialize() {
        formato.setItems(FXCollections.observableArrayList("BIN", "DEC", "HEX"));
        formato.setValue("BIN");
        extension.setItems(FXCollections.observableArrayList("asm", "txt", "lts",
                "lst", "py", "java", "cpp", "c", "json", "js"));
        extension.setValue("lst");
    }

    private String nombreSinExtension(String nombre){
        while(nombre.length() > 0 &&
                !nombre.substring(nombre.length()-1).equals(".")){
            nombre = nombre.substring(0, nombre.length()-1);
        }
        nombre = nombre.substring(0, nombre.length()-1);
        return nombre;
    }

    public void onClickSeleccionarArchivo(MouseEvent mouseEvent) {
        archivoSeleccionado =
                seleccionadorArchivo.showOpenDialog(Stage.getWindows().get(0));
        if(archivoSeleccionado != null){
            rutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
            nombreArchivo.setText(nombreSinExtension(archivoSeleccionado.getName()));
            rutaDirectorio.setText(archivoSeleccionado.getParent());
        }
    }

    public void onClickDirectorioSeleccionado(MouseEvent mouseEvent) {
        directorioSeleccionado =
                seleccionadorDirectorio.showDialog(Stage.getWindows().get(0));
        if(directorioSeleccionado != null){
            rutaDirectorio.setText(directorioSeleccionado.getAbsolutePath());
        }
    }

    private boolean lineaSinCodigo(String linea){
        String[] separaciones = linea.split(";");
        String codigo = separaciones[0];
        codigo = codigo.replace("\t", "");
        codigo = codigo.replace(" ", "");
        if(codigo.equals("")) return true;
        else return false;
    }

    private String eliminarComentarios(String linea){
        String[] separaciones = linea.split(";");
        String codigo = separaciones[0];
        return codigo;
    }

    private String formatearNumero(String base, int numero, int cerosIzquierda){
        String num = null;
        if (base.equals("BIN")) {
            num = Integer.toBinaryString(numero);
        } else if (base.equals("DEC")) {
            num = Integer.toString(numero);
        } else if (base.equals("HEX")) {
            num = Integer.toHexString(numero);
        }
        String cI = Integer.toString(cerosIzquierda);
        if(base.equals("BIN") || base.equals("DEC"))
            num = String.format("%0" + cI + "d", Integer.parseInt(num));
        else
            num = String.format("%0" + cI + "x", numero);
        return num;
    }

    public void onClickProcesarArchivo(MouseEvent mouseEvent) {
        try{
            BufferedReader lecturaArchivo = new BufferedReader(new FileReader(
                    archivoSeleccionado));
            FileWriter escribirArchivo = new FileWriter(
                    rutaDirectorio.getText() + '/' +
                    nombreArchivo.getText() + '.' + (String) extension.getValue());
            String linea;
            int i = 0;
            while((linea = lecturaArchivo.readLine()) != null){
                if(eliminarLineasSinCodigo.isSelected() && lineaSinCodigo(linea))
                    continue;
                if(eliminarLineasSinCodigo.isSelected())
                    linea = eliminarComentarios(linea);
                String numero = formatearNumero((String) formato.getValue(),
                        i, Integer.parseInt(cerosIzquierda.getText()));
                escribirArchivo.write(numero + " " + linea + '\n');
                i++;
            }

            lecturaArchivo.close();
            escribirArchivo.close();

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "../resources/dialogoAplicacion.fxml"));
            BorderPane borderPane = loader.load();
            dialogoAplicacionController controller =
                    loader.getController();
            controller.mostrarMensaje("Archivo creado correctamente");
            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
