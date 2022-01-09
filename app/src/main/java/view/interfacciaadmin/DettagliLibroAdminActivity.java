package view.interfacciaadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

        EditText detsTitoloET= findViewById(R.id.detsTitoloET);
        TextView detsISBNTV=findViewById(R.id.detsAdIsbnTV);
        EditText detsAutoreET= findViewById(R.id.detsAutoreET);
        EditText detsEditoreET= findViewById(R.id.detsEditoreET);
        TextView detsRatingTV=findViewById(R.id.detsADRatingTV);
        EditText detsAPTV= findViewById(R.id.detsAnnoET);
        EditText detsCopie=findViewById(R.id.detsNcopieET);
        EditText copertinaET=findViewById(R.id.detsImgET);

        Spinner categoriaSP=findViewById(R.id.categoriaDLSpinner);
        Spinner bibliotecaSP = findViewById(R.id.bibliotecaDLSpinner);
        Spinner zonaSP=findViewById(R.id.zonaDLSpinner);
        Button modificaButton=findViewById(R.id.modificaLibroButton);
        Button cancellaButton=findViewById(R.id.eliminaLibroButton);
        Button listaPrestitiButton=findViewById(R.id.listaPrestitiButton);


        Intent i = getIntent();
        Libro l = Libro.fromJsonToLibro(i.getStringExtra("Libro"));

        detsTitoloET.setText(l.getTitolo());
        detsISBNTV.setText(l.getIsbn());
        detsAutoreET.setText(l.getAutore());
        detsEditoreET.setText(l.getEditore());
        detsAPTV.setText(l.getAnnoPubbl() + "");
        detsRatingTV.setText(l.getRating()+ "");
        detsCopie.setText(l.getnCopie()+"");
        copertinaET.setText(l.getUrlCopertina());

        /*modificaButton.setOnClickListener(new View.OnClickListener() {
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
                Libro l= new Libro.LibroBuilder().titolo(detsTitoloTV.getText().toString())
                        .isbn(detsISBNTV.getText().toString())
                        .autore(detsAutoreTV.getText().toString())
                        .editore(detsEditoreTV.getText().toString())
                        .annoPubbl(Integer.parseInt(detsAPTV.getText().toString()))
                        .categoria(detsCatTV.getText().toString())
                        .nCopie(l.getnCopie()+copie);
                fp.modificaLibro()
            }
        });*/
    }
}
