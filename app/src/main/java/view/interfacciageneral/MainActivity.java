package view.interfacciageneral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.unisalib.R;

import presenter.utentepresenter.UtentePresenter;

/*IP Daniele: 192.168.255.1
    IP Sabatino: 192.168.1.61
    IP Pasquale: 192.168.1.7
 */
public class MainActivity extends AppCompatActivity {


    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_login);
        context=getApplicationContext();

        EditText emailText = findViewById(R.id.emailText);
        EditText passwordText = findViewById(R.id.passwordText);
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                UtentePresenter up = new UtentePresenter();
                up.login(email, password);
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}