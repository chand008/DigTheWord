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

    String word_to_guess,wtgBlank,level;
    int score=0,noOfTry=5;
    String user_name = null,Score="Score",attempt="*****";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayword);

        /*
        * START get extras from previous activity - 07122015 01:25 PM PST
        * This will pass the username from log in to this activity
        */
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
         //*END get extras from previous activity - 07122015 01:25 PM PST

        level="Level"+" " +Integer.toString(1);
        // Score= Score+" " +Integer.toString(score);
        final TextView TvLevel = (TextView) findViewById(R.id.TvLevel);
        TvLevel.setText(level);
        final TextView TvScore = (TextView) findViewById(R.id.TvScore);
        TvScore.setText(Score);
        final TextView Tvattempt =(TextView) findViewById(R.id.Tvattempt);
        Tvattempt.setText(attempt);
        final TextView Tvmsg =(TextView) findViewById(R.id.Tvmsg);
        Tvmsg.setText("");

        /*
         *START display word and enter word
        */
        Button button;
        button = (Button) findViewById(R.id.Benter);
        Button button_copy;

        final EditText etguessword;
        etguessword = (EditText) findViewById(R.id.Etcguessword);

        final TextView[] tvresult = new TextView[1];
        final String[] etgwrd = new String[1];

        //* START get the word from database
        final FetchDB mydb = new FetchDB(this);
        final TextView[] tvword = new TextView[1];
        tvword[0] = (TextView) findViewById(R.id.Tvword);
        word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));
        //* END get word from database

        //* START call method to blank a letter and set the word with letter blank
        wtgBlank= blankLetter(word_to_guess);
        tvword[0].setText(wtgBlank);
        //* END call method to blank a letter and set the word with letter blank

        /*
         *END display word and enter word
        */

        Button nextword = new Button(this);
        nextword = (Button) findViewById(R.id.Bnextbutton);

        /*
        * START ACTION WHEN NEXT BUTTON IS CLICKED
        */
        nextword.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //* START get word from database
                word_to_guess = mydb.getWordForGame(mydb.getWordIdForGame(1));
                //* END get word from database
                //*call method to blank a letter in word and display it
               // tvword[0].setText(word_to_guess);
                wtgBlank=blankLetter(word_to_guess);
                tvword[0].setText(wtgBlank);
                etguessword.setText(null);
                TextView tvresult = (TextView) findViewById(R.id.Tvguesswordlabel);
                tvresult.setText("Enter your Guess:");
                TextView tvhinttext = (TextView) findViewById(R.id.Tvhinttext);
                tvhinttext.setText(null);
                //TextView TvScore = (TextView) findViewById(R.id.TvScore);
                TvScore.setText(Score);
                Tvmsg.setText("");
            }
        });
        /*
        *  END ACTION WHEN NEXT BUTTON IS CLICKED
        */
        button_copy = button;

        button_copy.setOnClickListener(new View.OnClickListener() {
            //*ACTION WHEN TRY BUTTON IS CLICKED
            @Override
            public void onClick(View v) {
                //   TODO Auto-generated method stub

                tvresult[0] = (TextView) findViewById(R.id.Tvmsg);
                etgwrd[0] = etguessword.getText().toString();

                //*compare to see if the word entered by user mathes the word from db
                if (etgwrd[0].equals(word_to_guess)) {
                    tvresult[0].setText("Congrats!!! correct dig!!!");
                    score = score + 100;
                    Score= "Score "+Integer.toString(score);

                    //set a flag so that this word is not repeated for the same session
                    if (mydb.setFlag(word_to_guess)) {
                        System.out.println("Update on usage flag was successful");
                    } else {
                        System.out.println("Update on usage flag was NOT successful");
                    }
                }
                else
                {
                    tvresult[0].setText("Incorrect word");
                    noOfTry -= 1;
                    editAttempt(noOfTry);
                    if (noOfTry==0)
                    {
                        restart();
                    }
                }
                TvScore.setText(Score);
                Tvattempt.setText(attempt);
            }

        });

        /*
        * START Hint buttton : action when hint button is clicked
        */
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
//                TextView word_on_screen = (TextView) findViewById(R.id.Tvword);
//                String hint_statement = mydb.getWordHint(word_on_screen.getText().toString());
                String hint_statement = mydb.getWordHint(word_to_guess);
                System.out.println("this is the hint:- "+hint_statement);
                tvhinttext.setText(hint_statement);
            }
        });
        /*
        * END Hint buttton : action when hint button is clicked
        */
    }

    /*
    * START BACK buttton : action when hint button is clicked
    */
    public void onclick(View view){
        //* Method invoked when BACK button is clicked
        restart();
    }
    /*
    * END BACK buttton : action when hint button is clicked
    */

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

    public void editAttempt(int n)
    {
        /*
        * This method will reduce the number of attempts left for each incorrect word.
        *  Default is all attempts exhausted.
        */
        switch (n) {
            case 1:  attempt = "WRON*";
                     break;
            case 2:  attempt = "WRO**";
                     break;
            case 3: attempt = "WR***";
                    break;
            case 4: attempt = "W****";
                    break;
            default: attempt = "WRONG";
                     break;
        }

    }

    public void restart()
    {
        /*
        * This method defines the actions when back button is clicked.
        */
        //*Attempt to reset flags
        final FetchDB mydb = new FetchDB(this);
        Thread runthis = new Thread() {
            public void run() {

                if (mydb.setAllFlag())
                {
                    System.out.println("Reset all flags");
                   // Boolean r = mydb.scoreUpdate(user_name,score,1);
                    noOfTry=5;
                    score=0;
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

