package qa.edu.qu.cmps312.fragmentdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qa.edu.qu.cmps312.explicitactivitydemo.R;

public class PlanetListFragment extends Fragment {

    private DisplayFactsInterface displayFactsInterface;
    private static final String PLANETS = "PLANETS";
    private static final String PLANET = "PLANET";
    private ArrayList<Planet> planets;

    public PlanetListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        displayFactsInterface = (DisplayFactsInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planet_list_layout, container, false);
        planets = get_planets();

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        MyPlanetAdapter adapter = new MyPlanetAdapter(getActivity(), planets);
        listView.setAdapter(adapter);
        return rootView;
    }


    private class MyPlanetAdapter extends ArrayAdapter<Planet> {

        TextView planet_name_tv;
        ImageView planet_image_view;

        public MyPlanetAdapter(Context context, List<Planet> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Planet planet = getItem(position);
            final int planetIndex = position;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.listview_row_layout, parent, false);
                planet_image_view = (ImageView) convertView.findViewById(R.id.planet_image_view);
                planet_name_tv = (TextView) convertView.findViewById(R.id.planet_name_tv);
            }

            planet_image_view.setImageDrawable(getResources().getDrawable(planet.getPlanet_image()));
            planet_name_tv.setText(planet.getPlanet_name());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayFactsInterface.displayFacts(planet);
                }
            });

            return convertView;
        }
    }

    private ArrayList<Planet> get_planets() {

        ArrayList<Planet> planets = new ArrayList<>();

        String[] planet_names = getResources().getStringArray(R.array.planet_names);
        String[] planet_desc = getResources().getStringArray(R.array.planets_desc);
        // int[] planet_images = getResources().getIntArray(R.array.planet_images);

        TypedArray planet_images = getResources().obtainTypedArray(R.array.planet_images);

        for (int i = 0; i < planet_desc.length; i++) {

            Planet planet = new Planet(planet_names[i], planet_desc[i], planet_images.getResourceId(i, -1));
            planets.add(planet);

        }

        planet_images.recycle();
        return planets;
    }

}
