package presenter.prestitopresenter;

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

import cz.msebera.android.httpclient.Header;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import view.interfacciageneral.MainActivity;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.MieiPrestitiActivity;

public interface PrestitoPresenter {
    void creaPrestito(Prestito p);

    void mostraMieiPrestiti(Context c);

    void valutaPrestito(Prestito p, int voto, String commento);
}
