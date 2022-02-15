package model.libromanagement;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import model.posizionemanagement.Posizione;
import model.prestitomanagement.Prestito;
/**
 * Questa classe definisce un Libro. Un Libro ha un identificativo, un titolo, un autore, un editore, l'url della copertina,
 * la categoria, il numero di copie, l'anno di pubblicazione, il rating assegnato dagli utenti, una posizione a cui è situato,
 * e una lista contenente i prestiti in cui il libro è coinvolto.
 */
public class Libro implements Serializable{
    private String isbn, titolo, autore, editore, urlCopertina, categoria;
    private int nCopie, annoPubbl;
    private float rating;
    private Posizione posizione;
    private ArrayList<Prestito> prestiti;

    public String getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getEditore() {
        return editore;
    }

    public String getUrlCopertina() {
        return urlCopertina;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getnCopie() {
        return nCopie;
    }

    public int getAnnoPubbl() {
        return annoPubbl;
    }

    public float getRating() {
        return rating;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public ArrayList<Prestito> getPrestiti() {
        return prestiti;
    }

    public static String[] fromJsonToCategories(JSONArray response){
        Gson gson = new Gson();
        String[] categorie = gson.fromJson("" + response, String[].class);
        return categorie;
    }

    private static String fromJsonCategoria(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        String c = gson.fromJson(""+json.get("categoria"),String.class);
        return c;
    }

    public static String[] fromJsonCategorie(JSONArray response) throws JSONException {
        ArrayList<String> c=new ArrayList<>();
        for(int i=0;i<response.length();++i)
            c.add(Libro.fromJsonCategoria(response.getJSONObject(i)));
        String[] array = new String[c.size()];
        array = c.toArray(array);
        return array;
    }

    public static Libro[] fromJson(JSONArray response) {
        Gson gson = new Gson();
        Libro[] libri = gson.fromJson("" + response, Libro[].class);
        return libri;
    }

    public static Libro fromJsonToLibro(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        Libro libro= gson.fromJson(json, Libro.class);
        return libro;
    }

    public static String[] fromJsonToCategorie(String json){
        Gson gson = new Gson();
        String[] categorie = gson.fromJson(json, String[].class);
        return categorie;
    }

    public static String toJsonCategorie(List<String> categorie){
        Gson gson = new Gson();
        return gson.toJson(categorie);
    }

    public static ArrayList<Libro> fromJson(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        ArrayList<Libro> libri= new ArrayList<>(Arrays.asList(gson.fromJson(json,Libro[].class)));
        return libri;
    }

    public static String toJson(Libro l) {
        Gson gson = new Gson();
        return gson.toJson(l);
    }

    public static String toJson(ArrayList<Libro> libri) {
        Gson gson = new Gson();
        return gson.toJson(libri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return  getIsbn().equals(libro.getIsbn());
    }
    /**
     * Questa classe definisce un LibroBuilder. Un LibroBuilder ha un identificativo, un titolo, un autore, un editore, l'url della copertina,
     * la categoria, il numero di copie, l'anno di pubblicazione, il rating assegnato dagli utenti, una posizione a cui è situato,
     * e una lista contenente i prestiti in cui il libro è coinvolto.
     * Settato questi valori ritorna un oggetto di tipo Libro
     */
    public static class LibroBuilder{
        private String isbn, titolo, autore, editore, urlCopertina, categoria;
        private int nCopie, annoPubbl;
        private float rating;
        private Posizione posizione;
        private ArrayList<Prestito> prestiti;

        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param isbn l'identificativo dell'oggetto Libro
         * @return oggetto di tipo LibroBuilder con isbn settato
         */
        public LibroBuilder isbn(String isbn){
            this.isbn=isbn;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param titolo il titolo dell'oggetto Libro
         * @return oggetto di tipo LibroBuilder con titolo settato
         */
        public LibroBuilder titolo(String titolo){
            this.titolo=titolo;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param autore l'autore dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con autore settato
         */
        public LibroBuilder autore(String autore){
            this.autore=autore;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param editore l'editore dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con editore settato
         */
        public LibroBuilder editore(String editore){
            this.editore=editore;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param urlCopertina l'url che indirizza all'immagine di copertina del libro
         * @return oggetto di tipo LibroBuilder con l'url della copertina settato
         */
        public LibroBuilder urlCopertina(String urlCopertina){
            this.urlCopertina=urlCopertina;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param categoria la categoria dell'oggetto Libro
         * @return oggetto di tipo LibroBuilder con categoria settato
         */
        public LibroBuilder categoria(String categoria){
            this.categoria=categoria;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param nCopie il numero delle copie dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con il numero di copie settato
         */
        public LibroBuilder nCopie(int nCopie){
            this.nCopie=nCopie;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param annoPubbl l'anno di pubblicazione dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con l'anno di pubblicazione settato
         */
        public LibroBuilder annoPubbl(int annoPubbl){
            this.annoPubbl=annoPubbl;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param rating il rating dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con il rating settato
         */
        public LibroBuilder rating(float rating){
            this.rating=rating;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param posizione il rating dell'oggetto libro
         * @return oggetto di tipo LibroBuilder con la posizione settata
         */
        public LibroBuilder posizione(Posizione posizione){
            this.posizione=posizione;
            return this;
        }
        /**
         * Setta il parametro nell'oggetto LibroBuilder
         * @param prestiti la lista di prestiti riguardante l'oggetto libro
         * @return oggetto di tipo LibroBuilder con lista prestiti settata
         */
        public LibroBuilder prestiti(ArrayList<Prestito> prestiti){
            this.prestiti=prestiti;
            return this;
        }

        /**
         * Genera un oggetto di tipo Libro invocando il costruttore passando come argomento LibroBuilder
         * @return oggetto di tipo Libro
         */
        public Libro build(){
            return new Libro(this);
        }


    }
    /**
     * Crea un nuovo Libro settando gli opportuni parametri ottenuti dal LibroBuilder passato come argomento
     * @param lb LibroBuilder da cui si estraggono i valori precedentemente settati
     */
    private Libro(LibroBuilder lb){
        this.isbn = lb.isbn;
        this.titolo = lb.titolo;
        this.autore = lb.autore;
        this.editore = lb.editore;
        this.urlCopertina = lb.urlCopertina;
        this.categoria = lb.categoria;
        this.nCopie = lb.nCopie;
        this.annoPubbl = lb.annoPubbl;
        this.rating = lb.rating;
        this.posizione = lb.posizione;
        this.prestiti = lb.prestiti;
    }
}
