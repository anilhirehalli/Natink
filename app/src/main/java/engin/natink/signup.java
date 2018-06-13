package engin.natink;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    String a1,a2;
    EditText na_me,e_mail,pass_word,con_firm,phone_number;
    Button veri_fy;
    Spinner blood_group,se_x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        na_me = (EditText) findViewById(R.id.na_me);
        e_mail = (EditText) findViewById(R.id.e_mail);
        pass_word = (EditText) findViewById(R.id.pass_word);
        con_firm = (EditText) findViewById(R.id.con_firm);
        phone_number = (EditText) findViewById(R.id.phone_number);
        veri_fy = (Button) findViewById(R.id.veri_fy);
        blood_group = (Spinner) findViewById(R.id.blood_group);
        se_x = (Spinner) findViewById(R.id.se_x);

        veri_fy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( na_me.getText().toString().length() == 0 )
                {
                    na_me.setError( "First name is required!" );
                }
                else{

                    String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                            "\\@" +

                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                            "(" +

                            "\\." +

                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                            ")+";

                    String emal = e_mail.getText().toString();

                    Matcher matcherObj = Pattern.compile(validemail).matcher(emal);


                    if (matcherObj.matches()) {

                        a1 = pass_word.getText().toString();
                        a2 = con_firm.getText().toString();
                        if ((a1.length() == 0) || (a2.length() == 0)) {
                            pass_word.setError("Enter the password");
                        } else {
                            if (a1.equals(a2)) {
                                if ((phone_number.getText().toString().length() < 10)||phone_number.getText().toString().length() > 10) {
                                    phone_number.setError("Check the number once!");
                                }

                                else {
                                    if (blood_group.getSelectedItem().toString().equals("Blood group")) {
                                        Toast.makeText(getApplicationContext(), "Select the blood group", Toast.LENGTH_SHORT).show();

                                    } else {
                                        if (se_x.getSelectedItem().toString().equals("Sex")) {
                                            Toast.makeText(getApplicationContext(), "Select the Sex", Toast.LENGTH_SHORT).show();


                                        }
                                        else
                                            {
                                            Toast.makeText(getApplicationContext(), "Verify your E-mail", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }
                            }
                            else{
                                con_firm.setError("password didnt match");

                            }


                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong  email", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

}



