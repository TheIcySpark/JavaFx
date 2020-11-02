package controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;

public class dialogoAplicacionController {

    public Label mensaje;

    public void mostrarMensaje(String m){
        mensaje.setText(m);
    }

    public void onClickAceptar(MouseEvent mouseEvent) {
        Stage stage = (Stage) mensaje.getScene().getWindow();
        stage.close();
    }
}
