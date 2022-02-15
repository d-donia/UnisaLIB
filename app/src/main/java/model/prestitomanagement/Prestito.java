package model.prestitomanagement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import model.libromanagement.Libro;
import model.utentemanagement.Utente;
/**
 * Questa classe definisce un Prestito. Un prestito ha una data di inizio, una data di fine, una data di consegna,
 * un utente che lo richiede, un libro coinvolto nel prestito, un commento, un voto
 * e un valore che specifica se il prestito è attivo o meno
 */
public class Prestito {
    private GregorianCalendar dataInizio, dataFine, dataConsegna;
    private Utente utente;
    private Libro libro;
    private String commento;
    private int voto;
    private boolean attivo=false;

    public GregorianCalendar getDataInizio() { return dataInizio; }

    public GregorianCalendar getDataFine() { return dataFine; }

    public Utente getUtente() { return utente; }

    public Libro getLibro() { return libro; }

    public int getVoto() { return voto; }

    public GregorianCalendar getDataConsegna() { return dataConsegna;}

    public String getCommento() {return commento;}

    public boolean isAttivo() { return attivo; }
    /**
     * Questa classe definisce un PrestitoBuilder. Un PrestitoBuilder ha una data di inizio, una data di fine, una data di consegna,
     * un utente che lo richiede, un libro coinvolto nel prestito, un commento, un voto
     * e un valore che specifica se il prestito è attivo o meno.
     * Settati questi valori ritorna un oggetto Prestito
     */
    public static class PrestitoBuilder{
        private GregorianCalendar dataInizio, dataFine, dataConsegna;
        private Utente utente;
        private Libro libro;
        private String commento;
        private int voto;
        private boolean attivo=false;
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param dataInizio data d'inizio del Prestito
         * @return oggetto di tipo PrestitoBuilder con data di inizio settata
         */
        public Prestito.PrestitoBuilder dataInizio(GregorianCalendar dataInizio){
            this.dataInizio=dataInizio;
            dataFine = (GregorianCalendar) dataInizio.clone();
            dataFine.add(GregorianCalendar.DATE, +31);
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param dataFine data di fine del Prestito
         * @return oggetto di tipo PrestitoBuilder con data di fine settata
         */
        public Prestito.PrestitoBuilder dataFine(GregorianCalendar dataFine){
            this.dataFine=dataFine;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param dataConsegna data di consegna del Prestito
         * @return oggetto di tipo PrestitoBuilder con data di consegna settata
         */
        public Prestito.PrestitoBuilder dataConsegna(GregorianCalendar dataConsegna){
            this.dataConsegna=dataConsegna;
            this.attivo=false;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param utente utente che effettua il Prestito
         * @return oggetto di tipo PrestitoBuilder con utente settato
         */
        public Prestito.PrestitoBuilder utente(Utente utente){
            this.utente=utente;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param libro il libro coinvolto nel prestito
         * @return oggetto di tipo PrestitoBuilder con libro settato
         */
        public Prestito.PrestitoBuilder libro(Libro libro){
            this.libro=libro;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param commento stringa contenente il commento sul prestito
         * @return oggetto di tipo PrestitoBuilder con commento settato
         */
        public Prestito.PrestitoBuilder commento(String commento){
            this.commento=commento;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param voto il voto assegnato al prestito
         * @return oggetto di tipo PrestitoBuilder con voto settato
         */
        public Prestito.PrestitoBuilder voto(int voto){
            if (voto>0 && voto<=5)
                this.voto=voto;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto PrestitoBuilder
         * @param attivo variabile booleana che specifica se il prestito è attivo o meno
         * @return oggetto di tipo PrestitoBuilder con attivo settato
         */
        public Prestito.PrestitoBuilder attivo(boolean attivo){
            if(this.dataConsegna == null || attivo == false)
                this.attivo=attivo;
            return this;
        }
        /**
         * Genera un oggetto di tipo Prestito invocando il costruttore passando come argomento LibroBuilder
         * @return oggetto di tipo Prestito
         */
        public Prestito build(){
            return new Prestito(this);
        }
    }
    /**
     * Crea un nuovo Prestito settando gli opportuni parametri ottenuti dal PrestitoBuilder passato come argomento
     * @param pb PrestitoBuilder da cui si estraggono i valori precedentemente settati
     */
    private Prestito(Prestito.PrestitoBuilder pb){
        this.dataInizio = pb.dataInizio;
        this.dataFine = pb.dataFine;
        this.dataConsegna = pb.dataConsegna;
        this.utente = pb.utente;
        this.libro = pb.libro;
        this.voto = pb.voto;
        this.commento = pb.commento;
        this.attivo = pb.attivo;
    }

    public static String toJson(Prestito p){
        Gson gson = new Gson();
        Type fooType = new TypeToken<Prestito>() {}.getType();
        String json = gson.toJson(p,fooType);
        return json;
    }

    public static String toJson(List<Prestito> prestiti){
        Gson gson = new Gson();
        return gson.toJson(prestiti);
    }

    public static Prestito fromJsonToPrestito(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        Prestito prestito= gson.fromJson(json, Prestito.class);
        return prestito;
    }

    public static Prestito fromJson(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        Prestito p = gson.fromJson(""+json.get("Prestito"),Prestito.class);
        return p;
    }

    public static ArrayList<Prestito> fromJson(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        ArrayList<Prestito> p= new ArrayList<>(Arrays.asList(gson.fromJson(json,Prestito[].class)));
        return p;
    }
}