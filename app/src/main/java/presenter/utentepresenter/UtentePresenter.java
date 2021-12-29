package presenter.utentepresenter;

import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.utentemanagement.Utente;
import view.interfacciageneral.MainActivity;
import view.interfacciautente.HomeActivity;

public class UtentePresenter{
    static final String GenericURL="http://192.168.1.7:8080/UnisaLIBServer/UtentePresenter";

    public void login(String email, String password) {
        String MYURL=GenericURL+"/login";

        RequestParams params;
        AsyncHttpClient client;
        params=new RequestParams();
        params.put("email",email);
        params.put("pass",password);
        client=new AsyncHttpClient();
        client.post(MYURL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Utente u= null;
                try {
                    u = Utente.fromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i=new Intent();
                i.setClass(MainActivity.getAppContext(), HomeActivity.class);
                i.putExtra("Utente",u);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.getAppContext().startActivity(i);

                System.out.println(u.getEmail());
                System.out.println(u.getPassword());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MainActivity.getAppContext(), responseString, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
