/************************************************************************************
 *   Copyright (C) 2015 Chandhni Kannatintavida                                     *
 *   This project is licensed under the "MIT License". Please see the file          *
 *   "License.md"(https://github.com/chand008/Dig-The-Word/blob/master/License.md)  *
 *   in this distribution for license terms.                                        *
 *                                                                                  *
 ************************************************************************************/

package com.chand008.DigTheWord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import java.lang.*;

/**
 * Created by Chandhni on 7/10/2015.
 */
public class FetchDB extends SQLiteOpenHelper
{

        public static final String DATABASE_NAME = "MyDBName.db";
        public static final String USER_TABLE_NAME = "user";
        public static final String USER_COLUMN_NAME = "uname";
        public static final String USER_COLUMN_SCORE = "score";
        //private HashMap hp;

        public FetchDB(Context context)
        {
            super(context, DATABASE_NAME , null, 1);
        }

        public void createDatabase(SQLiteDatabase myDataBase)
        {

            try
            {
                this.onCreate(myDataBase);
            } catch (SQLiteException e) {
                System.out.println(e);
            }
            finally
            {
                try
                {
                    this.onCreateWord(myDataBase);
                }catch(SQLiteException e1){
                    System.out.println(e1);
                }
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // TODO Auto-generated method stub
            //*do a try catch here!! catch in case SQLiteDBExceptions-done
            try
            {
                db.execSQL(
                        " CREATE TABLE user"
                                + "(uname text PRIMARY KEY DEFAULT ('Guest') ,score integer DEFAULT (0) ,level INTEGER DEFAULT (1) )");


               }
            catch(SQLException e) {
                System.out.println(e);
            }
        }

    //*word table - 07/17/2015
    public void onCreateWord(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        //*do a try catch here!! catch in case SQLiteDBExceptions
        try
        {
            db.execSQL(
                    " CREATE TABLE worddb"
                            + "(word text ,hint text, level_no integer,severity integer,usage_flag char(1),word_id integer PRIMARY KEY)");
            db.execSQL("insert into worddb values('FOOD','KEEPS US ALIVE',1,1,'N',1)");
            db.execSQL("insert into worddb values('BUG','HATE THIS IN A CODE',1,1,'N',2)");
            db.execSQL("insert into worddb values('LOVE','CANNOT LIVE WITHOUT',1,1,'N',3)");
            db.execSQL("insert into worddb values('MICROBE','NEED A MICROSCOPE TO SEE THEM',1,2,'N',4)");
            db.execSQL("insert into worddb values('COG','GEAR-UP',1,2,'N',5)");
            db.execSQL("insert into worddb values('GLEAN','GATHER',2,3,'N',6)");
            db.execSQL("insert into worddb values('OSSIFY','OUTDATED',2,3,'N',7)");

        }
        catch(SQLException e) {
            System.out.println(e);
        }
//        finally {
//            try {
////                db.execSQL("insert into worddb values('FOOD','KEEPS US ALIVE',1,1,'N',1)");
////                db.execSQL("insert into worddb values('BUG','HATE THIS IN A CODE',1,1,'N',2)");
////                db.execSQL("insert into worddb values('LOVE','CANNOT LIVE WITHOUT',1,1,'N',3)");
////                db.execSQL("insert into worddb values('MICROBE','NEED A MICROSCOPE TO SEE THEM',1,2,'N',4)");
////                db.execSQL("insert into worddb values('COG','GEAR-UP',1,2,'N',5)");
////                db.execSQL("insert into worddb values('GLEAN','GATHER',2,3,'N',6)");
////                db.execSQL("insert into worddb values('OSSIFY','OUTDATED',2,3,'N',7)");
//            }
//            catch(SQLiteException e1){
//                System.out.println("Insert word exception" + e1);
//            }
//        }
    }

    //*word table - 07/17/2015

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS user");
            onCreate(db);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public boolean insertUsername  (String uname, String score)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (uname.isEmpty())
            {
                System.out.println("username blank");
            }
            else
            {
                contentValues.put("uname", uname);

            }

            contentValues.put("score", score);
            db.insert("user", null, contentValues);
            //*CLOSE db -07/18 10:10 am
            db.close();
            //*CLOSE db -07/18 10:10 am
            return true;
        }

        public Cursor getData(String uname)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from user where uname="+"'"+uname+"'",null);

            //*CLOSE cursor -07/18 10:14 am
            Cursor res_copy = res;
            res.close();
            //*CLOSE cursor -07/18 10:14 am

            return res_copy;
        }


    public String getDataString(String uname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from user where uname=" + "'" + uname + "'", null);
        //Cursor res =  db.rawQuery("select * from user where uname= ?" ,uname);
        //db.close();
        res.moveToFirst();
        String username ="null";
        if (res.getCount()!=0)
        {
            username = res.getString(0);

        }
        else
        {
            username = "unknown";
        }

        //*CLOSE cursor -07/18 10:19 am
        res.close();
        //*CLOSE cursor -07/18 10:19 am

        return username;

    }
    /*
    * This method updates the score of the user when back button is hit or when user completes
    * a level.
     */
    public Boolean scoreUpdate(String uname, int score, int level)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select username,score from user where uname=" + "'" + uname + "'", null);
        res.moveToFirst();
        int dbScore =0;
        if (res.getCount()!=0)
        {
            dbScore = Integer.valueOf(res.getString(1));
            if(score > dbScore)
            {
                try
                {
                    db.execSQL(
                            " UPDATE user"
                                    + "SET score="+score+"(where uname = "+"'"+uname+"'" +"and level="+level+")");


                }
                catch(SQLException e) {
                    System.out.println(e);
                }
            }
        }
        else
        {
            return false;
        }

        //*CLOSE cursor
        res.close();
        return true;
    }

    //*GET NEXT WORD ID
    public int getWordIdForGame(int next_level_no){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select word_id from worddb where level_no=" + next_level_no + " and usage_flag='N'", null);
            if (res.getCount()>0) {
                res.moveToFirst();
            }
            else{
                res.close();
                //*get max level to avoid infinite loop -07/18 - 01:38 PM
                res = db.rawQuery("select max(level_no) from worddb",null);
                res.moveToFirst();
                int max_level_no = Integer.parseInt(res.getString(0));
                res.close();
                //*get max level to avoid infinite loop -07/18 - 01:38 PM

                do{
                    next_level_no++;
                    res = db.rawQuery("select word_id from worddb where level_no=" + next_level_no + " and usage_flag='N'", null);
                }while(res.getCount()==0 && next_level_no < max_level_no);
                res.moveToFirst();
            }
            try {
                String v_word_id = res.getString(res.getColumnIndex("word_id"));
                return Integer.parseInt(v_word_id);
            } catch (Exception e) {
                System.out.println("Error with getting word id for level#" + next_level_no);
                return -1;
            }
        }
        catch(SQLiteException e1){
            System.out.println("Error with SQL query"+ e1);
            return -1;
        }
    }

    //*GET NEXT WORD
    public String getWordForGame(int next_word_id )
    {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("This is what i got in getWordForGame method: "+next_word_id);

        if (next_word_id > 0)
        { //*check word id for EOD..
            try
            {
                Cursor res = db.rawQuery("select word from worddb where word_id=" + next_word_id, null);
//            System.out.println("res.getColumnIndex(word)="+res.getColumnIndex("word") + " and Count =" + res.getCount());
                res.moveToFirst();
                String v_word = res.getString(res.getColumnIndex("word"));
//            System.out.println("The word picked from datbase is : "+ v_word);
                res.close();
                return v_word;
            } catch (SQLiteException e) {
                System.out.println("Error with getting word for word id#" + next_word_id);
                return null;
            } catch (CursorIndexOutOfBoundsException e) {
                System.out.println("Cursor out of bound when " + next_word_id + " - " + e);
                return null;
            }
        }
        else
        {   //*check word id for EOD..
            return "End of Game";
        }//*check word id for EOD..
    }

    //*UPDATE USAGE FLAG
    public boolean setFlag(String word)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try
        {
            Cursor res =  db.rawQuery( "update worddb set usage_flag = 'Y' where word="+"'"+word.trim()+"'",null);
            res.moveToFirst();
            res.close();
            return true;
        }
        catch(SQLiteException e) {
            System.out.println("Error updating usage flag for " + word);
            return false;
        }
    }

    //*UPDATE all USAGE FLAG
    public boolean setAllFlag()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try
        {
            Cursor res =  db.rawQuery( "update worddb set usage_flag = 'N'",null);
            res.moveToFirst();
            res.close();
            return true;
        }
        catch(SQLiteException e) {
            System.out.println("Error updating usage flag for all words");
            return false;
        }
    }

    //*query word hint
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public String getWordHint(String word)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try
        {
            Cursor res =  db.rawQuery("select hint from worddb where word=" + "'" + word.trim() + "'", null);
            res.moveToFirst();
            String hint_statement = res.getString(res.getColumnIndex("hint"));
            res.close();
            if (!hint_statement.isEmpty()) {
                return hint_statement;
            }
            else
            {
                return "No hint!! this should be an easy one :) ";
            }
        }
        catch(SQLiteException e) {
            System.out.println("Error updating usage flag for " + word);
            return "No hint!! this should be an easy one :) ";
        }
        catch (CursorIndexOutOfBoundsException e) {
            System.out.println("Cursor out of bound when " + word + " - " + e);
            return null;
        }
    }
}

