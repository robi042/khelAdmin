package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_uploaded_images_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_uploaded_image_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Ludo_view_image_activity extends AppCompatActivity implements Ludo_uploaded_images_adapter.OnItemClickListener{
    String matchID;
    APIService apiService;
    String api_token, secret_id;
    ImageView backButton;
    RecyclerView imageRecyclerView;
    private List<Ludo_uploaded_image_response.M> dataList = new ArrayList<>();
    private Ludo_uploaded_images_adapter adapter;
    Dialog loader;
    LinearLayout noDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_view_image);
        
        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        load_images();
    }

    private void load_images() {

        apiService.getUploadedLudoImages(secret_id, api_token, matchID).enqueue(new Callback<Ludo_uploaded_image_response>() {
            @Override
            public void onResponse(Call<Ludo_uploaded_image_response> call, Response<Ludo_uploaded_image_response> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    imageRecyclerView.setVisibility(View.VISIBLE);
                    dataList = response.body().getM();
                    adapter = new Ludo_uploaded_images_adapter(dataList);
                    adapter.setOnItemClickListener(Ludo_view_image_activity.this::OnItemClick);
                    imageRecyclerView.setAdapter(adapter);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    imageRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_uploaded_image_response> call, Throwable t) {
                noDataLayout.setVisibility(View.VISIBLE);
                imageRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void init_view() {
        loader = new Dialog(Ludo_view_image_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        matchID = getIntent().getStringExtra("match_id");

        EasySharedPref.init(Ludo_view_image_activity.this);
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        noDataLayout = findViewById(R.id.noDataLayoutID);

        imageRecyclerView = findViewById(R.id.imageRecyclerViewID);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void OnItemClick(int position) {
        Ludo_uploaded_image_response.M response = dataList.get(position);

        Dialog showImageAlert = new Dialog(Ludo_view_image_activity.this);
        showImageAlert.setContentView(R.layout.ludo_result_image_view_alert);
        showImageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showImageAlert.setCancelable(false);
        showImageAlert.show();

        Window window = showImageAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = showImageAlert.findViewById(R.id.closeButtonID);
        ImageView resultImage= showImageAlert.findViewById(R.id.resultImageID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageAlert.dismiss();
            }
        });

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(resultImage);
        pAttacher.update();

        Picasso.get().load(response.getImageLink()).into(resultImage);
    }
}