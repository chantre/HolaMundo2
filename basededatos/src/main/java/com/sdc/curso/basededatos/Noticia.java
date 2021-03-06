package com.sdc.curso.basededatos;

import java.util.Date;

/**
 * Created by mañá on 10/02/2015.
 */
public class Noticia {
    private int id;
    private String titular;
    private Date fecha;
    private String autor;
    private String contenido;

    public static final String CAMPO_ID = "id";
    public static final String CAMPO_TITULAR = "Titular";
    public static final String CAMPO_FECHA = "Fecha";
    public static final String CAMPO_AUTOR = "Autor";
    public static final String CAMPO_CONTENIDO = "Contenido";
    public static final String TABLA = "Noticias";

    public Noticia() {
    }

    public Noticia(int id, String titular, Date fecha, String autor, String contenido) {
        this.id = id;
        this.titular = titular;
        this.fecha = fecha;
        this.autor = autor;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
