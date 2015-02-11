package com.sdc.curso.parserxml;

import java.util.Date;

/**
 * Created by mañá on 10/02/2015.
 */
public class Terremoto {
    private String id;  //formato nc:1234567
    private String titulo;
    private float magnitud;
    private Date fechaActualizacion;
    private float latitud;
    private float longitud;
    private String link;

    public Terremoto() {
    }

    public Terremoto(String id, String titulo, float magnitud, Date fechaActualizacion, float latitud, float longitud, String link) {
        this.id = id;
        this.titulo = titulo;
        this.magnitud = magnitud;
        this.fechaActualizacion = fechaActualizacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(float magnitud) {
        this.magnitud = magnitud;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Terremoto{" +
                "titulo='" + titulo + '\'' +
                ", magnitud=" + magnitud +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
