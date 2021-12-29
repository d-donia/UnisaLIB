package presenter;

import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
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

    private void login(String email, String password){
        utentePresenter.login(email, password);
    }

    private void attivaPrestito(Prestito p){}

    private void cercaPrestitiperLibro(String isbn){}

    private void bloccaPostazione(int pID){}

    private void bloccaPostazione(int pID, Periodo p){}

    private void sbloccaPostazione(int pID, Periodo p){}

    private void cercaPostazione(int pID){}

    private void cercaPostazioni(Posizione po, GregorianCalendar d){}

    private void cercaPostazioni(Posizione po){}





}
