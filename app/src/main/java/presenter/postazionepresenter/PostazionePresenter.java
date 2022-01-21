package presenter.postazionepresenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;

public interface PostazionePresenter {
    void mostraRicercaPostazioni(boolean is_admin, Context c);

    void mostraElencoPostazioni(Posizione p, GregorianCalendar date);

    void mostraElencoPostazioni(Context c, Posizione p);

    ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date);

    void bloccoDeterminato(Postazione p, GregorianCalendar date, int oraInizio, int oraFine);

    void bloccoIndeterminato(String idPos);

    void cercaBlocchi(String id);

    void sbloccaPostazione(String idPos);

    void sbloccaPostazione(String idPos, Periodo periodo);
}
