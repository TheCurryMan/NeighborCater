package hackthenorth.neighborcater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import hackthenorth.neighborcater.models.User;
import hackthenorth.neighborcater.models.UserOrder;

/**
 * Created by rowandempster on 9/18/16.
 */

public class OrdersActivity extends AppCompatActivity {

    LinearLayout container;

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Iterator<DataSnapshot> iterator = dataSnapshot.child("users").child(user.getUid()).child("orders").getChildren().iterator();
            populateCells(iterator);
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            // ...
        }
    };

    private void populateCells(Iterator<DataSnapshot> iterator) {
        while(iterator.hasNext()){
            UserOrder order = iterator.next().getValue(UserOrder.class);
            View orderView = View.inflate(getApplicationContext(), R.layout.order_cell, null);
            ((TextView)(orderView.findViewById(R.id.order_cell_buyer_name))).setText(order.getName());
            ((TextView)(orderView.findViewById(R.id.order_cell_email))).setText(order.getEmail());
            ((TextView)(orderView.findViewById(R.id.order_cell_food_name))).setText(order.getFoodName());
            ((TextView)(orderView.findViewById(R.id.order_cell_phone_numbers))).setText(order.getNumber());

            container.addView(orderView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_layout);
        FirebaseDatabase.getInstance().getReference().addValueEventListener(postListener);
        container = (LinearLayout) this.findViewById(R.id.orders_container);
        getSupportActionBar().hide();
    }
}
