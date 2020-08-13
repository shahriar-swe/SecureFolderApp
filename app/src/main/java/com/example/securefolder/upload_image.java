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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class upload_image extends AppCompatActivity {

//step : 1
    ImageView uploadImage;
    Uri uri;
    EditText imageNameEditText,imageDescriptionEditText,imageDateEditText;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        uploadImage = findViewById(R.id.uploadImageViewId);
        imageNameEditText = findViewById(R.id.imageNameEditTextId);
        imageDescriptionEditText = findViewById(R.id.imageDescriptionEditTextId);
        imageDateEditText = findViewById(R.id.imageDateEditTextId);
    }

    //step :2
    public void selectImageButton(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }

    //step :3
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            uri = data.getData();
            uploadImage.setImageURI(uri);
        }else Toast.makeText(upload_image.this,"You Haven't Select Any Image",Toast.LENGTH_SHORT).show();

    }


    public void UploadImage(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image Uploading....");
        progressDialog.show();

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

    public void uploadImageButton(View view) {

        UploadImage();
    }


    public void UploadingImage() {
//final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Image Uploading...");
//        progressDialog.show();

        ImageData imageData = new ImageData(
                imageNameEditText.getText().toString(),
                imageDescriptionEditText.getText().toString(),
                imageDateEditText.getText().toString(),
                imageUrl
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("Image").child(myCurrentDateTime).setValue(imageData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(upload_image.this,"Image Uploaded",Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(upload_image.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
