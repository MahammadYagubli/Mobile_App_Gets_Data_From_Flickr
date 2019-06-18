package com.mahammadyagubli.flickrbrowser;

import java.io.Serializable;

public class Photo implements Serializable {
private static final long serialVersionUID=1L;
    private String mTitle,mAuthor,mAuthorId,mlink,mtags, mImage;

    public Photo(String title, String author, String authorId, String mlink, String mtags, String image) {
        mTitle = title;
        mAuthor = author;
        mAuthorId = authorId;
        this.mlink = mlink;
        this.mtags = mtags;
        mImage = image;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getAuthorId() {
        return mAuthorId;
    }

    String getMlink() {
        return mlink;
    }

    String getMtags() {
        return mtags;
    }

    String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mlink='" + mlink + '\'' +
                ", mtags='" + mtags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
