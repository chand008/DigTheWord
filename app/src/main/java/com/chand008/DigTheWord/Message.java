/************************************************************************************
 *   Copyright (C) 2015 Chandhni Kannatintavida                                     *
 *   This project is licensed under the "MIT License". Please see the file          *
 *   "License.md"(https://github.com/chand008/Dig-The-Word/blob/master/License.md)  *
 *   in this distribution for license terms.                                        *
 *                                                                                  *
 ************************************************************************************/
package com.chand008.DigTheWord;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Message extends ActionBarActivity {
    String user_name = "Unknown";
    //** Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    Bundle xtra = getIntent().getExtras();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        if (xtra != null)
        {
            user_name = xtra.getString("logged_in_user");
        }
        else
        {
            user_name = "Guest";
        }
        System.out.println("This is the user name passed: " + user_name);
        TextView tvusername = (TextView) findViewById(R.id.Tvusername);
        tvusername.setText(user_name);


         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent("android.intent.action.DISPLAY");
                intent.putExtra("logged_in_user", user_name);
                intent.putExtra("score", xtra.getString("score"));
                intent.putExtra("level_no",  xtra.getString("level_no"));
                startActivity(intent);
                Message.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
