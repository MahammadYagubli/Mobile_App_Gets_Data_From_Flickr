package com.mahammadyagubli.flickrbrowser;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
         activiteToolbar(true);
         Intent intent =getIntent();
         Photo photo=(Photo)intent.getSerializableExtra(PHOTO_TRANSFER);
             if(photo!=null){
             TextView photoitle=(TextView)findViewById(R.id.photo_title);
             Resources res=getResources();
             String text=res.getString(R.string.photo_title_text,photo.getTitle());
             photoitle.setText(text );

             TextView photoTags=(TextView)findViewById(R.id.photo_tags);
                 String tags=res.getString(R.string.photo_tags_text,photo.getMtags());
             photoTags.setText(tags);

             TextView photoAuthor=(TextView)findViewById(R.id.photo_author);
             photoAuthor.setText(photo.getAuthor());
             ImageView photoItem=(ImageView)findViewById(R.id.photo_image);
             Picasso.get().load(photo.getMlink())
                     .error(R.drawable.placeholder)
                     .placeholder(R.drawable.placeholder).
                       into(photoItem);
    }

    }

}
