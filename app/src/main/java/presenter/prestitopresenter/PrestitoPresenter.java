package presenter.prestitopresenter;

import android.content.Context;

import model.prestitomanagement.Prestito;

public interface PrestitoPresenter {
    void creaPrestito(Prestito p);

    void mostraMieiPrestiti(Context c);

    void valutaPrestito(Prestito p, int voto, String commento);
}
