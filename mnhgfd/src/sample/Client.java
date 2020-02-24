package sample;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Client {
    public  String conversacion;
    public  javafx.scene.control.Label chat;
    public  javafx.scene.control.TextField texto;
    public  javafx.scene.control.TextField Direccion;
    public  javafx.scene.control.TextField Nickname;

    @FXML
    private void keyDetecction(javafx.scene.input.KeyEvent event){ // esta  funcion agrega lo escrito al presionar enter
        if(event.getCode()== KeyCode.ENTER){ //línea obtenida de https://stackoverflow.com/questions/27982895/how-to-use-keyevent-in-javafx-project
            Sender();
        }

    }
    @FXML
    private void action(javafx.event.ActionEvent event){ //al presionar el boton se agrega lo escrito
            Sender();

    }
    public  void Sender(){
        conversacion+=  "\n"  + texto.getText();
        texto.setText("");
        chat.setText(conversacion);

        try { // obtenido modelo try catch de https://www.youtube.com/watch?v=L0Y6hawPB-E

            Socket puente = new Socket("192.168.18.27",9999);
            Paquete datos= new Paquete(); //creo un objeto de la clase paquete con el sobrenombre datos

            datos.setNombre(Nickname.getText());
            datos.setMensaje(texto.getText());
            datos.setIp(Direccion.getText());

            ObjectOutputStream informacion = new ObjectOutputStream(puente.getOutputStream());
            informacion.writeObject(datos);
            puente.close(); //cierro el socket




        } catch (UnknownHostException errores){
            errores.printStackTrace();
        } catch (IOException e1){
            System.out.println("Error de Conexión");
        }

    }




}
