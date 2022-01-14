package view.utenteview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

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
        FacadePresenter fp=new FacadePresenter();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        Button svcPrestitoButton = findViewById(R.id.svcPrestitoButton);
        Button svcPrenotazioneButton = findViewById(R.id.svcPrenotazioneButton);
        ImageButton userIB=findViewById(R.id.userIB);

        userIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ciao");
                PopupMenu popupMenu=new PopupMenu(HomeUtenteUnisaActivity.this, userIB);
                popupMenu.getMenuInflater().inflate(R.menu.utente_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String title=item.getTitle().toString();
                        switch (title){
                            case "Miei Prestiti":
                                fp.mostraMieiPrestiti(HomeUtenteUnisaActivity.getAppContext());
                                break;
                            case "Mie Prenotazioni":
                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                                break;
                            case "Miei Interessi":
                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                                break;
                            case "Logout":
                                AlertDialog confermaLogout = new AlertDialog.Builder(HomeUtenteUnisaActivity.this).
                                        setTitle("Logout").
                                        setMessage("Sicuro di voler uscire?").
                                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                fp.logout(HomeUtenteUnisaActivity.getAppContext());
                                                finish();
                                            }
                                        }).
                                        setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        }).show();
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


        svcPrestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.mostraRicercaLibri(u.isAdmin(),getApplicationContext());
            }
        });

        svcPrenotazioneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.mostraRicercaPostazioni(u.isAdmin(), getApplicationContext());
            }
        });

    }

    public static Context getAppContext() {
        return context;
    }
}
