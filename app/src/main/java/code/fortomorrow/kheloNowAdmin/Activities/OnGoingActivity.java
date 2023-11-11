package code.fortomorrow.kheloNowAdmin.Activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor.Arena_valor_ongoing_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.FreeFire.Free_fire_ongoing_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.LudoOngoingMatchAdapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.M;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_ongoing_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnGoingActivity extends AppCompatActivity implements Arena_valor_ongoing_adapter.OnArenaValorItemClickListener, LudoOngoingMatchAdapter.OnItemClickListener, LudoOngoingMatchAdapter.OnShowImageClickListener {
    private RecyclerView ongoingMatchRV;
    private APIService apiService;
    private String api_token, secret_id;
    private Spinner typeOfmatches;
    private List<M> gameTypeResponses = new ArrayList<>();
    private List<String> gameType = new ArrayList<>();
    private String checkType = "1";
    private String playTypeID;
    private List<Ludo_ongoing_response.M> ludoMatchList;
    LudoOngoingMatchAdapter adapter;
    Dialog loader;
    ImageView backButton;
    TextView nameText;
    Dialog ongoingDialog;
    //String previousNotes;

    private Arena_valor_ongoing_adapter arenaValorOngoingAdapter;
    private List<Arena_valor_response.M> arenaValorOngoingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);

        initall();


        playTypeID = getIntent().getStringExtra("play_type");
        getAllOngoingMatches(playTypeID);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_title();

    }

    private void set_title() {
        if (playTypeID.equals("1")) {
            nameText.setText("Free Fire (Regular)");
        } else if (playTypeID.equals("2")) {
            nameText.setText("Free Fire (CS Regular)");
        } else if (playTypeID.equals("3")) {
            nameText.setText("Ludo (Regular)");
        } else if (playTypeID.equals("4")) {
            nameText.setText("Ludo (Grand)");
        } else if (playTypeID.equals("5")) {
            nameText.setText("Free Fire (CS Grand)");
        } else if (playTypeID.equals("6")) {
            nameText.setText("Daily Scrims");
        } else if (playTypeID.equals("7")) {
            nameText.setText("Ludo (Premium Match)");
        } else if (playTypeID.equals("8")) {
            nameText.setText("Ludo (4 Player)");
        } else if (playTypeID.equals("9")) {
            nameText.setText("Free Fire (Premium)");
        } else if (playTypeID.equals("10")) {
            nameText.setText("Free Fire (Grand)");
        } else if (playTypeID.equals("11")) {
            nameText.setText("Arena of Valor (Regular)");
        } else if (playTypeID.equals("12")) {
            nameText.setText("Arena of Valor (Grand)");
        }
    }

    private void initall() {
        loader = new Dialog(OnGoingActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);
        //Log.d("tokenxx", api_token);
        ongoingMatchRV = findViewById(R.id.ongoingMatchRV);
        backButton = findViewById(R.id.backButton);
        nameText = findViewById(R.id.nameTextID);

        ongoingMatchRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Log.d("tokenxx", api_token);
    }

    private void getAllOngoingMatches(String playTypeID) {

        if (playTypeID.equals("3") || playTypeID.equals("4") || playTypeID.equals("7") || playTypeID.equals("8")) {
            ludo_func(playTypeID);
        } else if (playTypeID.equals("11") || playTypeID.equals("12")) {
            arena_of_valor_matches(playTypeID);
        } else {

            free_fire_func(playTypeID);
        }

    }

    private void arena_of_valor_matches(String playTypeID) {
        //Toast.makeText(this, playTypeID, Toast.LENGTH_SHORT).show();
        loader.show();
        apiService.arenaValorGet_ongoing_list(secret_id, api_token, playTypeID).enqueue(new Callback<Arena_valor_response>() {
            @Override
            public void onResponse(Call<Arena_valor_response> call, Response<Arena_valor_response> response) {
                loader.dismiss();
                if (response.body().e == 0) {
                    arenaValorOngoingList = response.body().m;
                    //Toast.makeText(getApplicationContext(), String.valueOf(ludoMatchList.size()), Toast.LENGTH_SHORT).show();
                    arenaValorOngoingAdapter = new Arena_valor_ongoing_adapter(arenaValorOngoingList);
                    arenaValorOngoingAdapter.setOnClickListener(OnGoingActivity.this::OnArenaValorItemClick);
                    ongoingMatchRV.setAdapter(arenaValorOngoingAdapter);
                }
            }

            @Override
            public void onFailure(Call<Arena_valor_response> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void free_fire_func(String playTypeID) {
        loader.dismiss();
        apiService.getOnGoingResponse(secret_id, api_token, playTypeID).enqueue(new Callback<OngoingMatchResponse>() {
            @Override
            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    ongoingMatchRV.setAdapter(new Free_fire_ongoing_adapter(OnGoingActivity.this, getApplicationContext(), response.body().getM()));
                }
            }

            @Override
            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void ludo_func(String type) {

        //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
        //Log.d("infoxxx", api_token);
        loader.show();
        apiService.getLudoOnGoingListResponse(secret_id, api_token, type).enqueue(new Callback<Ludo_ongoing_response>() {
            @Override
            public void onResponse(Call<Ludo_ongoing_response> call, Response<Ludo_ongoing_response> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    ludoMatchList = new ArrayList<>();
                    ludoMatchList = response.body().getM();
                    //Toast.makeText(getApplicationContext(), String.valueOf(ludoMatchList.size()), Toast.LENGTH_SHORT).show();
                    adapter = new LudoOngoingMatchAdapter(OnGoingActivity.this, getApplicationContext(), ludoMatchList);
                    adapter.setOnClickListener(OnGoingActivity.this::OnItemClick, OnGoingActivity.this::OnImageClick);
                    ongoingMatchRV.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Ludo_ongoing_response> call, Throwable t) {
                loader.dismiss();
            }
        });

    }

    public void movetoResult(Integer matchId, String note) {
        Dialog optionDialog = new Dialog(OnGoingActivity.this);
        optionDialog.setContentView(R.layout.ludo_ongoing_option_alert);
        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.show();

        Window window = optionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = optionDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout moveResultLayout = optionDialog.findViewById(R.id.moveResultLayoutID);
        LinearLayout viewImagesLayout = optionDialog.findViewById(R.id.viewImagesLayoutID);
        LinearLayout backTomatchListId = optionDialog.findViewById(R.id.backTomatchListId);

        viewImagesLayout.setVisibility(View.GONE);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(OnGoingActivity.this);
                noteDialog.setContentView(R.layout.note_alert);
                noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noteDialog.show();

                Window window = noteDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                ImageView closeButton = noteDialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteDialog.dismiss();
                    }
                });

                EditText noteEditText = noteDialog.findViewById(R.id.noteEditTextID);
                AppCompatButton noteUpdateButton = noteDialog.findViewById(R.id.noteUpdateButtonID);

                noteUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteText = noteEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(noteText)) {
                            Toast.makeText(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            loader.show();

                            apiService.updateFreeFireNotes(secret_id, api_token, String.valueOf(matchId), noteText).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        noteDialog.dismiss();
                                        optionDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                noteEditText.setText(note);
            }
        });

        moveResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(OnGoingActivity.this).create();
                alertDialog.setTitle("Match Moving to Result");
                alertDialog.setMessage("Do you want to move Result...");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.moveToResult(secret_id, api_token, String.valueOf(matchId)).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        Toasty.success(getApplicationContext(), "Moved to ongoing").show();
                                        apiService.getOnGoingResponse(secret_id, api_token, playTypeID).enqueue(new Callback<OngoingMatchResponse>() {
                                            @Override
                                            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                                                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));

                                                ongoingMatchRV.setAdapter(new Free_fire_ongoing_adapter(OnGoingActivity.this, getApplicationContext(), response.body().getM()));
                                                alertDialog.dismiss();
                                                optionDialog.dismiss();
                                            }

                                            @Override
                                            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //optionDialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        backTomatchListId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(OnGoingActivity.this).create();
                alertDialog.setTitle("Match Moving to Created List");
                alertDialog.setMessage("Do you want to move Match List...");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.back_to_created_list(secret_id, api_token, String.valueOf(matchId)).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        Toasty.success(getApplicationContext(), "Moved to Match List").show();
                                        apiService.getOnGoingResponse(secret_id, api_token, playTypeID).enqueue(new Callback<OngoingMatchResponse>() {
                                            @Override
                                            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                                                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));

                                                ongoingMatchRV.setAdapter(new Free_fire_ongoing_adapter(OnGoingActivity.this, getApplicationContext(), response.body().getM()));
                                                alertDialog.dismiss();
                                                optionDialog.dismiss();
                                            }

                                            @Override
                                            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //optionDialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });


    }

    @Override
    public void OnItemClick(int position) {
        Ludo_ongoing_response.M response = ludoMatchList.get(position);

        String matchID = String.valueOf(response.getMatchId());

        ongoingDialog = new Dialog(OnGoingActivity.this);
        ongoingDialog.setContentView(R.layout.ludo_ongoing_option_alert);
        ongoingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ongoingDialog.show();

        Window window = ongoingDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = ongoingDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout moveResultLayout = ongoingDialog.findViewById(R.id.moveResultLayoutID);
        LinearLayout viewImagesLayout = ongoingDialog.findViewById(R.id.viewImagesLayoutID);
        LinearLayout backTomatchListId = ongoingDialog.findViewById(R.id.backTomatchListId);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Add Notes", Toast.LENGTH_SHORT).show();
                Dialog noteDialog = new Dialog(OnGoingActivity.this);
                noteDialog.setContentView(R.layout.note_alert);
                noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noteDialog.show();

                Window window = noteDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                ImageView closeButton = noteDialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        noteDialog.dismiss();

                    }
                });

                EditText noteEditText = noteDialog.findViewById(R.id.noteEditTextID);
                AppCompatButton noteUpdateButton = noteDialog.findViewById(R.id.noteUpdateButtonID);

                noteUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteText = noteEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(noteText)) {
                            Toast.makeText(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            loader.show();

                            apiService.updateLudoNotes(secret_id, api_token, matchID, noteText).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        noteDialog.dismiss();
                                        ongoingDialog.dismiss();

                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                noteEditText.setText(ludoMatchList.get(position).getNote());
            }
        });


        moveResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(OnGoingActivity.this).create();
                alertDialog.setTitle("Match Moving to Result");
                alertDialog.setMessage("Do you want to move Result?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loader.show();
                                apiService.moveLudoResultResponse(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        loader.dismiss();


                                        if (response.body().getE() == 0) {
                                            Toasty.success(getApplicationContext(), "Moved to result", Toasty.LENGTH_SHORT).show();

                                            alertDialog.dismiss();
                                            ongoingDialog.dismiss();
                                            ludo_func(playTypeID);

                                        } else {
                                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                        loader.dismiss();

                                        Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //ongoingDialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        backTomatchListId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(OnGoingActivity.this).create();
                alertDialog.setTitle("Match Moving to Created List");
                alertDialog.setMessage("Do you want to move Match List...");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.ludo_back_to_created_list(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        Toasty.success(getApplicationContext(), "Moved to Match List").show();
                                        apiService.getOnGoingResponse(secret_id, api_token, playTypeID).enqueue(new Callback<OngoingMatchResponse>() {
                                            @Override
                                            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                                                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));

                                                ongoingMatchRV.setAdapter(new Free_fire_ongoing_adapter(OnGoingActivity.this, getApplicationContext(), response.body().getM()));
                                                alertDialog.dismiss();
                                                ongoingDialog.dismiss();
                                            }

                                            @Override
                                            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //optionDialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        viewImagesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ludo_view_image_activity.class);
                intent.putExtra("match_id", matchID);
                startActivity(intent);
            }
        });

    }

    @Override
    public void OnImageClick(int position) {
        /*Ludo_game_list_response response = ludoMatchList.get(position);

        Dialog dialog = new Dialog(OnGoingActivity.this);
        dialog.setContentView(R.layout.ludo_result_image_view_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView resultImage = dialog.findViewById(R.id.resultImageID);

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(resultImage);
        pAttacher.update();

        try {
            //Glide.with(getApplicationContext()).load(response.getImageLink()).into(resultImage);
            Picasso.get().load(response.getImageLink()).into(resultImage);
        } catch (Exception e) {

        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void OnArenaValorItemClick(int position) {

        Arena_valor_response.M response = arenaValorOngoingList.get(position);
        String matchID = response.matchId.toString();

        Dialog optionDialog = new Dialog(OnGoingActivity.this);
        optionDialog.setContentView(R.layout.ludo_ongoing_option_alert);
        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.show();

        Window window = optionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = optionDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout moveResultLayout = optionDialog.findViewById(R.id.moveResultLayoutID);
        LinearLayout viewImagesLayout = optionDialog.findViewById(R.id.viewImagesLayoutID);

        viewImagesLayout.setVisibility(View.GONE);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(OnGoingActivity.this);
                noteDialog.setContentView(R.layout.note_alert);
                noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noteDialog.show();

                Window window = noteDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(wlp);

                ImageView closeButton = noteDialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteDialog.dismiss();
                    }
                });

                EditText noteEditText = noteDialog.findViewById(R.id.noteEditTextID);
                AppCompatButton noteUpdateButton = noteDialog.findViewById(R.id.noteUpdateButtonID);

                noteUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteText = noteEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(noteText)) {
                            Toast.makeText(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            loader.show();

                            apiService.arenaValor_update_notes(secret_id, api_token, matchID, noteText).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        noteDialog.dismiss();
                                        optionDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                noteEditText.setText(response.admin_note);
            }
        });

        moveResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();

                AlertDialog alertDialog = new AlertDialog.Builder(OnGoingActivity.this).create();
                alertDialog.setTitle("Match Moving to Result");
                alertDialog.setMessage("Do you want to move Result?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loader.show();
                                apiService.arenaValorMove_to_result(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        loader.dismiss();


                                        if (response.body().getE() == 0) {
                                            Toasty.success(getApplicationContext(), "Moved to result", Toasty.LENGTH_SHORT).show();

                                            alertDialog.dismiss();
                                            arena_of_valor_matches(playTypeID);

                                        } else {
                                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                        loader.dismiss();

                                        Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //ongoingDialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });


        /**/
    }
}