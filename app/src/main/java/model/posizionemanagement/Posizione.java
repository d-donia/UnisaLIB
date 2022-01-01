package model.posizionemanagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import model.postazionemanagement.Postazione;

public class Posizione{
    private int id;
    private String biblioteca, zona;
    private ArrayList<Postazione> postazioni;

    public Posizione(int id, String biblioteca, String zona, ArrayList<Postazione> postazioni) {
        this.id = id;
        this.biblioteca = biblioteca;
        this.zona = zona;
        this.postazioni = postazioni;
    }

    public Posizione(int id, String biblioteca, String zona) {
        this.id = id;
        this.biblioteca = biblioteca;
        this.zona = zona;
    }

    public Posizione(String biblioteca, String zona) {
        this.biblioteca = biblioteca;
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(String biblioteca) {
        this.biblioteca = biblioteca;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public ArrayList<Postazione> getPostazioni() {
        return postazioni;
    }

    public void setPostazioni(ArrayList<Postazione> postazioni) {
        this.postazioni = postazioni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posizione posizione = (Posizione) o;
        return id == posizione.id && biblioteca.equals(posizione.biblioteca) && zona.equals(posizione.zona);
    }
}
