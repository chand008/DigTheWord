
/************************************************************************************
 *   Copyright (C) 2015 Chandhni Kannatintavida                                     *
 *   This project is licensed under the "MIT License". Please see the file          *
 *   "License.md"(https://github.com/chand008/Dig-The-Word/blob/master/License.md)  *
 *   in this distribution for license terms.                                        *
 *                                                                                  *
 ************************************************************************************/

package com.chand008.DigTheWord;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class notify extends ActionBarActivity {
    String user_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        Bundle xtra = getIntent().getExtras();
        if (xtra != null)
        {
            user_name = xtra.getString("logged_in_user");
        }
        else
        {
            user_name = "Guest";
        }
        TextView Tvusername = (TextView) findViewById(R.id.Tvusername);
        Tvusername.setText(user_name);

        //* If the user clicks OK start the game from level 1 again
        Button b = (Button) findViewById(R.id.ok);
        b.setOnClickListener(new View.OnClickListener() {
            //*ACTION WHEN hint BUTTON IS CLICKED
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.DISPLAY");
                intent.putExtra("logged_in_user", user_name);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
