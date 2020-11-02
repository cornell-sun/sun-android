package com.cornell.daily.sun;

public class SuggestedStoryObject {

    int postID;
    String title;
    AuthorObject[] authors;
    FeaturedMediaImages featuredMediaImages;

    SuggestedStoryObject(int postID, String title, AuthorObject[] authors, FeaturedMediaImages featuredMediaImages) {
        this.postID = postID;
        this.title = title;
        this.authors = authors;
        this.featuredMediaImages = featuredMediaImages;
    }
}
