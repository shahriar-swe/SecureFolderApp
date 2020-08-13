package com.example.securefolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailsActivity extends AppCompatActivity {

    private ImageView detailsImageView;
    private TextView imageNameTextView,detailsTextView,dateTextView;
    String key= "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsImageView = findViewById(R.id.detailsImageViewId);
        imageNameTextView = findViewById(R.id.detailsItemNameTextViewId);
        dateTextView = findViewById(R.id.detailsDateTextViewId);
        detailsTextView = findViewById(R.id.detailsDescriptionTextViewId);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){

            detailsTextView.setText(bundle.getString("description"));
            key = bundle.getString("keyValue");
            imageUrl = bundle.getString("image");
            imageNameTextView.setText(bundle.getString("imageName"));
            dateTextView.setText(bundle.getString("date"));
            //detailsImageView.setImageResource(bundle.getInt("image"));
            Glide.with(this).load(bundle.getString("image")).into(detailsImageView);
        }
    }

    public void deleteImageButton(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Image");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailsActivity.this,"Image Deleted Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),GalleryActivity.class));
                finish();
            }
        });
    }

    public void updateImageButton(View view) {

        startActivity(new Intent(getApplicationContext(),UpdateImageActivity.class).putExtra("imageNameKey",imageNameTextView.getText().toString())
        .putExtra("descriptionKey",detailsTextView.getText().toString()).putExtra("dateKey",dateTextView.getText().toString())
        .putExtra("oldimageUrl",imageUrl).putExtra("key",key));
    }
}
