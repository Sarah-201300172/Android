package qa.edu.qu.cmps312.implicitexplicitpermissionsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PlanetsActivity extends Activity {

    public static final String PLANET_INDEX = "PLANET_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planets);
    }

    public void displayFacts(View view){

        int planetIndex = 0;


        switch (view.getId()){
            case R.id.mercury:
                planetIndex=0;
                break;
            case R.id.venus:
                planetIndex=1;
                break;
            case R.id.earth:
                planetIndex=2;
                break;
            case R.id.mars:
                planetIndex=3;
                break;
            case R.id.jupiter:
                planetIndex=4;
                break;
            case R.id.saturn:
                planetIndex=5;
                break;
            case R.id.uranus:
                planetIndex=6;
                break;
            case R.id.neptune:
                planetIndex=7;
                break;

        }

        Intent i = new Intent(this, PlanetFactsActivity.class);
        i.putExtra(PLANET_INDEX,planetIndex);
        startActivity(i);
    }
}
