package com.iodevs.quoteit.Model;

import java.io.Serializable;

/**
 * Created by Touseef Rao on 9/15/2018.
 */

public class ImageQuote implements Serializable{
    private String quoteId,imageUrl,tags,categoryName;


    public ImageQuote(){}

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ImageQuote(String quoteId, String imageUrl, String tags, String categoryName) {
        this.quoteId = quoteId;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.categoryName = categoryName;
    }
}
