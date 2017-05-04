package com.example.wolf.myapp.util;

import android.os.Environment;
import android.util.Log;

import com.example.wolf.myapp.model.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Wolf on 5/2/2017.
 */

public class Util {
    private static String LOG_TAG = "Util";

    public static ArrayList<Item> getAllFolder(String filePath) {
        ArrayList<Item> myList = new ArrayList<>();

        File f = new File(filePath);
        File[] files = f.listFiles();
        for (File inFile : files) {

//            Log.e(LOG_TAG, inFile.getName() + " " + inFile.getPath() + " " + inFile.getParent());
            myList.add(new Item(inFile.getPath(),inFile.getName(),inFile.isDirectory(),inFile.getParent()));

        }
        return myList;
    }

    public static  void moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    public static void deleteFile(String inputPath) {
        Log.e(LOG_TAG,"deleteFile");
        try {
            // delete the original file
            new File(inputPath).delete();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void copyFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    public static void reNameFile(String currentParrentPath,String currentName, String newName){
        File dir= new File(currentParrentPath);
        Log.e(LOG_TAG,dir.toString());
        File from      = new File(dir, currentName);
        File to        = new File(dir, newName);
        from.renameTo(to);
    }
}
