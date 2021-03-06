package dk.easv.friendsv2.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dk.easv.friendsv2.R;

public class CustomAdapter extends ArrayAdapter<BEFriend> {

    List<BEFriend> friends;
    public CustomAdapter(@NonNull Context context, int resource, List<BEFriend> _friends) {
        super(context, resource);
        friends = _friends;
    }
    @Override
    public int getCount() {
        return friends.size();
    }

    LayoutInflater getLayoutInflater(){
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        if (view == null) {
            view = getLayoutInflater().inflate(R.layout.list_friend, null);
        }
        TextView txtName = view.findViewById(R.id.txtName1);
        ImageView imageView = view.findViewById(R.id.image_profile);

        int defaultHeight = 200;
        int imageWidth = defaultHeight;
        int imageHeight = defaultHeight;
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageWidth,imageHeight);
        int txtViewWidth = 1230;
        int txtViewHeight = defaultHeight;
        LinearLayout.LayoutParams txtViewParams = new LinearLayout.LayoutParams(txtViewWidth, txtViewHeight);

        BEFriend friend = friends.get(position);

        txtName.setText(friend.getName());

        Bitmap bitmap = decodeBase64(friend.getImage());
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(imageParams);
        txtName.setLayoutParams(txtViewParams);

        return view;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length );
        return bitmap;
    }
}
