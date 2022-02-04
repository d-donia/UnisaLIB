package view.utenteview;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.unisalib.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BloccoIndeterminatoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void bloccoIndeterminatoTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("admin@unisa.it"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("AdminAD1?"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        TimeUnit.SECONDS.sleep(4);

        ViewInteraction button = onView(
                allOf(withId(R.id.mgmtPrenotazioneButton), withText("Gestione Posti"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        button.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button2 = onView(
                allOf(withId(R.id.modificaPostButton), withText("Modifica Postazioni"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        button2.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction spinner = onView(
                allOf(withId(R.id.zonaSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                3),
                        isDisplayed()));
        spinner.perform(click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        checkedTextView.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.cercaButtonAD), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                4),
                        isDisplayed()));
        button3.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button4 = onView(
                allOf(withId(R.id.bloccoButton), withText("Blocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        button4.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.radioIndet), withText("Blocco Indeterminato"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0)));
        radioButton.perform(scrollTo(), click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.buttonBlocco), withText("Blocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        button5.perform(scrollTo(), click());

        pressBack();

        ViewInteraction button6 = onView(
                allOf(withId(R.id.sbloccoButton), withText("Sblocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        button6.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button7 = onView(
                allOf(withId(R.id.sbloccaButton), withText("SBLOCCA"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));
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
