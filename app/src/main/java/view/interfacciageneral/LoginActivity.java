package view.interfacciageneral;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unisalib.R;

import presenter.utentepresenter.UtentePresenter;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_login);
    }

    public void onLoginClicked(View v){
        EditText emailText = new EditText(getApplicationContext());
        emailText.findViewById(R.id.emailText);
        EditText passwordText = new EditText(getApplicationContext());
        passwordText.findViewById(R.id.passwordText);
        Button login = new Button(getApplicationContext());
        login.findViewById(R.id.loginButton);
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
}