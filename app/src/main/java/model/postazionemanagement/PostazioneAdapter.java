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
import java.util.List;

import model.posizionemanagement.Posizione;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;
import view.postazioneview.BloccoActivity;
import view.postazioneview.ElencoPostazioniAdminActivity;
import view.postazioneview.SbloccoActivity;
import view.utenteview.MainActivity;

public class PostazioneAdapter extends ArrayAdapter<Postazione> {
    private LayoutInflater inflater;
    private FacadePresenter fp;

    public PostazioneAdapter(Context context, int resource, List<Postazione> objects) {
        super(context, resource, objects);
        inflater=LayoutInflater.from(context);
        fp=new FacadePresenter();
    }

    public View getView(int position, View v, ViewGroup parent) {
        if (v == null)
            v = inflater.inflate(R.layout.postazione_element, null);

        Postazione p =  getItem(position);

        TextView postazioneTV = v.findViewById(R.id.idPostazione);
        Button bloccoButton = v.findViewById(R.id.bloccoButton);
        Button sbloccoButton = v.findViewById(R.id.sbloccoButton);
        Button cancellaButton = v.findViewById(R.id.cancellaPosButton);

        postazioneTV.setText(p.getId());

        bloccoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("postazione",Postazione.toJson(p));
                i.setClass(ElencoPostazioniAdminActivity.getAppContext(), BloccoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ElencoPostazioniAdminActivity.getAppContext().startActivity(i);
            }
        });

        sbloccoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.cercaBlocchi(p.getId());
            }
        });
        return v;
    }
}
