package com.cornell.daily.sun;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 Usage:
 Retrofit retrofit = NetworkManager.getNetworkManagerInstance();
 Call<List<Post>> posts = retrofit.getPostsBySection("section:1", "page:1");
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
    Call<Post> getPostById(@Path("postId") int postId);

    @GET("/wp/v2/posts")
    Call<List<Post>> getPostsById(@Query("page") int page);

    @GET("/wp/v2/posts")
    Call<List<Post>> getPostsBySection(@Query("categories") int section, @Query("page") int page);

    @GET("/wp/v2/users/{authorId}")
    Call<List<Post>> getPostsByAuthor(@Path("authorId") int authorId);

    @GET("/wp/v2/media/{mediaId}")
    Call<List<Post>> getMedia(@Path("mediaId") int mediaId);

    @GET("/wp/v2/categories/{categoryId}")
    Call<List<String>> getCategories(@Path("categoryId") int categoryId);

    @GET("/wp/v2/comments/{postId}")
    Call<List<Comment>> getComments(@Path("postId") int postId);

}