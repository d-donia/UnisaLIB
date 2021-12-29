package presenter;

import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.prestitomanagement.Prestito;
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

    public void mostraRicercaLibri(boolean is_admin) { libroPresenter.mostraRicercaLibri(is_admin); }

    public void ricercaLibri(String ricerca){
        libroPresenter.ricercaLibri(ricerca);
    }

    public void attivaPrestito(Prestito p){}

    public void cercaPrestitiperLibro(String isbn){}

    public void bloccaPostazione(int pID){}

    public void bloccaPostazione(int pID, Periodo p){}

    public void sbloccaPostazione(int pID, Periodo p){}

    public void cercaPostazione(int pID){}

    public void cercaPostazioni(Posizione po, GregorianCalendar d){}

    public void cercaPostazioni(Posizione po){}

    public void mostraRicercaPostazioni(boolean is_admin) { postazionePresenter.mostraRicercaPostazioni(is_admin); }

    public void ricercaLibriCategoria(String categoria) {
        libroPresenter.ricercaLibriCategoria(categoria);
    }
}
