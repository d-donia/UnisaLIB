package presenter.libropresenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import model.libromanagement.Libro;
import model.utentemanagement.Utente;
import view.interfacciaadmin.DettagliLibroAdminActivity;
import view.interfacciageneral.ElencoLibriActivity;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;
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

                Intent i=new Intent();
                i.setClass(RicercaActivity.getAppContext(), ElencoLibriActivity.class);
                i.putExtra("Libri", Libro.toJson(new ArrayList<>(Arrays.asList(libri))));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RicercaActivity.getAppContext().startActivity(i);
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
        String MYURL=GenericURL+"/ricerca-libri-categoria";
        RequestParams params;
        params= new RequestParams();
        params.put("ricerca", categoria);
        client.post(MYURL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Libro[] libri=Libro.fromJson(response);

                Intent i=new Intent();
                i.setClass(RicercaActivity.getAppContext(), ElencoLibriActivity.class);
                i.putExtra("Libri", Libro.toJson(new ArrayList<>(Arrays.asList(libri))));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RicercaActivity.getAppContext().startActivity(i);
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

    public void mostraDettagliLibro(Libro l) {
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(ElencoLibriActivity.getAppContext());
        Utente u = Utente.fromJson(userSession.getString("Utente", ""));

        Intent i=new Intent();

        if(u.isAdmin()){
            i.setClass(ElencoLibriActivity.getAppContext(), DettagliLibroAdminActivity.class);
            i.putExtra("Libro", Libro.toJson(l));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ElencoLibriActivity.getAppContext().startActivity(i);
        }
        else {
            i.setClass(ElencoLibriActivity.getAppContext(), DettagliLibroUtenteUnisaActivity.class);
            i.putExtra("Libro", Libro.toJson(l));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ElencoLibriActivity.getAppContext().startActivity(i);
        }
    }

    public Utente rimuoviLibroFromInteressi(Libro l, Utente u) {
        return u;
    }

    public Utente aggiungiLibroFromInteressi(Libro l, Utente u) {
        return u;
    }
}
