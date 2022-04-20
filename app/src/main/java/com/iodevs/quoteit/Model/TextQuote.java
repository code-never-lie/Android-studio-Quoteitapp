package com.iodevs.quoteit.Model;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/14/2018.
 */

public class TextQuote {
    private String quoteId,quoteText,authorName,categoryName;
    private String tags;

    public TextQuote(){};
    public TextQuote(String quoteId, String quoteText, String authorName, String categoryName, String tags) {
        this.quoteId = quoteId;
        this.quoteText = quoteText;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.tags = tags;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
