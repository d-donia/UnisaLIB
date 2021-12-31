package presenter.utentepresenter;

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

public class UtentePresenter{
    static final String GenericURL="http://192.168.1.7:8080/UnisaLIBServer/UtentePresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void login(String email, String password) {
        String MYURL=GenericURL+"/login";
        RequestParams params;
        params=new RequestParams();
        params.put("email",email);
        params.put("pass",password);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MainActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
