package view.interfacciaadmin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.unisalib.R;

import java.util.ArrayList;

import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import presenter.FacadePresenter;

public class AggiungiLibroActivity extends Activity {
    public FacadePresenter fp;
    public static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_aggiungi_libri);
        context = getApplicationContext();
        fp = new FacadePresenter();
        EditText isbnET = findViewById(R.id.isbnET);
        EditText titoloET = findViewById(R.id.titoloET);
        EditText autoreET = findViewById(R.id.autoreET);
        EditText editoreET = findViewById(R.id.editoreET);
        EditText annoET = findViewById(R.id.annoET);
        EditText ncopieET = findViewById(R.id.ncopieET);
        EditText imgET = findViewById(R.id.imgET);
        Spinner categoriaSpinner = findViewById(R.id.categoriaALSpinner);
        Spinner bibliotecaSpinner = findViewById(R.id.bibliotecaALSpinner);
        Spinner zonaSpinner = findViewById(R.id.zonaALSpinner);
        Button aggiungiLibroButton = findViewById(R.id.aggiungiLibroButton);

        String[] categorie = getIntent().getStringArrayExtra("categorie");
        categoriaSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorie));
        String pos = getIntent().getStringExtra("posizioni");
        ArrayList<Posizione> posizioni = Posizione.fromJson(pos);

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

        aggiungiLibroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Posizione p = new Posizione();
                for (Posizione x: posizioni)
                    if (x.getBiblioteca().equals(bibliotecaSpinner.getSelectedItem().toString()) && x.getZona().equals(zonaSpinner.getSelectedItem().toString()))
                        p=x;
                Libro l = new Libro.LibroBuilder().
                        isbn(isbnET.getText().toString()).
                        titolo(titoloET.getText().toString()).
                        autore(autoreET.getText().toString()).
                        editore(editoreET.getText().toString()).
                        urlCopertina(imgET.getText().toString()).
                        categoria(categoriaSpinner.getSelectedItem().toString()).
                        nCopie(Integer.parseInt(ncopieET.getText().toString())).
                        annoPubbl(Integer.parseInt(annoET.getText().toString())).
                        posizione(p).
                        build();
                fp.creaLibro(l);
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}
