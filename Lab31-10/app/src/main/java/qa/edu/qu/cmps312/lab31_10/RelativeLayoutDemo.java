package qa.edu.qu.cmps312.lab31_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RelativeLayoutDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout_demo);

        Button btn = (Button) findViewById(R.id.btn);
        final EditText et = (EditText) findViewById(R.id.ev);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RelativeLayoutDemo.this, et.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
