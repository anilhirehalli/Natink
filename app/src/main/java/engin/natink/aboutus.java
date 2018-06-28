package engin.natink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class aboutus extends AppCompatActivity {
    private WebView wvAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        wvAboutUs = (WebView) findViewById(R.id.webab);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //WebView view = new WebView(this);
       // view.getSettings().setJavaScriptEnabled(true);
        //view.loadUrl("file:///android_asset/about.html");
        //setContentView(view);
        wvAboutUs.loadUrl("file:///android_asset/about.html");
        wvAboutUs.clearCache(true);
        wvAboutUs.clearHistory();
        wvAboutUs.getSettings().setJavaScriptEnabled(true);
        wvAboutUs.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}