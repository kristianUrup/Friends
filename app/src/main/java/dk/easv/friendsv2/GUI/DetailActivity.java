package dk.easv.friendsv2.GUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dk.easv.friendsv2.DAL.FriendDAO;
import dk.easv.friendsv2.DAL.FriendDataAccess;
import dk.easv.friendsv2.DAL.IFriendDAO;
import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.R;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_BY_BITMAP = 101;
    static int PERMISSION_REQUEST_CODE = 1;

    EditText etName;
    EditText etPhone;
    CheckBox cbFavorite;
    ImageView imgProfilePic;
    Button btnOK;
    Button btnCancel;
    Button btnDeleteFriend;
    Toolbar toolbar;

    IFriendDAO fDao;

    BEFriend friend;
    int friendPosInListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fDao = FriendDataAccess.getInstance(this);

        checkPermission();

        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);
        imgProfilePic = findViewById(R.id.imgProfilePic);
        btnOK = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        btnDeleteFriend = findViewById(R.id.delete_friend);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setGUI();
        friendPosInListView = getIntent().getExtras().getInt("position");

        imgProfilePic.setOnClickListener(view -> takePicture());

        btnOK.setOnClickListener(view -> onClickOK());

        btnCancel.setOnClickListener(view -> onClickCancel());

        btnDeleteFriend.setOnClickListener(view -> deleteFriend(view));
    }

    private void setGUI()
    {
        friend = fDao.getFriendById(getIntent().getExtras().getInt("id"));

        etName.setText(friend.getName());
        etPhone.setText(friend.getPhone());
        cbFavorite.setChecked(friend.isFavorite());

        int imageWidth = 300;
        int imageHeight = imageWidth;
        TableRow.LayoutParams imageParams = new TableRow.LayoutParams(imageWidth,imageHeight);

        byte[] decodedBytes = Base64.decode(friend.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length );
        imgProfilePic.setImageBitmap(bitmap);
        imgProfilePic.setLayoutParams(imageParams);
    }

    private void checkPermission() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }

        ArrayList<String> permissions = new ArrayList<String>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), PERMISSION_REQUEST_CODE);
        }
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

    public void openFacebook(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1787040838249981"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/kristian.urup")));
        }
    }

    private void takePicture() {

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
                int height = imgProfilePic.getHeight();
                int width = imgProfilePic.getWidth();
                imgProfilePic.setImageBitmap(imageBitmap);
                imgProfilePic.setLayoutParams(new TableRow.LayoutParams(height, width));
                imageToString(imageBitmap);
            }
        }
    }

    private void imageToString(Bitmap bitmap){
        //BitmapDrawable drawableBit = (BitmapDrawable) imgProfilePic.getDrawable();
        //Bitmap bitmap = drawableBit.getBitmap();
        String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
        friend.setImage(encodedImage);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void onClickOK() {
        Log.d(TAG, "Clicked OKAY");
        Intent intent = new Intent();
        String base64Pic = friend.getImage();
        friend = new BEFriend(etName.getText().toString(),
                etPhone.getText().toString(),
                base64Pic);
        intent.putExtra("friend", friend);
        intent.putExtra("position", friendPosInListView);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void onClickCancel() {
        Log.d(TAG, "Clicked CANCEL");
        setResult(RESULT_CANCELED);
        finish();
    }

    private void deleteFriend(View view){
        fDao.deleteById(friend.getId());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
