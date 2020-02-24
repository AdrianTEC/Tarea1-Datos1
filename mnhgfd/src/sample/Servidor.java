package sample;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
public class Servidor implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket servidor= new ServerSocket(9999);
            Socket puente= servidor.accept();
            ObjectInputStream informacion= new ObjectInputStream(puente.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
