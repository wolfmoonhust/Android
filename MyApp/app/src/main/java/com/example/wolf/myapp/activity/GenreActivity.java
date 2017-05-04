package com.example.wolf.myapp.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wolf.myapp.R;
import com.example.wolf.myapp.model.Genres;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GenreActivity extends AppCompatActivity {
    public static  String tailUrl="index.php?mod=lesson&id=1";
    private String LOG_TAG="GenreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        new MyAsyncTask().execute(MainActivity.baseUrl+tailUrl);
    }

    public class MyAsyncTask extends AsyncTask<String, Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            String url=params[0];

            try {
                Document doc= Jsoup.connect(url).get();
                Elements subElements=doc.select("div.noidung");
                for( Element element: subElements){
                    //get data
                    Elements wordInfor=element.getAllElements();

                    String word=wordInfor.get(1).text();
                    String pronunciation=wordInfor.get(2).text();
//                    String engMean=element.nextSibling().toString();
                    String vietMean;
                    String example;
                    String exampleMean;
//                    String audioUrl=element.getElementsByAttribute("audio").first().text();
                    Log.e(LOG_TAG,"words "+word);

                }
                Log.e(LOG_TAG,""+subElements.size());
            } catch (IOException e) {
                e.printStackTrace();
            }


//            try {
//                Document doc= Jsoup.connect(urlLink).get();
//                String title=doc.title();
//                Elements subElements=doc.select("div.gallery-item");
//                //Log.e(LOG_TAG,"subElements "+subElements.size());
//                for(Element item : subElements){
//                    Element titleSubject=item.getElementsByTag("h3").first();
//                    Element imgSubject=item.getElementsByTag("img").first();
//                    Element linkSubject=item.getElementsByTag("a").first();
//                    // Log.e(LOG_TAG,titleSubject.text()+"\n"+imgSubject.attr("src").toString()+"\n"+linkSubject.attr("href"));
//                    myList.add(new Genres(titleSubject.text(),imgSubject.attr("src"),linkSubject.attr("href")));
//                }
//                return  myList;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
            return null;
        }
    }
}
