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

public class KiloToMile extends Activity
        implements OnEditorActionListener, OnClickListener, AdapterView.OnItemSelectedListener  {

    // Measurement Converter
    // Define variables for the widgets
    private Button resetButton;
    private Button applyButton;

    private TextView ConversionTextView;
    private Spinner splitSpinner;

    private TextView MileTextView;
    private TextView KilometersTextView;

    private TextView totalMiles;
    private EditText KilometersValueEditText;

    // define instance variables that should be saved
    private String milesVar = "";
    private String kiloVar = "";

    private float milesFloat = 1.6093f;
    private float kilometersFloat = 0.6214f;

    // set up preferences
    private SharedPreferences prefs;
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kilometers_to_miles);

        // Get references to the widgets
        resetButton = (Button) findViewById(R.id.resetButton);
        applyButton = (Button) findViewById(R.id.applyButton);

        ConversionTextView = (TextView) findViewById(R.id.ConversionTextView);
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);

        KilometersTextView = (TextView) findViewById(R.id.KilometersTextView);
        KilometersValueEditText = (EditText) findViewById(R.id.KilometersValueEditText);

        MileTextView = (TextView) findViewById(R.id.MilesTextView);
        totalMiles = (TextView) findViewById(R.id.totalMiles);

        //set array adapter for a spinner
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);

        // Set the listeners for buttons & EditText's
        resetButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        KilometersValueEditText.setOnEditorActionListener(this);

        // Set the listeners for Spinners
        splitSpinner.setSelection(2,false); // Sets default selection to null
        splitSpinner.setOnItemSelectedListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        calculateAndDisplay();
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
        editor.putString("KilometersValueEditText", kiloVar);
        editor.putFloat("milesFloat", milesFloat);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        kiloVar = savedValues.getString("KilometersValueEditText", "");
        milesFloat = savedValues.getFloat("milesFloat", 1.6093f);

        // set the amount on its widget
        KilometersValueEditText.setText(kiloVar);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay()
    {
        NumberFormat nf = NumberFormat.getInstance();
        // Get the Kilometers Variable from EditText
        kiloVar = KilometersValueEditText.getText().toString();
        float kilometersTotal;

        if (kiloVar.equals(""))
        {
            kilometersTotal = 0;
        }
        else
        {
            kilometersTotal = Float.parseFloat(kiloVar);
        }

        float kilometersFloat = 0.6214f;
        float milesTotal = 1.6093f;

        milesTotal = kilometersTotal * kilometersFloat;

        // Display with formatting

        totalMiles.setText(nf.format(milesTotal));
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
            calculateAndDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.resetButton:
                // Reloads MainActivity class
                startActivity(new Intent(KiloToMile.this, MainActivity.class));

            case R.id.applyButton:
                // Apply Button
                calculateAndDisplay();
                break;
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

                    Intent intent = new Intent(KiloToMile.this, MileToKilo.class);
                    startActivity(intent);
                }

                if (position == 2) // Kilometers to Miles
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(KiloToMile.this, KiloToMile.class);
                    startActivity(intent);
                }
                if (position == 3) //Inches to Centimeters
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(KiloToMile.this, InchToCentimeter.class);
                    startActivity(intent);
                }
                if (position == 4) // Centimeters to Inches
                {
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(KiloToMile.this, CentimeterToInch.class);
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