package presenter.libropresenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.NetworkOnMainThreadException;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPatch;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import model.libromanagement.Libro;
import model.utentemanagement.Utente;
import view.interfacciaadmin.DettagliLibroAdminActivity;
import view.interfacciageneral.ElencoLibriActivity;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;

public class LibroPresenter {
    static final String GenericURL = "http://192.168.1.61:8080/UnisaLIBServer/LibroPresenter";
    private AsyncHttpClient client = new AsyncHttpClient();

    public void mostraRicercaLibri(boolean is_admin) {
        String MYURL = GenericURL + "/mostra-ricerca-libri";
        RequestParams params;
        params = new RequestParams();
        params.put("is_admin", is_admin);
        client.post(MYURL, params, new JsonHttpResponseHandler() {
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
        String MYURL = GenericURL + "/ricerca-libri";
        RequestParams params;
        params = new RequestParams();
        params.put("ricerca", ricerca);
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                Libro[] libri = Libro.fromJson(response);

                Intent i = new Intent();
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
        String MYURL = GenericURL + "/ricerca-libri-categoria";
        RequestParams params;
        params = new RequestParams();
        params.put("ricerca", categoria);
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Libro[] libri = Libro.fromJson(response);

                Intent i = new Intent();
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

        Intent i = new Intent();

        if (u.isAdmin()) {
            i.setClass(ElencoLibriActivity.getAppContext(), DettagliLibroAdminActivity.class);
            i.putExtra("Libro", Libro.toJson(l));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ElencoLibriActivity.getAppContext().startActivity(i);
        } else {
            i.setClass(ElencoLibriActivity.getAppContext(), DettagliLibroUtenteUnisaActivity.class);
            i.putExtra("Libro", Libro.toJson(l));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ElencoLibriActivity.getAppContext().startActivity(i);
        }
    }

    public void rimuoviLibroFromInteressi(Libro l, Utente u) {
        String MYURL = GenericURL + "/rimuovi-interesse";
        RequestParams params = new RequestParams();
        params.put("isbn", l.getIsbn());
        params.put("email", u.getEmail());
        final Utente[] utente = {null};
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("" + response);
                try {
                    utente[0] = Utente.fromJson(response);
                    utente[0].getEmail();
                    DettagliLibroUtenteUnisaActivity.impostaNonInteresse(utente[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void aggiungiLibroFromInteressi(Libro l, Utente u) {
        String MYURL = GenericURL + "/aggiungi-interesse";
        RequestParams params = new RequestParams();
        params.put("isbn", l.getIsbn());
        params.put("email", u.getEmail());
        final Utente[] utente = {null};
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("" + response);
                try {
                    utente[0] = Utente.fromJson(response);
                    DettagliLibroUtenteUnisaActivity.impostaInteresse(utente[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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