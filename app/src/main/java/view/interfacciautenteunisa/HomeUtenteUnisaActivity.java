package view.interfacciautenteunisa;

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
import view.interfacciageneral.MainActivity;

public class HomeUtenteUnisaActivity extends Activity {
    Utente u;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_home);
        context=getApplicationContext();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        Button svcPrestitoButton = findViewById(R.id.svcPrestitoButton);
        Button svcPrenotazioneButton = findViewById(R.id.svcPrenotazioneButton);

        svcPrestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacadePresenter fp = new FacadePresenter();
                fp.mostraRicercaLibri(u.isAdmin());
            }
        });

        svcPrenotazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacadePresenter fp = new FacadePresenter();
                fp.mostraRicercaPostazioni(u.isAdmin());
            }
        });



    }

    public static Context getAppContext() {
        return context;
    }
}
