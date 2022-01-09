package presenter;

import android.content.Context;
import android.content.Context;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import presenter.libropresenter.LibroPresenter;
import presenter.posizionepresenter.PosizionePresenter;
import presenter.postazionepresenter.PostazionePresenter;
import presenter.prenotazionepresenter.PrenotazionePresenter;
import presenter.prestitopresenter.PrestitoPresenter;
import presenter.utentepresenter.UtentePresenter;

public class FacadePresenter {
    private UtentePresenter utentePresenter = new UtentePresenter();
    private LibroPresenter libroPresenter= new LibroPresenter();
    private PosizionePresenter posizionePresenter= new PosizionePresenter();
    private PostazionePresenter postazionePresenter = new PostazionePresenter();
    private PrenotazionePresenter prenotazionePresenter = new PrenotazionePresenter();
    private PrestitoPresenter prestitoPresenter = new PrestitoPresenter();

    public void login(String email, String password){
        utentePresenter.login(email, password);
    }

    public void logout(Context c) {
        utentePresenter.logout(c);
    }

    public void mostraRicercaLibri(boolean is_admin, Context c) { libroPresenter.mostraRicercaLibri(is_admin,c); }

    public void ricercaLibri(String ricerca){
        libroPresenter.ricercaLibri(ricerca);
    }

    public void creaPrestito(Prestito p) {
        prestitoPresenter.creaPrestito(p);
    }

    public void mostraMieiPrestiti(Context c) {
        prestitoPresenter.mostraMieiPrestiti(c);
    }

    public void rimuoviLibroFromInteressi(Libro l, Utente u) {
        libroPresenter.rimuoviLibroFromInteressi(l,u);
}

    public void aggiungiLibroToInteressi(Libro l, Utente u) {
        libroPresenter.aggiungiLibroFromInteressi(l,u);
    }

    public void attivaPrestito(Prestito p){}

    public void cercaPrestitiperLibro(String isbn){}

    public void bloccaPostazione(int pID){}

    public void bloccaPostazione(int pID, Periodo p){}

    public void sbloccaPostazione(int pID, Periodo p){}

    public void cercaPostazione(int pID){}

    public void cercaPostazioni(Posizione po, GregorianCalendar d){}

    public void cercaPostazioni(Posizione po){}

    public void mostraRicercaPostazioni(boolean is_admin, Context c) { postazionePresenter.mostraRicercaPostazioni(is_admin, c); }

    public void ricercaLibriCategoria(String categoria) {
        if(categoria.equalsIgnoreCase("Consigliati")){
            System.out.println(categoria);
        }
        else
            libroPresenter.ricercaLibriCategoria(categoria);
    }

    public void mostraDettagliLibro(Context c, Libro libro) {
        libroPresenter.mostraDettagliLibro(c, libro);
    }

    public void mostraElencoPostazioni(Posizione p, GregorianCalendar date) {
        postazionePresenter.mostraElencoPostazioni(p, date);
    }

    public ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date){
        return postazionePresenter.mostraOrariDisponibili(postazioni, idPos, prenotazioni,date);
    }


    public void creaPrenotazione(Prenotazione prenotazione) {
        prenotazionePresenter.creaPrenotazione(prenotazione);
    }

    public void valutaPrestito(Prestito p, int voto, String commento) {
        prestitoPresenter.valutaPrestito(p, voto, commento);

    }

    public void informazioniAggiuntaLibro() {
        libroPresenter.informazioniAggiuntaLibro();
    }

    public void creaLibro(Libro l) {
        libroPresenter.creaLibro(l);
    }

    public void mostraElencoPostazioni(Posizione p) {
        postazionePresenter.mostraElencoPostazioni(p);
    }

    public void bloccoIndeterminato(Postazione p) {
        postazionePresenter.bloccoIndeterminato(p);
    }

    public void bloccoDeterminato(Postazione p, GregorianCalendar date, int oraInizio, int oraFine) {
        postazionePresenter.bloccoDeterminato(p, date, oraInizio, oraFine);
    }
}
