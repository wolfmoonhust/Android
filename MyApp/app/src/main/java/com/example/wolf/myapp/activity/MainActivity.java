package com.example.wolf.myapp.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.wolf.myapp.R;
import com.example.wolf.myapp.adapter.GenreAdapter;
import com.example.wolf.myapp.model.Genres;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static  String baseUrl ="http://600tuvungtoeic.com/";
    private String LOG_TAG="MainActivity";
    private ArrayList<Genres> genreList;
    private GenreAdapter genreAdapter;
    private GridView gvGenreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        gvGenreList=(GridView)findViewById(R.id.gvGenreList);

        new MyAsyncTask().execute(baseUrl);
    }

    public class MyAsyncTask extends AsyncTask<String, Void, ArrayList<Genres>>{

        @Override
        protected ArrayList<Genres> doInBackground(String... params) {
             ArrayList<Genres> myList= new ArrayList<>();
            String urlLink=params[0];
            try {
                Document doc= Jsoup.connect(urlLink).get();
                String title=doc.title();
                Elements subElements=doc.select("div.gallery-item");
                //Log.e(LOG_TAG,"subElements "+subElements.size());
                for(Element item : subElements){
                    Element titleSubject=item.getElementsByTag("h3").first();
                    Element imgSubject=item.getElementsByTag("img").first();
                    Element linkSubject=item.getElementsByTag("a").first();
                   // Log.e(LOG_TAG,titleSubject.text()+"\n"+imgSubject.attr("src").toString()+"\n"+linkSubject.attr("href"));
                    myList.add(new Genres(titleSubject.text(),imgSubject.attr("src"),linkSubject.attr("href")));
                }
                return  myList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Genres> genres) {
            super.onPostExecute(genres);
            genreList=genres;
            genreAdapter= new GenreAdapter(genreList,MainActivity.this);
            gvGenreList.setAdapter(genreAdapter);
            Log.e(LOG_TAG,""+genreList.size());
        }
    }
}
