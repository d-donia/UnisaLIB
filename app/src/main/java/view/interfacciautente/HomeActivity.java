package view.interfacciautente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.unisalib.R;

import model.utentemanagement.Utente;

public class HomeActivity extends Activity {
    Utente u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_home);

        Intent i=getIntent();
        u= (Utente) i.getSerializableExtra("Utente");
    }
}
