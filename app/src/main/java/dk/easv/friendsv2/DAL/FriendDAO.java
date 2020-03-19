package dk.easv.friendsv2.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
    private SQLiteStatement updateStmt;

    public FriendDAO(Context ctx){
        OpenHelper openHelper = new OpenHelper(ctx);
        mDatabase = openHelper.getWritableDatabase();

        String INSERT = "insert into " + TABLE_NAME
                + "(name, phone, isDefault, image) values (?,?,?,?)";
        String UPDATE = "UPDATE " + TABLE_NAME
                + " SET name = ?,"
                + " phone = ?,"
                + " isDefault = ?,"
                + " image = ?"
                + " WHERE id = ?";

        insertStmt = mDatabase.compileStatement(INSERT);
        updateStmt = mDatabase.compileStatement(UPDATE);
    }

    @Override
    public long create(BEFriend friend) {
        insertStmt.bindString(1, friend.getName());
        insertStmt.bindString(2, friend.getPhone());
        insertStmt.bindString(3, String.valueOf(friend.isFavorite()));
        insertStmt.bindString(4, friend.getImage());
        return insertStmt.executeInsert();
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
        Cursor cursor = mDatabase.query(TABLE_NAME, new String[] {"id","name","phone", "isFavorite", "image"},
                "id = (?)",new String[] {Integer.toString(id)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                int friendID = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                boolean isFavorite = (cursor.getString(3).equals("true"));
                String image = cursor.getString(4);
                new BEFriend(friendID, name, phone, isFavorite, image);
            }while(cursor.moveToNext());

        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    @Override
    public void update(BEFriend friendToUpdate) {
        updateStmt.bindString(1, friendToUpdate.getName());
        updateStmt.bindString(2, friendToUpdate.getPhone());
        updateStmt.bindString(3, String.valueOf(friendToUpdate.isFavorite()));
        updateStmt.bindString(4, friendToUpdate.getImage());
        updateStmt.bindLong(5, friendToUpdate.getId());

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
