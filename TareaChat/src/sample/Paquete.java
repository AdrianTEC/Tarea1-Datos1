package sample;

import java.io.Serializable;
/* Paquete class makes packages for data flows
 * @author Adrian Gonzalez Jimenez
 * @Version 04/03/2020
 *
 */
public class Paquete implements Serializable //para enviar el paquete tengo que convertirlo en una serie de bites
{
    private String Nombre;
    private String ip;
    private String mensaje;
    private Integer port;
    private String remitente;

    //SETTERS AND GETTERS
    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public Paquete(){
        Nombre="";
        ip="";
        mensaje="";
        port=9999;
    }
    public Integer getPort() { return port; }

    public void setPort(Integer port) { this.port = port; }
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