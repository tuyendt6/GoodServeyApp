package samsung.com.suveyapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.samsung.object.Util;

/**
 * Created by SamSunger on 5/20/2015.
 */
public class CancelActivity extends AppCompatActivity {
    private Button Ok;
    private Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Ok = (Button) findViewById(R.id.imageButton4);
        Cancel = (Button) findViewById(R.id.imageButton3);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainAcitivity.class);
                i.putExtra("add", "list");
                startActivity(i);
                finish();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainAcitivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        String contentEmail = getIntent().getStringExtra("ContentEmail");
        SharedPreferences sharedPreferences = getSharedPreferences("question", Context.MODE_PRIVATE);

        if (contentEmail != null) {
            Util.sendEmail(getBaseContext(), sharedPreferences.getString("email", "tuyendt6@gmail.com"), "add new survey", contentEmail);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
