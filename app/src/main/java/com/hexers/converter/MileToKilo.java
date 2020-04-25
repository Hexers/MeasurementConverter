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

public class MileToKilo extends Activity
        implements OnEditorActionListener, OnClickListener, AdapterView.OnItemSelectedListener  {

    // Measurement Converter
    // Define variables for the widgets
    private Button newGameButton;

    private TextView ConversionTextView;
    private Spinner splitSpinner;

    private TextView MileTextView;
    private TextView KilometersTextView;

    // New
    private TextView totalKilometers;

    private EditText MilesValueEditText;


    // define instance variables that should be saved
    private String milesVar = "";
    private String kiloVar = "";

    private String mileString = "";
    private String kilometerString = "";

    //private String billAmountString = "";
    //private float tipPercent = .15f;

    private float milesFloat = 1.6093f;
    private float kilometersFloat = 0.6214f;


    // set up preferences
    private SharedPreferences prefs;
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miles_to_kilometers);

        // get references to the widgets

        newGameButton = (Button) findViewById(R.id.newGameButton);

        ConversionTextView = (TextView) findViewById(R.id.ConversionTextView);
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);

        MileTextView = (TextView) findViewById(R.id.MilesTextView);
        MilesValueEditText = (EditText) findViewById(R.id.MilesValueEditText);

        KilometersTextView = (TextView) findViewById(R.id.KilometersTextView);
        totalKilometers = (TextView) findViewById(R.id.totalKilometers);

        //set array adapter for a spinner
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);


        // Set the listeners for buttons & EditText's
        newGameButton.setOnClickListener(this);
        MilesValueEditText.setOnEditorActionListener(this);

        // Set the listeners for Spinners
        splitSpinner.setSelection(1,false); // Sets default selection to null
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
        //editor.putString("billAmountString", billAmountString);
        //editor.putFloat("tipPercent", tipPercent);
        editor.putString("MilesValueEditText", milesVar);
        editor.putFloat("kilometersFloat", kilometersFloat);

        //editor.putFloat("totalKilometers", totalKilometers);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        milesVar = savedValues.getString("MilesValueEditText", "");
        kilometersFloat = savedValues.getFloat("kilometersFloat", 0.6214f);

        // set the amount on its widget
        MilesValueEditText.setText(milesVar);

        // calculate and display
        calculateAndDisplay();

    }

    public void calculateAndDisplay()
    {
        NumberFormat nf = NumberFormat.getInstance();
        // Get the Miles Variable from EditText
        milesVar = MilesValueEditText.getText().toString();
        float milesTotal;

        if (milesVar.equals(""))
        {
            milesTotal = 0;
        }
        else
        {
            milesTotal = Float.parseFloat(milesVar);
            //MilesValueEditText.setText(milesVar);
        }

        float kilometersTotal = 0.6214f;
        float milesFloat = 1.6093f;

        kilometersTotal = milesTotal * milesFloat; // No.....

        // Display with formatting

        totalKilometers.setText(nf.format(kilometersTotal));

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

            case R.id.newGameButton:
                // Reloads MainActivity class
                startActivity(new Intent(MileToKilo.this, MainActivity.class));
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
        /*
            Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();

         */

        switch (parent.getId())
        {
            case R.id.splitSpinner:

                if (position == 0) // Choose a selection
                {
                    parent.setClickable(false);
                }

                if (position == 1) // Miles to Kilometers
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MileToKilo.this, MileToKilo.class);
                    startActivity(intent);
                }

                if (position == 2) // Kilometers to Miles
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MileToKilo.this, KiloToMile.class);
                    startActivity(intent);
                }
                if (position == 3) //Inches to Centimeters
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MileToKilo.this, InchToCentimeter.class);
                    startActivity(intent);
                }
                if (position == 4) // Centimeters to Inches
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MileToKilo.this, CentimeterToInch.class);
                    startActivity(intent);
                }
                //Do something
                //Toast.makeText(this, "Conversion Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                /*
                parent.setSelection(0); // Choose a Selection
                parent.setSelection(1); // Miles to Kilometers
                parent.setSelection(2); // Kilometers to Miles
                parent.setSelection(3); // Inches to Centimeters
                parent.setSelection(4); // Centimeters to Inches

                 */
                /*
                Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                break;
                 */

                /*
                        MilesValueEditText.setText(milesToKiloVar);
                        KilometersValueEditText.setText(kiloToMileVar);
                        InchValueEditText.setText(inchToCentVar);
                        CentValueEditText.setText(centToInchVar);
                */

                /*
            case R.id.splitSpinner:
                //Do another thing
                Toast.makeText(this, "Option Selected: " + parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

                 */
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Sometimes this can be empty
    }
}