package hackthenorth.neighborcater;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rowandempster on 9/17/16.
 */

public class ChoosingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing_layout);

        ImageView foodImageView = (ImageView) this.findViewById(R.id.choosing_layout_food_image);
        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(ChoosingActivity.this, MapActivity.class);
                startActivity(mapIntent);
            }
        });
    }

}
