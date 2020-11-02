package com.cornell.daily.sun;

public class FeaturedMediaImages {

    ImageInfo mediumLarge;
    ImageInfo thumbnail;
    ImageInfo full;

    FeaturedMediaImages(ImageInfo mediumLarge, ImageInfo thumbnail, ImageInfo full) {
        this.mediumLarge = mediumLarge;
        this.thumbnail = thumbnail;
        this.full = full;
    }

    public class ImageInfo {

        String url;
        int width;
        int height;

        ImageInfo(String url, int width, int height) {
            this.url = url;
            this.width = width;
            this.height = height;
        }
    }
}
