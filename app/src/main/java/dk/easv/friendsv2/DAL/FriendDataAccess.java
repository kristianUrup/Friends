package dk.easv.friendsv2.DAL;

import android.content.Context;

public class FriendDataAccess {
    public static IFriendDAO getInstance(Context c)
    { return new FriendDAO(c); }
}
