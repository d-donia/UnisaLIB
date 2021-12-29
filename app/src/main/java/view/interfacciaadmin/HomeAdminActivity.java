package view.interfacciaadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unisalib.R;


import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class HomeAdminActivity extends Activity {
    Utente u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        Intent i=getIntent();
        u = (Utente) i.getSerializableExtra("Utente");

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
