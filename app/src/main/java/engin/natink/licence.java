package engin.natink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class licence extends AppCompatActivity {
  Button agree;
  int initial1;
  TextView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        terms = (TextView)findViewById(R.id.licice);
        String htmlString="<u>Terms and conditions</u>";
        terms.setText(Html.fromHtml(htmlString));
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"link clicked ",Toast.LENGTH_SHORT).show();
                String url="m.facebook.com/EngINIndia/";
                Uri u= Uri.parse("http://"+url);
                Intent i=new Intent(Intent.ACTION_VIEW,u);
                startActivity(i);
            }
        });
        agree = (Button)findViewById(R.id.agreed);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkvalue();

                setvalue();
               // Toast.makeText(getApplicationContext(),initial1 +": inistial after the licence",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(licence.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


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
