package view.utenteview;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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

import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SbloccoIndeterminatoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sbloccoIndeterminatoTest() throws InterruptedException {
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

        TimeUnit.SECONDS.sleep(3);

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
                allOf(withId(R.id.sbloccoButton), withText("Sblocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        button4.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button5 = onView(
                allOf(withId(R.id.sbloccaButton), withText("SBLOCCA"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button7 = onView(
                allOf(withId(R.id.sbloccaButton), withText("Sblocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        button7.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button8 = onView(
                allOf(withId(R.id.sbloccoButton), withText("Sblocca"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        button8.perform(click());

        TimeUnit.SECONDS.sleep(3);

        ViewInteraction button6 = onView(
                allOf(withId(R.id.sbloccaButton), withText("SBLOCCA"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button6.check(doesNotExist());
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
