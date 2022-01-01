package presenter.prestitopresenter;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.prestitomanagement.Prestito;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;

public class PrestitoPresenter {
    static final String GenericURL="http://192.168.1.7:8080/UnisaLIBServer/PrestitoPresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void creaPrestito(Prestito p) {
        String MYURL=GenericURL + "/crea-prestito";
        RequestParams params;
        params=new RequestParams();
        params.put("Prestito", p);
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), "Prestito avvenuto con successo. Ritirare il libro in biblioteca", Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
