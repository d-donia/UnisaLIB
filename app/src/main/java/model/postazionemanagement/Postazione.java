package model.postazionemanagement;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;

public class Postazione {
    private String id;
    private boolean disponibile;
    private Posizione posizione;
    private ArrayList<Periodo> blocchi;

    public Postazione(String id, boolean disponibile, Posizione posizione) {
        this.id = id;
        this.disponibile = disponibile;
        this.posizione = posizione;
    }

    public Postazione(boolean disponibile, Posizione posizione) {
        this.disponibile = disponibile;
        this.posizione = posizione;
    }


    public static String toJson(ArrayList<Postazione> postazioni) {
        Gson gson = new Gson();
        return gson.toJson(postazioni);
    }

    public static Postazione[] fromJson(JSONArray response) {
        Gson gson = new Gson();
        return gson.fromJson("" + response, Postazione[].class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public ArrayList<Periodo> getBlocchi() {
        return blocchi;
    }

    public void setBlocchi(ArrayList<Periodo> blocchi) {
        this.blocchi = blocchi;
    }
}
