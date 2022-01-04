package presenter.prestitopresenter;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

import cz.msebera.android.httpclient.Header;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;

public class PrestitoPresenter {
    static final String GenericURL="http://192.168.1.61:8080/UnisaLIBServer/PrestitoPresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void creaPrestito(Prestito p) {
        String MYURL=GenericURL + "/crea-prestito";
        RequestParams params;
        params=new RequestParams();
        params.put("prestito", Prestito.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Prestito prestito = Prestito.fromJson(response);
                    SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(DettagliLibroUtenteUnisaActivity.getAppContext());
                    SharedPreferences.Editor editor = userSession.edit();
                    Utente utenteAggiornato=Utente.fromJson(userSession.getString("Utente", ""));
                    utenteAggiornato.getPrestiti().add(prestito);
                    editor.putString("Utente", Utente.toJson(utenteAggiornato)).apply();
                    Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), "Prestito avvenuto con successo. Ritirare il libro in biblioteca", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), responseString + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }
        });
    }
}
