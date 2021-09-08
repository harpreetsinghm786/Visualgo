package com.official.visualgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class profile_editor extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 123 ;
    RoundedImageView profilepic;
    FloatingActionButton imagechooser;
    EditText username;
    LinearLayout progressBar;
    Uri filePath;
    Button next;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        profilepic=findViewById(R.id.profilepic);
        imagechooser=findViewById(R.id.chooser);
        username=findViewById(R.id.username);
        storage= FirebaseStorage.getInstance();
        databaseReference= FirebaseDatabase.getInstance("https://visualgo-716db-default-rtdb.firebaseio.com/").getReference("userdata");
        auth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("userdata/"+auth.getCurrentUser().getPhoneNumber());
        progressBar=findViewById(R.id.progressbar);
        next=findViewById(R.id.next);

        username.setText(getIntent().getStringExtra("name"));
        Picasso.with(this).load(getIntent().getStringExtra("url")).into(profilepic);

        imagechooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText())){
                    Toast.makeText(profile_editor.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);


                    databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child("name").setValue(username.getText().toString());
                    uploadimage();

                    progressBar.setVisibility(View.GONE);


                    Toast.makeText(profile_editor.this, "Profile Updated", Toast.LENGTH_LONG).show();


                }

            }
        });

    }



    private void chooseimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage(){
        if (filePath != null) {
            final StorageReference fileReference = storageReference.child(auth.getCurrentUser().getUid()
                    + "." + getFileExtension(filePath));


            fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri dowload= task.getResult();
                                    String downloadurl=dowload.toString();
                                    databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child("url").setValue(downloadurl);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });



                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(profile_editor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(this).load(filePath).into(profilepic);

            }

        }


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(profile_editor.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
