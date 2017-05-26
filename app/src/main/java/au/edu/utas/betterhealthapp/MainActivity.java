package au.edu.utas.betterhealthapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        setHealthRanking();
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        setHealthRanking();
    }


    public void onButtonClick(View v)
    {

        if(v.getId()==R.id.Brecord)
        {
            Intent i=new Intent(MainActivity.this,Record.class);
            startActivity(i);
        }

        if(v.getId()==R.id.Bhistory){

            Intent i=new Intent(MainActivity.this,HistoryScreen.class);

            startActivity(i);
        }
        if(v.getId()==R.id.BReview){

            Intent i=new Intent(MainActivity.this,EntryReview.class);

            startActivity(i);
        }
        if(v.getId()==R.id.Badvice){

            Intent i=new Intent(MainActivity.this,Advice.class);

            startActivity(i);
        }
    }

    public void setHealthRanking()
    {
        Log.e("program", "hiding stars");
        ImageView star1 = (ImageView) findViewById(R.id.star1);
        ImageView star2 = (ImageView) findViewById(R.id.star2);
        ImageView star3 = (ImageView) findViewById(R.id.star3);

        star1.setVisibility(View.INVISIBLE);
        star2.setVisibility(View.INVISIBLE);
        star3.setVisibility(View.INVISIBLE);

        Log.e("program", "reading healthscore");

        String healthScore ="";
        try{
            Log.e("program", "setting FileInputStream");
            FileInputStream stream = openFileInput("HealthInfo");
            Log.e("program", "setting InputStreamReader");
            InputStreamReader iReader = new InputStreamReader(stream);
            Log.e("program", "setting BufferedReader");
            BufferedReader bReader = new BufferedReader(iReader);
            if ((healthScore = bReader.readLine())!=null)
            {
                int iHealthScore = Integer.parseInt(healthScore);
                if (iHealthScore == 0) {
                    //don't do anything lmao your health is bad
                }else
                if (iHealthScore == 1){
                    star1.setVisibility(View.VISIBLE);

                }else
                if (iHealthScore == 2){
                    star1.setVisibility(View.VISIBLE);
                    star2.setVisibility(View.VISIBLE);
                }else
                if (iHealthScore == 3){
                    star1.setVisibility(View.VISIBLE);
                    star2.setVisibility(View.VISIBLE);
                    star3.setVisibility(View.VISIBLE);
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
