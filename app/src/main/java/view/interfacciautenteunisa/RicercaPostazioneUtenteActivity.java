package view.interfacciautenteunisa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.unisalib.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.posizionemanagement.Posizione;
import presenter.FacadePresenter;

public class RicercaPostazioneUtenteActivity extends Activity {
    public final long SECONDINMILLS = 1000;
    public final long WEEKINMILLS = 604800000;
    public FacadePresenter fp;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        fp = new FacadePresenter();
        setContentView(R.layout.utente_ricerca_postazione);
        Spinner bibliotecaSpinner = findViewById(R.id.bibliotecaSpinner);
        Spinner zonaSpinner = findViewById(R.id.zonaSpinner);
        DatePicker datePicker = findViewById(R.id.datePicker);
        Button cercaButton = findViewById(R.id.cercaButton);
        String p = getIntent().getStringExtra("Posizioni");
        ArrayList<Posizione> posizioni = Posizione.fromJson(p);
        //inizializzazione del Biblioteca Spinner
        ArrayList<String> b = new ArrayList<>();
        for (Posizione x: posizioni)
            if (!b.contains(x.getBiblioteca()))
                b.add(x.getBiblioteca());
        String[] biblioteche = b.toArray(new String[0]);
        bibliotecaSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, biblioteche));
        //inizializzazione del Zona Spinner
        bibliotecaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ArrayList<String> z = new ArrayList<>();
                String selected = parentView.getSelectedItem().toString();
                for (Posizione x: posizioni) {
                    if (x.getBiblioteca().equals(selected))
                        z.add(x.getZona());
                }
                System.out.println(z);
                String[] zone = z.toArray(new String[0]);
                zonaSpinner.setAdapter(new ArrayAdapter<>(getAppContext(), android.R.layout.simple_spinner_dropdown_item, zone));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });
        datePicker.setMinDate(System.currentTimeMillis() - SECONDINMILLS);
        datePicker.setMaxDate(System.currentTimeMillis() + WEEKINMILLS);

        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posizione p = new Posizione();
                for (Posizione x: posizioni)
                    if (x.getBiblioteca().equals(bibliotecaSpinner.getSelectedItem().toString()) && x.getZona().equals(zonaSpinner.getSelectedItem().toString()))
                        p=x;
                GregorianCalendar date = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                fp.mostraElencoPostazioni(p, date);
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}
