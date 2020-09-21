package com.example.myapplication.Classes;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.Database.DataBaseOperations;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    EditText edt_name, edt_number, edt_email;
    ImageView iv_doc_file, iv_add_img;
    Button btn_registration;
    DataBaseOperations mDataBaseOperations;
    Boolean imgSelected = false;

    public static final int PICK_GALLERY = 1;
    private static final int PICK_CAMERA = 2;
    Bitmap bmp;

    public static String[] STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        main();
    }

    private void main() {
        mDataBaseOperations = new DataBaseOperations(getApplicationContext());
        edt_name = findViewById(R.id.edt_name);
        edt_number = findViewById(R.id.edt_number);
        edt_email = findViewById(R.id.edt_email);
        iv_add_img = findViewById(R.id.iv_add_img);
        iv_doc_file = findViewById(R.id.iv_doc_file);
        btn_registration = findViewById(R.id.btn_registration);

        iv_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionDialog();
            }
        });

        btn_registration.setOnClickListener(v -> {
            boolean val = validation();

            if (val) {
                String fileNameToSaveInDB = saveProfileImage();

                String name = edt_name.getText().toString().trim();
                String number = edt_number.getText().toString().trim();
                String email = edt_email.getText().toString().trim();

                mDataBaseOperations.insert(fileNameToSaveInDB, name, number, email);

                Log.d("--img--", "fileNameToSaveInDB: " + fileNameToSaveInDB);

            }

        });

    }

    public static String getRandom() {
        String result = "";
        Random random = new Random();
        result = String.format("%02d", random.nextInt(1000));
        return result;
    }

    private String saveProfileImage() {
        File _saveImage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("--img--", "filepath: " + _saveImage);
        File dir = new File(_saveImage.getAbsolutePath() + "/MyDemo/");
        String _filename = "";
        OutputStream output;

        _filename = "profile.jpg";
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.d("--img--", "dir: " + dir);
        File fileToSave = new File(dir.getAbsolutePath() + "/" + _filename.concat(getRandom()));
        Log.d("--img--", "fileToSave: " + fileToSave.getAbsolutePath());


        try {
            output = new FileOutputStream(fileToSave);
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, output);
            output.flush();
            output.close();

            Toast.makeText(RegistrationActivity.this, "Profile Saved to SD Card", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            _filename = "";
            Log.e("--img--", "Exception: " + e.getMessage());
            e.printStackTrace();
        }

        return _filename;
    }


    void selectionDialog() {

        final Dialog dialog = new Dialog(RegistrationActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cam_gallary);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);


        LinearLayout ll_gallery = dialog.findViewById(R.id.ll_gallery);
        LinearLayout ll_camera = dialog.findViewById(R.id.ll_camera);

        ll_gallery.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.cancel();

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS,
                        PICK_GALLERY);
            } else {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select profile"), PICK_GALLERY);

            }
        });

        ll_camera.setOnClickListener(v -> {
            dialog.dismiss();
            dialog.cancel();

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, CAMERA_PERMISSIONS, PICK_CAMERA);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_CAMERA);
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri _SelectedFileUri = data.getData();

            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), _SelectedFileUri);
                Log.e("Check", "Try: " + _SelectedFileUri);
                iv_doc_file.setImageBitmap(bmp);
                imgSelected = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Check", "Exception: " + e.getMessage());
            }
        } else if (requestCode == PICK_CAMERA && resultCode == RESULT_OK && data != null) {
            Uri _CaptureImage;
            try {
                bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                _CaptureImage = getImageUri(getApplicationContext(), bmp);
                Log.e("Check", "Try: " + _CaptureImage);
                iv_doc_file.setImageBitmap(bmp);
                imgSelected = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Check", "Exception: " + e.getMessage());
            }

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.e("Check", "Path: " + path);
        return Uri.parse(path);
    }

    private Boolean validation() {


        if (!imgSelected) {
            Toast.makeText(RegistrationActivity.this, "Document is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edt_name.getText() == null || edt_name.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
            edt_name.requestFocus();
            return false;
        }
        if (edt_number.getText() == null || edt_number.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Mobile!", Toast.LENGTH_SHORT).show();
            edt_number.requestFocus();
            return false;
        }

        if (edt_email.getText() == null || edt_email.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
            edt_email.requestFocus();
            return false;
        }

        return true;
    }

}
