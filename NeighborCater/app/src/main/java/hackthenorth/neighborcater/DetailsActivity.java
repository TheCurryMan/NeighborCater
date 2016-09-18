package hackthenorth.neighborcater;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import hackthenorth.neighborcater.models.Kitchen;
import hackthenorth.neighborcater.models.User;
import hackthenorth.neighborcater.models.XEResponse;
import hackthenorth.neighborcater.models.XETo;
import hackthenorth.neighborcater.networking.FoodService;
import hackthenorth.neighborcater.networking.ServiceGenerator;
import hackthenorth.neighborcater.utils.RoundingUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by rowandempster on 9/17/16.
 */
public class DetailsActivity extends AppCompatActivity {

    Kitchen kitchen;
    User user;
    User currentUser;
    private DatabaseReference mDatabase;
    Retrofit retrofit;
    TextView placeOrderButton;

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.child("users").child(kitchen.getUser()).getValue(User.class);
            populateTextFields();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            currentUser = dataSnapshot.child("users").child(user.getUid()).getValue(User.class);
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

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://xecdapi.xe.com/v1/convert_from.json/")
                .build();

        placeOrderButton = (TextView) this.findViewById(R.id.food_details_buy_now);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> orderToPost = new HashMap<String, Object>();

                orderToPost.put("foodName", kitchen.getFoodName());
                orderToPost.put("email", currentUser.getEmail());
                orderToPost.put("number", currentUser.getNumber());
                orderToPost.put("name", currentUser.getName());

                mDatabase.child("users").child(kitchen.getUser()).child("orders").push().updateChildren(orderToPost);
            }
        });



    }

    private void populateTextFields() {
        TextView kitchenTitle = (TextView) this.findViewById(R.id.food_details_kitchen_title);
        TextView ownerName = (TextView) this.findViewById(R.id.food_details_kitchen_owner_name);
        TextView address = (TextView) this.findViewById(R.id.food_details_kitchen_address);
        TextView foodName = (TextView) this.findViewById(R.id.food_details_food_name);
        TextView foodDescription = (TextView) this.findViewById(R.id.food_details_food_description);
        TextView email = (TextView) this.findViewById(R.id.food_details_email);
        TextView phoneAndDistance = (TextView) this.findViewById(R.id.food_details_food_phone_number_and_distance);
        TextView price = (TextView) this.findViewById(R.id.food_details_price);

        if(kitchen.getKitchenName()!=null) {
            kitchenTitle.setText(kitchen.getKitchenName());
        }
        if(user.getName()!=null) {
            ownerName.setText(user.getName());
        }
        if(kitchen.getAddress()!=null) {
            address.setText(kitchen.getAddress());
        }
        if(kitchen.getFoodName()!=null) {
            foodName.setText(kitchen.getFoodName());
        }
        if(kitchen.getFoodDescription()!=null) {
            foodDescription.setText(kitchen.getFoodDescription());
        }
        if(user.getEmail()!=null) {
            email.setText("Email: " + user.getEmail());
        }
        if(user.getNumber()!=null) {
            phoneAndDistance.setText(user.getNumber() + " | " + getIntent().getSerializableExtra("Distance") + " km");
        }
        if(kitchen.getPrice()!=null) {
            price.setText("$" + kitchen.getPrice());
        }
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodService service = ServiceGenerator.createService(FoodService.class, "hackthenorth012", "Waterloo3043");

                Call<XEResponse> xeResponseObservable = service.getXETest("USD,EUR,JPY,GBP,AUD,CAD,CHF,CNY,MXC,INR", "CAD", kitchen.getPrice());
                xeResponseObservable.enqueue(new Callback<XEResponse>() {
                    @Override
                    public void onResponse(Call<XEResponse> call, Response<XEResponse> response) {
                        populateCurrency((XEResponse)response.body());
                    }

                    @Override
                    public void onFailure(Call<XEResponse> call, Throwable t) {
                        Log.d("asdf", t.getMessage());
                    }
                });

            }
        });
    }

    private void populateCurrency(final XEResponse xeResponse) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout xeContainer = (LinearLayout) DetailsActivity.this.findViewById(R.id.xe_container);
                xeContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setVisibility(View.GONE);
                    }
                });
                xeContainer.removeAllViews();
                XETo[] toArray = xeResponse.getTo();
                for (XETo xeTo : toArray){
                    View xeView = getLayoutInflater().inflate(R.layout.xe_cell, null);
                    ((TextView)(xeView.findViewById(R.id.xe_cell_currency))).setText(xeTo.getQuotecurrency());
                    double amount = Double.valueOf(xeTo.getMid());
                    ((TextView)(xeView.findViewById(R.id.xe_cell_amount))).setText(RoundingUtils.roundDoubleToTwoDecimals(amount));
                    xeContainer.addView(xeView);
                }
                xeContainer.setVisibility(View.VISIBLE);
            }
        });
    }


}
