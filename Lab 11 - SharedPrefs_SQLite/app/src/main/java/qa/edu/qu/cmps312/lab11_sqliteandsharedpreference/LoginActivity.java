package qa.edu.qu.cmps312.lab11_sqliteandsharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    DatabaseOpenHelper dbHelper;


    EditText username;
    EditText userpass;
    CheckBox rememberPass;
    SharedPreferences prefs;
    String userEntered;
    String passEntered;
    String isAuthenticatedString;
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseOpenHelper(this);

        username = (EditText) findViewById(R.id.user_name);
        userpass = (EditText) findViewById(R.id.user_pass);
        rememberPass = (CheckBox) findViewById(R.id.checkbox);

        prefs = getPreferences(MODE_PRIVATE);

        rememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isChecked = b; //so next time it login automatically
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.apply();
            }
        });

        //Log.i("TAG", String.valueOf(isChecked));

        //then go login automatically
        //or we can check whether there is anything in shared prefs
        if (prefs.getBoolean("isChecked", false) != false) {
            String sharedString = prefs.getString("NAME", null);
            goToNextActivity(sharedString);
        }

    }


    public void userLogin(View view) {

        userEntered = username.getText().toString();

//        String sharedString = prefs.getString("USERNAME", null);
//
//        boolean isInShared = userEntered.equals(sharedString);
//        Log.i("TAG", "USERNAME SHARED " + sharedString);
//
//        if (isInShared) {
//            passEntered = prefs.getString("PASSWORD", null);
//            Log.i("TAG", "PASS SHARED " + passEntered);
//        } else

        passEntered = userpass.getText().toString();
        isAuthenticatedString = dbHelper.authenticate(userEntered, passEntered);

        if (null != isAuthenticatedString) {

            if (isChecked) { //if the user checked remember me

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("USERNAME", userEntered);
                editor.putString("NAME", isAuthenticatedString);
                editor.putString("PASSWORD", passEntered);
                //editor.clear();
                editor.apply();

            }

            goToNextActivity(isAuthenticatedString);
        }


    }

    public void userReg(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void goToNextActivity(String name) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(getString(R.string.IntentKey), name);
        startActivity(i);
    }
}
