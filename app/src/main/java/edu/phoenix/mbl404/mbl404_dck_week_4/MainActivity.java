package edu.phoenix.mbl404.mbl404_dck_week_4;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Layout items (in order of appearance).
    Spinner citySelect;
    public static TextView dataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize first layout.

        setContentView(R.layout.activity_main);

        //Bind java variable layout items to layout xml items (in order of appearance).

        citySelect = (Spinner) findViewById(R.id.city_selector);
        dataDisplay = (TextView) findViewById(R.id.weather_display);

        //Bind action bar to variable and bind program Icon to the bar (pure visual/marketing).

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.sisyphus);

        //Call function to create and bind an adapter to the team spinners.

        citySpinner();

    }

    /* ====== FUNCTION ======
    citySpinner invokes the spinner adapter, sets dropdown options via array in
    values, dropdown layout, and initial position
    */

    public void citySpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_text);
        citySelect.setAdapter(adapter);
        citySelect.setSelection(0);
        citySelect.setOnItemSelectedListener(new CitySpinnerActivity());
    }

    /* ====== CLASS ======
    CitySpinnerActivity class allows for various behaviors to be monitored by the listener,
    specifically position selection data. Calls appropriate API on selection.
     */

    public class CitySpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(citySelect.getSelectedItemPosition() == 0) {
                dataDisplay.setText("Select a city.");
            } else {
                switch (citySelect.getSelectedItemPosition()){
                    case 1:
                        WeatherScrape norfolkScrape = new WeatherScrape("http://api.openweathermap.org/data/2.5/weather?id=4776222&units=imperial&APPID=66f59108428e8ff9d3ba0b5a36617c60");
                        norfolkScrape.execute();
                        break;
                    case 2:
                        WeatherScrape richmondScrape = new WeatherScrape("http://api.openweathermap.org/data/2.5/weather?id=4781708&units=imperial&APPID=66f59108428e8ff9d3ba0b5a36617c60");
                        richmondScrape.execute();
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}