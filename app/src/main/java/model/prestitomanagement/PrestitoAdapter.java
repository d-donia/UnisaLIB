package model.prestitomanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import presenter.FacadePresenter;
import utils.InputFilterMinMax;

public class PrestitoAdapter extends ArrayAdapter<Prestito> {
    private LayoutInflater inflater;
    private FacadePresenter fp;

    public PrestitoAdapter(Context context, int resource, List<Prestito> objects) {
        super(context, resource, objects);
        inflater=LayoutInflater.from(context);
        fp=new FacadePresenter();
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
        LinearLayout commentoLayout = v.findViewById(R.id.commentoLayout);
        TextView commentoTV = v.findViewById(R.id.commentoTV);
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

        valutaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View valutaView=inflater.inflate(R.layout.valutazione_prestito, null);

                EditText votoET=valutaView.findViewById(R.id.votoPrestitoET);
                InputFilter filter= new InputFilterMinMax(1,5);
                votoET.setFilters(new InputFilter[]{filter});

                EditText commentoET=valutaView.findViewById(R.id.commentoPrestitoET);

                AlertDialog valutaPrestito = new AlertDialog.Builder(v.getContext()).
                        setTitle("Valuta Prestito").
                        setView(valutaView).
                        setPositiveButton(R.string.conferma, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    int voto = Integer.parseInt(votoET.getText().toString());
                                    String commento = commentoET.getText().toString();
                                    fp.valutaPrestito(p, voto, commento);
                                }
                                catch (NumberFormatException ex){
                                    Toast.makeText(v.getContext(), "Voto non rispetta il formato. Prestito non valutato", Toast.LENGTH_SHORT).show();
                                }
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

        int voto=p.getVoto();
        if(dataConsegna!=null){
            System.out.println("Voto:" +voto);
            if(voto!=0){
                prestitoRatingTV.setText(p.getVoto()+"");
                prestitoRatingTV.setVisibility(View.VISIBLE);
                System.out.println("CommentTV: " + p.getCommento());
                if(p.getCommento()!=null)
                    commentoTV.setText(p.getCommento());
                else{
                    commentoTV.setText("commento non presente");
                }
                commentoLayout.setVisibility(View.VISIBLE);
                prestitoStarIV.setVisibility(View.VISIBLE);
                valutaButton.setVisibility(View.GONE);
            }
            else{
                prestitoRatingTV.setVisibility(View.GONE);
                prestitoStarIV.setVisibility(View.GONE);
                commentoLayout.setVisibility(View.GONE);
                valutaButton.setVisibility(View.VISIBLE);
            }
        }
        else{
            System.out.println("Voto:" + voto);
            valutaButton.setVisibility(View.GONE);
            prestitoRatingTV.setVisibility(View.GONE);
            prestitoStarIV.setVisibility(View.GONE);
            commentoLayout.setVisibility(View.GONE);
        }

        return v;






    }
}
