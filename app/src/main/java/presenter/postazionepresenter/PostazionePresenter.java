package presenter.postazionepresenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;

public interface PostazionePresenter {
    void mostraRicercaPostazioni(boolean is_admin, Context c);

    void mostraElencoPostazioni(Posizione p, GregorianCalendar date);

    void mostraElencoPostazioni(Posizione p);

    ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date);

    void bloccoDeterminato(Postazione p, GregorianCalendar date, int oraInizio, int oraFine);

    void bloccoIndeterminato(String idPos);

}
