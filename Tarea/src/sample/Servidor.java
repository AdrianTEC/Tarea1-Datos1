package sample;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class Servidor extends Application implements Runnable  {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Servidor.fxml"));
        primaryStage.setTitle("Servidor");
        primaryStage.setScene(new Scene(root, 382, 314));
        primaryStage.show();
    }
    @Override
    public void run() {
        try {
            ServerSocket servidor= new ServerSocket(9999);

            String nick;
            String ip;
            String mensaje;

            Paquete paquete_rec;

            while(true){
                Socket puente= servidor.accept();
                ObjectInputStream informacion= new ObjectInputStream(puente.getInputStream());
                paquete_rec=(Paquete) informacion.readObject();

                nick= paquete_rec.getNombre();
                ip= paquete_rec.getIp();
                mensaje= paquete_rec.getMensaje();
                Socket destino= new Socket(ip,9090);
                ObjectOutputStream P_reenvio = new ObjectOutputStream(destino.getOutputStream());

                P_reenvio.writeObject(paquete_rec);
                destino.close();
                puente.close();

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
