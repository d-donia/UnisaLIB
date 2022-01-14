package presenter.postazionepresenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.Header;
import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import utils.SwitchDate;
import view.interfacciaadmin.ElencoPostazioniAdminActivity;
import view.interfacciaadmin.HomeAdminActivity;
import view.interfacciaadmin.RicercaPostazioneAdminActivity;
import view.interfacciautenteunisa.ElencoPostazioniUtenteActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.RicercaPostazioneUtenteActivity;

public interface PostazionePresenter {
    void mostraRicercaPostazioni(boolean is_admin, Context c);

    void mostraElencoPostazioni(Posizione p, GregorianCalendar date);

    void mostraElencoPostazioni(Posizione p);

    ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date);

    void bloccoDeterminato(Postazione p, GregorianCalendar date, int oraInizio, int oraFine);

    void bloccoIndeterminato(String idPos);

}
