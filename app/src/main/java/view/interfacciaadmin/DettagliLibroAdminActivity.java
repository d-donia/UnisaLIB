package view.interfacciaadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import model.libromanagement.Libro;
import presenter.FacadePresenter;

public class DettagliLibroAdminActivity extends Activity {
    private FacadePresenter fp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dettagli_libro);
        fp=new FacadePresenter();

        TextView detsTitoloTV= findViewById(R.id.detsTitoloTV);
        TextView detsISBNTV=findViewById(R.id.detsISBNTV);
        TextView detsAutoreTV= findViewById(R.id.detsAutoreTV);
        TextView detsEditoreTV= findViewById(R.id.detsEditoreTV);
        TextView detsCatTV=findViewById(R.id.detsCatTV);
        TextView detsRatingTV=findViewById(R.id.detsRatingTV);
        TextView detsAPTV= findViewById(R.id.detsAPTV);

        TextView detsPosizioneTV= findViewById(R.id.detsPosizioneTV);
        ImageView detsCopertinaIV= findViewById(R.id.detsCopertinaIV);
        Button modificaButton=findViewById(R.id.modificaButton);
        Button cancellaButton=findViewById(R.id.cancellaButton);
        Button listaPrestitiButton=findViewById(R.id.listaPrestitiButton);


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
    }
}
