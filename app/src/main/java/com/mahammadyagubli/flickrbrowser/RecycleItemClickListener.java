package com.mahammadyagubli.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecycleItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "RecycleItemClickListene";

    interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }
    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetectorCompat;

    public RecycleItemClickListener(Context context,final RecyclerView recycleView, OnRecyclerClickListener listener) {
   mListener=listener;
   mGestureDetectorCompat=new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){

       @Override
       public boolean onSingleTapUp(MotionEvent e) {
           Log.d(TAG, "onSingleTapUp:  starts");
           View childView=recycleView.findChildViewUnder(e.getX(),e.getY());
           if(childView!=null &&mListener!=null){
               Log.d(TAG, "onSingleTapUp:  calling listener.onItemClick");
          mListener.onItemClick(childView,recycleView.getChildAdapterPosition(childView));
           }


           return true;
       }

       @Override
       public void onLongPress(MotionEvent e) {
           Log.d(TAG, "onLongPress: starts");
           View childView=recycleView.findChildViewUnder(e.getX(),e.getY());
           if(childView!=null &&mListener!=null){
               Log.d(TAG, "onLongPress:  calling listener.OnItemClick");
mListener.onItemLongClick(childView, recycleView.getChildAdapterPosition(childView));
           }
           super.onLongPress(e);
       }
   });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent:  starts");

      if(mGestureDetectorCompat!=null){

          boolean result =mGestureDetectorCompat.onTouchEvent(e);
          Log.d(TAG, "onInterceptTouchEvent:  returnes "+result);
          return result;
      }
      else {
          Log.d(TAG, "onInterceptTouchEvent:  returned: false");
          return false;
      }

            }
}
