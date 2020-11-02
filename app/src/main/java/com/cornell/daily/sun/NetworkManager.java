package com.cornell.daily.sun;

import java.util.List;

//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Query;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 Usage:
 Retrofit retrofit = NetworkManager.getNetworkManagerInstance();
 Call<List<PostObject>> posts = retrofit.getPostsBySection("section:1", "page:1");
 posts.enqueue(this);
 */

public class NetworkManager {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://cornellsun.staging.wpengine.com/wp-json";

    public static Retrofit getNetworkManagerInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

public interface API {

    @GET("/wp/v2/posts/{postId}")
    Call<PostObject> getPostById(@Path("postId") int postId);

    @GET("/wp/v2/posts")
    Call<List<PostObject>> getPostsById(@Query("page") int page);

    @GET("/wp/v2/posts")
    Call<List<PostObject>> getPostsBySection(@Query("categories") int section, @Query("page") int page);

    @GET("/wp/v2/users/{authorId}")
    Call<List<PostObject>> getPostsByAuthor(@Path("authorId") int authorId);

    @GET("/wp/v2/media/{mediaId}")
    Call<List<PostObject>> getMedia(@Path("mediaId") int mediaId);

    @GET("/wp/v2/categories/{categoryId}")
    Call<List<String>> getCategories(@Path("categoryId") int categoryId);

    @GET("/sun-backend-extension/v1/{postId}")
    Call<List<Comment>> getComments(@Path("postId") int postId);

    @GET("/sun-backend-extension/v1/trending")
    Call<List<PostObject>> getTrending();

    @GET("/wp/v2/posts")
    Call<List<PostObject>> searchPosts(@Query("search") String query, @Query("page") int page);

    @GET("/sun-backend-extension/v1/featured")
    Call<PostObject> getFeatured();

    @GET("/wp/v2/urltoid")
    Call<Integer> urlToId(@Query("url") String url);

}