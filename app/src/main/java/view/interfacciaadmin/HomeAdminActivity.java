package view.interfacciaadmin;

import android.app.Activity;
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

public class HomeAdminActivity extends Activity {
    Utente u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

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
                FacadePresenter fp = new FacadePresenter();
                fp.mostraRicercaLibri(u.isAdmin());
            }
        });

        mgmtPrestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacadePresenter fp = new FacadePresenter();
                fp.mostraRicercaPostazioni(u.isAdmin());
            }
        });


    }
}
