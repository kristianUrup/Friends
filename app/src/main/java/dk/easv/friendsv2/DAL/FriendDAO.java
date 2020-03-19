package dk.easv.friendsv2.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
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
                + "(name, phone, isFavorite, image) values (?,?,?,?)";
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
        ArrayList<BEFriend> m_friends;

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT, phone TEXT, isFavorite TEXT, image TEXT)");
            String defaultImage = "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";
            m_friends = new ArrayList<BEFriend>();
            m_friends.add(new BEFriend("Alex", "123456", true, defaultImage));
            m_friends.add(new BEFriend("Anders", "234567", defaultImage));
            m_friends.add(new BEFriend("Andreas", "126256", true, defaultImage));
            m_friends.add(new BEFriend("Asvør", "234567", defaultImage));
            m_friends.add(new BEFriend("Casper", "123456", true, defaultImage));
            m_friends.add(new BEFriend("Christian", "994567", defaultImage));
            m_friends.add(new BEFriend("Daniel", "127426", defaultImage));
            m_friends.add(new BEFriend("David", "204587", true, defaultImage));
            m_friends.add(new BEFriend("Grzegorz", "123456", defaultImage));
            m_friends.add(new BEFriend("Henrik", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Huseen", "123456", defaultImage));
            m_friends.add(new BEFriend("Jakub", "234567", defaultImage));
            m_friends.add(new BEFriend("Jan", "123456", defaultImage));
            m_friends.add(new BEFriend("Jørgen", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Kasper", "123456", defaultImage));
            m_friends.add(new BEFriend("Kristian", "234567", defaultImage));
            m_friends.add(new BEFriend("Mads", "123456", true, defaultImage));
            m_friends.add(new BEFriend("Mark", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Marek", "123456", defaultImage));
            m_friends.add(new BEFriend("Martin", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Mate", "123456", defaultImage));
            m_friends.add(new BEFriend("Mathias", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Nedas", "234567", defaultImage));
            m_friends.add(new BEFriend("Nijas", "123456", defaultImage));
            m_friends.add(new BEFriend("Niklas", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Philip", "123456", defaultImage));
            m_friends.add(new BEFriend("Simon", "234567", defaultImage));
            m_friends.add(new BEFriend("Szymon", "234567", true, defaultImage));
            m_friends.add(new BEFriend("Theis", "123456", defaultImage));
            m_friends.add(new BEFriend("Thorbjørn", "234567", defaultImage));
            for (BEFriend friend: m_friends) {
                ContentValues values = new ContentValues();
                values.put("name", friend.getName());
                values.put("phone", friend.getPhone());
                values.put("isFavorite", String.valueOf(friend.isFavorite()));
                values.put("image", friend.getImage());
                db.insert(TABLE_NAME, null, values);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
