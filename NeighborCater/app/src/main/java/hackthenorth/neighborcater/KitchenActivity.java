package hackthenorth.neighborcater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hackthenorth.neighborcater.models.Kitchen;

public class KitchenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private SeekBar seekBar;
    private TextView textIndicator;

    private EditText foodName;
    private EditText foodDescription;
    private EditText kitchenName;
    private EditText location;


    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        mDatabase = FirebaseDatabase.getInstance().getReference("kitchens");
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        foodName = (EditText)findViewById(R.id.editTextFoodName);
        foodDescription = (EditText)findViewById(R.id.editTextFoodDescription);
        kitchenName = (EditText) findViewById(R.id.kitchenName);
        location = (EditText)findViewById(R.id.editTextLocation);
        mSubmitButton = (Button) findViewById(R.id.submitButton);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBar.getProgress();
                textIndicator.setText("Covered: " + progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        textIndicator = (TextView)findViewById(R.id.textView4);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String foodNameData = foodName.getText().toString();
                String seekBarData = String.valueOf(seekBar.getProgress());
                String foodDescriptionData = foodDescription.getText().toString();
                String kitchenNameData = kitchenName.getText().toString();
                String locationData = location.getText().toString();

                String lat = "30";
                String longt = "30";

                Kitchen kObj = new Kitchen();
                kObj.setFoodName(foodNameData);
                kObj.setPrice(seekBarData);
                kObj.setFoodDescription(foodDescriptionData);
                kObj.setKitchenName(kitchenNameData);
                kObj.setAddress(locationData);
                kObj.setLatitude(lat);
                kObj.setLongitude(longt);
                kObj.setUser("random string that we don't know where to extract from.");

                mDatabase.push().setValue(kObj);


            }
        });











    }


}
