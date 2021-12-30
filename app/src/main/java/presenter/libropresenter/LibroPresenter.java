package presenter.libropresenter;

import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import model.libromanagement.Libro;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;

public class LibroPresenter {
    static final String GenericURL="http://192.168.255.1:8080/UnisaLIBServer/LibroPresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void mostraRicercaLibri(boolean is_admin){
        String MYURL=GenericURL+"/mostra-ricerca-libri";
        RequestParams params;
        params= new RequestParams();
        params.put("is_admin", is_admin);
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                String[] categorie = Libro.fromJsonToCategories(response);

                Intent i = new Intent();
                i.setClass(HomeUtenteUnisaActivity.getAppContext(), RicercaActivity.class);
                i.putExtra("Categorie", categorie);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeUtenteUnisaActivity.getAppContext().startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(HomeUtenteUnisaActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ricercaLibri(String ricerca) {
        String MYURL=GenericURL+"/ricerca-libri";
        RequestParams params;
        params= new RequestParams();
        params.put("ricerca", ricerca);
        client.post(MYURL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Libro[] libri=Libro.fromJson(response);
                for(int i=0; i<libri.length; i++){
                    System.out.println(libri[i].getTitolo());
                }
                //aggiungere codice per passare all'activity dove visualizzare la lista di libri
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomeUtenteUnisaActivity.getAppContext(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomeUtenteUnisaActivity.getAppContext(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(HomeUtenteUnisaActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ricercaLibriCategoria(String categoria) {

    }
}
