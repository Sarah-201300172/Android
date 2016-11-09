package qa.edu.qu.cmps312.fragmentdemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by abdulahi on 11/6/16.
 */

public class Planet implements Parcelable {

    String planet_name;
    String planet_desc;
    int planet_image;
    private static final long serialVersionUID = 1L;


    public Planet(String planet_name, String planet_desc, int planet_image) {
        this.planet_name = planet_name;
        this.planet_desc = planet_desc;
        this.planet_image = planet_image;
    }

    public String getPlanet_name() {
        return planet_name;
    }

    public void setPlanet_name(String planet_name) {
        this.planet_name = planet_name;
    }


    public String getPlanet_desc() {
        return planet_desc;
    }


    public int getPlanet_image() {
        return planet_image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.planet_name);
        dest.writeString(this.planet_desc);
        dest.writeInt(this.planet_image);
    }

    protected Planet(Parcel in) {
        this.planet_name = in.readString();
        this.planet_desc = in.readString();
        this.planet_image = in.readInt();
    }

    public static final Parcelable.Creator<Planet> CREATOR = new Parcelable.Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel source) {
            return new Planet(source);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };
}
