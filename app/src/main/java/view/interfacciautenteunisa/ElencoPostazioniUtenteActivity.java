package view.interfacciautenteunisa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.unisalib.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.prenotazionemanagement.Prenotazione;
import presenter.FacadePresenter;
import utils.SwitchDate;

public class ElencoPostazioniUtenteActivity extends Activity {
    public FacadePresenter fp = new FacadePresenter();
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_elenco_postazioni);
        fp = new FacadePresenter();
        context = getApplicationContext();
        Spinner postazioneSpinner = findViewById(R.id.postazioneSpinner);
        Spinner orarioSpinner = findViewById(R.id.orarioSpinner);
        TextView posizioneText = findViewById(R.id.posizioneTextView);
        TextView dataText = findViewById(R.id.dataTextView);
        ArrayList<Postazione> postazioni = Postazione.fromJson(getIntent().getStringExtra("postazioni"));
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
                Postazione pos = new Postazione();
                for (Postazione p : postazioni)
                    if (p.getId().equals(adapterView.getSelectedItem().toString()))
                        pos = p;
                ArrayList<Periodo> orariDisponibili = new ArrayList<>();
                for (int i = 8; i < 18; i += 2)
                    orariDisponibili.add(new Periodo(i, i+2));

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
                        for ( int i = prenotazione.getOraInizio(); i < prenotazione.getOraFine(); i += 2)
                            orariNonDisponibili.add(new Periodo(i, i+2));
                for (Periodo p : orariNonDisponibili)
                    if (orariDisponibili.contains(p))
                        orariDisponibili.remove(p);

                ArrayList<String> fasceOrarie = new ArrayList<>();
                for (Periodo p : orariDisponibili)
                    fasceOrarie.add(p.getOraInizio() + " - " + p.getOraFine());

                String[] orari = fasceOrarie.toArray(new String[0]);
                orarioSpinner.setAdapter(new ArrayAdapter<>(getAppContext(), android.R.layout.simple_spinner_dropdown_item, orari));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public static Context getAppContext() {
        return context;
    }
}
