package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Application implements Runnable {

    private String userName;
    private String ip;
    private String message;
    private String Log="";

    private String userName2;
    private String ip2;
    private String message2;


    private Paquete letter= new Paquete();

    private Label DIALOGBOX= new Label();

    @Override
    public void start(Stage primaryStage) throws  Exception {

        primaryStage.setTitle("DuckDelivery");
        //I ADD THE DUCK ICON
        Image image = new Image("sample/Delivery Duck.png");
        ImageView icon = new ImageView(image);
        icon.setFitHeight(103);
        icon.setFitWidth(103);
        icon.setLayoutX(330);
        // I ADD THE TEXT FIELD
        TextField textbox = new TextField();

        textbox.setPrefSize(300, 40);
        textbox.setLayoutY(330);
        textbox.setLayoutX(20);
        textbox.setStyle("-fx-background-color: #5073b5");
        // IP TEXT FIELD
        TextField ipDirecction = new TextField();
        ipDirecction.setPrefSize(100, 30);
        ipDirecction.setLayoutY(100);
        ipDirecction.setLayoutX(380);
        ipDirecction.setPromptText("IP Address");

        // nick nick FIELD
        TextField nick = new TextField();
        nick.setPrefSize(100, 30);
        nick.setLayoutY(140);
        nick.setLayoutX(380);
        nick.setPromptText("Sender");
        //I ADD THE DIALOG PANEL



        DIALOGBOX.setStyle("-fx-background-color: #5073b5 ;-fx-background-radius: 30");

        DIALOGBOX.setPrefSize(340, 240);
        DIALOGBOX.setLayoutX(20);
        DIALOGBOX.setLayoutY(40);
        DIALOGBOX.setAlignment(Pos.TOP_LEFT);
        DIALOGBOX.setPadding(new Insets(10, 10, 10, 10));


        // I ADD THE BUTTON
        Button btn = new Button();
        btn.setText("Send");
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: #5073b5");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ip= ipDirecction.getText();
                userName= nick.getText();
                message= textbox.getText();
                Log+="<"+userName+"> : "+ message + "\n";
                DIALOGBOX.setText(Log );
                SendMesagge();
                textbox.setText("");

            }
        });
        btn.setLayoutX(340);
        btn.setLayoutY(330);
        Pane root = new Pane();
        // I ADD THE COMPONENTS TO THE ROOT
        root.getChildren().add(nick);
        root.getChildren().add(ipDirecction);
        root.getChildren().add(DIALOGBOX);
        root.getChildren().add(textbox);

        root.getChildren().add(btn);
        root.getChildren().add(icon);
        root.setStyle("-fx-background-color: #202f4a");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        Thread HiloCliente= new Thread(this);
        HiloCliente.start();
    }

    private void SendMesagge(){
    //compressing data
    letter.setIp(ip);
    letter.setNombre(userName);
    letter.setMensaje(message);

        try {
            //CREO SOCKET
            Socket puente = new Socket("192.168.18.27",9999);

            ObjectOutputStream informacion = new ObjectOutputStream(puente.getOutputStream());

            informacion.writeObject(letter);
            puente.close(); //cierro el socket
            informacion.close();


        } catch (UnknownHostException errores){ errores.printStackTrace(); }
          catch (IOException e1){ DIALOGBOX.setText(Log+"\n"+"Time out ERROR"); }



    }


    @Override
    public void run() {
        try {
            ServerSocket Servertoclient= new ServerSocket(9090);
            Paquete paquete_rec;

            while (true){
                Socket puenteS = Servertoclient.accept();
                ObjectInputStream informacion = new ObjectInputStream(puenteS.getInputStream());
                paquete_rec = (Paquete) informacion.readObject();

                //Redefino las variables del objeto paquete_rec
                userName2 = paquete_rec.getNombre();
                ip = paquete_rec.getIp();
                message2 = paquete_rec.getMensaje();


                Log+= "\n"+"<"+userName2+">  :"+ message2;
                Platform.runLater(()->{
                    DIALOGBOX.setText(Log);
                });
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}




