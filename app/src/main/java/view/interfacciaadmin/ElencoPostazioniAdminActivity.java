package view.interfacciaadmin;

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
import model.postazionemanagement.Postazione;
import model.postazionemanagement.PostazioneAdapter;
import model.prestitomanagement.Prestito;
import model.prestitomanagement.PrestitoAdapter;
import presenter.FacadePresenter;
import view.interfacciautenteunisa.MieiPrestitiActivity;

public class ElencoPostazioniAdminActivity extends Activity {
    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_elenco_postazioni);
        context=getApplicationContext();
        Intent i=getIntent();
        ArrayList<Postazione> postazioni= Postazione.fromJson(i.getStringExtra("postazioni"));

        ListView lv = findViewById(R.id.postazioneLV);


        PostazioneAdapter postazioneAdapter = new PostazioneAdapter(this, R.layout.postazione_element, new ArrayList<Postazione>());

        lv.setAdapter(postazioneAdapter);
        for (Postazione p:postazioni) {
            System.out.println(p.getId()+"ok");
            postazioneAdapter.add(p);
        }
    }

    public static Context getAppContext(){
        return context;
    }
}
