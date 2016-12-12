package qa.edu.qu.cmps312.expenses;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sarahalhussaini on 11/19/16.
 */

public class HomeFragment extends Fragment {

    private onFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mListener == null) {
            try {
                mListener = (onFragmentInteractionListener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(mListener.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button expensesButton = (Button) view.findViewById(R.id.expenses_btn);
        Button converterButton = (Button) view.findViewById(R.id.converter_btn);
        Button statsButton = (Button) view.findViewById(R.id.stats_btn);

        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.optionClicked(R.string.EXPENSES_FRAGMENT);
            }
        });

        converterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.optionClicked(R.string.CONVERTER_FRAGMENT);
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.optionClicked(R.string.STATS_FRAGMENT);
            }
        });


        return view;
    }
}
