package com.example.e_commerce.API;

import android.content.Context;

import com.example.e_commerce.Listener.RandomRecipeResponseListener;
import com.example.e_commerce.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        if (tags.size() != 0)
        {
            tags.add("vegetarian");
        }
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.spoonacular_api_key), "10", tags);


        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

//    public void getRecipeDetails(RecipeDetailsListener listener, int id){
//        CallRecipeDetails callRecipeDetails = retrofit.create((CallRecipeDetails.class));
//        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
//        call.enqueue(new Callback<RecipeDetailsResponse>() {
//            @Override
//            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
//                if(!response.isSuccessful()){
//                    listener.didError(response.message());
//                    return;
//                }
//                listener.didFetch(response.body(), response.message());
//            }
//
//            @Override
//            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
//                listener.didError(t.getMessage());
//            }
//        });
//    }
//
    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags")List<String> tags
        );
    }
//    private interface CallRecipeDetails{
//        @GET("recipes/{id}/information")
//        Call<RecipeDetailsResponse> callRecipeDetails(
//                @Path("id") int id,
//                @Query("apiKey") String apiKey
//        );
//    }
//
//    public void getInstructions(InstructionListener listener, int id){
//        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
//        Call<List<InstructionResponse>> call = callInstructions.callInstruction(id, context.getString(R.string.api_key));
//        call.enqueue(new Callback<List<InstructionResponse>>() {
//            @Override
//            public void onResponse(Call<List<InstructionResponse>>call, Response<List<InstructionResponse>> response) {
//                if(!response.isSuccessful()){
//                    listener.didError(response.message());
//                    return;
//                }
//                listener.didFetch(response.body(),response.message());
//            }
//
//            @Override
//            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {
//                listener.didError(t.getMessage());
//            }
//        });
//    }
//    private interface CallInstructions{
//        @GET("recipes/{id}/analyzedInstructions")
//        Call<List<InstructionResponse>> callInstruction (
//                @Path("id") int id,
//                @Query("apiKey") String apiKey
//        );
//    }
}
