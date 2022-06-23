package ar.edu.itba.hci_app.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ar.edu.itba.hci_app.R;


public class SettingsActivity extends AppCompatActivity {
    TextView messageView, messageT;
    Button btnEspanol, btnEnglish;
    Context context;
    Resources resources;
    UiModeManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_activity_settings);
        }

        manager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        // referencing the text and button views
        messageView = (TextView) findViewById(R.id.textView);
        messageT = (TextView) findViewById(R.id.textView3);
        btnEspanol = findViewById(R.id.btnEspanol);
        btnEnglish = findViewById(R.id.btnEnglish);

        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                resources = context.getResources();
                messageView.setText(resources.getString(R.string.title_activity_settings));
                messageT.setText(resources.getString(R.string.languages));
            }
        });

        btnEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = LocaleHelper.setLocale(SettingsActivity.this, "es");
                resources = context.getResources();
                messageView.setText(resources.getString(R.string.title_activity_settings));
                messageT.setText(resources.getString(R.string.languages));
            }
        });

    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void NightModeOn (View view) {
        manager.setNightMode(manager.MODE_NIGHT_YES);
    }

    public void NightModeOff (View view) {
        manager.setNightMode(manager.MODE_NIGHT_NO);
    }

}