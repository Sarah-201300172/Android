package qa.edu.qu.cmps312.fragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import qa.edu.qu.cmps312.explicitactivitydemo.R;

public class PlanetFactsFragment extends Fragment {


    public PlanetFactsFragment() {
    }

    public static PlanetFactsFragment newInstance(Planet planet) {

        Bundle args = new Bundle();
        args.putParcelable(SolarFactMainActivity.PLANET, planet);
        PlanetFactsFragment fragment = new PlanetFactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Planet planet =   getArguments().getParcelable(SolarFactMainActivity.PLANET); // this gets the arguments passed from the activity during the creation of the fragment

        View rootView = inflater.inflate(R.layout.fragment_planet_facts_layout,container,false);

        // TODO : inflate the "fragment_planet_facts_layout" and assign it to a rootView object declared above
        // TODO : Inside the "fragment_planet_facts_layout" there are two views . One "ImageView to display the Planet Image and another one "TextView" to display the fact about the planet,
        // TODO: Your task is to use  the planet object above and assign the "IMAGE_VIEW"
        // and Image using the planet.getPlanet_image() and the TEXT_VIEW" description text using the planet.getPlanet_desc()

        ImageView planetIV = (ImageView) rootView.findViewById(R.id.planetImage);
        TextView descTV = (TextView) rootView.findViewById(R.id.factsTv);

        descTV.setText(planet.getPlanet_desc());
        planetIV.setImageDrawable(getResources().getDrawable(planet.getPlanet_image()));

        return rootView;

    }



}
