package view.interfacciautenteunisa;

import android.app.Activity;
import android.content.Context;
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

public class MieiPrestitiActivity extends Activity {
    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_miei_prestiti);
        context=getApplicationContext();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        Utente utente = Utente.fromJson(userSession.getString("Utente",""));
        ArrayList<Prestito> prestitiUtente=utente.getPrestiti();

        ListView lv = findViewById(R.id.prestitiLV);


        PrestitoAdapter prestitoAdapter = new PrestitoAdapter(this, R.layout.libro_element, new ArrayList<Prestito>());

        lv.setAdapter(prestitoAdapter);
        for (Prestito p:prestitiUtente) {
            prestitoAdapter.add(p);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public static Context getAppContext(){
        return context;
    }
}
