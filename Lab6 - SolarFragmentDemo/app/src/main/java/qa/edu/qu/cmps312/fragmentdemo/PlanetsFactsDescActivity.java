package qa.edu.qu.cmps312.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import qa.edu.qu.cmps312.explicitactivitydemo.R;


public class PlanetsFactsDescActivity  extends AppCompatActivity {

    Planet planet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_facts_layout);


        //This will get you the planets passed when the listview item is clicked
        planet =  getIntent().getParcelableExtra(SolarFactMainActivity.PLANET);


        /*

        TODO : Create a dynamic PlanetFactsFragment that displays the facts about the planet and add that fragment to the "FrameLayout"
        that you declared inside the "activity_planet_facts_layout" with the ID of  "facts_fragment",

        This dynamic fragment will be used to display the facts when the user presses on a planet from the list
        STEPS
               A. You first need to create a PlanetFactsFragment instance using PlanetFactsFragment.newInstance(planet) factory method;
               B. You need to instantiate a FragmentManager object, And use that object to instantiate a FragmentTransaction object.
               C. Then add that fragment to the Framelayout using the fragmentTransaction
               D. After you add the fragment then commit the transaction
        */

        PlanetFactsFragment planetFactsFragment = PlanetFactsFragment.newInstance(planet); // new planetFragment

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); // get manager and begin transaction
        fragmentTransaction.replace(R.id.facts_fragment, planetFactsFragment); //replace the frame layout with the dynamic fragment
        fragmentTransaction.commit();


    }


}
