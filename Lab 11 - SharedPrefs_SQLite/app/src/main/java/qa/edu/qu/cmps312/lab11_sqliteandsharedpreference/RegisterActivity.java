package qa.edu.qu.cmps312.lab11_sqliteandsharedpreference;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    DatabaseOpenHelper dbHelper;
    EditText newUsername;
    EditText newUserpass;
    EditText newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseOpenHelper(this);

        newUsername = (EditText) findViewById(R.id.new_user_name);
        newUserpass = (EditText) findViewById(R.id.new_user_pass);
        newName = (EditText) findViewById(R.id.name);
    }


    public void userReg(View view) {

        ContentValues values = new ContentValues();

        values.put(DatabaseOpenHelper.USERNAME, newUsername.getText().toString());
        values.put(DatabaseOpenHelper.PASSWORD, newUserpass.getText().toString());
        values.put(DatabaseOpenHelper._NAME, newName.getText().toString());

        dbHelper.addUser(values);

        finish();

    }
}
