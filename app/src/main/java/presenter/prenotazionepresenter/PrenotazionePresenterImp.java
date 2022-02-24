package presenter.prenotazionepresenter;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import model.utentemanagement.Utente;
import view.postazioneview.ElencoPostazioniUtenteActivity;

public class PrenotazionePresenterImp implements PrenotazionePresenter{
    static final String GenericURL="http://192.168.1.5:8080/UnisaLIBServer/PrenotazionePresenter";
    private AsyncHttpClient client=new AsyncHttpClient();

    public void creaPrenotazione(Prenotazione prenotazione) {
        String MYURL=GenericURL + "/crea-prenotazione";
        RequestParams params;
        params=new RequestParams();
        params.put("prenotazione", Prenotazione.toJson(prenotazione));
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(ElencoPostazioniUtenteActivity.getAppContext());
                SharedPreferences.Editor editor = userSession.edit();
                Utente utenteAggiornato = null;
                try {
                    utenteAggiornato = Utente.fromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.putString("Utente", Utente.toJson(utenteAggiornato)).commit();
                Toast.makeText(ElencoPostazioniUtenteActivity.getAppContext(), "Prenotazione avvenuta con successo ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(ElencoPostazioniUtenteActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(ElencoPostazioniUtenteActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("Prestito fallito");
                Toast.makeText(ElencoPostazioniUtenteActivity.getAppContext(), responseString + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }
        });
    }
}
