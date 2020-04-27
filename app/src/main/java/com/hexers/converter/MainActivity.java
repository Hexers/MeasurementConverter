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
import android.widget.AdapterView;
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
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity
        implements OnEditorActionListener, OnClickListener, AdapterView.OnItemSelectedListener {

    // Measurement Converter
    // Define variables for the widgets
    private Button applyButton;
    private Button resetButton;

    private TextView ConversionTextView;
    private Spinner splitSpinner;

    // set up preferences
    private SharedPreferences prefs;
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets

        applyButton = (Button) findViewById(R.id.applyButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        ConversionTextView = (TextView) findViewById(R.id.ConversionTextView);
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);


        //set array adapter for a spinner
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);


        // Set the listeners for buttons & EditText's
        applyButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        // Set the listeners for Spinners
        splitSpinner.setSelection(0,false); // Sets default selection to null
        splitSpinner.setOnItemSelectedListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

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
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables

        // set the amount on its widget

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

            case R.id.resetButton:
                // Reloads MainActivity class
                startActivity(new Intent(MainActivity.this, MainActivity.class));

            case R.id.applyButton:
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.splitSpinner:

                if (position == 0) // Choose a selection
                {
                    parent.setClickable(false);
                }

                if (position == 1) // Miles to Kilometers
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, MileToKilo.class);
                    startActivity(intent);
                }

                if (position == 2) // Kilometers to Miles
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, KiloToMile.class);
                    startActivity(intent);
                }
                if (position == 3) //Inches to Centimeters
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, InchToCentimeter.class);
                    startActivity(intent);
                }
                if (position == 4) // Centimeters to Inches
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, CentimeterToInch.class);
                    startActivity(intent);
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Sometimes this can be empty
    }
}