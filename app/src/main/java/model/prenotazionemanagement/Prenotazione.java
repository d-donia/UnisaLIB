package model.prenotazionemanagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import model.postazionemanagement.Postazione;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
/**
 * Questa classe definisce una Prenotazione. Una prenotazione ha una data, un'ora d'inizio, un'ora di fine, un utente che la prenota
 * e una postazione coinvolta nella prenotazione.
 */
public class Prenotazione {
    private GregorianCalendar data;
    private int oraInizio, oraFine;
    private Utente utente;
    private Postazione postazione;
    /**
     * Crea una nuova Prenotazione settando gli opportuni parametri
     * @param data data della prenotazione
     * @param oraInizio orario di inizio della prenotazione
     * @param oraFine orario di termine della prenotazione
     * @param utente Utente che effettua la prenotazione
     * @param postazione postazione coinvolta nella prenotazione
     */
    public Prenotazione(GregorianCalendar data, int oraInizio, int oraFine, Utente utente, Postazione postazione) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.utente = utente;
        this.postazione = postazione;
    }

    public static Prenotazione[] fromJson(JSONArray response) throws JSONException {
        ArrayList<Prenotazione> p=new ArrayList<>();
        for(int i=0;i<response.length();++i)
            p.add(Prenotazione.fromJson(response.getJSONObject(i)));
        Prenotazione[] array = new Prenotazione[p.size()];
        array = p.toArray(array);
        return array;
    }

    public static Prenotazione fromJson(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        Prenotazione p = gson.fromJson(""+json.get("prenotazione"),Prenotazione.class);
        return p;
    }

    public static String toJson(ArrayList<Prenotazione> prenotazioni) {
        Gson gson = new Gson();
        return gson.toJson(prenotazioni);
    }

    public static ArrayList<Prenotazione> fromJson(String json) {
        Gson gson = new Gson();
        ArrayList<Prenotazione> prenotazioni= new ArrayList<>(Arrays.asList(gson.fromJson(json,Prenotazione[].class)));
        return prenotazioni;
    }

    public static String toJson(Prenotazione p){
        Gson gson = new Gson();
        Type fooType = new TypeToken<Prenotazione>() {}.getType();
        String json = gson.toJson(p,fooType);
        return json;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }

    public int getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(int oraInizio) {
        this.oraInizio = oraInizio;
    }

    public int getOraFine() {
        return oraFine;
    }

    public void setOraFine(int oraFine) {
        this.oraFine = oraFine;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Postazione getPostazione() {
        return postazione;
    }

    public void setPostazione(Postazione postazione) {
        this.postazione = postazione;
    }
}


