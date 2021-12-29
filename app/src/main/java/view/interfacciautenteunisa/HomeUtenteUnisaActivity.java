package view.interfacciautenteunisa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unisalib.R;

import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class HomeUtenteUnisaActivity extends Activity {
    Utente u;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_home);
        context=getApplicationContext();

        Intent i=getIntent();
        u = (Utente) i.getSerializableExtra("Utente");

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
