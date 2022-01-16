package view.utenteview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;


import model.utentemanagement.Utente;
import presenter.FacadePresenter;
import view.libroview.GestioneLibroAdminActivity;
import view.postazioneview.GestionePostazioneAdminActivity;

public class HomeAdminActivity extends Activity {
    private Utente u;
    private FacadePresenter fp;
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        context=getApplicationContext();
        ImageButton userIB=findViewById(R.id.adminIB);
        userIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confermaLogout = new AlertDialog.Builder(HomeAdminActivity.this).
                        setTitle("Logout").
                        setMessage("Sicuro di voler uscire?").
                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fp.logout(HomeAdminActivity.getAppContext());
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
                Intent i=new Intent();
                i.setClass(getApplicationContext(), GestionePostazioneAdminActivity.class);
                startActivity(i);
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
