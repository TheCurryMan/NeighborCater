package hackthenorth.neighborcater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChoosingActivity extends AppCompatActivity {

    private ImageView foodImage;
    private ImageView storeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing);

        foodImage = (ImageView)findViewById(R.id.imageView);
        storeImage = (ImageView)findViewById(R.id.imageView2);

        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapActivity = new Intent(ChoosingActivity.this, MapActivity.class);
                startActivity(MapActivity);
            }
        });

        storeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kitchenActivity = new Intent(ChoosingActivity.this, KitchenActivity.class);
                startActivity(kitchenActivity);
            }
        });

    }
}
