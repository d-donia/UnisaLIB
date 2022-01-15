package model.prestitomanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.unisalib.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class PrestitoLibroAdapter extends ArrayAdapter<Prestito> {
    private LayoutInflater inflater ;
    public PrestitoLibroAdapter(Context context, int resource, List<Prestito> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup parent){
        if(v==null)
            v=inflater.inflate(R.layout.admin_prestito_element, null);

        Prestito p = (Prestito) getItem(position);

        TextView utenteTV = v.findViewById(R.id.utenteADTV);
        TextView dataInizioTV = v.findViewById(R.id.dataInizioADTV);
        TextView dataFineTV = v.findViewById(R.id.dataFineADTV);
        Button attivaButton = v.findViewById(R.id.attivaButton);
        Button concludiButton = v.findViewById(R.id.concludiButton);

        utenteTV.setText(p.getUtente().getEmail());
        GregorianCalendar dataInizio=p.getDataInizio();
        dataInizioTV.setText(dataInizio.get(Calendar.DAY_OF_MONTH) + "/" + (dataInizio.get(Calendar.MONTH)+1) + "/" + dataInizio.get(Calendar.YEAR));
        GregorianCalendar dataFine=p.getDataFine();
        dataFineTV.setText(dataFine.get(Calendar.DAY_OF_MONTH) + "/" + (dataFine.get(Calendar.MONTH)+1) + "/" + dataFine.get(Calendar.YEAR));
        GregorianCalendar dataConsegna=p.getDataConsegna();

        if(p.isAttivo()) {
            attivaButton.setVisibility(View.GONE);
            concludiButton.setVisibility(View.VISIBLE);
        }
        else{
            attivaButton.setVisibility(View.VISIBLE);
            concludiButton.setVisibility(View.GONE);
        }

        return v;

    }
}
