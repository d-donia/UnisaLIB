package presenter.postazionepresenter;

import android.content.Intent;
import android.widget.Toast;

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
import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.ElencoPostazioniUtenteActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.RicercaPostazioneUtenteActivity;

public class PostazionePresenter {
        static final String GenericURL="http://192.168.1.7:8080/UnisaLIBServer/PostazionePresenter";
        private AsyncHttpClient client=new AsyncHttpClient();

        public void mostraRicercaPostazioni(boolean is_admin){
            String MYURL=GenericURL+"/mostra-ricerca-postazioni";
            RequestParams params;
            params= new RequestParams();
            params.put("is_admin", is_admin);

            client.post(MYURL, params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    Posizione[] posizioni = Posizione.fromJson(response);

                    Intent i = new Intent();
                    i.setClass(HomeUtenteUnisaActivity.getAppContext(), RicercaPostazioneUtenteActivity.class);
                    i.putExtra("Posizioni", Posizione.toJson(new ArrayList<>(Arrays.asList(posizioni))));
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

    public void mostraElencoPostazioni(Posizione p, GregorianCalendar date) {
        String MYURL=GenericURL+"/mostra-elenco-postazioni";
        RequestParams params;
        params= new RequestParams();
        params.put("anno", date.get(Calendar.YEAR));
        params.put("mese", date.get(Calendar.MONTH));
        params.put("giorno", date.get(Calendar.DATE));
        params.put("posizione", Posizione.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Postazione[] postazioni = new Postazione[0];
                Prenotazione[] prenotazioni = new Prenotazione[0];
                System.out.println(response);
                try {
                    postazioni = Postazione.fromJson(response.getJSONArray(1));
                    prenotazioni = Prenotazione.fromJson(response.getJSONArray(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent();
                i.setClass(RicercaPostazioneUtenteActivity.getAppContext(), ElencoPostazioniUtenteActivity.class);
                i.putExtra("postazioni", Postazione.toJson(new ArrayList<>(Arrays.asList(postazioni))));
                i.putExtra("prenotazioni", Prenotazione.toJson(new ArrayList<>(Arrays.asList(prenotazioni))));
                i.putExtra("anno", date.get(Calendar.YEAR));
                i.putExtra("mese", date.get(Calendar.MONTH));
                i.putExtra("giorno", date.get(Calendar.DATE));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RicercaPostazioneUtenteActivity.getAppContext().startActivity(i);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response);
                try {
                    Postazione[] postazioni=Postazione.fromJson(response.getJSONArray("postazioni"));
                    Prenotazione[] prenotazioni=Prenotazione.fromJson(response.getJSONArray("prenotazioni"));
                    for(int i=0;i<postazioni.length;++i)
                        System.out.println(postazioni[i].getId());
                    for(Prenotazione pr:prenotazioni)
                        System.out.println(pr.getOraFine());
                    /*System.out.println(""+response.getJSONArray("postazioni"));
                    JSONArray pos=response.getJSONArray("postazioni");
                    for(int i=0;i<pos.length();++i)

                    postazioni = Postazione.fromJson(response.getJSONArray("postazioni"));
                    for(Postazione p:postazioni)
                        System.out.println(p.getId());
                    prenotazioni = Prenotazione.fromJson(response.getJSONArray("prenotazioni"));
                    for(Prenotazione pr:prenotazioni)
                        System.out.println(pr.getOraFine());*/
                    Intent i = new Intent();
                    i.setClass(RicercaPostazioneUtenteActivity.getAppContext(), ElencoPostazioniUtenteActivity.class);
                    i.putExtra("postazioni", Postazione.toJson(new ArrayList<>(Arrays.asList(postazioni))));
                    i.putExtra("prenotazioni", Prenotazione.toJson(new ArrayList<>(Arrays.asList(prenotazioni))));
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
}
