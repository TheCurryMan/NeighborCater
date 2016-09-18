package hackthenorth.neighborcater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import hackthenorth.neighborcater.models.Kitchen;
import hackthenorth.neighborcater.models.User;

/**
 * Created by rowandempster on 9/17/16.
 */
public class DetailsActivity extends AppCompatActivity {

    Kitchen kitchen;
    User user;
    private DatabaseReference mDatabase;

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.child("users").child(kitchen.getUser()).getValue(User.class);
            populateTextFields();

            // ...
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            // ...
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details_layout);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(postListener);
        kitchen = (Kitchen) getIntent().getSerializableExtra("Kitchen");






    }

    private void populateTextFields() {
        ImageView foodImage = (ImageView) this.findViewById(R.id.food_details_image);
        TextView kitchenTitle = (TextView) this.findViewById(R.id.food_details_kitchen_title);
        TextView ownerName = (TextView) this.findViewById(R.id.food_details_kitchen_owner_name);
        TextView address = (TextView) this.findViewById(R.id.food_details_kitchen_address);
        TextView foodName = (TextView) this.findViewById(R.id.food_details_food_name);
        TextView foodDescription = (TextView) this.findViewById(R.id.food_details_food_description);
        TextView email = (TextView) this.findViewById(R.id.food_details_email);
        TextView phoneAndDistance = (TextView) this.findViewById(R.id.food_details_food_phone_number_and_distance);
        TextView price = (TextView) this.findViewById(R.id.food_details_price);


        kitchenTitle.setText(kitchen.getKitchenName());
        ownerName.setText(user.getName());
        address.setText(kitchen.getAddress());
        foodName.setText(kitchen.getFoodName());
        foodDescription.setText(kitchen.getFoodDescription());
        email.setText("Email: " + user.getEmail());
        phoneAndDistance.setText(user.getNumber() + " | " + getIntent().getSerializableExtra("Distance") + " km");
        price.setText("$"+kitchen.getPrice());
    }
}
