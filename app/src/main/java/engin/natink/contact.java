package engin.natink;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class contact extends AppCompatActivity {
    ListView listView;
    TextView textView;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Cursor c;
    ArrayList<String> contacts= new ArrayList<String>();
    ArrayList<String> selectcontacts = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    Button butt_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView) findViewById(R.id.contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contact.this,message.class);
                intent.putExtra("mylist", selectcontacts);startActivity(intent);
                finish();
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED ) {
            //Name of Method for Calling Message
            showContacts();

        } else {
            //TODO
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }


         adapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, contacts);
        //  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
        //        this, android.R.layout.simple_list_item_1, selectcontacts);

        listView.setAdapter(adapter);
        if(contacts.isEmpty())
        {
          //  Toast.makeText(getApplicationContext(),"List view is Null",Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //selectcontacts.add(contacts.get(i));
                AlertDialog.Builder builder = new AlertDialog.Builder(contact.this);
                builder.setCancelable(false);
                builder.setTitle("Confirm..,");
                builder.setMessage("Are you sure you want to Add this number as Emergency contact number.");
                builder.setPositiveButton("yes.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                      Object o =listView.getItemAtPosition(i);
                      selectcontacts.add((String) o);
                    //    selectcontacts.add(o);
                     //   Toast.makeText(getApplicationContext(),selectcontacts
                       //       +" "+contacts.get(i)+"  "+i + " Yes was Clicked",Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getApplicationContext(),o
                         //       +" "+listView.getItemAtPosition(i)+"  "+i + " Yes was Clicked",Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                })
                        .setNegativeButton("No.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            //    Toast.makeText(getApplicationContext(),"No was Clicked",Toast.LENGTH_SHORT).show();
                                //      finish();
                            }
                        });

                // Create the AlertDialog object and return it
                builder.create().show();
                // Toast.makeText(getApplicationContext(),"Item Clicked: "+i,Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem Item = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView)Item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                listView.setAdapter(adapter);


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showContacts() {
        c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

        contacts = new ArrayList();
        while (c.moveToNext()) {

            String contactName = c.getString(c.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ));
            String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(contactName + "\n" + phNumber);
            //contacts.add(contactName + "\n"+ phNumber);


            //Toast.makeText(this,"COntacts: "+contacts,Toast.LENGTH_SHORT).show();
        }
        c.close();
    }

}
