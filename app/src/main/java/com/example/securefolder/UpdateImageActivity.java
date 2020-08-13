package com.example.securefolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UpdateImageActivity extends AppCompatActivity {

    ImageView uploadImage;
    Uri uri;
    EditText imageNameEditText,imageDescriptionEditText,imageDateEditText;
    String imageUrl;
    String key,oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String imageName,imageDescription,imageDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);

        uploadImage = findViewById(R.id.uploadImageViewId);
        imageNameEditText = findViewById(R.id.imageNameEditTextId);
        imageDescriptionEditText = findViewById(R.id.imageDescriptionEditTextId);
        imageDateEditText = findViewById(R.id.imageDateEditTextId);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            Glide.with(UpdateImageActivity.this).load(bundle.getString("oldimageUrl")).into(uploadImage);
            imageNameEditText.setText(bundle.getString("imageNameKey"));
            imageDescriptionEditText.setText(bundle.getString("descriptionKey"));
            imageDateEditText.setText(bundle.getString("dateKey"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldimageUrl");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Image").child("key");
        //storageReference = FirebaseStorage.getInstance().getReference().child("Image").child(uri.getLastPathSegment());
    }

    public void selectImageButton(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            uri = data.getData();
            uploadImage.setImageURI(uri);
        }else Toast.makeText(this,"You Haven't Select Any Image",Toast.LENGTH_SHORT).show();

    }


    public void updateImageButton(View view) {

        imageName = imageNameEditText.getText().toString().trim();
        imageDescription = imageDescriptionEditText.getText().toString().trim();
        imageDate = imageDateEditText.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image Uploading....");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference().child("Image").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                UploadingImage();
                progressDialog.dismiss();

                //Toast.makeText(upload_racipe.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }


    public void UploadingImage() {
//final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Image Uploading...");
//        progressDialog.show();

        ImageData imageData = new ImageData(
                imageName,
                imageDescription,
                imageDate,
                imageUrl
        );

        databaseReference.setValue(imageData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                storageReferenceNew.delete();
                Toast.makeText(UpdateImageActivity.this,"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        //FirebaseDatabase.getInstance().getReference("Image")
                /*.child(myCurrentDateTime).setValue(imageData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateImageActivity.this,"Image Uploaded",Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateImageActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
