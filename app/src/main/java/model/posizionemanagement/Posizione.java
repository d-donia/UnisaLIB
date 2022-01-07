package model.posizionemanagement;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import model.libromanagement.Libro;
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
        postazioni = new ArrayList<>();
    }

    public Posizione(String biblioteca, String zona) {
        this.biblioteca = biblioteca;
        this.zona = zona;
        postazioni = new ArrayList<>();
    }

    public Posizione() {
        postazioni = new ArrayList<>();
    }

    public static Posizione[] fromJsonEtic(JSONArray response) throws JSONException {
        ArrayList<Posizione> c=new ArrayList<>();
        for(int i=0;i<response.length();++i)
            c.add(Posizione.fromJson(response.getJSONObject(i)));
        Posizione[] array = new Posizione[c.size()];
        array = c.toArray(array);
        return array;
    }

    public static Posizione fromJson(JSONObject response) throws JSONException {
        Gson gson = new Gson();
        Posizione p = gson.fromJson("" + response.get("posizione"), Posizione.class);
        return p;
    }

    public static Posizione[] fromJson(JSONArray response) {
        Gson gson = new Gson();
        Posizione[] posizioni = gson.fromJson("" + response, Posizione[].class);
        return posizioni;
    }

    public static String toJson(ArrayList<Posizione> posizioni) {
        Gson gson = new Gson();
        return gson.toJson(posizioni);
    }

    public static ArrayList<Posizione> fromJson(String json) {
        Gson gson = new Gson();
        ArrayList<Posizione> posizioni= new ArrayList<>(Arrays.asList(gson.fromJson(json,Posizione[].class)));
        return posizioni;
    }

    public static String toJson(Posizione p) {
        Gson gson = new Gson();
        return gson.toJson(p);
    }

    public static Posizione fromJsonToPosizione(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        Posizione posizione= gson.fromJson(json, Posizione.class);
        return posizione;
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
