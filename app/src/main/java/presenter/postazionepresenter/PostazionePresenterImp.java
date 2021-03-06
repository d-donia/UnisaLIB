package presenter.postazionepresenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.google.gson.JsonSyntaxException;
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
import view.postazioneview.BloccoActivity;
import view.postazioneview.ElencoPostazioniAdminActivity;
import view.postazioneview.SbloccoActivity;
import view.utenteview.HomeAdminActivity;
import view.postazioneview.RicercaPostazioneAdminActivity;
import view.postazioneview.ElencoPostazioniUtenteActivity;
import view.utenteview.HomeUtenteUnisaActivity;
import view.postazioneview.RicercaPostazioneUtenteActivity;

public class PostazionePresenterImp implements PostazionePresenter{
    static final String GenericURL = "http://192.168.1.5:8080/UnisaLIBServer/PostazionePresenter";
    private AsyncHttpClient client = new AsyncHttpClient();

    public void mostraRicercaPostazioni(boolean is_admin, Context c) {
        String MYURL = GenericURL + "/mostra-ricerca-postazioni";
        RequestParams params;
        params = new RequestParams();
        params.put("is_admin", is_admin);

        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Posizione[] posizioni = Posizione.fromJson(response);
                Intent i = new Intent();
                if(!is_admin){
                    i.setClass(c, RicercaPostazioneUtenteActivity.class);
                    i.putExtra("Posizioni", Posizione.toJson(new ArrayList<>(Arrays.asList(posizioni))));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(i);
                }
                else {
                    i.setClass(c, RicercaPostazioneAdminActivity.class);
                    i.putExtra("Posizioni", Posizione.toJson(new ArrayList<>(Arrays.asList(posizioni))));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(i);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(c, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostraElencoPostazioni(Posizione p, GregorianCalendar date) {
        String MYURL = GenericURL + "/mostra-elenco-postazioni";
        RequestParams params;
        params = new RequestParams();
        params.put("anno", date.get(Calendar.YEAR));
        params.put("mese", date.get(Calendar.MONTH));
        params.put("giorno", date.get(Calendar.DATE));
        params.put("posizione", Posizione.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Postazione[] postazioni = Postazione.fromJsonArray(response.getJSONArray("postazioni"));
                    Prenotazione[] prenotazioni = Prenotazione.fromJson(response.getJSONArray("prenotazioni"));

                    Intent i = new Intent();
                    i.setClass(RicercaPostazioneUtenteActivity.getAppContext(), ElencoPostazioniUtenteActivity.class);
                    i.putExtra("postazioni", Postazione.toJson(new ArrayList<>(Arrays.asList(postazioni))));
                    i.putExtra("prenotazioni", Prenotazione.toJson(new ArrayList<>(Arrays.asList(prenotazioni))));
                    i.putExtra("posizione", Posizione.toJson(p));
                    i.putExtra("anno", date.get(Calendar.YEAR));
                    i.putExtra("mese", date.get(Calendar.MONTH));
                    i.putExtra("giorno", date.get(Calendar.DATE));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    RicercaPostazioneUtenteActivity.getAppContext().startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(HomeUtenteUnisaActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostraElencoPostazioni(Context c, Posizione p) {
        String MYURL = GenericURL + "/mostra-elenco-postazioni-admin";
        RequestParams params;
        params = new RequestParams();
        params.put("posizione", Posizione.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<Postazione> postazioni = Postazione.fromJsonArray(""+response);
                Intent i = new Intent();
                i.setClass(c, ElencoPostazioniAdminActivity.class);
                i.putExtra("postazioni", Postazione.toJson(postazioni));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                c.startActivity(i);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(RicercaPostazioneAdminActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date) {
        Postazione pos = new Postazione();

        for (Postazione p : postazioni)
            if (p.getId().equals(idPos))
                pos = p;

        ArrayList<Periodo> orariDisponibili = new ArrayList<>();
        for (int i = 9; i < 18; i += 2) {
            if(i==13)
                ++i;
            orariDisponibili.add(new Periodo(i, i + 2));
        }

        ArrayList<Periodo> blocchi = new ArrayList<>();
        for (Periodo periodo : pos.getBlocchi())
            if (SwitchDate.equalsDate(periodo.getData(), date))
                blocchi.add(new Periodo(periodo.getOraInizio(), periodo.getOraFine()));
        for (Periodo p : blocchi)
            if (orariDisponibili.contains(p))
                orariDisponibili.remove(p);

        ArrayList<Periodo> orariNonDisponibili = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni)
            if (prenotazione.getPostazione().getId().equals(pos.getId()) && SwitchDate.equalsDate(prenotazione.getData(), date))
                orariNonDisponibili.add(new Periodo(prenotazione.getOraInizio(), prenotazione.getOraFine()));
        GregorianCalendar currentTime = new GregorianCalendar();
        for (Periodo p : orariDisponibili)
            if (SwitchDate.equalsDate(date, currentTime) && currentTime.get(Calendar.HOUR_OF_DAY) >= p.getOraInizio())
                orariNonDisponibili.add(p);
        for (Periodo p : orariNonDisponibili)
            if (orariDisponibili.contains(p))
                orariDisponibili.remove(p);

        ArrayList<String> fasceOrarie = new ArrayList<>();
        for (Periodo p : orariDisponibili)
            fasceOrarie.add(p.getOraInizio() + " - " + p.getOraFine());

        return fasceOrarie;
    }

    public void bloccoDeterminato(Postazione p, GregorianCalendar date, int oraInizio, int oraFine) {
        if(oraFine>oraInizio) {
            String MYURL = GenericURL + "/blocco-determinato";
            RequestParams params = new RequestParams();
            params.put("idPos", p.getId());
            params.put("periodo", Periodo.toJson(new Periodo(0, oraInizio, oraFine, date)));
            client.post(MYURL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        Toast.makeText(BloccoActivity.getAppContext(), response.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(BloccoActivity.getAppContext(), "Errore visualizzazione messaggio", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(BloccoActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    super.onSuccess(statusCode, headers, responseString);
                }
            });
        }
        else{
            Toast.makeText(BloccoActivity.getAppContext(), "Ora fine deve essere maggiore di ora inizio", Toast.LENGTH_SHORT).show();
        }

    }

    public void bloccoIndeterminato(String idPos) {
        String MYURL = GenericURL + "/blocco-indeterminato";
        RequestParams params;
        params = new RequestParams();
        params.put("idPos", idPos);

        client.post(MYURL,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Toast.makeText(BloccoActivity.getAppContext(), response.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(BloccoActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    Toast.makeText(BloccoActivity.getAppContext(), errorResponse.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void cercaBlocchi(String id) {
        String MYURL = GenericURL + "/cerca-blocchi";
        RequestParams params;
        params = new RequestParams();
        params.put("idPos", id);

        client.post(MYURL,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                SharedPreferences postazione = PreferenceManager.getDefaultSharedPreferences(ElencoPostazioniAdminActivity.getAppContext());
                SharedPreferences.Editor editor = postazione.edit();
                try {
                    Postazione pos=Postazione.fromJsonArray(response);
                    if((!pos.getBlocchi().isEmpty()) || pos.isDisponibile()==false) {
                        editor.putString("postazione", Postazione.toJson(pos)).commit();
                        Intent i = new Intent();
                        i.setClass(ElencoPostazioniAdminActivity.getAppContext(), SbloccoActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ElencoPostazioniAdminActivity.getAppContext().startActivity(i);
                    }
                    else
                        Toast.makeText(ElencoPostazioniAdminActivity.getAppContext(), "Non sono presenti blocchi", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ElencoPostazioniAdminActivity.getAppContext(), "Errore", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(BloccoActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    Toast.makeText(BloccoActivity.getAppContext(), errorResponse.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sbloccaPostazione(String idPos) {
        String MYURL = GenericURL + "/sblocca-postazione";
        RequestParams params;
        params = new RequestParams();
        params.put("idPos", idPos);

        client.post(MYURL,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(SbloccoActivity.getAppContext());
                    Postazione p=null;
                    try {
                        p= Postazione.fromJson(userSession.getString("postazione", ""));
                    }
                    catch (JsonSyntaxException e){
                        e.printStackTrace();
                    }
                    mostraElencoPostazioni(SbloccoActivity.getAppContext(),p.getPosizione());
                    Toast.makeText(ElencoPostazioniAdminActivity.getAppContext(), response.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SbloccoActivity.getAppContext(), "Errore", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(SbloccoActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    Toast.makeText(SbloccoActivity.getAppContext(), errorResponse.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sbloccaPostazione(String idPos, Periodo periodo) {
        if (periodo.getOraFine() > periodo.getOraInizio()) {
            String MYURL = GenericURL + "/sblocca-postazione-periodo";
            RequestParams params;
            params = new RequestParams();
            params.put("idPos", idPos);
            params.put("periodo", Periodo.toJson(periodo));

            client.post(MYURL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(SbloccoActivity.getAppContext());
                        Postazione p = null;
                        try {
                            p = Postazione.fromJson(userSession.getString("postazione", ""));
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        mostraElencoPostazioni(SbloccoActivity.getAppContext(), p.getPosizione());
                        Toast.makeText(ElencoPostazioniAdminActivity.getAppContext(), response.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SbloccoActivity.getAppContext(), "Errore", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(SbloccoActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        Toast.makeText(SbloccoActivity.getAppContext(), errorResponse.get("messaggio").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else{
            Toast.makeText(SbloccoActivity.getAppContext(), "Ora fine deve essere maggiore di ora inizio", Toast.LENGTH_SHORT).show();
        }
    }
}
