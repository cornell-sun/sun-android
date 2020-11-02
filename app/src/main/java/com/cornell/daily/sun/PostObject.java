package com.cornell.daily.sun;

import java.util.*;
//import com.cornell.daily.sun.AuthorObject;
//import com.cornell.daily.sun.FeaturedMediaImages;
//import com.cornell.daily.sun.PostAttachmentObject;

public class PostObject {

    //Bookmark data
    Date storeDate;
    boolean didSave;

    //Post data
    int id;
    Date date;
    String title;
    String content;
    String excerpt;
    String link;
    AuthorObject[] author;
    FeaturedMediaImages featuredMediaImages;
    String featuredMediaCaption;
    String featuredMediaCredit;
    String[] categories;
    String primaryCategory;
    String[] tags;
    PostAttachmentObject[] postAttachments;
    PostType postType;
    SuggestedStoryObject[] suggestedStories;
}
