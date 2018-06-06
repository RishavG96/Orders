package hera.com.orders;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateURL extends AppCompatActivity {

    EditText editText;
    Button update;
    SQLiteDatabase db;
    TextView oldurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_url);
        editText=findViewById(R.id.editText);
        update=findViewById(R.id.button);
        oldurl=findViewById(R.id.textView);
        db=openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c=db.rawQuery("select * from url",null);
        StringBuffer sb=new StringBuffer();
        while(c.moveToNext()) {
            sb.append(c.getString(0));
        }
        //oldurl.setText("The old URL is: "+sb);
        editText.setText(sb);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("url",editText.getText().toString());
                db.update("url", cv, "", null);
                Toast.makeText(getApplicationContext(),"URL Updated",Toast.LENGTH_SHORT).show();
//                Cursor c=db.rawQuery("select * from url",null);
//                while(c.moveToNext())
//                {
//                    Toast.makeText(getApplicationContext(), c.getString(0),Toast.LENGTH_SHORT).show();
//                }
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
