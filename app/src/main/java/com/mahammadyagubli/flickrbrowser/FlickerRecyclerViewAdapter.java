package com.mahammadyagubli.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class FlickerRecyclerViewAdapter extends RecyclerView.Adapter<FlickerRecyclerViewAdapter.FlickrImageViewHolder> {
Picasso mPicasso;
    private static final String TAG = "FlickerRecyclerViewAdap";
    private List<Photo> mPhotoList;
    private Context mContext;
    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickImageViewHolder";
        ImageView thumbnail=null;
        TextView title=null;

        public FlickrImageViewHolder(  View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            this.title=(TextView)itemView.findViewById(R.id.tsttitle);

        }
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(  ViewGroup parent, int viewtype) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.browser,parent,false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
if(mPhotoList==null ||mPhotoList.size()==0){

    holder.thumbnail.setImageResource(R.drawable.placeholder);
    holder.title.setText(R.string.empty_photo);
}
else { Photo photoItem=mPhotoList.get(position);
    Log.d(TAG, "onBindViewHolder: "+photoItem.getTitle());
    Picasso.get().load(photoItem.getImage()).error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder).into(holder.thumbnail);
    holder.title.setText(photoItem.getTitle());
}

    }

    @Override
    public int getItemCount() {

        return ((mPhotoList!=null)&&(mPhotoList.size()!=0)?mPhotoList.size():1);
    }
void loadNewData(List<Photo> newPhototlist){
        mPhotoList=newPhototlist;
        notifyDataSetChanged();

}
public Photo getPhoto(int position){

        return ((mPhotoList!=null)&&mPhotoList.size()!=0 ?mPhotoList.get(position) :null);

}
    public FlickerRecyclerViewAdapter(List<Photo> photoList, Context context) {
        mPhotoList = photoList;
        mContext = context;
    }
}
