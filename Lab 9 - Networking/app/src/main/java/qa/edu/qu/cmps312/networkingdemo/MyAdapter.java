package qa.edu.qu.cmps312.networkingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abdulahi on 11/26/16.
 */

public class MyAdapter extends ArrayAdapter<User> {


    public MyAdapter(Context context, ArrayList<User> users)
    {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (user.getFirstName() != null) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.row_layout, parent, false);
            }

            TextView userName = (TextView) convertView.findViewById(R.id.userName);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.userImage);

            userName.setText((user.getFirstName() + " " + user.getLastName()).toUpperCase());

            //This is good library for loading Images from the website or file
            Picasso.with(getContext()).load(user.getPicture_url()).into(imageView);
        }

        return convertView;

    }
}
