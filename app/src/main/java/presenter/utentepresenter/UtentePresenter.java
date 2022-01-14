package presenter.utentepresenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.utentemanagement.Utente;
import view.interfacciaadmin.HomeAdminActivity;
import view.interfacciageneral.MainActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;

public interface UtentePresenter {
    void login(String email, String password);

    void logout(Context c);
}
