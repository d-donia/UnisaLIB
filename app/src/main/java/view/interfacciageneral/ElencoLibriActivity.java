package view.interfacciageneral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.unisalib.R;

import java.util.ArrayList;

import model.libromanagement.Libro;
import model.libromanagement.LibroAdapter;
import presenter.FacadePresenter;

public class ElencoLibriActivity extends Activity {
    private FacadePresenter fp;
    private static Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_elenco_libri);
        fp=new FacadePresenter();
        context=getApplicationContext();

        Intent i = getIntent();
        String libri=i.getStringExtra("Libri");
        ArrayList<Libro> l=Libro.fromJson(libri);
        ListView lv = findViewById(R.id.libriLV);

        LibroAdapter libroAdapter = new LibroAdapter(this, R.layout.libro_element, new ArrayList<Libro>());

        lv.setAdapter(libroAdapter);
        for (Libro lib:l) {
            libroAdapter.add(lib);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Libro l = (Libro) lv.getItemAtPosition(position);
                fp.mostraDettagliLibro(l);
            }
        });
    }

    public static Context getAppContext(){
        return context;
    }
}
