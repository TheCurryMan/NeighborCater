package hackthenorth.neighborcater;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rowandempster on 9/17/16.
 */

public class ChoosingActivity extends AppCompatActivity {

    @BindView(R.id.choosing_layout_food_image)
    ImageView foodImageView;
    @BindView(R.id.choosing_layout_store_image)
    ImageView storeImageView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.choosing_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.choosing_layout_food_image)
    void goToBuyerHome(){
        Intent mapIntent = new Intent(ChoosingActivity.this, MapActivity.class);
        ChoosingActivity.this.startActivity(mapIntent);
    }
    @OnClick(R.id.choosing_layout_store_image)
    void goToStoreHome(){

    }
}
