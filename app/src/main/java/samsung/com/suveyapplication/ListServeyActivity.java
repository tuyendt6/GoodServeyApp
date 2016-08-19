package samsung.com.suveyapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.samsung.adapter.SerVeyAdapter;
import com.samsung.object.ServeyOject;
import com.samsung.object.Util;
import com.samsung.provider.SamsungProvider;
import com.samsung.table.tblEncuestaDatos;
import com.samsung.table.tblEncuestaDisenos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SamSunger on 5/13/2015.
 */
public class ListServeyActivity extends AppCompatActivity {
    private ArrayList<ServeyOject> mListServeyOjbect = new ArrayList<ServeyOject>();
    private SerVeyAdapter ServeyAdapter;
    private ListView mListView;
    private ImageButton mNewServey;
    private ImageButton mBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servey_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Encuestas");

        mListView = (ListView) findViewById(R.id.listservey);
        ServeyAdapter = new SerVeyAdapter(getBaseContext(), R.layout.servey_item_layout, mListServeyOjbect);
        mListView.setAdapter(ServeyAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), ListQuestionActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                Util.ServeySelected = mListServeyOjbect.get(position);
                finish();
            }
        });
        mNewServey = (ImageButton) findViewById(R.id.imbaddnewservey);
        mBack = (ImageButton) findViewById(R.id.imbexit);
        mNewServey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainAcitivity.class);
                i.putExtra("add", "add");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("tuyenpx", "tuyenpx = called me  ListServeyActivity ");

        if (mListServeyOjbect.size() == 0) {
            SetupView();
        }
    }

    private void SetupView() {
        Cursor c = getContentResolver().query(SamsungProvider.URI_ENCUESTA_DISENOS, null, null, null, null);
        if (c.getCount() == 0) {
            return;
        }
        while (c.moveToNext()) {
            //  String PK_ID, String NOMBRE, String DESCRIPCION, String ACTIVO, String TIMEDONE, String GRUPO_RESPUESTAS_ID
            ServeyOject serveyOject = new ServeyOject(c.getString(c.getColumnIndexOrThrow(tblEncuestaDisenos.PK_ID)), c.getString(c.getColumnIndexOrThrow(tblEncuestaDisenos.NOMBRE)),
                    c.getString(c.getColumnIndexOrThrow(tblEncuestaDisenos.DESCRIPCION)), c.getString(c.getColumnIndexOrThrow(tblEncuestaDisenos.ACTIVO)), "");
            Log.e("ListServeyActivity ", serveyOject.toString());
            if (serveyOject.getACTIVO().equals("True") && checkQuestionObject(serveyOject)) {
                mListServeyOjbect.add(serveyOject);
                ServeyAdapter.notifyDataSetChanged();
            }
        }
        c.close();
    }


    private boolean checkQuestionObject(ServeyOject questionObject) {
        Log.e("checkQuestionObject", "QuestionObjectPKID = " + questionObject.getPK_ID());
        SharedPreferences sharedPreferences = getSharedPreferences("question", Context.MODE_PRIVATE);
        String ID = sharedPreferences.getString("venderID", "1");
        Cursor c = getContentResolver().query(SamsungProvider.URI_ENCUESTADATOS, null, tblEncuestaDatos.DISENO_ID + "=? AND " + tblEncuestaDatos.VENDEDOR_ID + " =?", new String[]{questionObject.getPK_ID(), ID}, null);
        Log.e("checkQuestionObject", "c.getCount = " + c.getCount());
        if (c.getCount() < 1) {
            return true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        dateFormat.setLenient(false);
        while (c.moveToNext()) {
            String PK_ID = c.getString(c.getColumnIndex(tblEncuestaDatos.DISENO_ID));
            String Date = c.getString(c.getColumnIndexOrThrow(tblEncuestaDatos.FECHAHORA_ENCUESTA));
            //  18/6/1991 vs 18/6/1991 22;;33333

            Log.e("checkQuestionObject", "getCurrentDate() 1  = " + getCurrentDate());
            Log.e("checkQuestionObject", "getCurrentDate() 2  = " + Date);

            Log.e("checkQuestionObject", "PK_ID = " + PK_ID);
            String date_first;
            String date_second;
            date_first = dateFormat.format(new Date(Date));
            date_second = dateFormat.format(new Date(getCurrentDate()));
            // check date and pkID to show
            if (date_first.equalsIgnoreCase(date_second)) {
                Log.e("tuyenpx", " compare sucess");
                return false;
            } else {
                Log.e("tuyenpx", " compare failed");

            }

        }
        return true;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(calendar.getTime().getTime());
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