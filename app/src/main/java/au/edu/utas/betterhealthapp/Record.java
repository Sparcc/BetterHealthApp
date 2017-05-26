package au.edu.utas.betterhealthapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by store on 5/05/2016.
 */
public class Record extends Activity{
    //calling databasehelper class
    DatabaseHelper myDb;
    //variables for date picker
    String Pdate;
    Button btn;
    //button for delete
    Button btnDelete;

    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;
    //----

    EditText editFtype, editServings, editTextId;
    Spinner Fspin;
    Button btnAddrecord;
Button btnClearrecord;
    String Fgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        //creating database helper class
        myDb=new DatabaseHelper(this);
//insance for calender
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

      //
        Fspin=(Spinner)findViewById(R.id.spin);
        //editFGroup=(EditText)findViewById(R.id.TFfgroup);
        editFtype=(EditText)findViewById(R.id.TFftype);
        editServings=(EditText)findViewById(R.id.TFserves);
        btnAddrecord=(Button)findViewById(R.id.Baddrecord);
        btnClearrecord=(Button)findViewById(R.id.Bclear);//button for clear
        //object created for spinner
        Spinner sp=(Spinner)findViewById(R.id.spin);
        //minvoking item listener to select item from spinner
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(Record.this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                //assigning selected spinner item to variable
                Fgroup=parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AddData(); // calling adding data event
        ClearData(); //calling  clear data event
        //DeleteData();
    }

//event for calender
    public void showDialogOnButtonClick(){


        btn=(Button)findViewById(R.id.Bdate);
        btn.setOnClickListener(
                new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );

    }
    //------
    @Override
    protected Dialog onCreateDialog(int id){

        if(id==DIALOG_ID)
            return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        return null;
    }
    //--
    //constructer to choose current date
    private DatePickerDialog.OnDateSetListener dpickerListener
            =new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x=year;
            month_x=monthOfYear+1;//month from date picker starts from 0 which is annoying
            day_x= dayOfMonth;
            Toast.makeText(Record.this,year_x+"/"+month_x+"/"+day_x,Toast.LENGTH_SHORT).show();
           //setting calender date in edittext
            EditText show_date = (EditText) findViewById( R.id.Sdate );

            String f1 = String.format("%04d", year_x);
            String f2 = String.format("%02d", (month_x));
            String f3 = String.format("%02d", day_x);
            Pdate=f1+"-"+f2+"-"+f3;
            Log.e("datepicker","date to input is: "+Pdate);

            show_date.setText( Pdate);
        }
    };

    //event for clear data
    public void ClearData(){
        btnClearrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  editFGroup.setText("");
                editFtype.setText(""); //set type textifle clear
                editServings.setText(""); //sets serving size edit text clear
            }
        });
    }
// button event to add data
    public void AddData(){
       btnAddrecord.setOnClickListener(
               new View.OnClickListener(){

                   @Override
                   public void onClick(View v) {
                      boolean isInserted= myDb.insertData(
                               Fspin.getSelectedItem().toString(),
                               editFtype.getText().toString(),
                               editServings.getText().toString(),
                               Pdate);
                       //SHOW MSG
                       if(isInserted==false)
                           Toast.makeText(Record.this,"Data is not Added",Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(Record.this,"Data is Added",Toast.LENGTH_LONG).show();
                   }
               }

       );



    }



}
