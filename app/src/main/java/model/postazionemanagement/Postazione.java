package model.postazionemanagement;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import model.posizionemanagement.Posizione;
import model.utentemanagement.Utente;

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

    public Postazione() {

    }


    public static String toJson(ArrayList<Postazione> postazioni) {
        Gson gson = new Gson();
        return gson.toJson(postazioni);
    }

    public static String toJson(Postazione p) {
        Gson gson = new Gson();
        return gson.toJson(p);
    }

    public static Postazione[] fromJsonArray(JSONArray response) throws JSONException {
        ArrayList<Postazione> p=new ArrayList<>();
        for(int i=0;i<response.length();++i)
            p.add(Postazione.fromJsonArray(response.getJSONObject(i)));
        Postazione[] array = new Postazione[p.size()];
        array = p.toArray(array);
        return array;
    }

    public static Postazione fromJsonArray(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        Postazione p = gson.fromJson(""+json.get("postazione"),Postazione.class);
        return p;
    }

    public static Postazione fromJson(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        Postazione p = gson.fromJson(json,Postazione.class);
        return p;
    }

    public static ArrayList<Postazione> fromJsonArray(String json) {
        Gson gson = new Gson();
        ArrayList<Postazione> postazioni= new ArrayList<>(Arrays.asList(gson.fromJson(json,Postazione[].class)));
        return postazioni;
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
