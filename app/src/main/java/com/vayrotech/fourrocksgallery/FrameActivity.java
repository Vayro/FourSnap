package com.vayrotech.fourrocksgallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;

import com.vayrotech.fourrocksgallery.DatabaseStuff.ViewDatabaseFragment;
import com.vayrotech.fourrocksgallery.FolderFragmentStuff.FoldersFragment;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class FrameActivity extends AppCompatActivity {


    public static final int CAMERA_REQUEST_CODE = 69;
    public static final int CAMERA_PASS_REQUEST_CODE = 420;
    public static final String TABLENAME = "tableimage";

    Uri selectedImage; //declare selected image bitmap; this will be passed to the "selected" fragment
    String currentPhotoPath;
    //declare buttons
    Button cameraButton, galleryButton, selectedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);


        //initialize bitmap placeholder
        selectedImage = null;


        //initialize buttons
        cameraButton = findViewById(R.id.buttonCamera);
        galleryButton = findViewById(R.id.buttonGallery);
        selectedButton = findViewById(R.id.buttonSelected);

        //set onClickListeners
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(FrameActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                //stuff

                askCameraPermissions();


            }
        });


        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toGalleryFrag();


            }
        });

        selectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSelectedFrag();
            }
        });


        //starts app with galleryFragment
        replaceFragment(new FoldersFragment());


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.selectedMenu:
                toSelectedFrag();
                return true;
            case R.id.galleryMenu:
                toGalleryFrag();
                return true;
            case R.id.cameraMenu:
                askCameraPermissions();
                return true;
            case R.id.toDatabaseMenu:
                toDatabaseFrag();
                return true;

          //  case R.id.dateAscending:
            case R.id.dateDescending:
            case R.id.nameAscending:
            case R.id.nameDescending:
            case R.id.clearDBMenu:
                return false;


        }
return true;


    }

    private void toDatabaseFrag() {
        replaceFragment(new ViewDatabaseFragment());
    }

    public void toSelectedFrag()
    {

        replaceFragment(new SelectedFragmentActivity());

    }
    public void toGalleryFrag()
    {
        replaceFragment(new FoldersFragment());
        //replaceFragment(new GalleryFragmentActivity());

    }













    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m);
        return true;


    }


    //camera stuff ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void askCameraPermissions() {

        //permission for camera request
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // openCamera();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Requires permission to use camera.", Toast.LENGTH_SHORT).show();

            }


        }


    }




  /*  private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,CAMERA_REQUEST_CODE);
    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {












         //  Bitmap checkDataImg = (Bitmap) data.getExtras().get("data");




/*
           // String filePath = data.getPath();
           // Bitmap bitmap = BitmapFactory.decodeFile(data.getExtras().get("data"));
            if (checkDataImg==null){
                Toast.makeText(this, "bitmap null", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Bitmap not null ", Toast.LENGTH_SHORT).show();

            }


*/






            if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "No Photo Taken, camera cancelled", Toast.LENGTH_SHORT).show();
                File fdelete = new File(currentPhotoPath);
                if(fdelete.delete())
                {
                    Log.d("deleted", "Temporary file deleted");
                }


            }else{


            if (resultCode == Activity.RESULT_OK /*&& data !=null test*/) {
                File f = new File(currentPhotoPath);
                selectedImage = Uri.fromFile(f);
                Toast.makeText(this, "Saved file to " + Uri.fromFile(f), Toast.LENGTH_SHORT).show();


                //galleryAddPic()
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);


                //move to SelectedFragment upon leaving camera
                replaceFragment(new SelectedFragmentActivity());


            }

            else
            {
                Toast.makeText(this, "No photo taken", Toast.LENGTH_SHORT).show();
                File fdelete = new File(currentPhotoPath);
                if(fdelete.delete())
                {
                    Log.d("deleted", "Temporary file deleted");
                }


            }


        }}


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); //temp saving location
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //External/Public directory
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //   static final int REQUEST_IMAGE_CAPTURE = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent


        //the following code I got from Google official API, for some reason it doesn't work with my AVD devices, but it worked when I tested it on my actual device:
        // if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


        //INSTEAD, I had to use the following if statement:
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();












            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "penis", Toast.LENGTH_SHORT).show();
                // ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        } else {

            Toast.makeText(this, "No camera activity", Toast.LENGTH_SHORT).show();
        }
    }


    //end camera stuff ~~~~~~~~~~~~~~~~~~~~~~~~~

    public interface FragmentChangeListener {

        void replaceFragment(Fragment fragment);

    }


    //load fragment
    public void replaceFragment(Fragment fragment) {

        //  Toast.makeText(this,"replacing fragment",Toast.LENGTH_SHORT).show(); <------this dubugging toast message is no longer needed
        Bundle bundle = new Bundle();
        bundle.putParcelable("passedImage", selectedImage);
        bundle.putString("tableName", TABLENAME);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }


}
