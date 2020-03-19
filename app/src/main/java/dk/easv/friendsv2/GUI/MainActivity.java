package dk.easv.friendsv2.GUI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import dk.easv.friendsv2.GUI.DetailActivity;
import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.CustomAdapter;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";

    Friends m_friends;
    CustomAdapter adapter1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();

        adapter1 = new CustomAdapter(this,android.R.layout.simple_list_item_1, m_friends);

        setListAdapter(adapter1);

    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        Log.d(TAG, "Detail activity will be started");
        BEFriend friend = m_friends.getAll().get(position);
        addData(x, friend);
        x.putExtra("position", position);
        startActivityForResult(x, 10);
        Log.d(TAG, "Detail activity is started");

    }

    private void addData(Intent x, BEFriend f)
    {
        x.putExtra("id", f.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Got result back");
        if (resultCode == RESULT_OK) {
            BEFriend updatedFriend = (BEFriend) data.getExtras().getSerializable("friend");
            int position = data.getExtras().getInt("position");
            Log.d(TAG, "Updated friend: " + updatedFriend.getName() + " " + updatedFriend.getImage() + " " + updatedFriend.getPhone());
            m_friends.update(updatedFriend, position);
        }
    }
}
