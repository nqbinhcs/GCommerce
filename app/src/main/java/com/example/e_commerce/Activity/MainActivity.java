package com.example.e_commerce.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adaptor.CategoryAdaptor;
import com.example.e_commerce.Adaptor.PopularAdaptor;
import com.example.e_commerce.Domain.CategoryDomain;
import com.example.e_commerce.Domain.FoodDomain;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Fruits", "cat_11"));
        category.add(new CategoryDomain("Vegetables", "cat_22"));
        category.add(new CategoryDomain("Diary", "cat_33"));
        category.add(new CategoryDomain("Meat", "cat_44"));

        categoryAdapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();

        foodList.add(new FoodDomain("001", "Bell Peper Red", "pop_11", "A red peper", 4.0, "5.0"));
        foodList.add(new FoodDomain("002", "Lamb Meat", "pop_22", "A fresh meat", 45.0, "5.0"));
        foodList.add(new FoodDomain("003", "Combo Fresh", "pop_33", "Meat and Fish", 4.0, "5.0"));


        popularAdapter = new PopularAdaptor(foodList);
        recyclerViewPopularList.setAdapter(popularAdapter);
//        FirebaseFirestore.getInstance().collection("food").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty()){
//                            List<DocumentSnapshot> list_food = queryDocumentSnapshots.getDocuments();
//                            ArrayList<FoodDomain> arrayTmp = new ArrayList<>();
//
//                            for (DocumentSnapshot d: list_food){
//                                arrayTmp.add(new FoodDomain(
//                                        d.getId(),
//                                        d.getString("name"),
//                                        d.getString("pic"),
//                                        d.getString("desc"),
//                                        d.getString("averageRating")
//                                ));
//                            }
//
//                            for(int i = 0; i < arrayTmp.size() - 1; i++) {
//                                for(int j = i; j < arrayTmp.size(); j++) {
//                                    if(Double.parseDouble(arrayTmp.get(i).getAverageRating()) < Double.parseDouble(arrayTmp.get(j).getAverageRating()))
//                                        Collections.swap(arrayTmp, i, j);
//                                }
//                            }
//
//                            for(int i=0; i<5; i++) {
//                                foodList.add(arrayTmp.get(i));
//                            }
//
//                            popularAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
    }
}