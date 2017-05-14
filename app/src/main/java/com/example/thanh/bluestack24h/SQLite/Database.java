package com.example.thanh.bluestack24h.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thanh.bluestack24h.rss.RSSItem;

import java.util.ArrayList;

/**
 * Created by thanh on 5/12/17.
 */

public class Database {
    private static final String DATABASE_NAME="DB_NEWS";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_RECENT="RECENT";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_LINK="link";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_PUBDATE="pubdate";
    public static final String COLUMN_IMG="img";

    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;


    public Database(Context context) {
        Database.context = context;
    }

    public Database open() throws SQLException{
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }

    public long addItem(RSSItem item){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, item.getTitle());
        cv.put(COLUMN_LINK, item.getLink());
        cv.put(COLUMN_DESCRIPTION, item.getDescription());
        cv.put(COLUMN_PUBDATE, item.getPubdate());
        cv.put(COLUMN_IMG, item.getImg());

        return db.insert(TABLE_RECENT, null, cv);
    }

    public ArrayList<RSSItem> getItems(){
        ArrayList<RSSItem> listItems = new ArrayList<>();
        String columns[] = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_LINK, COLUMN_DESCRIPTION, COLUMN_PUBDATE,COLUMN_IMG};
        Cursor mCursor = db.query(TABLE_RECENT,columns,null,null,null,null,null);
        int iTitle = mCursor.getColumnIndex(COLUMN_TITLE);
        int iLink = mCursor.getColumnIndex(COLUMN_LINK);
        int iDescription = mCursor.getColumnIndex(COLUMN_DESCRIPTION);
        int iPubdate = mCursor.getColumnIndex(COLUMN_PUBDATE);
        int iImg = mCursor.getColumnIndex(COLUMN_IMG);

        for (mCursor.moveToLast(); !mCursor.isBeforeFirst(); mCursor.moveToPrevious()){
            RSSItem item = new RSSItem(mCursor.getString(iTitle), mCursor.getString(iLink), mCursor.getString(iDescription), mCursor.getString(iPubdate),mCursor.getString(iImg));
            listItems.add(item);
        }

        mCursor.close();
        return listItems;
    }

    private class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_RECENT+" ( "+
                    COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_TITLE+" TEXT NOT NULL, "+
                    COLUMN_LINK+" TEXT NOT NULL, "+
                    COLUMN_DESCRIPTION+" TEXT, "+
                    COLUMN_PUBDATE+" TEXT, "+
                    COLUMN_IMG+" TEXT ); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_RECENT);
            onCreate(db);
        }
    }
}
