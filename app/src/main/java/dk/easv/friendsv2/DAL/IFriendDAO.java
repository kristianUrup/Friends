package dk.easv.friendsv2.DAL;

import java.util.List;

import dk.easv.friendsv2.Model.BEFriend;

public interface IFriendDAO {
    long create(BEFriend friend);
    void deleteById();
    void deleteAll();
    List<BEFriend> selectAll();
    void update(BEFriend friendToUpdate);
}
