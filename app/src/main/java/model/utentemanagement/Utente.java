package model.utentemanagement;

import java.util.ArrayList;

import model.librimanagement.Libro;
import model.prenotazionimanagement.Prenotazione;
import model.prestitimanagement.Prestito;

public class Utente {
    private String email, password, nome, cognome, matricola;
    private boolean admin;
    private ArrayList<Libro> interessi;
    private ArrayList<Prestito> prestiti;
    private ArrayList<Prenotazione> prenotazioni;

    public Utente(String email, String password, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.admin = true;
    }

    public Utente(String email, String password, String nome, String cognome, String matricola) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.admin = false;
        interessi = new ArrayList<>();
        prestiti = new ArrayList<>();
        prenotazioni = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Libro> getInteressi() {
        return interessi;
    }

    public void setInteressi(ArrayList<Libro> interessi) {
        this.interessi = interessi;
    }

    public ArrayList<Prestito> getPrestiti() {
        return prestiti;
    }

    public void setPrestiti(ArrayList<Prestito> prestiti) {
        this.prestiti = prestiti;
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}