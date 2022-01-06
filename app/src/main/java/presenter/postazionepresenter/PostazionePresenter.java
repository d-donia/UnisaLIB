package presenter.postazionepresenter;

import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.Header;
import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import utils.SwitchDate;
import view.interfacciageneral.RicercaActivity;
import view.interfacciautenteunisa.ElencoPostazioniUtenteActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.RicercaPostazioneUtenteActivity;

public class PostazionePresenter {
    static final String GenericURL = "http://192.168.1.7:8080/UnisaLIBServer/PostazionePresenter";
    private AsyncHttpClient client = new AsyncHttpClient();

    public void mostraRicercaPostazioni(boolean is_admin) {
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
        String MYURL = GenericURL + "/mostra-elenco-postazioni";
        RequestParams params;
        params = new RequestParams();
        System.out.println(date.get(Calendar.YEAR));
        params.put("anno", date.get(Calendar.YEAR));
        params.put("mese", date.get(Calendar.MONTH));
        params.put("giorno", date.get(Calendar.DATE));
        params.put("posizione", Posizione.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Postazione[] postazioni = Postazione.fromJson(response.getJSONArray("postazioni"));
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

    public ArrayList<String> mostraOrariDisponibili(ArrayList<Postazione> postazioni, String idPos, ArrayList<Prenotazione> prenotazioni, GregorianCalendar date) {
        Postazione pos = new Postazione();

        for (Postazione p : postazioni)
            if (p.getId().equals(idPos))
                pos = p;

        ArrayList<Periodo> orariDisponibili = new ArrayList<>();
        for (int i = 9; i < 18; i += 2) {
            if (i == 13)
                i++;
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
        for(Periodo p : orariDisponibili)
            if (SwitchDate.equalsDate(date, currentTime) && currentTime.get(Calendar.HOUR_OF_DAY)>=p.getOraInizio())
                orariNonDisponibili.add(p);
        for (Periodo p : orariNonDisponibili)
            if (orariDisponibili.contains(p))
                orariDisponibili.remove(p);

        ArrayList<String> fasceOrarie = new ArrayList<>();
        for (Periodo p : orariDisponibili)
            fasceOrarie.add(p.getOraInizio() + " - " + p.getOraFine());

        return fasceOrarie;
    }
}
