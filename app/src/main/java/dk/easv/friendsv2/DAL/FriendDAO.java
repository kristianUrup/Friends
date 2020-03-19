package dk.easv.friendsv2.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import dk.easv.friendsv2.Model.BEFriend;

public class FriendDAO implements IFriendDAO {

    private static final String DATABASE_NAME = "Friend.Database";
    private static final String TABLE_NAME = "Friends";
    private static final int VERSION = 2;

    private SQLiteDatabase database;
    private SQLiteStatement insertStmt;

    public FriendDAO(Context context) {
        String INSERT = "INSERT INTO " + TABLE_NAME
                + "(name, phone, isFavorite, image) VALUES(?,?,?,?)";
    }

    @Override
    public long create(BEFriend friend) {
        return 0;
    }

    @Override
    public void deleteById() {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BEFriend> selectAll() {
        return null;
    }

    @Override
    public void update(BEFriend friendToUpdate) {

    }
}
