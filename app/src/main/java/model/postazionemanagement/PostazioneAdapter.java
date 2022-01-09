package model.postazionemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.unisalib.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.prestitomanagement.Prestito;
import presenter.FacadePresenter;

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

        return v;
    }
}
