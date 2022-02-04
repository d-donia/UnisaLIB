package view.utenteview;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.preference.PreferenceManager;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.unisalib.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;

import model.libromanagement.Libro;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import utils.SwitchDate;
import view.libroview.DettagliLibroUtenteUnisaActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AggiungiInteresseTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void aggiungiInteresseTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("p.somma11@studenti.unisa.it"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("p.somma"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.svcPrestitoButton), withText("Prestito Libri"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        button.perform(click());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.mylistview),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)))
                .atPosition(6);
        linearLayout.perform(click());

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.libriLV),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(0);
        relativeLayout.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.interesseButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        imageButton.perform(click());

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(DettagliLibroUtenteUnisaActivity.getAppContext());
        Utente u=Utente.fromJson(userSession.getString("Utente",null));
        boolean trovato=false;
        for(Libro l:u.getInteressi()){
            if(l.getIsbn().equals("0002005018")) {
                trovato=true;
            }
        }
        assertTrue(trovato);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
