package com.cornell.daily.sun;

public class PostAttachmentObject {

    int id;
    String name;
    String caption;
    String authorName;
    String mediaType;
    String url;

    PostAttachmentObject(int id, String name, String caption, String authorName, String mediaType, String url) {
        this.id = id;
        this.name = name;
        this.caption = caption;
        this.authorName = authorName;
        this.mediaType = mediaType;
        this.url = url;
    }

}
