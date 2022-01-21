package view.postazioneview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.unisalib.R;

import java.util.ArrayList;

import model.posizionemanagement.Posizione;
import presenter.FacadePresenter;

public class RicercaPostazioneAdminActivity extends Activity {
    public FacadePresenter fp;
    public static Context context;

    public static Context getAppContext() {
        return context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ricerca_postazione);
        fp=new FacadePresenter();
        context=getApplicationContext();
        Spinner bibliotecaSpinner = findViewById(R.id.bibliotecaSpinner);
        Spinner zonaSpinner = findViewById(R.id.zonaSpinner);
        Button cercaButton = findViewById(R.id.cercaButtonAD);
        ArrayList<String> b = new ArrayList<>();
        String p = getIntent().getStringExtra("Posizioni");
        ArrayList<Posizione> posizioni = Posizione.fromJson(p);
        //spinner biblioteca, recupero delle varie biblioteche
        for (Posizione x : posizioni) {
            if (!b.contains(x.getBiblioteca()))
                b.add(x.getBiblioteca());
        }
        String[] biblioteche = b.toArray(new String[0]);
        bibliotecaSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, biblioteche));
        bibliotecaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> z = new ArrayList<>();
                String selected = adapterView.getSelectedItem().toString();
                for (Posizione x: posizioni) {
                    if (x.getBiblioteca().equals(selected))
                        z.add(x.getZona());
                }
                System.out.println(z);
                String[] zone = z.toArray(new String[0]);
                zonaSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, zone));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Posizione p = new Posizione();
                for (Posizione x: posizioni)
                    if (x.getBiblioteca().equals(bibliotecaSpinner.getSelectedItem().toString()) && x.getZona().equals(zonaSpinner.getSelectedItem().toString()))
                        p=x;
                fp.mostraElencoPostazioni(RicercaPostazioneAdminActivity.getAppContext(),p);
            }
        });
    }

    }
