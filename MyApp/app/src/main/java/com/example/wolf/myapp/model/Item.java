package com.example.wolf.myapp.model;

/**
 * Created by Wolf on 5/2/2017.
 */

public class Item {

    private String path;
    private String name;
    private boolean isDirectory;
    private String parent;

    public Item(String path, String name, boolean type, String parent) {
        this.path = path;
        this.name = name;
        this.isDirectory = type;
        this.parent=parent;

    }


    public boolean isDirectory() {
        return isDirectory;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setDirectory(boolean directory) {
        this.isDirectory = directory;
    }
}
