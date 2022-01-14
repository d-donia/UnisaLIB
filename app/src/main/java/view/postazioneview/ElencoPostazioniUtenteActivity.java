package view.postazioneview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class ElencoPostazioniUtenteActivity extends Activity {
    public FacadePresenter fp = new FacadePresenter();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_elenco_postazioni);
        fp = new FacadePresenter();
        context = getApplicationContext();
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        Utente u = Utente.fromJson(userSession.getString("Utente", ""));
        Spinner postazioneSpinner = findViewById(R.id.postazioneSpinner);
        Spinner orarioSpinner = findViewById(R.id.orarioSpinner);
        TextView posizioneText = findViewById(R.id.posizioneTextView);
        TextView dataText = findViewById(R.id.dataTextView);
        Button confermaButton = findViewById(R.id.confermaPrenotazioneButton);
        ArrayList<Postazione> postazioni = Postazione.fromJsonArray(getIntent().getStringExtra("postazioni"));
        ArrayList<Prenotazione> prenotazioni = Prenotazione.fromJson(getIntent().getStringExtra("prenotazioni"));
        Posizione posizione = Posizione.fromJsonToPosizione(getIntent().getStringExtra("posizione"));
        GregorianCalendar date = new GregorianCalendar(getIntent().getIntExtra("anno", 0),
                getIntent().getIntExtra("mese", 0), getIntent().getIntExtra("giorno", 0));

        posizioneText.setText(posizione.getBiblioteca() + ", " + posizione.getZona());
        dataText.setText(date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1) + "/" + date.get(Calendar.YEAR));

        ArrayList<String> postazioniId = new ArrayList<>();
        for (Postazione p : postazioni)
            postazioniId.add(p.getId());
        String[] idPos = postazioniId.toArray(new String[0]);
        postazioneSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, idPos));
        postazioneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int k, long l) {
                String id = adapterView.getSelectedItem().toString();
                ArrayList<String> orariDisponibili = fp.mostraOrariDisponibili(postazioni, id, prenotazioni, date);
                if (orariDisponibili.isEmpty())
                    Toast.makeText(ElencoPostazioniUtenteActivity.getAppContext(), "Nessuna fascia oraria disponibile per questa postazione", Toast.LENGTH_SHORT).show();
                String[] orari = orariDisponibili.toArray(new String[0]);
                orarioSpinner.setAdapter(new ArrayAdapter<>(getAppContext(), android.R.layout.simple_spinner_dropdown_item, orari));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        confermaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Postazione pos = new Postazione();
                for (Postazione p : postazioni)
                    if (p.getId().equals(postazioneSpinner.getSelectedItem().toString()))
                        pos=p;
                String orarioSelected = orarioSpinner.getSelectedItem().toString();
                int oraInizio = Integer.parseInt(orarioSelected.substring(0, (orarioSelected.indexOf("-")-1)));
                int oraFine = Integer.parseInt(orarioSelected.substring(orarioSelected.indexOf("-")+2));
                Prenotazione pre = new Prenotazione(date, oraInizio, oraFine, u, pos);

                AlertDialog confermaPrestito = new AlertDialog.Builder(ElencoPostazioniUtenteActivity.this).
                        setTitle("Conferma prenotazione").
                        setMessage("Sicuro di voler prenotare la postazione " + pos.getId() + " dalle ore " + pre.getOraInizio() + " alle ore " + pre.getOraFine() + "?").
                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                fp.creaPrenotazione(pre);
                            }
                        }).
                        setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}
