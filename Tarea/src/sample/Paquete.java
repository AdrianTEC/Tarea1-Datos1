package sample;

import java.io.Serializable;

public class Paquete implements Serializable //para enviar el paquete tengo que convertirlo en una serie de bites
{
    private String Nombre;
    private String ip;
    private String mensaje;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}