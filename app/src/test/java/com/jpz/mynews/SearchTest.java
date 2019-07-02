package com.jpz.mynews;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jpz.mynews.controllers.activities.SearchActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SearchTest {

    private SearchActivity searchActivity;
    private EditText editText;
    private CheckBox checkBox;
    private Button button;

    @Before
    public void setUp() {
        searchActivity = Robolectric.buildActivity(SearchActivity.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();

        Context context = RuntimeEnvironment.systemContext;
        editText = new EditText(context);
        checkBox = new CheckBox(context);
        button = new Button(context);
    }

    @Test
    public void checkMainActivityNotNull(){
        assertNotNull(searchActivity);
    }

    @Test
    public void ensureSearchButtonIsEnabled() {
        // Check if searchButton is enabled when type text and check a box
        editText.findViewById(R.id.base_search_fragment_query);
        editText.setText("");
        checkBox.findViewById(R.id.base_search_fragment_checkbox_one);
        checkBox.performClick();
        assertTrue(button.isEnabled());
    }
}