package presenter.utentepresenter;

import android.content.Context;

public interface UtentePresenter {
    void login(String email, String password);

    void logout(Context c);
}
