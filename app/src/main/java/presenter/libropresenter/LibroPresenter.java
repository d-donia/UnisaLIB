package presenter.libropresenter;

import android.content.Context;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import model.utentemanagement.Utente;
import view.interfacciaadmin.AggiungiLibroActivity;
import view.interfacciaadmin.DettagliLibroAdminActivity;
import view.interfacciaadmin.GestioneLibroAdminActivity;
import view.interfacciageneral.ElencoLibriActivity;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;
import view.interfacciautenteunisa.ElencoPostazioniUtenteActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.MieiPrestitiActivity;

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