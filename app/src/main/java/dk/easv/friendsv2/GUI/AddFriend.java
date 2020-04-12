package dk.easv.friendsv2.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

import dk.easv.friendsv2.DAL.FriendDAO;
import dk.easv.friendsv2.DAL.FriendDataAccess;
import dk.easv.friendsv2.DAL.IFriendDAO;
import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.R;

public class AddFriend extends AppCompatActivity {

    String TAG = MainActivity.TAG;
    CheckBox isFavorized;
    TextView txtName;
    TextView txtPhone;
    FriendDAO fDao;
    ImageView imgProfile;
    FrameLayout frameLayout;
    String imgProfileString;
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BY_BITMAP = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        frameLayout = findViewById(R.id.frame_layout);
        fDao = (FriendDAO) FriendDataAccess.getInstance(this);
        isFavorized = findViewById(R.id.is_favorite);
        txtName = findViewById(R.id.txt_name_add);
        txtPhone = findViewById(R.id.txt_phone_add);
        imgProfile = findViewById(R.id.img_friend);

        imgProfile.setOnClickListener(view -> takePicture());
    }

    public void onOkClick(View view){
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        boolean isFavorite = isFavorized.isChecked();
        if((name.isEmpty() || phone.isEmpty()) ){
            Toast.makeText(getApplicationContext(),"Phone and name needs to be filled",Toast.LENGTH_SHORT).show();
        }
        else{
            BEFriend friendToAdd = new BEFriend(name, phone, isFavorite, imgProfileString);
            fDao.create(friendToAdd);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BY_BITMAP);
        } else
            Log.d(TAG, "camera app could NOT be started");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BY_BITMAP) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Log.d(TAG, "Size of bitmap = " + imageBitmap.getByteCount());
                int height = imgProfile.getHeight();
                int width = imgProfile.getWidth();
                FrameLayout.LayoutParams params  = new FrameLayout.LayoutParams(width, height);
                imgProfile.setLayoutParams(params);
                imgProfile.setImageBitmap(imageBitmap);
                imgProfileString = imageToString(imageBitmap);
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        return encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
