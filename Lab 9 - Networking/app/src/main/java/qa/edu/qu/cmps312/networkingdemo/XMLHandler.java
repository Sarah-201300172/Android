package qa.edu.qu.cmps312.networkingdemo;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarahalhussaini on 11/30/16.
 */

public class XMLHandler {

    private static final String TITLE_TAG = "title";
    private static final String FIRST_TAG = "first";
    private static final String LAST_TAG = "last";
    private static final String LARGE_TAG = "large";

    private String mTitle, mFirst, mLast, mLarge;
    private boolean mIsParsingTitle, mIsParsingFirst, mIsParsingLast, mIsParsingLarge;

    private final ArrayList<User> mUsers = new ArrayList<>();


    public ArrayList<User> parseContent(InputStream xmlDataStream) throws IOException {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(xmlDataStream));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    Log.i("TAG", xpp.getName());
                    startTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    endTag(xpp.getName());
                } else if (eventType == XmlPullParser.TEXT) {
                    Log.i("TAG", xpp.getText());
                    text(xpp.getText());
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {

        }

        return mUsers;
    }

    private void text(String text) {
        if (mIsParsingTitle) {
            mTitle = text.trim();
        } else if (mIsParsingFirst) {
            mFirst = text.trim();
        } else if (mIsParsingLast) {
            mLast = text.trim();
        } else if (mIsParsingLarge) {
            mLarge = text.trim();
        }
    }

    private void endTag(String name) {
        if (name.equals(TITLE_TAG)) {
            mIsParsingTitle = false;
        } else if (name.equals(FIRST_TAG)) {
            mIsParsingFirst = false;
        } else if (name.equals(LAST_TAG)) {
            mIsParsingLast = false;
        } else if (name.equals(LARGE_TAG)) {
            mIsParsingLarge = false;
        } else if (name.equals("results")) {

            User user = new User();

            user.setTitle(mTitle);
            user.setFirstName(mFirst);
            user.setLastName(mLast);
            user.setPicture_url(mLarge);

            mUsers.add(user);

            mTitle = null;
            mFirst = null;
            mLast = null;
            mLarge = null;
        }
    }

    private void startTag(String name) {
        if (name.equals(FIRST_TAG)) {
            mIsParsingFirst = true;
        } else if (name.equals(LAST_TAG)) {
            mIsParsingLast = true;
        } else if (name.equals(TITLE_TAG)) {
            mIsParsingTitle = true;
        } else if (name.equals(LARGE_TAG)) {
            mIsParsingLarge = true;
        }
    }
}
