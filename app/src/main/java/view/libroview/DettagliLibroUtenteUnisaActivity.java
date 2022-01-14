package view.libroview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import java.util.GregorianCalendar;

import model.libromanagement.Libro;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class DettagliLibroUtenteUnisaActivity extends Activity {
    private static Context context;
    private static ImageButton interesseButton;
    private FacadePresenter fp;
    private Utente u;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_dettagli_libro);
        fp=new FacadePresenter();
        context=getApplicationContext();

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }

        TextView detsTitoloTV= findViewById(R.id.detsTitoloTV);
        TextView detsISBNTV=findViewById(R.id.detsISBNTV);
        TextView detsAutoreTV= findViewById(R.id.detsAutoreTV);
        TextView detsEditoreTV= findViewById(R.id.detsEditoreTV);
        TextView detsCatTV=findViewById(R.id.detsCatTV);
        TextView detsRatingTV=findViewById(R.id.detsRatingTV);
        TextView detsAPTV= findViewById(R.id.detsAPTV);

        TextView detsPosizioneTV= findViewById(R.id.detsPosizioneTV);
        ImageView detsCopertinaIV= findViewById(R.id.detsCopertinaIV);
        Button prestitoButton=findViewById(R.id.prestitoButton);
        ImageButton menuButton=findViewById(R.id.menuLibroIB);
        interesseButton=findViewById(R.id.interesseButton);

        Intent i = getIntent();
        Libro l = Libro.fromJsonToLibro(i.getStringExtra("Libro"));

        detsTitoloTV.setText(l.getTitolo());
        detsISBNTV.setText(l.getIsbn());
        detsAutoreTV.setText(l.getAutore());
        detsEditoreTV.setText(l.getEditore());
        detsAPTV.setText(l.getAnnoPubbl() + "");
        detsCatTV.setText(l.getCategoria());
        detsRatingTV.setText(l.getRating()+ "");

        detsPosizioneTV.setText(l.getPosizione().getBiblioteca() + ", " + l.getPosizione().getZona());

        //Settare immagine url utilizzando libreria glide
        Glide.with(this).load(l.getUrlCopertina()).into(detsCopertinaIV);

        if(u.getInteressi().contains(l))
            interesseButton.setImageResource(R.drawable.rsz_heart);
        else
            interesseButton.setImageResource(R.drawable.like);

        prestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = Utente.fromJson(userSession.getString("Utente", ""));
                    AlertDialog confermaPrestito = new AlertDialog.Builder(DettagliLibroUtenteUnisaActivity.this).
                            setTitle("Conferma prestito").
                            setMessage("Sicuro di voler prendere in prestito il libro " + l.getTitolo() + "?").
                            setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        u = Utente.fromJson(userSession.getString("Utente", ""));
                                    } catch (JsonSyntaxException e) {
                                        e.printStackTrace();
                                    }
                                    dialog.cancel();
                                    Prestito p = new Prestito.PrestitoBuilder().
                                            utente(u).
                                            libro(l).
                                            dataInizio(new GregorianCalendar()).build();
                                    fp.creaPrestito(p);
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
        interesseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u= Utente.fromJson(userSession.getString("Utente", ""));
                if(u.getInteressi().contains(l)) {
                    //richiesta al server per eliminare libro dalla lista di interesse
                    fp.rimuoviLibroFromInteressi(l, u);
                }
                else {
                    //richiesta al server per aggiungere libro alla lista di interesse
                    fp.aggiungiLibroToInteressi(l, u);
                }
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ciao");
                PopupMenu popupMenu=new PopupMenu(DettagliLibroUtenteUnisaActivity.this, menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.utente_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String title=item.getTitle().toString();
                        switch (title){
                            case "Miei Prestiti":
                                fp.mostraMieiPrestiti(DettagliLibroUtenteUnisaActivity.getAppContext());
                                break;
                            case "Mie Prenotazioni":
                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                                break;
                            case "Miei Interessi":
                                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                                break;
                            case "Logout":
                                AlertDialog confermaLogout = new AlertDialog.Builder(DettagliLibroUtenteUnisaActivity.this).
                                        setTitle("Logout").
                                        setMessage("Sicuro di voler uscire?").
                                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                fp.logout(DettagliLibroUtenteUnisaActivity.getAppContext());
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

    }

    public static Context getAppContext(){
        return context;
    }

    public static void impostaNonInteresse(Utente utenteAggiornato){
        interesseButton.setImageResource(R.drawable.like);
    }

    public static void impostaInteresse(Utente utenteAggiornato){
        interesseButton.setImageResource(R.drawable.rsz_heart);
    }
}