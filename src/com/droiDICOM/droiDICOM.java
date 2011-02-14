package com.droiDICOM;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;

public class droiDICOM extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button browseButton = (Button) findViewById(R.id.browse_button);
        browseButton.setOnClickListener(this);
        Button aboutButton = (Button)findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent i;
		switch(v.getId()) {
		case R.id.browse_button:
			i = new Intent(this, BrowseFiles.class);
			startActivity(i);
			break;
		case R.id.about_button:
			i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.exit_button:
			this.finish();
			break;
		}
		
	}
    
    
    
}