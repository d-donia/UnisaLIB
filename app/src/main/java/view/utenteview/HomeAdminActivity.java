package view.utenteview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;


import model.utentemanagement.Utente;
import presenter.FacadePresenter;
import view.libroview.GestioneLibroAdminActivity;

public class HomeAdminActivity extends Activity {
    Utente u;
    FacadePresenter fp;

    public static Context getAppContext() {
        return getAppContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        fp=new FacadePresenter();
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        Button mgmtPrestitoButton = findViewById(R.id.mgmtPrestitoButton);
        Button mgmtPrenotazioneButton = findViewById(R.id.mgmtPrenotazioneButton);

        mgmtPrenotazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sono qui");
                fp.mostraRicercaPostazioni(u.isAdmin(), getApplicationContext());
            }
        });

        mgmtPrestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getApplicationContext(), GestioneLibroAdminActivity.class);
                startActivity(i);
            }
        });


    }
}