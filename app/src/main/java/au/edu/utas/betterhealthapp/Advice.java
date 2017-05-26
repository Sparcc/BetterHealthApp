package au.edu.utas.betterhealthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Advice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
    }

    public void onButtonClick(View v)
    {
        Log.e("advice", "Button has been pressed");
        if(v.getId()==R.id.Bguidelines)
        {
            Log.e("advice", "Attempting to redirect to guidelines screen");
            Intent i=new Intent(Advice.this,Guidelines.class);
            startActivity(i);
        }
    }
}
