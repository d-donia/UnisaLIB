package view.interfacciaadmin;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.unisalib.R;

import model.postazionemanagement.Postazione;

public class BloccoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_blocco_postazione);
        Postazione p=Postazione.fromJson(getIntent().getStringExtra("postazione"));
    }
}
