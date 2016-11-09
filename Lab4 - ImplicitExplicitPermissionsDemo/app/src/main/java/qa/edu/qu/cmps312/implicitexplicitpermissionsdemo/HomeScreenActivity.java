package qa.edu.qu.cmps312.implicitexplicitpermissionsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void openPlanetActivity(View view){
        Intent i = new Intent(this,PlanetsActivity.class);
        startActivity(i);
    }

    public void exit(View view){

        finish();
    }
}
