package view.interfacciageneral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.unisalib.R;

import java.util.ArrayList;
import java.util.Arrays;

import model.libromanagement.Libro;
import model.libromanagement.LibroAdapter;

public class ElencoLibriActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_elenco_libri);

        Intent i = getIntent();
        //Prova ObjectMapper
        ListView lv = findViewById(R.id.libriLV);

        LibroAdapter libroAdapter = new LibroAdapter(this, R.layout.libro_element, libri);

        lv.setAdapter(libroAdapter);
    }
}
