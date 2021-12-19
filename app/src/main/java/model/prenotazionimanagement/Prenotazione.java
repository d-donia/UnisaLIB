package model.prenotazionimanagement;

import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionimanagement.Postazione;
import model.utentemanagement.Utente;

public class Prenotazione {
    private GregorianCalendar data;
    private int oraInizio, oraFine;
    private Utente utente;
    private Postazione postazione;

    public Prenotazione(GregorianCalendar data, int oraInizio, int oraFine, Utente utente, Postazione postazione) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.utente = utente;
        this.postazione = postazione;
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


