package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slider_add_activity extends AppCompatActivity {

    ImageView backButton, sliderImage;

    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String uriStrng;
    final int IMAGE_REQUEST_CODE = 999;
    Bitmap bitmap;
    private Uri filepath;

    AppCompatButton addButton;
    TextInputEditText titleText, urlText;
    Dialog loader;
    APIService apiService;
    String secret_id, api_token;
    FloatingActionButton viewSliderList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 900, 450, false);
                    //selectedImage.setImageBitmap(bitmap);
                    sliderImage.setImageURI(filepath);
                    check = 1;
                    //uriStrng = uri.toString();
                    //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_add);


        init_view();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sliderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Slider_add_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleText.getText().toString().trim();
                String url = urlText.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(url)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    if (check != 1) {
                        Toasty.error(getApplicationContext(), "Empty image", Toasty.LENGTH_SHORT).show();
                    } else {

                        //Toast.makeText(getApplicationContext(), url + " " + title, Toast.LENGTH_SHORT).show();
                        //Log.d("matchxxx", matchID + " " + jwt_token);
                        loader.show();
                        apiService.createSlider(secret_id, api_token, title, "data:image/jpeg;base64," + imgToString(bitmap), url).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                loader.dismiss();
                                if (response.body().getE() == 0) {
                                    Toasty.success(getApplicationContext(), "Created", Toasty.LENGTH_SHORT).show();
                                    //finish();
                                } else {
                                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                                loader.dismiss();
                                //Log.d("errroxx", t.getMessage());
                            }
                        });
                    }
                }


            }
        });

        viewSliderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Slider_list_activity.class));
            }
        });
    }

    private void init_view() {

        loader = new Dialog(Slider_add_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        sliderImage = findViewById(R.id.sliderImageID);
        addButton = findViewById(R.id.addButtonID);
        titleText = findViewById(R.id.titleTextID);
        urlText = findViewById(R.id.urlTextID);
        viewSliderList = findViewById(R.id.viewSliderListID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        //long lengthbmp = imgbytes.length;

        //Toast.makeText(getApplicationContext(), String.valueOf(lengthbmp/1024), Toast.LENGTH_SHORT).show();

        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Slider_add_activity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_GET_CONTENT));
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}