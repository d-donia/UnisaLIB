package view.postazioneview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class GestionePostazioneAdminActivity extends Activity {
    private FacadePresenter fp;
    private Utente u;
    private static Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_gestione_postazioni);
        fp=new FacadePresenter();
        context=getApplicationContext();
        ImageButton userIB=findViewById(R.id.gestioneLibroIB);
        userIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confermaLogout = new AlertDialog.Builder(GestionePostazioneAdminActivity.this).
                        setTitle("Logout").
                        setMessage("Sicuro di voler uscire?").
                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fp.logout(GestionePostazioneAdminActivity.getAppContext());
                                finish();
                            }
                        }).
                        setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        Button aggiungiButton=findViewById(R.id.aggiungiPostButton);
        Button modificaButton=findViewById(R.id.modificaPostButton);

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }


        modificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.mostraRicercaPostazioni(u.isAdmin(), getApplicationContext());
            }
        });
    }

    public static Context getAppContext(){return context;}
}
