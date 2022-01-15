package presenter.prestitopresenter;

import android.content.Context;

import java.util.GregorianCalendar;

import model.prestitomanagement.Prestito;

public interface PrestitoPresenter {
    void creaPrestito(Prestito p);

    void mostraMieiPrestiti(Context c);

    void valutaPrestito(Prestito p, int voto, String commento);

    void mostraPrestitiLibro(String isbn);

    void attivaPrestito(Prestito p);

    void concludiPrestito(Prestito p, GregorianCalendar date);
}
