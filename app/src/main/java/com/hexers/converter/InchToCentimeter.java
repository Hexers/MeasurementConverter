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

public class InchToCentimeter extends Activity
        implements OnEditorActionListener, OnClickListener, AdapterView.OnItemSelectedListener  {

    // Measurement Converter
    // Define variables for the widgets
    private Button newGameButton;

    private TextView ConversionTextView;
    private Spinner splitSpinner;

    private TextView MileTextView;
    private TextView KilometersTextView;
    private TextView InchTextView;
    private TextView CentTextView;

    private EditText MilesValueEditText;
    private EditText KilometersValueEditText;
    private EditText InchValueEditText;
    private EditText CentValueEditText;


    // define instance variables that should be saved
    private String milesToKiloVar = "";
    private String kiloToMileVar = "";
    private String inchToCentVar = "";
    private String centToInchVar = "";

    private String mileString = "";
    private String kilometerString = "";
    private String inchString = "";
    private String centimeterString = "";

    //private String billAmountString = "";
    //private float tipPercent = .15f;

    // set up preferences
    private SharedPreferences prefs;
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inches_to_centimeters);

        // get references to the widgets

        newGameButton = (Button) findViewById(R.id.newGameButton);

        ConversionTextView = (TextView) findViewById(R.id.ConversionTextView);
        splitSpinner = (Spinner) findViewById(R.id.splitSpinner);


        MileTextView = (TextView) findViewById(R.id.MilesTextView);
        KilometersTextView = (TextView) findViewById(R.id.KilometersTextView);
        InchTextView = (TextView) findViewById(R.id.InchTextView);
        CentTextView = (TextView) findViewById(R.id.CentTextView);

        MilesValueEditText = (EditText) findViewById(R.id.MilesValueEditText);
        KilometersValueEditText = (EditText) findViewById(R.id.KilometersValueEditText);
        InchValueEditText = (EditText) findViewById(R.id.InchValueEditText);
        CentValueEditText = (EditText) findViewById(R.id.CentValueEditText);

        //set array adapter for a spinner
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);


        // Set the listeners for buttons & EditText's
        newGameButton.setOnClickListener(this);
        MilesValueEditText.setOnEditorActionListener(this);
        KilometersValueEditText.setOnEditorActionListener(this);
        InchValueEditText.setOnEditorActionListener(this);
        CentValueEditText.setOnEditorActionListener(this);

        // Set the listeners for Spinners
        splitSpinner.setSelection(3,false); // Sets default selection to null
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
        //editor.putString("billAmountString", billAmountString);
        //editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        milesToKiloVar = savedValues.getString("MilesValueEditText", "");
        kiloToMileVar = savedValues.getString("KilometersValueEditText", "");
        inchToCentVar = savedValues.getString("InchValueEditText", "");
        centToInchVar = savedValues.getString("CentValueEditText", "");

        // set the amount on its widget
        MilesValueEditText.setText(milesToKiloVar);
        KilometersValueEditText.setText(kiloToMileVar);
        InchValueEditText.setText(inchToCentVar);
        CentValueEditText.setText(centToInchVar);

        // get name for player one
        //String playerOneName = prefs.getString("player_one_name", "");
        //namePlayerOneTextView.setText(playerOneName);

        // get name for player two
        //String playerTwoName = prefs.getString("player_two_name", "");
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
            ConversionTextView.setVisibility(View.VISIBLE);

            MileTextView.setVisibility(View.VISIBLE);
            MilesValueEditText.setVisibility(View.VISIBLE);

            KilometersTextView.setVisibility(View.VISIBLE);
            KilometersValueEditText.setVisibility(View.VISIBLE);

            InchTextView.setVisibility(View.VISIBLE);
            InchValueEditText.setVisibility(View.VISIBLE);

            CentTextView.setVisibility(View.VISIBLE);
            CentValueEditText.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(InchToCentimeter.this, MainActivity.class));
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

                    Intent intent = new Intent(InchToCentimeter.this, MileToKilo.class);
                    startActivity(intent);
                }

                if (position == 2) // Kilometers to Miles
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(InchToCentimeter.this, KiloToMile.class);
                    startActivity(intent);
                }
                if (position == 3) //Inches to Centimeters
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(InchToCentimeter.this, InchToCentimeter.class);
                    startActivity(intent);
                }
                if (position == 4) // Centimeters to Inches
                {
                    //Do something
                    Toast.makeText(parent.getContext(), "Conversion Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(InchToCentimeter.this, CentimeterToInch.class);
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