package view.utenteview;


import androidx.preference.PreferenceManager;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.unisalib.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import model.prenotazionemanagement.Prenotazione;
import model.utentemanagement.Utente;
import utils.SwitchDate;
import view.libroview.DettagliLibroUtenteUnisaActivity;
import view.postazioneview.ElencoPostazioniUtenteActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PrenotazioneTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void prenotazioneTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("s.celentano16@studenti.unisa.it"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("s.cele"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button = onView(
                allOf(withId(R.id.svcPrenotazioneButton), withText("Prenota Posto"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        button.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction spinner = onView(
                allOf(withId(R.id.bibliotecaSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        spinner.perform(click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        checkedTextView.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.cercaButton), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                3),
                        isDisplayed()));
        button2.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction spinner2 = onView(
                allOf(withId(R.id.orarioSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        spinner2.perform(click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.confermaPrenotazioneButton), withText("Conferma"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                6),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(android.R.id.button1), withText("SI"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        button4.perform(scrollTo(), click());

        TimeUnit.SECONDS.sleep(3);

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(ElencoPostazioniUtenteActivity.getAppContext());
        Utente u=Utente.fromJson(userSession.getString("Utente",null));
        boolean trovato=false;
        for(Prenotazione pren:u.getPrenotazioni()){
            if(pren.getPostazione().getId().equals("A1")) {
                trovato=true;
            }
        }
        assertTrue(trovato);
    }

    @Test
    public void prenotazioneFallitaTest() throws InterruptedException {
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

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button = onView(
                allOf(withId(R.id.svcPrenotazioneButton), withText("Prenota Posto"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        button.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction spinner = onView(
                allOf(withId(R.id.bibliotecaSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        spinner.perform(click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        checkedTextView.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.cercaButton), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                3),
                        isDisplayed()));
        button2.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction spinner2 = onView(
                allOf(withId(R.id.orarioSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        spinner2.perform(click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.confermaPrenotazioneButton), withText("Conferma"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                6),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(android.R.id.button1), withText("SI"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        button4.perform(scrollTo(), click());

        TimeUnit.SECONDS.sleep(3);

        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(ElencoPostazioniUtenteActivity.getAppContext());
        Utente u=Utente.fromJson(userSession.getString("Utente",null));
        boolean trovato=false;
        for(Prenotazione pren:u.getPrenotazioni()){
            if(!(pren.getPostazione().getId().equals("A1") && SwitchDate.equalsDate(pren.getData(),new GregorianCalendar()))) {
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
