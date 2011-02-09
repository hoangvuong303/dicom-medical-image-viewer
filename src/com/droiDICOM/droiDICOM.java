package com.droiDICOM;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class droiDICOM extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button browseButton = (Button) findViewById(R.id.browse_button);
        Button aboutButton = (Button)findViewById(R.id.about_button);
        Button exitButton = (Button) findViewById(R.id.exit_button);
    }
    
    
    
}