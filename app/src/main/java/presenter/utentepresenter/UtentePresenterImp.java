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

public class UtentePresenterImp implements UtentePresenter{

    static final String GenericURL="http://192.168.1.5:8080/UnisaLIBServer/UtentePresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void login(String email, String password) {
        String MYURL=GenericURL+"/login";
        RequestParams params;
        params=new RequestParams();
        params.put("email",email);
        params.put("pass",password);
        System.out.println(MYURL);
        client.post(MYURL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Utente u= null;
                try {
                    u = Utente.fromJson(response);

                    //Salvataggio utente loggato in shared preferences come oggetto json
                    SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
                    SharedPreferences.Editor editor = userSession.edit();
                    editor.putString("Utente", Utente.toJson(u));
                    editor.commit();
                    Intent i=new Intent();
                    if(u.isAdmin()) {
                        i.setClass(MainActivity.getAppContext(), HomeAdminActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.getAppContext().startActivity(i);
                    }
                    else{
                        i.setClass(MainActivity.getAppContext(), HomeUtenteUnisaActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.getAppContext().startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MainActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MainActivity.getAppContext(), "Problemi di connessione", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logout(Context c) {
        //Eliminazione utente da shared preferences
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = userSession.edit();
        editor.remove("Utente").commit();

        //Creazione intent e reindirizzamento alla pagina di login
        Intent i=new Intent();
        i.setClass(c, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);
    }
}
