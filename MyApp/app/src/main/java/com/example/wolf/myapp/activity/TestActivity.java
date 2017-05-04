package com.example.wolf.myapp.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.wolf.myapp.R;
import com.example.wolf.myapp.adapter.GridViewAdapter;
import com.example.wolf.myapp.adapter.ListViewAdapter;
import com.example.wolf.myapp.model.Item;
import com.example.wolf.myapp.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private String LOG_TAG = "TestActivity";
    String testUrl = "https://vozforums.com/showthread.php?t=6057685";
    String rootPath = Environment.getExternalStorageDirectory().toString();
    WebView wvTest;
    ViewStub stubList;
    ViewStub stubGrid;
    private ListView listView;
    private GridView gridView;

    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    private int currentViewMode = 0;
    private ArrayList<Item> productList;
    private String currentParrent = "";
    private String currentFileNameSelected="";
    private String currentParentFileSelected="";
    private String currentPath="";
    private String targetFolderSelected="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //Log.e(LOG_TAG,"test");
//        wvTest =(WebView) findViewById(R.id.wvTest);
//        wvTest.
        //new MyAsyncTask().execute(MainActivity.baseUrl+GenreActivity.tailUrl);
//        new getPostAsynTask().execute(testUrl);
        // Util.getAllFolder();

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.lvMyList);
        gridView = (GridView) findViewById(R.id.gvMyList);

        listView.setOnItemClickListener(onItemClickListener);
        gridView.setOnItemClickListener(onItemClickListener);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemLongClickListener(onItemLongClickListener);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        getProductList(rootPath);

        switchView();


    }

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(LOG_TAG, "onItemLongClick");
            view.setSelected(true);
            currentFileNameSelected=productList.get(position).getName();
            currentParentFileSelected=productList.get(position).getParent();
            return true;
        }
    };


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SparseBooleanArray clickItemPosition = listView.getCheckedItemPositions();
            //view.setSelected(true);
            Log.e(LOG_TAG, productList.get(position).getName());
            if (productList.get(position).isDirectory()) {
                getProductList(productList.get(position).getPath());
                setAdapters();
            }
//            ArrayList<String> newList= Util.getAllFolder(productList.get(position).getPath());
            //  Util.deleteFile(productList.get(position).getPath());
        }
    };



    private void switchView() {

        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();

    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(productList, this);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(productList, this);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<Item> getProductList(String path) {
        productList = new ArrayList<>();
        productList = Util.getAllFolder(path);
        File currentFile = new File(path);

        if(currentFile.isDirectory()){
            currentParrent = currentFile.getParent();
            currentPath=path;
            Log.e(LOG_TAG, "currentParrent " + currentParrent);
            return productList;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.e(LOG_TAG, "onOptionItemSelected");
        switch (item.getItemId()) {
            case R.id.item_menu_1: {
                if (currentViewMode == VIEW_MODE_LISTVIEW) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                switchView();
                break;
            }
            case R.id.item_copy: {
                Log.e(LOG_TAG,"copy");

                break;
            }
            case R.id.item_delete: {
//                Util.deleteFile();

                if(currentFileNameSelected!=null&&currentParentFileSelected!=null){
                    Util.deleteFile(currentParentFileSelected+"/"+currentFileNameSelected);
                    updateUI(currentParentFileSelected);
                    currentFileNameSelected="";
                    Log.e(LOG_TAG,"delete ");

                }
                break;
            }

            case R.id.item_patse: {
                Log.e(LOG_TAG,"pase");
                targetFolderSelected= currentParrent;
                Util.copyFile(currentParentFileSelected+"/",currentFileNameSelected,currentPath+"/");
                updateUI(currentPath);
                break;
            }
            case  R.id.item_rename:
            {
                Util.reNameFile(currentParentFileSelected,currentFileNameSelected,"abc.mp3");
                updateUI(currentParentFileSelected);
                break;
            }
        }

        return true;
    }

    public void updateUI(String current){
        getProductList(current);
        setAdapters();
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];

            try {
                Document doc = Jsoup.connect(url).get();
                Elements subElements = doc.select("div.noidung");
                Log.e(LOG_TAG, subElements.get(0).toString());
                return subElements.get(0).toString();
//                    String word=wordInfor.get(1).text();
//                    String pronunciation=wordInfor.get(2).text();
////                    String engMean=element.nextSibling().toString();
//                    String vietMean;
//                    String example;
//                    String exampleMean;
////                    String audioUrl=element.getElementsByAttribute("audio").first().text();
//                    Log.e(LOG_TAG,"words "+word);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            wvTest.loadData(s, "text/html; charset=utf-8", "UTF-8");
            Log.e(LOG_TAG, s);
        }
    }

    public class getPostAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("div.voz-post-message");
                Log.e(LOG_TAG, "" + elements.size());
                return elements.get(0).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            wvTest.loadData(s, "text/html; charset=utf-8", "UTF-8");

        }

    }

    @Override
    public void onBackPressed() {
        Log.e(LOG_TAG, "onbackPressed");
        if (currentParrent.equals("/storage/emulated")) {
            finish();
        } else {
            getProductList(currentParrent);
            setAdapters();

        }

    }
}
