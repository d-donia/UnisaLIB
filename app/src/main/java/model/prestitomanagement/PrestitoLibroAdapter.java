package model.prestitomanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unisalib.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import presenter.FacadePresenter;
import utils.InputFilterMinMax;
/**
 * Questa classe si occupa di creare un Adapter per inserire i Prestiti
 * e le email degli utenti in un ListView
 */
public class PrestitoLibroAdapter extends ArrayAdapter<Prestito> {
    private LayoutInflater inflater ;
    private FacadePresenter fp;
    public PrestitoLibroAdapter(Context context, int resource, List<Prestito> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        fp=new FacadePresenter();
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

        attivaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.attivaPrestito(p);
            }
        });

        concludiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View concludiView=inflater.inflate(R.layout.conclusione_prestito, null);

                DatePicker conclusioneDP =concludiView.findViewById(R.id.conclusioneDP);
                TextView libroTV=concludiView.findViewById(R.id.libroPrestitoTV);
                TextView utenteTV=concludiView.findViewById(R.id.utentePrestitoTV);

                libroTV.setText(p.getLibro().getIsbn());
                utenteTV.setText(p.getUtente().getEmail());
                conclusioneDP.setMaxDate(System.currentTimeMillis());

                AlertDialog valutaPrestito = new AlertDialog.Builder(v.getContext()).
                        setTitle("Conclusione prestito").
                        setView(concludiView).
                        setPositiveButton(R.string.conferma, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GregorianCalendar date = new GregorianCalendar(conclusioneDP.getYear(), conclusioneDP.getMonth(), conclusioneDP.getDayOfMonth());
                                fp.concludiPrestito(p, date);
                            }
                        }).
                        setNegativeButton(R.string.esci, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        return v;

    }
}
