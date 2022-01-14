package presenter.libropresenter;

import android.content.Context;

import model.libromanagement.Libro;
import model.utentemanagement.Utente;

public interface LibroPresenter {

    void mostraRicercaLibri(boolean is_admin, Context c);

    void ricercaLibri(String ricerca);

    void ricercaLibriCategoria(String categoria);

    void mostraDettagliLibro(Context c, Libro l);

    void rimuoviLibroFromInteressi(Libro l, Utente u);

    void aggiungiLibroFromInteressi(Libro l, Utente u);

    void informazioniAggiuntaLibro();

    void creaLibro(Libro libro);
}