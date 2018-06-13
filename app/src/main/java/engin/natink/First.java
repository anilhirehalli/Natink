package engin.natink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class First extends AppCompatActivity {
 int initial1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }


            @Override
            public void onFinish() {
                //set the new Content of your activity
                checkvalue();
              //  setvalue();
                checkvalue();
               // Toast.makeText(getApplicationContext(),initial1+" : initial",Toast.LENGTH_SHORT).show();
                if(initial1==0)
                {
                    PrefManager prefManager = new PrefManager(getApplicationContext());

                    // make first time launch TRUE
                    prefManager.setFirstTimeLaunch(true);

                    startActivity(new Intent(First.this, licence.class));
                    finish();

                }

                else {
                    Intent intent = new Intent(First.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }.start();

    }
    public void del()
    {

        //Toast.makeText(getApplicationContext(),"its comming",Toast.LENGTH_SHORT).show();
    }
    private void setvalue() {
        initial1++;
        SharedPreferences settings1 = getSharedPreferences("Begining", 0);
        SharedPreferences.Editor editor1 = settings1.edit();
        editor1.putInt("SNOW_DENSITY1",initial1);
        editor1.commit();
    }

    private void checkvalue() {
        SharedPreferences settings1 = getSharedPreferences("Begining", 0);
        initial1 = settings1.getInt("SNOW_DENSITY1", 0);
    }

}

