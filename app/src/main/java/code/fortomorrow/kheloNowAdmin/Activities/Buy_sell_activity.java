package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_buy_sell;
import code.fortomorrow.kheloNowAdmin.Fragments.Fragment_shop;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.TabControl.TabLayoutControl;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Buy_sell_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    APIService apiService;
    String api_token, secret_id;
    ImageView backButton;
    FloatingActionButton addItemButton;
    Dialog loader;
    ImageView selectImageButton, reloadButton;

    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    int check = 0;
    String uriStrng;
    final int IMAGE_REQUEST_CODE = 999;
    Bitmap bitmap;
    private Uri filepath;


    TabLayout tabLayout;
    ViewPager viewPager;

    String[] types = {"shop", "buy_sell"};
    String type;

    EditText watchLinkEditText;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/5, bitmap.getHeight()/5, false);
                    selectImageButton.setImageBitmap(bitmap);
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
        setContentView(R.layout.activity_buy_sell);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_item();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

                /*overridePendingTransition(0, 0);
                recreate();
                overridePendingTransition(0, 0);*/
            }
        });


        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Fragment_buy_sell(), "Buy & Sell");
        viewPageAdapter.addFragment(new Fragment_shop(), "Shop");

        //viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(1).select();

        TabLayoutControl.enableTabs(tabLayout, false);
    }


    private void add_item() {
        Dialog addItemAlert = new Dialog(Buy_sell_activity.this);
        addItemAlert.setContentView(R.layout.buy_sell_item_add_alert);
        addItemAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addItemAlert.setCancelable(false);
        addItemAlert.show();

        EditText nameEditText = addItemAlert.findViewById(R.id.nameEditTextID);
        EditText priceEditText = addItemAlert.findViewById(R.id.priceEditTextID);
        EditText linkEditText = addItemAlert.findViewById(R.id.linkEditTextID);
        watchLinkEditText = addItemAlert.findViewById(R.id.watchLinkEditTextID);
        EditText discountEditText = addItemAlert.findViewById(R.id.discountEditTextID);
        EditText descriptionEditText = addItemAlert.findViewById(R.id.descriptionEditTextID);
        AppCompatButton addButton = addItemAlert.findViewById(R.id.addButtonID);
        ImageView closeButton = addItemAlert.findViewById(R.id.closeButtonID);
        Spinner typeSpinner = addItemAlert.findViewById(R.id.typeSpinnerID);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        typeSpinner.setAdapter(aa);

        typeSpinner.setOnItemSelectedListener(this);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemAlert.dismiss();
            }
        });

        Window window = addItemAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        selectImageButton = addItemAlert.findViewById(R.id.selectImageButtonID);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Buy_sell_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imgToString(bitmap);
                //Toast.makeText(Buy_sell_activity.this, type, Toast.LENGTH_SHORT).show();
                String productName = nameEditText.getText().toString().trim();
                String productPrice = priceEditText.getText().toString().trim();
                String linkText = linkEditText.getText().toString().trim();
                String discount = discountEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String watchLink = watchLinkEditText.getText().toString().trim();

                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productPrice) || TextUtils.isEmpty(linkText)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    if (check != 1) {
                        Toasty.error(getApplicationContext(), "select image", Toasty.LENGTH_SHORT).show();
                    } else {
                        //Toasty.success(getApplicationContext(), "ok", Toasty.LENGTH_SHORT).show();
                        //imgToString(bitmap);
                        loader.show();
                        apiService.addButAndSellItem(secret_id, api_token, productName, description, productPrice, linkText, watchLink, discount, type, "data:image/jpeg;base64," + imgToString(bitmap)).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                loader.dismiss();
                                if (response.body().getE() == 0) {
                                    Toasty.success(getApplicationContext(), "successful", Toasty.LENGTH_SHORT).show();
                                } else {
                                    Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                loader.dismiss();
                                Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void init_view() {

        loader = new Dialog(Buy_sell_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        Log.d("tokenxx", secret_id+" "+api_token);

        backButton = findViewById(R.id.backButton);
        addItemButton = findViewById(R.id.addItemButtonID);

        tabLayout = findViewById(R.id.tab_layoutID);
        viewPager = findViewById(R.id.view_pagerLayoutID);

        reloadButton = findViewById(R.id.reloadButtonID);

    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        long lengthbmp = imgbytes.length;

        //Toast.makeText(getApplicationContext(), String.valueOf(lengthbmp / 1024), Toast.LENGTH_SHORT).show();

        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Buy_sell_activity.this);
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
        type = parent.getItemAtPosition(position).toString();

        if (type.equals("shop")) {
            watchLinkEditText.setVisibility(View.GONE);
        } else {
            watchLinkEditText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}