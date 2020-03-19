package dk.easv.friendsv2.DAL;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import dk.easv.friendsv2.Model.BEFriend;

public class FriendDAO implements IFriendDAO {


    private static final String DATABASE_NAME = "sqlite.mDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Friend";

    private SQLiteDatabase mDatabase;
    private SQLiteStatement insertStmt;

    public FriendDAO(Context ctx){
        OpenHelper openHelper = new OpenHelper(ctx);
        mDatabase = openHelper.getWritableDatabase();

        String INSERT = "insert into " + TABLE_NAME
                + "(name) values (?)";

        insertStmt = mDatabase.compileStatement(INSERT);
    }

    @Override
    public long create(BEFriend friend) {
        return 0;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BEFriend> getAllFriends() {
        return null;
    }

    @Override
    public BEFriend getFriendById(int id) {
        Cursor cursor = mDatabase.query(TABLE_NAME, new String[] {"id","name","isFavorite","image"},
                "",null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                new BEFriend(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            }
        }
        return null;
    }

    @Override
    public void update(BEFriend friendToUpdate) {

    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
