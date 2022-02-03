package presenter.libropresenter;

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
import view.libroview.AggiungiLibroActivity;
import view.libroview.DettagliLibroAdminActivity;
import view.libroview.GestioneLibroAdminActivity;
import view.libroview.ElencoLibriActivity;
import view.libroview.RicercaActivity;
import view.libroview.DettagliLibroUtenteUnisaActivity;
import view.utenteview.HomeUtenteUnisaActivity;

public class LibroPresenterImp implements LibroPresenter {
    static final String GenericURL = "http://192.168.255.1:8080/UnisaLIBServer/LibroPresenter";
    private AsyncHttpClient client = new AsyncHttpClient();

    public void mostraRicercaLibri(boolean is_admin, Context c) {
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
                i.setClass(c, RicercaActivity.class);
                i.putExtra("Categorie", categorie);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Intent i = new Intent();
                i.setClass(c, RicercaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(i);
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
                System.out.println(""+response);
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
        params.put("categoria", categoria);
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

    public void mostraDettagliLibro(Context c, Libro l) {
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(c);
        Utente u = Utente.fromJson(userSession.getString("Utente", ""));

        Intent i = new Intent();

        if (u.isAdmin()) {
            String MYURL = GenericURL + "/dettagli-libro-admin";
            RequestParams params = new RequestParams();
            client.post(MYURL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    String[] categorie=null;
                    Posizione[] posizioni=null;
                    try {
                        categorie=Libro.fromJsonCategorie(response.getJSONArray("categorie"));
                        posizioni=Posizione.fromJsonEtic(response.getJSONArray("posizioni"));
                        Intent i=new Intent();
                        i.setClass(c, DettagliLibroAdminActivity.class);
                        i.putExtra("categorie", Libro.toJsonCategorie(new ArrayList<>(Arrays.asList(categorie))));
                        i.putExtra("posizioni", Posizione.toJson(new ArrayList<>(Arrays.asList(posizioni))));
                        i.putExtra("libro", Libro.toJson(l));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        c.startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

        } else {
            i.setClass(c, DettagliLibroUtenteUnisaActivity.class);
            i.putExtra("libro", Libro.toJson(l));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(i);
        }
    }

    public void rimuoviLibroFromInteressi(Libro l, Utente u) {
        String MYURL = GenericURL + "/rimuovi-interesse";
        RequestParams params = new RequestParams();
        params.put("isbn", l.getIsbn());
        params.put("email", u.getEmail());
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("" + response);
                try {
                    Utente utente= Utente.fromJson(response);
                    if(utente!=null) {
                        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(DettagliLibroUtenteUnisaActivity.getAppContext());
                        SharedPreferences.Editor editor = userSession.edit();
                        editor.putString("Utente", Utente.toJson(utente)).apply();
                        DettagliLibroUtenteUnisaActivity.impostaNonInteresse(utente);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void aggiungiLibroFromInteressi(Libro l, Utente u) {
        String MYURL = GenericURL + "/aggiungi-interesse";
        RequestParams params = new RequestParams();
        params.put("isbn", l.getIsbn());
        params.put("email", u.getEmail());
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("" + response);
                try {
                    Utente utente = Utente.fromJson(response);
                    if(utente!=null){
                        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(DettagliLibroUtenteUnisaActivity.getAppContext());
                        SharedPreferences.Editor editor = userSession.edit();
                        editor.putString("Utente", Utente.toJson(utente)).apply();
                        DettagliLibroUtenteUnisaActivity.impostaInteresse(utente);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void informazioniAggiuntaLibro() {
        String MYURL = GenericURL + "/informazioni-aggiunta";
        RequestParams params = new RequestParams();
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String[] categorie=null;
                Posizione[] posizioni=null;
                try {
                    categorie=Libro.fromJsonCategorie(response.getJSONArray("categorie"));
                    posizioni=Posizione.fromJsonEtic(response.getJSONArray("posizioni"));
                    Intent i=new Intent();
                    i.setClass(GestioneLibroAdminActivity.getAppContext(), AggiungiLibroActivity.class);
                    i.putExtra("categorie", Libro.toJsonCategorie(new ArrayList<>(Arrays.asList(categorie))));
                    i.putExtra("posizioni", Posizione.toJson(new ArrayList<>(Arrays.asList(posizioni))));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    GestioneLibroAdminActivity.getAppContext().startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void creaLibro(Libro libro) {
        String MYURL=GenericURL + "/crea-libro";
        RequestParams params;
        params=new RequestParams();
        params.put("libro", Libro.toJson(libro));
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Toast.makeText(AggiungiLibroActivity.getAppContext(), ""+response.get("messaggio"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(AggiungiLibroActivity.getAppContext(), "Errore riceazione messaggio, salvataggio avvenuto", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Crea Libro fallito");
                Toast.makeText(AggiungiLibroActivity.getAppContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Crea Libro fallito");
                Toast.makeText(AggiungiLibroActivity.getAppContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("Crea Libro fallito");
                Toast.makeText(AggiungiLibroActivity.getAppContext(), responseString, Toast.LENGTH_LONG).show();
            }
        });
    }
}
