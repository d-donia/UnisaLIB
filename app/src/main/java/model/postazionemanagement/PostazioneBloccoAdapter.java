package model.postazionemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import model.posizionemanagement.Posizione;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;
import presenter.postazionepresenter.PostazionePresenterImp;
import view.postazioneview.BloccoActivity;
import view.postazioneview.ElencoPostazioniAdminActivity;
import view.postazioneview.SbloccoActivity;

public class PostazioneBloccoAdapter extends ArrayAdapter<Periodo> {
    private LayoutInflater inflater;
    private FacadePresenter fp;

    public PostazioneBloccoAdapter(Context context, int resource, List<Periodo> objects) {
        super(context, resource, objects);
        inflater=LayoutInflater.from(context);
        fp=new FacadePresenter();
    }

    public View getView(int position, View v, ViewGroup parent) {
        if (v == null)
            v = inflater.inflate(R.layout.postazione_blocco_element, null);

        Periodo p = getItem(position);
        TextView tvData = v.findViewById(R.id.tvDataId);
        TextView tvOrario = v.findViewById(R.id.tvOrarioId);
        Button sblocco = v.findViewById(R.id.eliminaBlocco);
        Date data = p.getData().getTime();
        DateFormat df = DateFormat.getDateInstance();
        tvData.setText(df.format(data));
        tvOrario.setText(p.getOraInizio() +" - "+p.getOraFine());
        sblocco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Postazione pos = null;
                SharedPreferences postazione = PreferenceManager.getDefaultSharedPreferences(SbloccoActivity.getAppContext());
                try {
                    pos = Postazione.fromJson(postazione.getString("postazione", ""));
                }
                catch (JsonSyntaxException e){
                    e.printStackTrace();
                }
                fp.sbloccaPostazione(pos.getId(),p);
            }

        });

        return v;
    }
}
