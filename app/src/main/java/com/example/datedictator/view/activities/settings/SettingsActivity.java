package com.example.datedictator.view.activities.settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.datedictator.R;
import com.example.datedictator.view.activities.MainActivity;
import com.example.datedictator.view.activities.registration.LoginRegistrationActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText nameText, phoneText;
    private Button confirmButton;
    private ImageButton exitButton;
    private ImageView profileImage;
    private FirebaseAuth mAuth;
    private DatabaseReference mSettingsDatabase;
    String userId, name, phone, profileImageUrl; //userSex;
    private Uri resultUri;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        nameText = findViewById(R.id.edit_name);
        phoneText = findViewById(R.id.edit_phone);
        exitButton= findViewById(R.id.goBack_button);
        confirmButton = findViewById(R.id.confirm_button);
        profileImage = findViewById(R.id.profileImageSettings);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            userSex = extras.getString("sex");
//        }

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mSettingsDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Мерлин спасибо брат
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback logic here
            if (uri != null) {
                resultUri = uri;
                profileImage.setImageURI(resultUri);
                Log.d("PhotoPicker", "Selected URI: " + uri);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        getUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageChooser();
//            }
//        });
    }


    private void imageChooser(){

        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

//  Устарело выше поновее
//    private void imageChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//        //startActivityForResult(intent,1);
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    resultUri = selectedImageUri
//                    profileImage.setImageURI(selectedImageUri);
//
//                }
//            }
//        }
//    }

    private void getUserInfo() {
        mSettingsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("name")!=null){
                        name = map.get("name").toString();
                        nameText.setText(name);
                    }
                    if(map.get("phone")!=null){
                        phone = map.get("phone").toString();
                        phoneText.setText(phone);
                    }
                    if (map.get("profileImageUrl")!=null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(profileImageUrl).into(profileImage);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Ультрадушная хуйня
    public void confirmSettings(View view) {
        name= nameText.getText().toString();
        phone= phoneText.getText().toString();
        if((!phone.equals("") && !name.equals(""))){
            //зАПОЛНЕНИЕ ДАННЫХ
            Map userInfo = new HashMap<>();
            userInfo.put("name",name);
            userInfo.put("phone",phone);
            mSettingsDatabase.updateChildren(userInfo);
            if(resultUri!= null){
                StorageReference filepath = FirebaseStorage.getInstance().getReference()
                        .child("profileImages").child(userId);
                Bitmap bitmap = null;

                //бИТМАПИМ
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Сжатие пикчи
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
                byte[] data = baos.toByteArray();

                //Блять тут вообще нихуя не понимаю че за пиздец происходит D:
                UploadTask uploadTask = filepath.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        finish();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl = uri;
                                Map userInfo = new HashMap<>();
                                userInfo.put("profileImageUrl",downloadUrl.toString());
                                mSettingsDatabase.updateChildren(userInfo);
                                finish();
                            }
                        });
                    }
                });
            }
            else {
                finish();
            }
        }

    }

    public void goToMain(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(SettingsActivity.this, LoginRegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}