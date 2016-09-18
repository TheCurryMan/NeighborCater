package hackthenorth.neighborcater;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;

public class MyTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

//        getSupportActionBar().hide();

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, KitchenActivity.class);
        spec = tabHost.newTabSpec("Create Post").setIndicator("Create Post")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, OrdersActivity.class);
        spec = tabHost.newTabSpec("View Orders").setIndicator("View Orders")
                .setContent(intent);
        tabHost.addTab(spec);


    }


}
