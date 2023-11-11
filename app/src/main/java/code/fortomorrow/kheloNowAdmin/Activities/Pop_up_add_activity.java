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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pop_up_add_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView backButton, sliderImage;
    AppCompatButton addImageButton;
    TextInputEditText titleText, urlText;
    LinearLayout imageLayout, textLayout;
    Dialog loader;

    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String uriStrng;
    final int IMAGE_REQUEST_CODE = 999;
    Bitmap bitmap;
    private Uri filepath;
    Spinner itemSpinner;
    String[] items = {"image", "text"};
    String item;

    EditText inputText;
    AppCompatButton addTextButton;
    String api_token, secret_id;
    APIService apiService;
    TextView popUpCheckBox;
    int pos = 0;
    LinearLayout statusUpdateLayoutButton;

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
                    sliderImage.setImageBitmap(bitmap);
                    //sliderImage.setImageURI(filepath);
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
        setContentView(R.layout.activity_pop_up_add);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Pop_up_add_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        init_view();

        itemSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        itemSpinner.setAdapter(aa);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sliderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Pop_up_add_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String title = titleText.getText().toString().trim();
                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                String url = urlText.getText().toString().trim();

                if (TextUtils.isEmpty(url)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    if (check != 1) {
                        Toasty.error(getApplicationContext(), "Empty image", Toasty.LENGTH_SHORT).show();
                    } else {

                        loader.show();
                        apiService.createPopUp(secret_id, api_token, item, "", "data:image/jpeg;base64," + imgToString(bitmap), url).enqueue(new Callback<SorkariResponse>() {
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

        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                String input = inputText.getText().toString().trim();

                if (TextUtils.isEmpty(input)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {

                    loader.show();

                    apiService.createPopUp(secret_id, api_token, item, input, "", "").enqueue(new Callback<SorkariResponse>() {
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
                        }
                    });

                }
            }
        });

        statusUpdateLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                apiService.updatePopUpStatus(secret_id, api_token).enqueue(new Callback<Pop_up_response>() {
                    @Override
                    public void onResponse(Call<Pop_up_response> call, Response<Pop_up_response> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            Toasty.success(getApplicationContext(), "Status updated", Toasty.LENGTH_SHORT).show();
                            pop_up_check();
                        } else {
                            //Toasty.error(getApplicationContext(), response.body().getM().getPopupStatus(), Toasty.LENGTH_SHORT).show();
                            Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Pop_up_response> call, Throwable t) {
                        Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                        loader.dismiss();
                    }
                });
            }
        });

        pop_up_check();

    }

    private void pop_up_check() {
        apiService.getPopUpStatus(secret_id, api_token).enqueue(new Callback<Pop_up_response>() {
            @Override
            public void onResponse(Call<Pop_up_response> call, Response<Pop_up_response> response) {
                if (response.body().getE() == 0) {
                    //Toasty.success(getApplicationContext(), "Status updated", Toasty.LENGTH_SHORT).show();
                    Boolean status = response.body().getM().getPopupStatus();
                    if (status) {
                        popUpCheckBox.setText("Hide");
                        popUpCheckBox.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        popUpCheckBox.setText("Show");
                        popUpCheckBox.setTextColor(getResources().getColor(R.color.green));
                    }
                }
            }

            @Override
            public void onFailure(Call<Pop_up_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {

        backButton = findViewById(R.id.backButton);
        sliderImage = findViewById(R.id.sliderImageID);
        addImageButton = findViewById(R.id.addImageButtonID);
        //titleText = findViewById(R.id.titleTextID);
        urlText = findViewById(R.id.urlTextID);
        itemSpinner = findViewById(R.id.itemSpinnerID);
        textLayout = findViewById(R.id.textLayoutID);
        imageLayout = findViewById(R.id.imageLayoutID);
        inputText = findViewById(R.id.inputTextID);
        addTextButton = findViewById(R.id.addTextButtonID);
        popUpCheckBox = findViewById(R.id.popUpCheckBoxID);
        statusUpdateLayoutButton = findViewById(R.id.statusUpdateLayoutButtonID);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Pop_up_add_activity.this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = String.valueOf(parent.getSelectedItem());

        //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();

        if (item.equals("image")) {
            imageLayout.setVisibility(View.VISIBLE);
            textLayout.setVisibility(View.GONE);
        } else if (item.equals("text")) {
            imageLayout.setVisibility(View.GONE);
            textLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}