package com.iodevs.quoteit.Model;

/**
 * Created by Touseef Rao on 9/14/2018.
 */

public class Category {

    private String Image,Name;


    public Category(){}

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public Category(String image, String name, String[] tags) {
        Image = image;
        Name = name;

    }
}
