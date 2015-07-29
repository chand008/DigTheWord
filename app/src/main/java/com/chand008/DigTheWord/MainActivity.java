/************************************************************************************
 *   Copyright (C) 2015 Chandhni Kannatintavida                                     *
 *   This project is licensed under the "MIT License". Please see the file          *
 *   "License.md"(https://github.com/chand008/Dig-The-Word/blob/master/License.md)  *
 *   in this distribution for license terms.                                        *
 *                                                                                  *
 ************************************************************************************/

package com.chand008.DigTheWord;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android .view.View;
import android.content.Intent;

import com.chand008.DigTheWord.R;


public class MainActivity extends ActionBarActivity
{

    FetchDB mydb = new FetchDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*create instance of button and edit text from activity_main.xml
        Button button = (Button) findViewById(R.id.loginbutton);

        /*
        * START create/open database
        * need to use openOrCreateDatabase method ,
        * otherwise you need to have an exception catching to take care of the database already exist situation.
        */

        SQLiteDatabase database = openOrCreateDatabase("MyDBName.db", Context.MODE_PRIVATE, null);
        mydb.createDatabase(database);

        /*
        * END create/open database
        */

        /*
        * START action when login button is clicked
        */
        button.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v)
            {
                 /* this part of code, checks if the entered username is already present in the database, if it is
                 *  present then just pass the value of username and go to displayword activity. if not add the username
                 *  score(as 0) and level as 1 and then pass the value of username and go to displayword activity
                 */
                String etuname1, etuname2;
                EditText etusername = (EditText) findViewById(R.id.Username);
                //*   get the value of username the user entered
                etuname1 = etusername.getText().toString().trim();

                if (mydb.getDataString(etuname1).equals("unknown"))
                {
                    Boolean x = mydb.insertUsername(etuname1, "0");
                    if (x)
                    {
                        System.out.println("Good");
                        etusername.setText(mydb.getDataString(etuname1) + "- just for horror");
                    } else {
                        etusername.setText("False");
                    }
                }
                if (etuname1.isEmpty())
                {
                    etuname1 = "Guest";
                }

                Intent intent = new Intent("android.intent.action.DISPLAY");

                //* START pass extras for teh next activity - 07/12/2015
                intent.putExtra("logged_in_user", mydb.getDataString(etuname1));
                //* END pass extras for teh next activity - 07/12/2015

                //* START close DB - prevent leak 07/18 - 10:30 am - no database leak since
                mydb.close();
                //*END close DB - prevent leak 07/18 - 10:30 am

                startActivity(intent);


            }
        });

        /*
        * END action when login button is clicked
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
