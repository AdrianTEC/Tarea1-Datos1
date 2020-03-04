package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class Server extends Application implements Runnable {
    private String nick;
    private String ip;
    private String mensaje;
    private Label DIALOGBOX=new Label();;
    private Label Myip=new Label();
    private Label port =new Label();
    private String Log="";
    private Thread Hilo;
    private Integer EnterPortNumber= 1025;

    /*Star builds Server Window
        Using JAVAFX
            Using Thread
     */

    @Override
    public void start(Stage Serverstage) throws Exception {
        Serverstage.setTitle("SERVER");
        //I ADD THE DUCK ICON
        Image image = new Image("sample/Delivery Duck.png");
        ImageView icon = new ImageView(image);
        icon.setFitHeight(303);
        icon.setFitWidth(303);
        icon.setLayoutX(200);
        DIALOGBOX=new Label();
        DIALOGBOX.setStyle("-fx-background-color: #5073b5 ;-fx-background-radius: 30");
        DIALOGBOX.setPrefSize(210, 240);
        DIALOGBOX.setLayoutX(20);
        DIALOGBOX.setLayoutY(40);
        DIALOGBOX.setAlignment(Pos.TOP_LEFT);
        DIALOGBOX.setPadding(new Insets(10, 10, 10, 10));



        //get from https://www.lawebdelprogramador.com/codigo/Java/2532-Obtener-la-IP-local-y-la-IP-de-un-dominio.html
        InetAddress address = InetAddress.getLocalHost();
        //
        Myip.setLayoutX(30);
        Myip.setLayoutY(350);
        Myip.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Myip.setText("   The server ip is ; "+address.getHostAddress()+"  ");
        Myip.setStyle("-fx-background-color: #5073b5;-fx-background-radius: 30");

        port.setLayoutX(30);
        port.setLayoutY(320);
        port.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        port.setStyle("-fx-background-color: #5073b5;-fx-background-radius: 30");
        // I ADD THE COMPONENTS TO THE ROOT
        Pane root = new Pane();
        root.getChildren().addAll(Myip,DIALOGBOX,icon,port);

        root.setStyle("-fx-background-color: #202f4a");
        Serverstage.setScene(new Scene(root, 500, 400));
        Serverstage.setResizable(false);

        Serverstage.show();
        SetPort();
        port.setText("   The server port is  ; "+EnterPortNumber+"  ");
        Hilo= new Thread(this);
        Hilo.start();

    }
    /* Run function is  in charge of keep server listenning client demands while Server Window is on.

    */

    @Override
    public void run() {


        try {

            ServerSocket servidor= new ServerSocket(EnterPortNumber); //intenta hacer la conexion
            Paquete paquete_rec;

            //noinspection InfiniteLoopStatement
            while (true) {


                Socket puenteS = servidor.accept();

                /*This catch who is connected
                  get from https://www.youtube.com/watch?v=zsONbH8HDEk&t=22s
                 */
                if(puenteS.getInetAddress()!=null){

                    InetAddress location=  puenteS.getInetAddress();
                    String IpRemota = location.getHostAddress();
                    System.out.println(IpRemota);
                    Log+= "\n" + IpRemota + "Is connected";
                    Platform.runLater(()->{
                        DIALOGBOX.setText( Log);
                    });
                }




                ObjectInputStream informacion = new ObjectInputStream(puenteS.getInputStream());

                paquete_rec = (Paquete) informacion.readObject();

                //Redefino las variables del objeto paquete_rec
                nick = paquete_rec.getNombre();
                ip = paquete_rec.getIp();
                mensaje = paquete_rec.getMensaje();

                /* Get from https://www.reddit.com/r/javahelp/comments/7qvqau/problem_with_updating_gui_javafx/
                * it allows me to edit the DialogBox what is bein used by Hilo
                 */

                Log+= "\n"+"<"+nick+">  :"+ mensaje;
                Platform.runLater(()->{
                    DIALOGBOX.setText(Log);
                });




                //establezco un puente hasta el otro  cliente
                if(!ip.equals("")) {
                    ObjectOutputStream P_reenvio;
                    try (Socket destino = new Socket(ip, 9090)) {
                        P_reenvio = new ObjectOutputStream(destino.getOutputStream());

                        //sobrescribo  a P_reenvio con la información del paquete recibido
                        P_reenvio.writeObject(paquete_rec);
                        destino.close();
                        P_reenvio.close();
                        //CIERRO LOS FLUJOS DE DATOS
                    } catch (Exception W) {
                        System.out.println("NO SE PUDO CONECTAR");

                    }
                }

                puenteS.close();

                informacion.close();

            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*SetPort finds an available port for client connections, it modify the integer EnterPortNumber until finds
    a available port.

     */

    public void SetPort(){
        boolean flag= true;
        while (flag && EnterPortNumber <= 60000 ) {
            EnterPortNumber +=1;
            try {
                ServerSocket servidorPrueba = new ServerSocket(EnterPortNumber);
                servidorPrueba.close();
                flag=false;
            } catch (IOException e) {
                System.out.println("PortOccupiedError");
                e.printStackTrace();
            }

        }
    }

}

/*User is a class that save the user information like Nickname, IP, and entrancePort
it allows me to connect with different entrance port users


 */
class user{


    String Nickname;
    String Ip;
    Integer EntrancePort;
    public user(){
        Nickname="";
        Ip="";
        EntrancePort=9999;
    }
    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public Integer getEntrancePort() {
        return EntrancePort;
    }

    public void setEntrancePort(Integer entrancePort) {
        EntrancePort = entrancePort;
    }
}