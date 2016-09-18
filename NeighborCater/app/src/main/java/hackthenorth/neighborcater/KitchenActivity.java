package hackthenorth.neighborcater;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hackthenorth.neighborcater.models.Kitchen;

public class KitchenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText priceAmount;
    private TextView textIndicator;

    private EditText foodName;
    private EditText foodDescription;
    private EditText kitchenName;
    private double lat;
    private double longi;
    private String address;
    private FloatingActionButton floatingActionButton;
    PlaceAutocompleteFragment autocompleteFragment;

    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        mDatabase = FirebaseDatabase.getInstance().getReference("kitchens");
        priceAmount = (EditText) findViewById(R.id.priceAmount);
        foodName = (EditText)findViewById(R.id.editTextFoodName);
        foodDescription = (EditText)findViewById(R.id.editTextFoodDescription);
        kitchenName = (EditText) findViewById(R.id.kitchenName);
        mSubmitButton = (Button) findViewById(R.id.submitButton);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.kitchen_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ordersIntent = new Intent(KitchenActivity.this, OrdersActivity.class);
                startActivity(ordersIntent);
            }
        });


//        getActionBar().hide();
//        getSupportActionBar().hide();





        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String foodNameData = foodName.getText().toString();
                String priceAmountData = priceAmount.getText().toString();
                String foodDescriptionData = foodDescription.getText().toString();
                String kitchenNameData = kitchenName.getText().toString();


                Kitchen kObj = new Kitchen();
                kObj.setFoodName(foodNameData);
                kObj.setPrice(priceAmountData);
                kObj.setFoodDescription(foodDescriptionData);
                kObj.setKitchenName(kitchenNameData);
                kObj.setAddress(address);
                kObj.setLatitude(lat);
                kObj.setLongitude(longi);
                kObj.setUser(FirebaseAuth.getInstance().getCurrentUser().getUid());

                mDatabase.push().setValue(kObj);

                clearEditTexts();


            }
        });




         autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.editTextLocation);
        autocompleteFragment.setHint("Search Your Location");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lat = place.getLatLng().latitude;
                longi = place.getLatLng().longitude;
                address = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });






    }

    private void clearEditTexts() {
        foodName.setText("");
        priceAmount.setText("");
        foodDescription.setText("");
        kitchenName.setText("");
        address = "";
        autocompleteFragment.setText("");
        Toast.makeText(getApplicationContext(), "Created Kitchen!", Toast.LENGTH_SHORT);
    }


}
