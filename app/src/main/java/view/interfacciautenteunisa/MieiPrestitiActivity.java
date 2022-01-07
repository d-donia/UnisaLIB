package view.interfacciautenteunisa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.unisalib.R;

import java.util.ArrayList;

import model.libromanagement.Libro;
import model.libromanagement.LibroAdapter;
import model.prestitomanagement.Prestito;
import model.prestitomanagement.PrestitoAdapter;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class MieiPrestitiActivity extends Activity {
    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_miei_prestiti);
        context=getApplicationContext();
        FacadePresenter fp=new FacadePresenter();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i=getIntent();
        ArrayList<Prestito> prestitiUtente=Prestito.fromJson(i.getStringExtra("prestiti"));

        ListView lv = findViewById(R.id.prestitiLV);


        PrestitoAdapter prestitoAdapter = new PrestitoAdapter(this, R.layout.libro_element, new ArrayList<Prestito>());

        lv.setAdapter(prestitoAdapter);
        for (Prestito p:prestitiUtente) {
            prestitoAdapter.add(p);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prestito prestito=(Prestito) lv.getItemAtPosition(position);
                Libro l= prestito.getLibro();
                fp.mostraDettagliLibro(MieiPrestitiActivity.getAppContext(), l);
            }
        });
    }

    public static Context getAppContext(){
        return context;
    }
}
