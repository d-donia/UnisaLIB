package view.postazioneview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.unisalib.R;

import java.util.GregorianCalendar;

import model.postazionemanagement.Postazione;
import presenter.FacadePresenter;

public class BloccoActivity extends Activity {
    public final long SECONDINMILLS = 1000;
    public final long WEEKINMILLS = 604800000;
    private FacadePresenter fp;
    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_blocco_postazione);
        fp=new FacadePresenter();
        context=getApplicationContext();
        Postazione p=Postazione.fromJson(getIntent().getStringExtra("postazione"));
        TextView postazioneTV=findViewById(R.id.postazioneTV);
        postazioneTV.setText("Postazione "+p.getId());
        RadioButton bloccoIRB=findViewById(R.id.radioIndet);
        RadioButton bloccoDRB=findViewById(R.id.radioDet);
        DatePicker dp=findViewById(R.id.datePickerBlocco);
        dp.setMinDate(System.currentTimeMillis() - SECONDINMILLS);
        dp.setMaxDate(System.currentTimeMillis() + WEEKINMILLS);
        EditText edOraInizio=findViewById(R.id.oraInizioBlocco);
        EditText edOraFine=findViewById(R.id.oraFineBlocco);
        Button bBlocco=findViewById(R.id.buttonBlocco);
        bloccoIRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dp.setVisibility(View.GONE);
                    edOraFine.setVisibility(View.GONE);
                    edOraInizio.setVisibility(View.GONE);
                }
            }
        });
        bloccoDRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dp.setVisibility(View.VISIBLE);
                    edOraFine.setVisibility(View.VISIBLE);
                    edOraInizio.setVisibility(View.VISIBLE);
                }
            }
        });
        bBlocco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bloccoDRB.isChecked()) {
                    try {
                        int oraFine = Integer.parseInt(edOraFine.getText().toString());
                        int oraInizio = Integer.parseInt(edOraInizio.getText().toString());
                        GregorianCalendar date = new GregorianCalendar(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        fp.bloccoDeterminato(p,date,oraInizio,oraFine);
                    }catch(Exception e) {
                        Toast.makeText(getApplicationContext(), "Inserire orari corretti", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    if(bloccoIRB.isChecked())
                        fp.bloccoIndeterminato(p.getId());
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}
