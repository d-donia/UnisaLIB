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

    public static class LibroBuilder{
        private String isbn, titolo, autore, editore, urlCopertina, categoria;
        private int nCopie, annoPubbl;
        private float rating;
        private Posizione posizione;
        private ArrayList<Prestito> prestiti;


        public LibroBuilder isbn(String isbn){
            this.isbn=isbn;
            return this;
        }

        public LibroBuilder titolo(String titolo){
            this.titolo=titolo;
            return this;
        }

        public LibroBuilder autore(String autore){
            this.autore=autore;
            return this;
        }

        public LibroBuilder editore(String editore){
            this.editore=editore;
            return this;
        }

        public LibroBuilder urlCopertina(String urlCopertina){
            this.urlCopertina=urlCopertina;
            return this;
        }

        public LibroBuilder categoria(String categoria){
            this.categoria=categoria;
            return this;
        }

        public LibroBuilder nCopie(int nCopie){
            this.nCopie=nCopie;
            return this;
        }

        public LibroBuilder annoPubbl(int annoPubbl){
            this.annoPubbl=annoPubbl;
            return this;
        }

        public LibroBuilder rating(float rating){
            this.rating=rating;
            return this;
        }

        public LibroBuilder posizione(Posizione posizione){
            this.posizione=posizione;
            return this;
        }

        public LibroBuilder prestiti(ArrayList<Prestito> prestiti){
            this.prestiti=prestiti;
            return this;
        }


        public Libro build(){
            return new Libro(this);
        }


    }

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
