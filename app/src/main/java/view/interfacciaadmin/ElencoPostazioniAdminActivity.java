package view.interfacciaadmin;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.unisalib.R;

public class ElencoPostazioniAdminActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_elenco_postazioni);
    }
}
