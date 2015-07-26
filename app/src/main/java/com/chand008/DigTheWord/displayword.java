/************************************************************************************
 *   Copyright (C) 2015 Chandhni Kannatintavida                                     *
 *   This project is licensed under the "MIT License". Please see the file          *
 *   "License.md"(https://github.com/chand008/Dig-The-Word/blob/master/License.md)  *
 *   in this distribution for license terms.                                        *
 *                                                                                  *
 ************************************************************************************/

package com.chand008.DigTheWord;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chand008.DigTheWord.R;


public class displayword extends Activity {

    String word_to_guess,wtgBlank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayword);

        String user_name = null;
        //*get extras from previous activity - 07122015 01:25 PM PST
        Bundle xtra = getIntent().getExtras();

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
        //*get extras from previous activity - 07122015 01:25 PM PST

        //*code to display word and enter correct word
        Button button;
        button = (Button) findViewById(R.id.Benter);
        Button button_copy;
        final EditText etguessword;
        final TextView[] tvresult = new TextView[1];
        final String[] etgwrd = new String[1];

        etguessword = (EditText) findViewById(R.id.Etcguessword);

        //*get the word from database

        final FetchDB mydb = new FetchDB(this);
        final TextView[] tvword = new TextView[1];
        tvword[0] = (TextView) findViewById(R.id.Tvword);
        //String word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));
        word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));

        //*call method to blank a letter
        //tvword[0].setText(word_to_guess);
        wtgBlank= blankLetter(word_to_guess);
       // tvword[0].setText(blankLetter(word_to_guess));
        tvword[0].setText(wtgBlank);

        Button nextword = new Button(this);
        nextword = (Button) findViewById(R.id.Bnextbutton);

        nextword.setOnClickListener(new View.OnClickListener()
        {

            //* ACTION WHEN NEXT BUTTON IS CLICKED
            @Override
            public void onClick(View v)
            {
                //*get word from database
                String word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));
                //*call method to blank a letter in word
               // tvword[0].setText(word_to_guess);
                wtgBlank=blankLetter(word_to_guess);
                tvword[0].setText(wtgBlank);
                etguessword.setText(null);
                TextView tvresult = (TextView) findViewById(R.id.Tvguesswordlabel);
                tvresult.setText("Enter your Guess:");
                TextView tvhinttext = (TextView) findViewById(R.id.Tvhinttext);
                tvhinttext.setText(null);
            }
        });

        button_copy = button;

        button_copy.setOnClickListener(new View.OnClickListener()
        {
            //*ACTION WHEN TRY BUTTON IS CLICKED
            @Override
            public void onClick(View v)
            {
              //   TODO Auto-generated method stub
                //*get word from db
               // String word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));

                //*call method to blank letter in word
               // tvword[0].setText(word_to_guess);
              //  tvword[0].setText(blankLetter(word_to_guess));

                tvresult[0] = (TextView) findViewById(R.id.Tvguesswordlabel);
                etgwrd[0] = etguessword.getText().toString();
                //*compare to see if the word entered by user mathes the word from db
                if (etgwrd[0].equals(word_to_guess))
                {
                    tvresult[0].setText("Congrats!!! correct dig!!!");
                    //set a flag so that this word is not repeated for the same session
                    if (mydb.setFlag(word_to_guess))
                    {
                        System.out.println("Update on usage flag was successful");
                    }
                    else
                    {
                        System.out.println("Update on usage flag was NOT successful");
                    }
                }
                else
                {
                    tvresult[0].setText("Incorrect word");
                }
            }
        });

        Button hint = new Button(this);
        hint = (Button) findViewById(R.id.Bhint);
        final TextView tvhinttext = (TextView) findViewById(R.id.Tvhinttext);

        hint.setOnClickListener(new View.OnClickListener()
        {
            //*ACTION WHEN hint BUTTON IS CLICKED
            @Override
            public void onClick(View v)
            {
                //*get the word on screen and find the hint from the db
                TextView word_on_screen = (TextView) findViewById(R.id.Tvword);
                String hint_statement = mydb.getWordHint(word_on_screen.getText().toString());
                System.out.println("this is the hint:- "+hint_statement);
                tvhinttext.setText(hint_statement);
            }
        });
    }
    public void onclick(View view){
        /*
        * Method invoked when BACK button is clicked
        */
        //*Attempt to reset flags
        final FetchDB mydb = new FetchDB(this);
        Thread runthis = new Thread() {
            public void run() {

                if (mydb.setAllFlag())
                {
                    System.out.println("Reset all flags");
                } else

                {
                    System.out.println("Flags not reset");
                }
            }
        };

        runthis.start();

        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public String blankLetter(String word)
    {
    /*
    * This method will blank a random letter of the word and
    * return the word. The word from db is split into char
    * and one char is changed as _ and its stored in a variable
    * which is returned.
    */
       // String wtgBlank = null;
        String[] wordElements = word.split("");
        String wtgBlank = wordElements[0];
        int j = randomNumber(word.length());
        for(int i=1;i<=word.length();i++)
        {
            if(i==j)
                wordElements[i] = "_";

            wtgBlank = wtgBlank+ wordElements[i] ;
        }

        return wtgBlank;
    }
    public int randomNumber(int limit)
    {
    /*
    * This method generates random numbers using math.random()
    */
        int random = (int )(Math.random() * limit + 1);
        return random;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_displayword, menu);
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

