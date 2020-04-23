package com.hexers.converter;

import java.text.NumberFormat;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.BackgroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.w3c.dom.Text;

public class MileToKilo extends Activity
        implements OnEditorActionListener, OnClickListener {

    // Measurement Converter
    // Define variables for the widgets
    private TextView ConversionTextView;
    private TextView MileTextView;
    private TextView KilometersTextView;
    private EditText MilesValueEditText;
    private EditText KilometersValueEditText;
    private Spinner splitSpinner;

    // define instance variables that should be saved


    // set up preferences
    private SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miles_to_kilometers);

        // get references to the widgets
        ConversionTextView = (TextView) findViewById(R.id.ConversionTextView);
        MileTextView = (TextView) findViewById(R.id.MilesTextView);
        KilometersTextView = (TextView) findViewById(R.id.KilometersTextView);
        MilesValueEditText = (EditText) findViewById(R.id.MilesValueEditText);
        KilometersTextView = (EditText) findViewById(R.id.KilometersValueEditText);
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);

        //set array adapter for a spinner
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);


        // set the listeners
        //newGameButton.setOnClickListener(this);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = prefs.edit();
        //editor.putString("billAmountString", billAmountString);
        //editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get name for player one
        String playerOneName = prefs.getString("player_one_name", "");
        //namePlayerOneTextView.setText(playerOneName);

        // get name for player two
        String playerTwoName = prefs.getString("player_two_name", "");
        //namePlayerTwoTextView.setText(playerTwoName);

    }

    public void calculateAnDisplay()
    {
        // Miles to Kilometers = 1.6093
        double oneMile = 1.6093; // to km
        // Kilometers to Miles = 0.6214
        double oneKilometer = 0.6214; // to miles
        // Inches to Centimeters = 2.54
        double oneInch = 2.54; // to centimeters
        // Centimeters to Inches = 0.3937
        double oneCentimeter = 0.3937; // to inches

        //split amount and show / hide split amount variable
        int splitPosition = splitSpinner.getSelectedItemPosition();
        int split = splitPosition + 1;
        float perPersonAmount = 0;
        if (split == 1)
        {
            //perPersonLabel.setVisibility(View.GONE);
            //perPersonTextView.setVisibility(View.GONE);
        }
        else
        {
            //perPersonAmount = totalAmount / split;
            //perPersonLabel.setVisibility(View.VISIBLE);
            //perPersonTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int keyCode = -1;
        if (event != null) {
            keyCode = event.getKeyCode();
        }
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_ENTER) {
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.newGameButton:
                // Reloads MainActivity class
                startActivity(new Intent(MileToKilo.this, MileToKilo.class));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}