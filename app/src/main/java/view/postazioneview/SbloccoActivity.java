package view.postazioneview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import model.postazionemanagement.Periodo;
import model.postazionemanagement.Postazione;
import model.postazionemanagement.PostazioneAdapter;
import model.postazionemanagement.PostazioneBloccoAdapter;
import model.utentemanagement.Utente;
import presenter.FacadePresenter;

public class SbloccoActivity extends Activity {
    private FacadePresenter fp;
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.admin_sblocco_postazione);
        Postazione p=null;
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            p= Postazione.fromJson(userSession.getString("postazione", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        Button sblocca = findViewById(R.id.sbloccaButton);
        ListView lv = findViewById(R.id.listViewBlocco);
        if(p.isDisponibile()){
            sblocca.setVisibility(View.GONE);
            PostazioneBloccoAdapter postazioneBloccoAdapter = new PostazioneBloccoAdapter(this, R.layout.postazione_blocco_element, new ArrayList<Periodo>());
            lv.setAdapter(postazioneBloccoAdapter);

            for (Periodo periodo: p.getBlocchi()) {
                System.out.println(p.getId()+"ok");
                postazioneBloccoAdapter.add(periodo);
            }
        }
        else {
            sblocca.setVisibility(View.VISIBLE);
            sblocca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
