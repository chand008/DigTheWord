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
                //System.out.println(e);
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    this.onCreateWord(myDataBase);
                }catch(SQLiteException e1){
                    //System.out.println(e1);
                    e1.printStackTrace();
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
               // System.out.println(e);
                e.printStackTrace();
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

            //new set of words
            db.execSQL("insert into worddb values('WAX','gradually increase in intensity or size',1,1,'N',8)");
            db.execSQL("insert into worddb values('AMOK','frenzied or uncontrolled state; as fans meet their rockstar',1,1,'N',9)");
            db.execSQL("insert into worddb values('JUNTA','military takeover or aggressive takeover by a group',2,2,'N',10)");
            db.execSQL("insert into worddb values('MELEE','wild, confusing fight',2,2,'N',11)");
            db.execSQL("insert into worddb values('CANKER','cancer',3,3,'N',12)");
            db.execSQL("insert into worddb values('COEVAL','coexisting, contemporary',3,3,'N',13)");
            db.execSQL("insert into worddb values('FECUND','foetus',3,3,'N',14)");
            db.execSQL("insert into worddb values('FLEECE','to deceive',3,3,'N',15)");
            db.execSQL("insert into worddb values('HEYDEY','prime of a period',3,3,'N',16)");
            db.execSQL("insert into worddb values('VIRAGO','violent ill-tempered women',3,3,'N',17)");
            db.execSQL("insert into worddb values('EXHORT','encourage',3,3,'N',18)");
            db.execSQL("insert into worddb values('FERRET','rig into something',3,3,'N',19)");
            db.execSQL("insert into worddb values('ERRANT','to stray around',3,3,'N',20)");
            db.execSQL("insert into worddb values('ERRAND','to do something',3,3,'N',21)");
            db.execSQL("insert into worddb values('PONDER','contemplate',3,3,'N',22)");
            db.execSQL("insert into worddb values('MACABRE','massacre',4,4,'N',23)");
            db.execSQL("insert into worddb values('DIVULGE','federal police divulge details',4,4,'N',24)");
            db.execSQL("insert into worddb values('POLEMIC','written or verbal attack on someone',4,4,'N',25)");
            db.execSQL("insert into worddb values('HARRIED','harassed',4,4,'N',26)");
            db.execSQL("insert into worddb values('ASKANCE','suspicious - think about old couple watching goth teens',4,4,'N',27)");
            db.execSQL("insert into worddb values('PREEMPT','finish before someone get hold of anything',4,4,'N',28)");
            db.execSQL("insert into worddb values('REPLETE','full',4,4,'N',29)");
            db.execSQL("insert into worddb values('PAUCITY','lack of something; think of time to pause, we dont have anything',4,4,'N',30)");
            db.execSQL("insert into worddb values('SANGUINE','optimistic',5,6,'N',31)");
            db.execSQL("insert into worddb values('REGICIDE','murder of king or queen; murder of ruler',5,6,'N',32)");
            db.execSQL("insert into worddb values('HEGEMONY','dominance of a territory',5,6,'N',33)");
            db.execSQL("insert into worddb values('EDIFYING','intellectually uplifting',5,6,'N',34)");
            db.execSQL("insert into worddb values('CONSTRUE','interpret',5,6,'N',35)");
            db.execSQL("insert into worddb values('EXACTING','demanding accuracy',5,6,'N',36)");
            db.execSQL("insert into worddb values('OPULENCE','wealth which show no bounds',5,6,'N',37)");
            db.execSQL("insert into worddb values('ESOTERIC','arcane, abstruse, obtuse',5,6,'N',38)");
            db.execSQL("insert into worddb values('SPECIOUS','spurious',5,6,'N',39)");
            db.execSQL("insert into worddb values('PITTANCE','small amount of money',5,6,'N',40)");
            db.execSQL("insert into worddb values('BEATIFIC','blissfully happy',5,6,'N',41)");
            db.execSQL("insert into worddb values('EMPIRICAL','earned from experience',6,7,'N',42)");
            db.execSQL("insert into worddb values('SYCOPHANT','person who will do anything or favors for personal gain',6,7,'N',43)");
            db.execSQL("insert into worddb values('SAGACIOUS','wiseman; think about sage',6,7,'N',44)");
            db.execSQL("insert into worddb values('EXCORIATE','chastise, upbraid, castigate, lambast, chide, rebuke, reprimand',6,7,'N',45)");
            db.execSQL("insert into worddb values('MENDACITY','untruthfulness',6,7,'N',46)");
            db.execSQL("insert into worddb values('MALADROIT','clumsy',6,7,'N',47)");
            db.execSQL("insert into worddb values('OLIGARCHS','rich society where power is centered around a few; plutocracy',6,7,'N',48)");
            db.execSQL("insert into worddb values('BELLICOSE','aggressive, warlike',6,7,'N',49)");
            db.execSQL("insert into worddb values('DISPARATE','different, dissimilar',6,7,'N',50)");
            db.execSQL("insert into worddb values('CHECKERED','black-marked due to unfortunate or disreputable happenings',6,7,'N',51)");
            db.execSQL("insert into worddb values('INVECTIVE','abusive',6,7,'N',52)");
            db.execSQL("insert into worddb values('DESECRATE','willfully destroy or violate a holy place; think of it as de-destroy sacred place',6,7,'N',53)");
            db.execSQL("insert into worddb values('VICARIOUS','feel oneself the sensations or emotions felt by someone mostly near or dear',6,7,'N',54)");
            db.execSQL("insert into worddb values('EXORBITANT','expensive',7,8,'N',55)");
            db.execSQL("insert into worddb values('DILETTANTE','amateur',7,8,'N',56)");
            db.execSQL("insert into worddb values('NONPLUSSED','confused on how to react',7,8,'N',57)");
            db.execSQL("insert into worddb values('PUGNACIOUS','ready to fight',7,8,'N',58)");
            db.execSQL("insert into worddb values('HODGEPODGE','mix of things',7,8,'N',59)");
            db.execSQL("insert into worddb values('CADAVEROUS','haggard, emaciated, gaunt',7,8,'N',60)");
            db.execSQL("insert into worddb values('INELUCTABLE','inevitable',8,8,'N',61)");
            db.execSQL("insert into worddb values('PERQUISITES','perks',8,8,'N',62)");
            db.execSQL("insert into worddb values('DELETERIOUS','harmful to living creatures',8,8,'N',63)");
            db.execSQL("insert into worddb values('EFFICACIOUS','efficient, providing desired results',8,8,'N',64)");
            db.execSQL("insert into worddb values('IGNOMINIOUS','embarrassing',8,8,'N',65)");
            db.execSQL("insert into worddb values('MAGNANIMOUS','showing spirit and generous to a person or group, usually rival',8,8,'N',66)");
            db.execSQL("insert into worddb values('EXTENUATING','forgivable; think about exonerating (free from charges)',8,8,'N',67)");
            db.execSQL("insert into worddb values('INSALUBRIOUS','insanitory',8,9,'N',68)");
            db.execSQL("insert into worddb values('IMPONDERABLE','not contemplatable or impossible to estimate or figure out',8,9,'N',69)");
            db.execSQL("insert into worddb values('SURREPTITIOUS','secretive',9,9,'N',70)");
            db.execSQL("insert into worddb values('PRIMOGENITURE','first born child',9,9,'N',71)");
            db.execSQL("insert into worddb values('DISINTERESTED','unbiased, neutral',9,9,'N',72)");
            db.execSQL("insert into worddb values('SANCTIMONIOUS','thought that one is better sanctified than other; holier-than-thou',9,9,'N',73)");
            db.execSQL("insert into worddb values('UNCONSCIONABLE','not agreeable by one’s conscience',9,9,'N',74)");
            db.execSQL("insert into worddb values('ANTHROPOMORPHIC','human like characteristics',10,10,'N',75)");

            //new set of words

        }
        catch(SQLException e) {
            //System.out.println(e);
            e.printStackTrace();
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
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public boolean insertScore  (String uname, int score, int level)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery("select uname,score from user where uname=" + "'" + uname + "'", null);
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
                               // " UPDATE user SET score="+score+" where uname = "+"'"+uname+"'");
                                    "INSERT INTO user VALUES("+"'"+uname+"',"+score+","+level);
                        // +"and level="+level+"");


                    }
                    catch(SQLException e) {
                       // System.out.println(e);
                        e.printStackTrace();
                    }
                }
            }

            //*CLOSE cursor
            res.close();

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
    public int scoreUpdate(String uname, int score, int level)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select uname,score from user where uname=" + "'" + uname + "'", null);
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
                            " UPDATE user SET score="+score+" where uname = "+"'"+uname+"'");
                                    // +"and level="+level+"");


                }
                catch(SQLException e) {
                    //System.out.println(e);
                    e.printStackTrace();
                }
            }
        }

        //*CLOSE cursor
        res.close();
        return dbScore;
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
                //* increment the level no if there are not enough word in that level
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

    /**trial for random word generation**/
    //*GET NEXT WORD ID
//    public int getWordId(int next_level_no){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int random = 0;
//        String v_word_full = "";
//        try {
//            Cursor res = db.rawQuery("select word_id from worddb where level_no=" + next_level_no + " and usage_flag='N'", null);
//            if (res.getCount()>0) {
//                res.moveToFirst();
//            }
//            else{
//                res.close();
//                //*get max level to avoid infinite loop -07/18 - 01:38 PM
//                res = db.rawQuery("select max(level_no) from worddb",null);
//                res.moveToFirst();
//                int max_level_no = Integer.parseInt(res.getString(0));
//                res.close();
//                //*get max level to avoid infinite loop -07/18 - 01:38 PM
//                //* increment the level no if there are not enough word in that level
//                do{
//                    next_level_no++;
//                    res = db.rawQuery("select word_id from worddb where level_no=" + next_level_no + " and usage_flag='N'", null);
//                }while(res.getCount()==0 && next_level_no < max_level_no);
//                res.moveToFirst();
//            }
//            try {
//                int[] wordidlist = new int[0];
//                v_word_full=res.getString(columnIndex("wordid"));
//                for(int i=2;i<=res.getCount();i++)
//                {
//                    v_word_full = v_word_full+res.getString(i);
//
//                }
//                String[] v_word = v_word_full.split("");
//                for(int j=0;j <v_word.length;j++)
//                {
//                    wordidlist[j] =Integer.parseInt(v_word[j]);
//                    System.out.println("huio"+wordidlist[j]);
//                }
//
//
//                random = (int )(Math.random() * (wordidlist.length) + 0);
//                System.out.println("hhhhhhhhh"+res.getCount());
//                System.out.println("huio"+random);
//                return wordidlist[random];
//            } catch (Exception e) {
//                System.out.println("Error with getting word id for level#" + next_level_no);
//                System.out.println("hhhhhhhhh"+res.getCount());
//                System.out.println("huio"+random);
//                e.printStackTrace();
//                return -1;
//            }
//        }
//        catch(SQLiteException e1){
//            System.out.println("Error with SQL query"+ e1);
//            return -1;
//        }
//    }



    /**********end of trial******************/




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
                e.printStackTrace();
                return null;
            } catch (CursorIndexOutOfBoundsException e) {
                System.out.println("Cursor out of bound when " + next_word_id + " - " + e);
                e.printStackTrace();
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

