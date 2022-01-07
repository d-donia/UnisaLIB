package view.interfacciaadmin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class GestioneLibroAdminActivity extends Activity {
    FacadePresenter fp;
    Utente u;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_gestione_libri);
        fp=new FacadePresenter();
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        Button addLibro= findViewById(R.id.addButton);
        Button modifyLibro=findViewById(R.id.modifyButton);

        addLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(getApplicationContext(),AggiungiLibroActivity.class);
                startActivity(i);
            }
        });

        modifyLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.mostraRicercaLibri(u.isAdmin(),getApplicationContext());
            }
        });
    }
}
