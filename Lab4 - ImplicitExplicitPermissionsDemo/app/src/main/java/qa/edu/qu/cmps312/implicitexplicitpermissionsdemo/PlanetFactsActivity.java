package qa.edu.qu.cmps312.implicitexplicitpermissionsdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PlanetFactsActivity extends Activity {

    int planetImageArray[] = {
            R.drawable.mercury, R.drawable.venus, R.drawable.earth, R.drawable.mars,
            R.drawable.jupiter, R.drawable.saturn, R.drawable.uranus, R.drawable.neptune
    };

    String planetFactsArray[];
    TextView descTV;
    ImageView planetIV;
    String planetFactString;
    int planetIndex = 0;
    boolean permissionGranted = false;

    final static int WRITE_EXTERNAL_REQUEST_CODE = 22;
    final static int READ_EXTERNAL_REQUEST_CODE = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_facts);

        planetFactsArray = getResources().getStringArray(R.array.planets);

        planetIV = (ImageView) findViewById(R.id.planetImage);
        descTV = (TextView) findViewById(R.id.factsTv);

        planetIndex = getIntent().getIntExtra(PlanetsActivity.PLANET_INDEX, 0);

        planetFactString = planetFactsArray[planetIndex];


        descTV.setText(planetFactString);
        planetIV.setImageResource(planetImageArray[planetIndex]);

    }

    public void nextPlanet(View view) {

        planetIndex = ++planetIndex % 7;
        Intent i = new Intent(this, PlanetFactsActivity.class);
        i.putExtra(PlanetsActivity.PLANET_INDEX, planetIndex);
        startActivity(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        planetFactString = planetFactsArray[planetIndex];
        int planetImageId = planetImageArray[planetIndex];

        descTV.setText(planetFactString);
        planetIV.setImageResource(planetImageId);
    }

    public void sendEmail(View view) {

        String permissions[] = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
        int permissionCheck = ContextCompat.checkSelfPermission(this, permissions[0]);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            String[] email_add = {"nasaPlanet@qu.edu.qa","sa1300172@qu.edu.qa"};
            String subject = "Check these facts";
            String message = descTV.getText().toString();

            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setType("plain/text");
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, email_add);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, message);

            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(getAttachment(planetImageArray[planetIndex])));

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            } else {
                Toast.makeText(this, "No configured email client on your phone", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You don't have the permission", Toast.LENGTH_LONG).show();
            requestThesePermissions(permissions);
        }
    }

    private File getAttachment(int resId) {

        FileOutputStream outStream;
        File file;

        Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        String filename = getResources().getResourceName(planetImageArray[planetIndex]);

        filename = filename.substring(filename.lastIndexOf('/') + 1);

        Log.d("PLANET NAME ", filename);
        file = new File(extStorageDirectory, filename + ".png");
        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;


    }

    public void requestThesePermissions(String[] permissions) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permissions[0]);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                /////
            }
            ActivityCompat.requestPermissions(this, permissions, WRITE_EXTERNAL_REQUEST_CODE);
        } else {
            permissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case WRITE_EXTERNAL_REQUEST_CODE:
            case READ_EXTERNAL_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "Please try the email now", Toast.LENGTH_LONG).show();
                } else {
                    permissionGranted = false;
                }
                break;
        }
    }


}