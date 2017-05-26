package au.edu.utas.betterhealthapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EntryReview extends AppCompatActivity {

    TextView textEntry;
    DatabaseHelper myDb;
    Button btnDelete;
    EditText editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_review);

        textEntry = (TextView) findViewById(R.id.textEntry);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editTextId = (EditText) findViewById(R.id.editText);
        myDb = new DatabaseHelper(this);
        editTextId.clearFocus();
        showEntries();
        setupDataDeletion();
    }

    //event for delete
    public void setupDataDeletion(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Integer deleteRows=myDb.deleteData(editTextId.getText().toString());
            if(deleteRows>0)
            {
                Toast.makeText(EntryReview.this,"Data is deleted",Toast.LENGTH_LONG).show();
                textEntry.setText("");
                showEntries();
            }
            else
                Toast.makeText(EntryReview.this,"Data is not deleted",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showEntries(){

        Log.e("entry_screen","attempting to show entries");
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }
        Log.e("entry_screen","using string buffer");
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append("Entry ID: "+res.getString(0)+"\n");
            buffer.append("Food Group: "+res.getString(1)+"\n");
            buffer.append("Food Type: "+res.getString(2)+"\n");
            buffer.append("Servings: "+res.getString(3)+"\n");
            buffer.append("Date: "+res.getString(4)+"\n\n");
        }
        Log.e("entry_screen","Showing entries by setting text");
        textEntry.setText(buffer.toString());
    }

}
