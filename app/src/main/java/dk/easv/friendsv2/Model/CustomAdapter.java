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

import dk.easv.friendsv2.R;

public class CustomAdapter extends ArrayAdapter<BEFriend> {

    Friends friends;
    public CustomAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        friends = new Friends();
    }
    @Override
    public int getCount() {
        return friends.m_friends.size();
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

        int width = 200;
        int height = width;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);

        BEFriend friend = friends.m_friends.get(position);
        txtName.setText(friend.getName());

        Bitmap bitmap = decodeBase64(friend.getImage() );
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(params);

        return view;



    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input,Base64.URL_SAFE);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
