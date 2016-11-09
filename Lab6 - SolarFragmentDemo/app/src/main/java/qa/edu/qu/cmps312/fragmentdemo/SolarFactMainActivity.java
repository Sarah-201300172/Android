package qa.edu.qu.cmps312.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import qa.edu.qu.cmps312.explicitactivitydemo.R;

public class SolarFactMainActivity extends AppCompatActivity implements DisplayFactsInterface {


    public static final String PLANET = "PLANET";   //this TAG is used to pass a planet to other activity through the bundle
    final String TAG = SolarFactMainActivity.class.getSimpleName();
    boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solarfact_main);

    }


    //This method checks if the device is a tablet or a handset using a boolean declared in the dimens.xml resource file
    @Override
    public void displayFacts(Planet planet) {

        // TODO : Get the resource boolean "isTablet" that you have declared in the dimens.xml and assign it to the isTablet variable declared above

        isTablet = getResources().getBoolean(R.bool.isTablet);
        // TODO : Handle the Tablet Case : if the " isTablet" variable is "true" that means you are in a Tablet so make sure to add the dynamic fragment to the left PANE

        if (isTablet) {

            PlanetFactsFragment planetFactsFragment = PlanetFactsFragment.newInstance(planet); // new planetFragment

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); // get manager and begin transaction
            fragmentTransaction.replace(R.id.facts_fragment, planetFactsFragment); //replace the frame layout with the dynamic fragment
            fragmentTransaction.commit();

        } else { //if phone

            //just launch another activity and pass it the planet

            Intent i = new Intent(this, PlanetsFactsDescActivity.class);
            i.putExtra(PLANET,planet);
            startActivity(i);
        }
        /*
        STEPS
                A. You first need to create a PlanetFactsFragment instance using PlanetFactsFragment.newInstance(planet) factory method;
                B. Then you need to instantiate a FragmentManager object, And use that object to instantiate a FragmentTransaction object by using beginTransaction Method.
                C. Then add that fragment to the Framelayout using the fragmentTransaction. The framelayout ID is "facts_fragment"
                D. After you add the fragment then commit the transaction
        */


        /*  TODO : Handle the Phone/Hanset case :

         STEPS:
            *** If the " isTablet" variable is false that means you are in a phone/handset mode.
            *** So you need to launch an new activity using INTENT and pass the planet object to the PlanetsFactsDescActivity.

          Do the following:
                A. Create an Explicit Intent that launches the "PlanetsFactsDescActivity"
                B. Pass the planet object as an extra using the PLANET tag declared above. Like : "(PLANET, planet)"
                C. Start the Activity
         */

    }
}

