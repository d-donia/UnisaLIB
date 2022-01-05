package model.prestitomanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PrestitoAdapter extends ArrayAdapter<Prestito> {
    private LayoutInflater inflater;

    public PrestitoAdapter(Context context, int resource, List<Prestito> objects) {
        super(context, resource, objects);
        inflater=LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup parent){
        if(v==null)
            v=inflater.inflate(R.layout.prestito_element, null);

        Prestito p = (Prestito) getItem(position);

        ImageView prestitoCopertinaIV=v.findViewById(R.id.prestitoCopertinaIV);
        TextView libroTV = v.findViewById(R.id.libroTV);
        TextView dataInizioTV = v.findViewById(R.id.dataInizioTV);
        TextView dataFineTV = v.findViewById(R.id.dataFineTV);
        TextView dataConsegnaTV = v.findViewById(R.id.dataConsegnaTV);
        TextView attivoTV = v.findViewById(R.id.attivoTV);
        TextView prestitoRatingTV = v.findViewById(R.id.prestitoRatingTV);
        Button valutaButton = v.findViewById(R.id.valutaButton);
        ImageView prestitoStarIV = v.findViewById(R.id.prestitoStarIV);

        Glide.with(v).load(p.getLibro().getUrlCopertina()).into(prestitoCopertinaIV);

        libroTV.setText(p.getLibro().getTitolo() + ", " + p.getLibro().getIsbn() );
        GregorianCalendar dataInizio=p.getDataInizio();
        dataInizioTV.setText(dataInizio.get(Calendar.DAY_OF_MONTH) + "/" + (dataInizio.get(Calendar.MONTH)+1) + "/" + dataInizio.get(Calendar.YEAR));
        GregorianCalendar dataFine=p.getDataFine();
        dataFineTV.setText(dataFine.get(Calendar.DAY_OF_MONTH) + "/" + (dataFine.get(Calendar.MONTH)+1) + "/" + dataFine.get(Calendar.YEAR));
        GregorianCalendar dataConsegna=p.getDataConsegna();
        if(dataConsegna!=null)
            dataConsegnaTV.setText(dataConsegna.get(Calendar.DAY_OF_MONTH) + "/" + dataConsegna.get(Calendar.MONTH) + "/" + dataConsegna.get(Calendar.YEAR));
        else{
            dataConsegnaTV.setText("Non Consegnato");
        }
        if(p.isAttivo())
            attivoTV.setText("SI");
        else {
            attivoTV.setText("NO");
        }

        int voto=p.getVoto();
        if(voto!=0){
            System.out.println("Voto:" +voto);
            prestitoRatingTV.setText(voto);
            prestitoStarIV.setVisibility(View.VISIBLE);
            valutaButton.setVisibility(View.GONE);
        }
        else{
            System.out.println("Voto:" + voto);
            valutaButton.setVisibility(View.VISIBLE);
        }

        return v;






    }
}
