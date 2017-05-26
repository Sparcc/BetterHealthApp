package au.edu.utas.betterhealthapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;

public class HistoryScreen extends AppCompatActivity {

    //recommended food intakes for a week
    int recVegeIntake = 5*7;
    int recFruitIntake = 2*7;
    int recGrainIntake = 6*7;
    int recMeatIntake = 2*7;
    int recDairyIntake = 3*7;
    int recOtherIntake = 0;

    int vegeIntake = 0;
    int fruitIntake = 0;
    int grainIntake = 0;
    int meatIntake = 0;
    int dairyIntake = 0;
    int otherIntake = 0;

    TextView textRecVegeIntake;
    TextView textRecFruitIntake;
    TextView textRecGrainIntake;
    TextView textRecMeatIntake;
    TextView textRecDairyIntake;
    TextView textRecOtherIntake;

    TextView textVegeIntake;
    TextView textFruitIntake;
    TextView textGrainIntake;
    TextView textMeatIntake;
    TextView textDairyIntake;
    TextView textOtherIntake;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);

        //Intent intent = getIntent();
        //myDb = (DatabaseHelper)intent.getSerializableExtra("myDb");
        myDb = new DatabaseHelper(this);
        //myDb.testMe();

        Log.e("program", "setting labels");
        setLabels();
        Log.e("program", "showing history");
        showHistory();
        Log.e("program", "calculating healthScore");

        //saving healthscore to disk
        String filename = "HealthInfo";
        String healthScore = Integer.toString(calculateHealthScore());
        Log.e("program", "healthscore="+Integer.parseInt(healthScore));
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(healthScore.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0; i < 2; i++) {
            Toast.makeText(HistoryScreen.this, "Health Score has been calculated.\n See the main screen for your rank.", Toast.LENGTH_LONG).show();
        }
    }

    public void setLabels(){
        textRecVegeIntake = (TextView) findViewById(R.id.textRecVegeIntake);
        textRecFruitIntake = (TextView) findViewById(R.id.textRecFruitIntake);
        textRecGrainIntake = (TextView) findViewById(R.id.textRecGrainIntake);
        textRecMeatIntake = (TextView) findViewById(R.id.textRecMeatIntake);
        textRecDairyIntake = (TextView) findViewById(R.id.textRecDairyIntake);
        textRecOtherIntake = (TextView) findViewById(R.id.textRecOtherIntake);

        textVegeIntake = (TextView) findViewById(R.id.textVegeIntake);
        textFruitIntake = (TextView) findViewById(R.id.textFruitIntake);
        textGrainIntake = (TextView) findViewById(R.id.textGrainIntake);
        textMeatIntake = (TextView) findViewById(R.id.textMeatIntake);
        textDairyIntake = (TextView) findViewById(R.id.textDairyIntake);
        textOtherIntake = (TextView) findViewById(R.id.textOtherIntake);

        textRecVegeIntake.setText(Integer.toString(recVegeIntake));
        textRecFruitIntake.setText(Integer.toString(recFruitIntake));
        textRecGrainIntake.setText(Integer.toString(recGrainIntake));
        textRecMeatIntake.setText(Integer.toString(recMeatIntake));
        textRecDairyIntake.setText(Integer.toString(recDairyIntake));
        textRecOtherIntake.setText(Integer.toString(recOtherIntake));
    }

    public void showHistory(){
        vegeIntake = myDb.getHistoryOf("Vegetables");
        fruitIntake = myDb.getHistoryOf("Fruits");
        grainIntake = myDb.getHistoryOf("Grain");
        meatIntake = myDb.getHistoryOf("Meats");
        dairyIntake = myDb.getHistoryOf("Dairy");
        otherIntake = myDb.getHistoryOf("Other");
        Log.e("program", "vegeIntake="+vegeIntake);
        Log.e("program", "fruitIntake="+fruitIntake);
        Log.e("program", "grainIntake="+grainIntake);
        Log.e("program", "meatIntake="+meatIntake);
        Log.e("program", "dairyIntake="+dairyIntake);
        Log.e("program", "otherIntake="+otherIntake);


        textVegeIntake.setText(Integer.toString(vegeIntake));
        textFruitIntake.setText(Integer.toString(fruitIntake));
        textGrainIntake.setText(Integer.toString(grainIntake));
        textMeatIntake.setText(Integer.toString(meatIntake));
        textDairyIntake.setText(Integer.toString(dairyIntake));
        textOtherIntake.setText(Integer.toString(otherIntake));
    }

    int calculateHealthScore() {
        int[] intakes = new int[6];
        int[] recIntakes = new int[6];
        int[] scores = new int[6];
        int healthScore = 0;

        intakes[0] = vegeIntake;
        recIntakes[0] = recVegeIntake;
        intakes[1] = fruitIntake;
        recIntakes[1] = recFruitIntake;
        intakes[2] = grainIntake;
        recIntakes[2] = recGrainIntake;
        intakes[3] = meatIntake;
        recIntakes[3] = recMeatIntake;
        intakes[4] = dairyIntake;
        recIntakes[4] = recDairyIntake;
        intakes[5] = otherIntake;
        recIntakes[5] = recOtherIntake;

        //all intakes for each food are judged against ranges to determine a health score
        //user must also eat a minimum amount of food to get enough nutrients otherwise their health score
        //would be affected
        int comp = 0;
        for (int i =0; i<5; i++){
            comp = intakes[i]-recIntakes[i];
            if(comp >= 10 || comp <= -10 || intakes[i] <=  recIntakes[i]/3){
                scores[i] = 0;
            }else
            if(comp >= 5 || comp <= -5){
                scores[i] = 1;
            }else
            if(comp >= 2 || comp <= -2){
                scores[i] = 2;
            }else{
                scores[i] = 3;
            }

            Log.e("healthscore", "i="+i);
            Log.e("healthscore", "comp="+comp);
            Log.e("healthscore", "scores[i]="+scores[i]);
        }
        //other foodgroup is determined separately due to the uniqueness of this group
        //having eaten nothing for an entire week including no "other" foods should
        //not equate to a health score of 1. You can live without the "other food group
        comp = intakes[5] - recIntakes[5];
        if(comp >= 10 || comp <= -10){
            scores[5] = 0;
        }else
        if(comp >= 5 || comp <= -5){
            scores[5] = 1;
        }else
        if(comp >= 2 || comp <= -2){
            scores[5] = 2;
        }else{
            scores[5] = 3;
        }

        healthScore = scores[0];
        for (int i=1;i<6;i++){
            healthScore += scores[i];
            Log.e("healthscore", "i="+i);
            Log.e("healthscore", "comp="+comp);
            Log.e("healthscore", "scores[i]="+scores[i]);
        }
        Log.e("healthscore", "total of scores="+healthScore);
        float calc = ((float)healthScore)/6.0f;
        Log.e("healthscore", "calc="+calc);

        healthScore = (int)Math.round(calc);
        Log.e("healthscore", "rounded healthscore="+healthScore);

        return healthScore;
    }
}
