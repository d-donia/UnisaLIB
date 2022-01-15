package view.prestitoview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import model.prestitomanagement.Prestito;
import model.prestitomanagement.PrestitoAdapter;
import model.prestitomanagement.PrestitoLibroAdapter;
import presenter.FacadePresenter;

public class PrestitiLibroActivity extends Activity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_prestiti_libro);
        context = getApplicationContext();

        FacadePresenter fp=new FacadePresenter();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i=getIntent();
        ArrayList<Prestito> prestitiLibro=Prestito.fromJson(i.getStringExtra("prestiti"));
        String message = i.getStringExtra("message");
        if(message!=null){
            Snackbar.make(findViewById(R.id.prestitiLibroLayout), message, Snackbar.LENGTH_SHORT).show();
        }

        ListView lv = findViewById(R.id.prestitiLibroLV);

        PrestitoLibroAdapter prestitoAdapter = new PrestitoLibroAdapter(this, R.layout.admin_prestito_element, new ArrayList<Prestito>());

        lv.setAdapter(prestitoAdapter);
        for (Prestito p : prestitiLibro) {
            prestitoAdapter.add(p);
        }
    }

    public static Context getAppContext(){
        return context;
    }
}
