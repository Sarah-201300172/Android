package qa.edu.qu.cmps312.networkingdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    String random_user_site_json = "https://randomuser.me/api/?results=5&format=json";
    String random_user_site_xml = "https://randomuser.me/api/?results=5&format=xml";

    ListView listView;

    private static final String TITLE_TAG = "title";
    private static final String FIRST_TAG = "first";
    private static final String LAST_TAG = "last";
    private static final String LARGE_TAG = "large";
    private static final String NAME_TAG = "name";
    private static final String PICTURE_TAG = "picture";

    boolean JSON_FORMAT = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);

    }

    public void clickHandler(View view) {

        switch (view.getId()) {
            case R.id.get_json_data:
                JSON_FORMAT = true;
                //TODO : pass the appropriate value to the Downloader Async Task
                new Downloader().execute(random_user_site_json);
                break;
            case R.id.get_xml_data:
                JSON_FORMAT = false;
                //TODO : pass the appropriate value to the Downloader Async Task
                new Downloader().execute(random_user_site_xml);
                break;
        }

    }

    private class Downloader extends AsyncTask<String, Void, ArrayList<User>> {

        InputStream inputStream;
        URL url;
        ArrayList<User> users = new ArrayList<>();

        HttpURLConnection connection;

        @Override
        protected ArrayList<User> doInBackground(String... urls) {

            try {

                //TODO : create the network connection and download the content using the HttpURLConnection

                /* Hint: First create a URL connection using the passed URL

                 => Now initialize the important parameters of the URL connection
                 => Make the HTTPURLConnection object set the following connection timeouts
                        // setConnectTimeout 5000 and
                        // setReadTimeout to 3000

                 => Set the type of request you want [GET]
                 => Now try the connection to the network
                 => read the data using the input stream
                 => depending on the button that was clicked pass the inputStream to it
                 */

                connection = (HttpURLConnection) new URL(urls[0]).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(3000);

                inputStream = new BufferedInputStream(
                        connection.getInputStream());

                if (JSON_FORMAT) {
                    users = extractUsersFromJson(inputStream);
                } else {
                    users = extractUsersFromXml(inputStream);

                }

                return users;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //TODO : Close the connection here

                if (connection != null)
                    connection.disconnect();

            }

            return null;
        }

        private ArrayList<User> extractUsersFromJson(InputStream jsonDataStream) throws JSONException {

            String data = convertToString(jsonDataStream);
            ArrayList<User> users = new ArrayList<>();


            //TODO write the JSON parsing Code and and return a list of user object
            // Only extract the following keys from the JSON user data
            // 1. title ,
            // 2. first ,
            // 3. last and
            // 4. large [Large is found inside the picture]

            JSONObject responseObject = (JSONObject) new JSONTokener(
                    data).nextValue();

            JSONArray results = responseObject
                    .getJSONArray("results");

            for (int idx = 0; idx < results.length(); idx++) {

                User user = new User();

                JSONObject result = (JSONObject) results.get(idx);
                JSONObject name = result.getJSONObject(NAME_TAG);
                JSONObject picture = result.getJSONObject(PICTURE_TAG);

                Log.i("TAG", name.get(FIRST_TAG).toString());

                user.setTitle(name.get(TITLE_TAG).toString());
                user.setFirstName(name.get(FIRST_TAG).toString());
                user.setLastName(name.get(LAST_TAG).toString());
                user.setPicture_url(picture.get(LARGE_TAG).toString());

                users.add(user);
            }

            return users;
        }

        private ArrayList<User> extractUsersFromXml(InputStream xmlDataStream) throws XmlPullParserException, IOException {

//            ArrayList<User> users = new ArrayList<>();
//            User user = new User();

            //TODO Create the data using XmlPullParser  and return a list of user object
            // Only extract the following keys from the XML user data
            // 1. title ,
            // 2. first ,
            // 3. last and
            // 4. large [Large is found inside the picture]


            return new XMLHandler().parseContent(xmlDataStream);


        }


        // This method is used to convert input stream object to a string
        // It is used inside the extractUsersFromJSON method
        private String convertToString(InputStream inputStream) {

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));
            StringBuilder sp = new StringBuilder();
            String line;


            try {
                while ((line = br.readLine()) != null) {
                    sp.append(line);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return sp.toString();
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            super.onPostExecute(users);

            if (users != null) {
                //TODO populate the user data on the listview using the "MyAdapter"

                MyAdapter myAdapter = new MyAdapter(MainActivity.this, users);
                listView.setAdapter(myAdapter);
            }


        }


    }
}

