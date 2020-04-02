package dk.easv.friendsv2.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import dk.easv.friendsv2.DAL.FriendDAO;
import dk.easv.friendsv2.DAL.FriendDataAccess;
import dk.easv.friendsv2.DAL.IFriendDAO;
import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.R;

public class AddFriend extends AppCompatActivity {

    CheckBox isFavorized;
    TextView txtName;
    TextView txtPhone;
    FriendDAO fDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        fDao = (FriendDAO) FriendDataAccess.getInstance(this);
        isFavorized = findViewById(R.id.is_favorite);
        txtName = findViewById(R.id.txt_name_add);
        txtPhone = findViewById(R.id.txt_phone_add);
    }

    public void onOkClick(View view){
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        boolean isFavorite = isFavorized.isChecked();
        String defaultImage = "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";
        if((name.isEmpty() || phone.isEmpty()) ){
            Toast.makeText(getApplicationContext(),"Phone and name needs to be filled",Toast.LENGTH_SHORT).show();
        }
        else{
            BEFriend friendToAdd = new BEFriend(name, phone, isFavorite, defaultImage);
            fDao.create(friendToAdd);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
