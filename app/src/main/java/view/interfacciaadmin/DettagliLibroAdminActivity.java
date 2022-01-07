package view.interfacciaadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import model.libromanagement.Libro;
import model.posizionemanagement.Posizione;
import presenter.FacadePresenter;

public class DettagliLibroAdminActivity extends Activity {
    private FacadePresenter fp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dettagli_libro);
        fp=new FacadePresenter();

        EditText detsTitoloTV= findViewById(R.id.detsTitoloTV);
        TextView detsISBNTV=findViewById(R.id.detsISBNTV);
        EditText detsAutoreTV= findViewById(R.id.detsAutoreTV);
        EditText detsEditoreTV= findViewById(R.id.detsEditoreTV);
        EditText detsCatTV=findViewById(R.id.detsCatTV);
        TextView detsRatingTV=findViewById(R.id.detsRatingTV);
        EditText detsAPTV= findViewById(R.id.detsAPTV);
        EditText detsCopie=findViewById(R.id.detsCopieTV);

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

        modificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []s=detsPosizioneTV.getText().toString().split(",");
                int copie=0;
                try {
                    copie = Integer.parseInt(detsCopie.getText().toString());
                }catch(Exception e){
                    copie=0;
                }
                Posizione p=new Posizione(s[0],s[1]);
                /*Libro l= new Libro.LibroBuilder().titolo(detsTitoloTV.getText().toString())
                        .isbn(detsISBNTV.getText().toString())
                        .autore(detsAutoreTV.getText().toString())
                        .editore(detsEditoreTV.getText().toString())
                        .annoPubbl(detsAPTV.getText().toString())
                        .categoria(detsCatTV.getText().toString())
                        .nCopie(l.getnCopie()+copie);
                fp.modificaLibro()*/
            }
        });
    }
}
