package view.interfacciautenteunisa;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.libromanagement.Libro;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class DettagliLibroUtenteUnisaActivity extends Activity {
    private static Context context;
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
        ImageButton interesseButton=findViewById(R.id.interesseButton);

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

        prestitoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confermaPrestito = new AlertDialog.Builder(DettagliLibroUtenteUnisaActivity.this).
                        setTitle("Conferma prestito").
                        setMessage("Sicuro di voler prendere in prestito il libro " + l.getTitolo() + "?").
                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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


            }
        });

    }

    public static Context getAppContext(){
        return context;
    }
}
