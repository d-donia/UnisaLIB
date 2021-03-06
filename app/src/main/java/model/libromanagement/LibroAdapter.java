package model.libromanagement;

import android.app.PendingIntent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import java.util.List;
/**
 * Questa classe si occupa di creare un Adapter per inserire i Libri con i propri valori in un listview
 */
public class LibroAdapter extends ArrayAdapter<Libro> {
    private LayoutInflater inflater;
    public LibroAdapter(Context context, int resource, List<Libro> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v=inflater.inflate(R.layout.libro_element, null);

        Libro l = getItem(position);

        ImageView copertinaImageView = (ImageView) v.findViewById(R.id.copertinaIV);
        TextView titoloTextView = (TextView) v.findViewById(R.id.titoloTV);
        TextView descrizioneTextView = (TextView) v.findViewById(R.id.descrizioneTV);
        TextView isbnTextView = (TextView) v.findViewById(R.id.isbnTV);

        Glide.with(v).load(l.getUrlCopertina()).into(copertinaImageView);
        titoloTextView.setText(l.getTitolo());
        descrizioneTextView.setText(l.getAutore()+", " + l.getAnnoPubbl() + ", " + l.getEditore());
        isbnTextView.setText(l.getIsbn());

        copertinaImageView.setTag(position);
        titoloTextView.setTag(position);
        descrizioneTextView.setTag(position);
        isbnTextView.setTag(position);

        return v;
    }
}
