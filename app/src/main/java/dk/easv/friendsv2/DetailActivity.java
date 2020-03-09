package dk.easv.friendsv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;

import dk.easv.friendsv2.Model.BEFriend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;

    EditText etName;
    EditText etPhone;
    CheckBox cbFavorite;

    BEFriend friend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);

        setGUI();


    }

    private void setGUI()
    {
        friend = (BEFriend) getIntent().getSerializableExtra("friend");

        etName.setText(friend.getName());
        etPhone.setText(friend.getPhone());
        cbFavorite.setChecked(friend.isFavorite());
    }

    public void openGMail(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Emailing link");
        intent.putExtra(Intent.EXTRA_TEXT, "Link is \n" +
                "This is the body of hte message");
        startActivity(Intent.createChooser(intent, ""));
    }

    public void openDialer(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel" + friend.getPhone()));
        startActivity(intent);
    }
}
