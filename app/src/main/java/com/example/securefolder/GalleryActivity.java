package com.example.securefolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ImageData> myImageList;
    ImageData imageData;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerViewId);
        searchEditText = findViewById(R.id.searchEditTextId);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(GalleryActivity.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items....");

        myImageList = new ArrayList<>();

        /*imageData = new ImageData("one big pizza with cheese","yummy cheese big pizza","29-06-2020",R.drawable.pizza);
        myImageList.add(imageData);
        imageData = new ImageData("one bull pasta","yummy bull pasta with chicken and cheese","30-06-2020",R.drawable.pasta);
        myImageList.add(imageData);
        imageData = new ImageData("one burger with cheese","yummy cheese big burger","01-07-2020",R.drawable.burger);
        myImageList.add(imageData);
        imageData = new ImageData("italian cake","yummy italian birthday cake","27-06-2020",R.drawable.cake);
        myImageList.add(imageData);*/

        myAdapter = new MyAdapter(GalleryActivity.this,myImageList);
        recyclerView.setAdapter(myAdapter);

        //ei jaiga theke retrive er kaz shoro hoise

        databaseReference = FirebaseDatabase.getInstance().getReference("Image");
        progressDialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myImageList.clear();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    ImageData imageData = itemSnapshot.getValue(ImageData.class);
                    imageData.setKey(itemSnapshot.getKey());
                    myImageList.add(imageData);
                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        //search er work ei jaiga theke
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }

    private void filter(String text) {

        ArrayList<ImageData> filterList = new ArrayList<>();

        for(ImageData item : myImageList){

            //ekhane getItemName() dara item neme ke search kora hoyeche . chaile ami getItemDescription() darao search korte partam
            if(item.getItemName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
// ekhane ekta error ashbe ashle balbe dhore mathod create korlei hobe
        myAdapter.filteredList(filterList);

    }


    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this,upload_image.class));
    }
}
