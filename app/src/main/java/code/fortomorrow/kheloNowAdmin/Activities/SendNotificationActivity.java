package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationActivity extends AppCompatActivity {

    private TextView imageNotification;
    EditText mTitle, mDetails;
    Button mBtn;
    private TextView imageNotificationOff;
    private APIService apiService;
    private StorageReference ProductImagesRef;
    private DatabaseReference productsRef;
    private String api_token, secret_id;
    ImageView backButton;
    ImageView selectImageButton;

    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String uriStrng;
    final int IMAGE_REQUEST_CODE = 999;
    Bitmap bitmap;
    private Uri filepath;

    Dialog loader;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4, bitmap.getHeight()/4, false);
                    //selectedImage.setImageBitmap(bitmap);
                    selectImageButton.setImageURI(filepath);
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
        setContentView(R.layout.activity_send_notification);

        init_view();

        imageNotificationOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setApplicationId("com.androiddevelopershub.khelaghor") // Required for Analytics.
                            .setApiKey("1:330570226639:android:3cc1d7a8f5cbede17c10ba") // Required for Auth.
                            .setDatabaseUrl("https://khelo-d763f-default-rtdb.firebaseio.com/")
                            .setStorageBucket("khelo-d763f.appspot.com")
                            // If you wanted to
                            .build();
                    FirebaseApp.initializeApp(SendNotificationActivity.this, options, "CompanyA");
                    FirebaseApp appCompanyA = FirebaseApp.getInstance("CompanyA");

                    productsRef = FirebaseDatabase.getInstance(appCompanyA).getReference();
                    ProductImagesRef = FirebaseStorage.getInstance(appCompanyA).getReference().child("Product Images");
                    HashMap<String, Object> productMap = new HashMap<>();

                    productMap.put("link", "No");
                    productMap.put("imagelink", "No");
                    productMap.put("status", "false");
                    productMap.put("message", "Hello I am Abdul from Kallanpur");
                    productMap.put("button", "Ok");


                    productsRef.child("Body").updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SendNotificationActivity.this, "Image Notification Turned Off from server", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(SendNotificationActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {

                }
            }
        });

        imageNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendNotificationActivity.this, Send_image_notification_activity.class));
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                String details = mDetails.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(details)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    if (check == 1) {
                        loader.show();
                        // imgToString(bitmap);
                        apiService.sendImageNotification(secret_id, api_token, "data:image/jpeg;base64," + imgToString(bitmap), title, details).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                loader.dismiss();
                                if (response.body().getE() == 0) {
                                    Toasty.success(getApplicationContext(), "Successfully Send", Toasty.LENGTH_SHORT).show();
                                } else {
                                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                loader.dismiss();
                            }
                        });
                    } else {
                        apiService.sendNotification(secret_id, api_token, title, details).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                if (response.body().getE() == 0) {
                                    Toasty.success(getApplicationContext(), "Successfully Send", Toasty.LENGTH_SHORT).show();
                                } else {
                                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SorkariResponse> call, Throwable t) {

                            }
                        });
                    }

                }


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(SendNotificationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

    }

    private void init_view() {
        loader = new Dialog(SendNotificationActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(SendNotificationActivity.this);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);
        imageNotificationOff = findViewById(R.id.imageNotificationOff);
        backButton = findViewById(R.id.backButton);
        mTitle = findViewById(R.id.mTitle);
        mDetails = findViewById(R.id.mDetails);
        imageNotification = findViewById(R.id.imageNotification);
        mBtn = findViewById(R.id.mBtn);
        selectImageButton = findViewById(R.id.selectImageButtonID);
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        long lengthbmp = imgbytes.length;

       // Toast.makeText(getApplicationContext(), String.valueOf(lengthbmp / 1024), Toast.LENGTH_SHORT).show();

        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SendNotificationActivity.this);
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
