package com.mahammadyagubli.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class getFlickerJsonData extends AsyncTask<String,Void,List<Photo>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "getFlickerJsonData";
    private List<Photo> mphotoList=null;
    private String mBaseUrl,mLanguage;
    private boolean mMatchAll;
    private boolean runningSameThread=false;

    private final OnDataAvailable mCallBack;

    public getFlickerJsonData( OnDataAvailable callBack,String baseUrl, String language, boolean matchAll) {

        Log.d(TAG, "getFlickerJsonData: called");  mBaseUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUrl=createUri(params[0],mLanguage,mMatchAll);
        Log.d(TAG, "doInBackground: "+destinationUrl);
        GetRawData getRawData=new GetRawData(this);
        getRawData.runInSameThread(destinationUrl);
        Log.d(TAG, "doInBackground: ends");
        return mphotoList;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");
       if(mCallBack!=null){

           mCallBack.OnDataAvailable(mphotoList,DownloadStatus.OK);
       }
        Log.d(TAG, "onPostExecute: finishs");
     //   super.onPostExecute(photos);
    }

    @Override
    public void OnDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "OnDownloadComplete: status="+status);
        if(status==DownloadStatus.OK){

            mphotoList=new ArrayList<>();
            try{
                JSONObject jsonData=new JSONObject(data);
                JSONArray itemArray=jsonData.getJSONArray("items");
                for(int i=0;i<itemArray.length();i++){
                    JSONObject jsonPhoto=itemArray.getJSONObject(i);
                    String title=jsonPhoto.getString("title");
                    String author=jsonPhoto.getString("author");
                    String authorId=jsonPhoto.getString("author_id");
                    String tags=jsonPhoto.getString("tags");
                    JSONObject jsonMedia=jsonPhoto.getJSONObject("media");
                    String photoUrl=jsonMedia.getString("m");
                    String link=photoUrl.replace("_m.","_b.");
                    Photo photoObject=new Photo(title,author,authorId,link,tags,photoUrl);
mphotoList.add(photoObject);
                    Log.d(TAG, "OnDownloadComplete: "+photoObject.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "OnDownloadComplete: Error Processing Json Data");
            }

        }
        if(mCallBack!=null ){
//now inform
            mCallBack.OnDataAvailable(mphotoList,status);

        }
        Log.d(TAG, "OnDownloadComplete: ends");
    }
    interface OnDataAvailable {

        void OnDataAvailable(List<Photo> data, DownloadStatus status);

 }

void executeOnSameThread(String searchCriteria){
    Log.d(TAG, "executeOnSameThread: starts");
    runningSameThread=true;
        String destionationUri=createUri(searchCriteria,mLanguage,mMatchAll);
        GetRawData getRawData=new GetRawData(this);
        getRawData.execute(destionationUri);
    Log.d(TAG, "executeOnSameThread: ends");
}

private String createUri(String searchCrieteria, String lan, boolean matchAll){

    Log.d(TAG, "createUri: starts");
    return Uri.parse(mBaseUrl).buildUpon().appendQueryParameter("tags",searchCrieteria)
            .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
            .appendQueryParameter("lang",lan)
            .appendQueryParameter("format","json")
            .appendQueryParameter("nojsoncallback","1").build().toString();

}



}
