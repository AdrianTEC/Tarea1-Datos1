package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Application implements Runnable {

    private String userName;
    private String ip;
    private String message;
    private String Log="";
    private String userName2;
    private String message2;
    private String MY_IP_ADDRESS;
    private Label DIALOGBOX= new Label();
    private Paquete letter= new Paquete();
    private TextField ExitPort= new TextField();
    private Label ENTERPort= new Label();
    private TextField ipDirecctionS = new TextField();
    private Integer EnterPortNumber=1025;


    /*The start function is in charge of building the client window and show it
     *@author Adrián González Jiménez
     *@Version 02/03/2020
     * @param Stage primaryStage
    */
    @Override
    public void start(Stage primaryStage) throws  Exception {

        primaryStage.setTitle("DuckDelivery");
        //I ADD THE DUCK ICON
        Image image = new Image("sample/Delivery Duck.png");
        ImageView icon = new ImageView(image);
        icon.setFitHeight(103);
        icon.setFitWidth(103);
        icon.setLayoutX(310);

        // I ADD THE TEXT FIELD
        TextField textbox = new TextField();
        textbox.setPrefSize(300, 40);
        textbox.setLayoutY(330);
        textbox.setLayoutX(20);
        textbox.setStyle("-fx-background-color: #5073b5");
        textbox.setDisable(true);


        // IP TEXT FIELD
        TextField ipDirecction = new TextField();
        ipDirecction.setPrefSize(100, 30);
        ipDirecction.setLayoutY(100);
        ipDirecction.setLayoutX(380);
        ipDirecction.setPromptText("Receptor IP Address");
        ipDirecction.setStyle("-fx-background-color: #5073b5");

        //Server IP TEXT FIELD
        Label hostname= new Label();
        hostname.setText("  Server Address  ");
        hostname.setLayoutX(380);
        hostname.setLayoutY(155);
        hostname.setStyle("-fx-background-color: #395385;-fx-background-radius: 15");

        // THAT'S THE SERVER IP ADDRESS
        ipDirecctionS.setPrefSize(100, 30);
        ipDirecctionS.setLayoutY(180);
        ipDirecctionS.setLayoutX(380);
        ipDirecctionS.setPromptText("Host");
        ipDirecctionS.setText("127.0.0.1");
        ipDirecctionS.setStyle("-fx-background-color: #5073b5");


        // nick nick FIELD
        Label textNick= new Label();
        textNick.setText("Sender Name");
        textNick.setStyle("-fx-background-color: #5073b5;-fx-background-radius: 30");
        textNick.setLayoutX(410);
        textNick.setLayoutY(20);

        TextField nick = new TextField();
        nick.setPrefSize(70, 30);
        nick.setLayoutY(40);
        nick.setLayoutX(410);
        nick.setPromptText("Your name");
        nick.setText("Duck");
        nick.setStyle("-fx-background-color: #5073b5");


        //EXIT AND ENTER PORTS

        ExitPort.setPrefSize(70,30);
        ExitPort.setLayoutY(225);
        ExitPort.setPromptText("ExitPort");
        ExitPort.setLayoutX(410);
        ExitPort.setStyle("-fx-background-color: #5073b5");


        ENTERPort.setPrefSize(70,30);
        ENTERPort.setLayoutY(260);
        ENTERPort.setLayoutX(410);
        ENTERPort.setStyle("-fx-background-color: #5073b5");

        //I ADD THE DIALOG PANEL



        DIALOGBOX.setStyle("-fx-background-color: #5073b5 ;-fx-background-radius: 30");

        DIALOGBOX.setPrefSize(340, 240);
        DIALOGBOX.setLayoutX(20);
        DIALOGBOX.setLayoutY(40);
        DIALOGBOX.setAlignment(Pos.TOP_LEFT);
        DIALOGBOX.setPadding(new Insets(10, 10, 10, 10));

        //How to get my IP
        // get from https://www.lawebdelprogramador.com/codigo/Java/2532-Obtener-la-IP-local-y-la-IP-de-un-dominio.html



        InetAddress address = InetAddress.getLocalHost();
        MY_IP_ADDRESS=address.getHostAddress();


        Label Myip=new Label();
        Myip.setLayoutX(0);
        Myip.setLayoutY(0);
        Myip.setText("  Your Ip address is :  "+MY_IP_ADDRESS+"  ");
        Myip.setStyle("-fx-background-color: #5073b5;-fx-background-radius: 30");
        Myip.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        // I ADD THE BUTTON
        Button btn = new Button();
        btn.setDisable(true);
        btn.setText("Send");
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: #395385");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ip= ipDirecction.getText();
                userName= nick.getText();
                message= textbox.getText();

                if(!textbox.getText().isEmpty()){

                Log+="\n"+"<"+userName+">"  + message ;

                DIALOGBOX.setText(Log );
                try {
                    SendMesagge();
                }catch (Exception e){
                    Log+= "\n"+"<SERVIDOR>" + "Puerto erróneo o vacio";
                    DIALOGBOX.setText(Log);

                }

                textbox.setText("");}

            }
        });
        btn.setLayoutX(340);
        btn.setLayoutY(330);



        //button for port
        Button generator= new Button();
        generator.setLayoutX(400);
        generator.setLayoutY(330);
        generator.setStyle("-fx-background-color: #395385");
        generator.setPrefHeight(40);
        generator.setPrefWidth(80);
        generator.setText("Gen.Port");
        generator.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if(!ExitPort.getText().isEmpty()) {
                SetPort();
                ENTERPort.setText(String.valueOf(EnterPortNumber));
                btn.setDisable(false);
                generator.setDisable(true);
                textbox.setDisable(false);
                ;}
                    else {
                        Log+="\n Please insert a Exit Port";
                        DIALOGBOX.setText(Log);
                    }
                }catch (Exception e){
                    btn.setDisable(true);
                    generator.setDisable(false);
                    textbox.setDisable(true);
                }
            }
        });


        // I ADD THE COMPONENTS TO THE ROOT
        Pane root = new Pane();
        root.getChildren().addAll(textNick,generator,hostname,ipDirecctionS,DIALOGBOX,ENTERPort,Myip,nick,ipDirecction,textbox,btn,icon,ExitPort);
        root.setStyle("-fx-background-color: #202f4a");



        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setResizable(false);

        primaryStage.show();


        //Opens the thread for multitasking

        Thread HiloCliente= new Thread(this);
        HiloCliente.start();
    }

    /* This build the Client Window
     *@author Adrian González Jimenez
     * @Version 04/03/2020
     * @param nothing
     * @throws UnknownHostException
     * @throws IOException
     */
    private void SendMesagge(){


        //compressing data in package
        letter.setPort(EnterPortNumber);
        letter.setIp(ip);
        letter.setNombre(userName);
        letter.setMensaje(message);
        letter.setRemitente(MY_IP_ADDRESS);

        try {
            //BUILD SOCKET
            Socket puente = new Socket(ipDirecctionS.getText(), Integer.parseInt(ExitPort.getText()));
            //CREATE DATA FLOW
            ObjectOutputStream informacion = new ObjectOutputStream(puente.getOutputStream());
            //PUT THE PACKAGE IN THE DATA FLOW
            informacion.writeObject(letter);
            //CLOSES SOCKETS
            puente.close(); //cierro el socket
            informacion.close();


        } catch (UnknownHostException errores){ errores.printStackTrace(); }
          catch (IOException e1){ DIALOGBOX.setText(Log+"\n"+"<SERVIDOR>"+"Time out ERROR, verifique la ip destino  \n la permanencia a la misma red , o la  disponibilidad de \n un servidor"); }


        }

    /*This executes listening function, looking for data imputs and modifies DIALOG BOX
     *@author Adrian Gonzalez Jimenez
     * @Version 04/03/2020
     * @param nothing
     * @throws IOException | ClassNotFoundException
     */
    @Override
    public void run() {
        try {

            // CREATE SOCKET
            ServerSocket Servertoclient= new ServerSocket(9999);
            Paquete paquete_rec;

            while (true){
                //ACCEPT CONNECTIONS IN PORT
                Socket puenteS = Servertoclient.accept();
                // CREATING DATA FLOW
                ObjectInputStream informacion = new ObjectInputStream(puenteS.getInputStream());
                // INTERCEPT A PACKAGE
                paquete_rec = (Paquete) informacion.readObject();

                //SAVE PACKAGE DATA
                userName2 = paquete_rec.getNombre();
                ip = paquete_rec.getIp();
                message2 = paquete_rec.getMensaje();


                Log+= "\n"+"<"+userName2+">  :"+ message2;
                //ALLOWS INTERACTION BETWEEN THREADS
                Platform.runLater(()->{
                    DIALOGBOX.setText(Log);
                });

            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*This finds a free port for receiving data packages
     * @Version 04/03/2020
     * @param nothing
     * @throws IOException
     */
    public void SetPort(){
        boolean flag= true;
        while (flag && EnterPortNumber <= 60000 ) {
            EnterPortNumber= (int) (Math.random() * 60000) + 1025;
            try {
                ServerSocket servidorPrueba = new ServerSocket(EnterPortNumber);
                servidorPrueba.close();
                flag=false;
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}




