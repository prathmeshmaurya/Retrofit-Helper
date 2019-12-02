package com.example.retrofitcodinginflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Since;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();


        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://jsonplaceholder.typicode.com").addConverterFactory(GsonConverterFactory.create(gson)).build();

        /*Since JsonPlaceHolderApi is an interface we cannot use new to create objects unless the functions inside it are
        implemented. So we first use retrofit (Object of Retrofit) to create the implementations for us. */
         jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
       // getComments(3);
        //createPost();
        //deletePost();
        updatePost();
    }// OnCreate ends here

    public void getPosts(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                //if response was unsuccessful display the response code.
                if( !response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                textViewResult.setText("Worked!\n\n");
                // IF control reaches here, means the response was successful and we call the response.body()
                List<Post> posts = response.body();
                for(Post post: posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n" ;
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    public void getComments(int id){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for(Comment comment: comments){
                    String content ="";
                    content += "Id : " + comment.getId() + "\n";
                    content += "Post ID : " + comment.getPostId() + "\n";
                    content += "Name : " + comment.getName() + "\n";
                    content += "Email : " + comment.getEmail() + "\n";
                    content += "Text : " + comment.getText()+ "\n";

                    textViewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        final Post post  = new Post("New Text", 23, "New Title");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content  += "Code: " + response.code() + "\n";
                content += postResponse.getTitle() + "\n";
                content += postResponse.getText() + "\n";
                content += postResponse.getId() + "\n";
                content += postResponse.getUserId() + "\n";

                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost(){
        Post post = new Post("New Text", 12, null);
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content  += "Code: " + response.code() + "\n";
                content += postResponse.getTitle() + "\n";
                content += postResponse.getText() + "\n";
                content += postResponse.getId() + "\n";
                content += postResponse.getUserId() + "\n";

                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.append("Code : " + response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
            }
        });
    }

}
