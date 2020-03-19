package dk.easv.friendsv2.DAL;

import java.util.List;

import dk.easv.friendsv2.Model.BEFriend;

public interface IFriendDAO {
    long create(BEFriend friend);
    void deleteById(int id);
    void deleteAll();
    List<BEFriend> getAllFriends();
    BEFriend getFriendById(int id, String start);
    void update(BEFriend friendToUpdate);
}
