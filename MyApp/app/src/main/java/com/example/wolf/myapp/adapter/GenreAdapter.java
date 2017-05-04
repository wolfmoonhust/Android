package com.example.wolf.myapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wolf.myapp.R;
import com.example.wolf.myapp.model.Genres;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Wolf on 4/30/2017.
 */

public class GenreAdapter extends BaseAdapter {
    private ArrayList<Genres> genresList;
    private Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    private String LOG_TAG="GenreApdater";
    public GenreAdapter(ArrayList<Genres> genresList, Context context) {
        this.genresList = genresList;
        this.context = context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.context));
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(500))
                .showImageForEmptyUri(R.drawable.genreitem)
                .showImageOnFail(R.drawable.genreitem)
                .build();

    }

    @Override
    public int getCount() {
        return genresList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder= new ViewHolder();
            convertView=inflater.inflate(R.layout.genre_item,null);

            viewHolder.imgGenreItem =(ImageView) convertView.findViewById(R.id.imgGenreItem);
            viewHolder.tvNameGenreItem=(TextView)convertView.findViewById(R.id.tvNameGenreItem);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.tvNameGenreItem.setText(genresList.get(position).getName());
        viewHolder.imgGenreItem.setImageBitmap(null);
//        Bitmap bitmap=imageLoader.loadImage("", new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                // Do whatever you want with Bitmap
//            }
//        });
        imageLoader.displayImage(genresList.get(position).getImg(), viewHolder.imgGenreItem,options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
//                Log.e(LOG_TAG,"onLoadStarted");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                Log.e(LOG_TAG,"onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Log.e(LOG_TAG,"onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
//                Log.e(LOG_TAG,"onLoadCancelled");
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView imgGenreItem;
        TextView tvNameGenreItem;
    }
}
