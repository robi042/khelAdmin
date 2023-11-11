package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor.Arena_valor_result_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_match_result_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.FreeFire.Free_fire_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.Model.GameType.M;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_result_list;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ResultActivity extends AppCompatActivity implements Arena_valor_result_adapter.OnArenaValorItemClickListener, Ludo_match_result_adapter.OnItemClickListener, Ludo_match_result_adapter.OnImageViewListener {
    private RecyclerView resultRV;
    private APIService apiService;
    private String api_token, secret_id;

    private List<M> gameTypeResponses = new ArrayList<>();
    private List<String> gameType = new ArrayList<>();
    private String checkType = "1";
    private String playTypeID;
    private List<Ludo_game_result_list.M> gameResultList = new ArrayList<>();
    private List<Ludo_game_result_list.M> ludoSearchList = new ArrayList<>();
    private Ludo_match_result_adapter adapter;

    ImageView backButton;
    TextView nameText;

    EditText searchEditText;
    LinearLayout searchLayoutButton, noDataLayout;
    ImageView reLoadButton;

    private List<code.fortomorrow.kheloNowAdmin.Model.Ongoing.M> matchList = new ArrayList<>();
    private List<code.fortomorrow.kheloNowAdmin.Model.Ongoing.M> searchList = new ArrayList<>();
    Dialog loader;

    int check = 0;

    private Arena_valor_result_adapter arenaValorResultAdapter;
    private List<Arena_valor_response.M> arenaValorResultList = new ArrayList<>();
    private List<Arena_valor_response.M> arenaValorSearchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initall();

        set_title();
        //Toast.makeText(getApplicationContext(), playTypeID, Toast.LENGTH_SHORT).show();
        getAllOngoingMatches(playTypeID);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    check = 0;
                    getAllOngoingMatches(playTypeID);
                }
            }
        });

        //8/02/2022 at 04:00 PM
        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().toLowerCase().trim();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {

                    if (playTypeID.equals("3") || playTypeID.equals("4")) {
                        ludoSearchList.clear();
                        for (int i = 0; i < gameResultList.size(); i++) {
                            if (gameResultList.get(i).getTitle().contains(searchText)) {
                                ludoSearchList.add(gameResultList.get(i));
                            }

                        }

                        if (ludoSearchList.size() != 0) {
                            check = 1;
                            noDataLayout.setVisibility(View.GONE);
                            resultRV.setVisibility(View.VISIBLE);
                            adapter = new Ludo_match_result_adapter(ludoSearchList);
                            adapter.setOnClickListener(ResultActivity.this::OnItemClick, ResultActivity.this::OnImageClick);
                            resultRV.setAdapter(adapter);
                        } else {
                            check = 0;
                            noDataLayout.setVisibility(View.VISIBLE);
                            resultRV.setVisibility(View.GONE);
                        }

                    } else if (playTypeID.equals("11") || playTypeID.equals("12")) {

                        arenaValorSearchList.clear();

                        for (int i = 0; i < arenaValorResultList.size(); i++) {
                            if (arenaValorResultList.get(i).title.contains(searchText)) {
                                arenaValorSearchList.add(arenaValorResultList.get(i));
                            }

                        }

                        if (arenaValorSearchList.size() != 0) {
                            check = 1;
                            noDataLayout.setVisibility(View.GONE);
                            resultRV.setVisibility(View.VISIBLE);
                            arenaValorResultAdapter = new Arena_valor_result_adapter(arenaValorSearchList);
                            arenaValorResultAdapter.setOnClickListener(ResultActivity.this::OnArenaValorItemClick);
                            resultRV.setAdapter(arenaValorResultAdapter);
                        } else {
                            check = 0;
                            noDataLayout.setVisibility(View.VISIBLE);
                            resultRV.setVisibility(View.GONE);
                        }

                    } else {
                        searchList.clear();
                        for (int i = 0; i < matchList.size(); i++) {
                            if (matchList.get(i).getTitle().contains(searchText)) {
                                searchList.add(matchList.get(i));
                            }

                        }

                        if (searchList.size() != 0) {
                            noDataLayout.setVisibility(View.GONE);
                            resultRV.setVisibility(View.VISIBLE);
                            resultRV.setAdapter(new Free_fire_result_adapter(ResultActivity.this, getApplicationContext(), searchList));
                        } else {
                            noDataLayout.setVisibility(View.VISIBLE);
                            resultRV.setVisibility(View.GONE);
                        }
                    }


                    //Toast.makeText(getApplicationContext(), String.valueOf(searchList.size()), Toast.LENGTH_SHORT).show();

                }

                //Toast.makeText(getApplicationContext(), "Available soon", Toast.LENGTH_SHORT).show();
            }
        });

        reLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchEditText.getText().clear();
                getAllOngoingMatches(playTypeID);
            }
        });

        //Log.d("tokenxx", api_token);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    private void initall() {
        loader = new Dialog(ResultActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        playTypeID = getIntent().getStringExtra("play_type");

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        Log.d("tokenxx", api_token+" "+secret_id+" "+playTypeID);

        backButton = findViewById(R.id.backButton);
        nameText = findViewById(R.id.nameTextID);
        resultRV = findViewById(R.id.resultRV);
        resultRV.setHasFixedSize(true);
        resultRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        reLoadButton = findViewById(R.id.reLoadButtonID);
        noDataLayout = findViewById(R.id.noDataLayoutID);
    }

    private void getAllOngoingMatches(String playTypeID) {
        if (playTypeID.equals("3") || playTypeID.equals("4") || playTypeID.equals("7") || playTypeID.equals("8")) {
            ludo_func(playTypeID);
        } else if (playTypeID.equals("11") || playTypeID.equals("12")) {
            arena_of_valor_matches(playTypeID);
        } else {
            //Toast.makeText(getApplicationContext(), playTypeID, Toast.LENGTH_SHORT).show();
            free_fire_func(playTypeID);
        }
    }

    private void arena_of_valor_matches(String playTypeID) {

        loader.show();
        apiService.arenaValorGet_result_list(secret_id, api_token, playTypeID).enqueue(new Callback<Arena_valor_response>() {
            @Override
            public void onResponse(Call<Arena_valor_response> call, Response<Arena_valor_response> response) {
                loader.dismiss();
                if (response.body().e == 0 && response.body().m.size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    resultRV.setVisibility(View.VISIBLE);
                    arenaValorResultList = response.body().m;
                    arenaValorResultAdapter = new Arena_valor_result_adapter(arenaValorResultList);
                    arenaValorResultAdapter.setOnClickListener(ResultActivity.this::OnArenaValorItemClick);
                    resultRV.setAdapter(arenaValorResultAdapter);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    resultRV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Arena_valor_response> call, Throwable t) {

            }
        });
    }

    private void free_fire_func(String playTypeID) {


        matchList.clear();
        loader.show();
        apiService.getResultMatches(secret_id, api_token, playTypeID).enqueue(new Callback<OngoingMatchResponse>() {
            @Override
            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    //Log.d("allresult",new Gson().toJson(response.body()));
                    noDataLayout.setVisibility(View.GONE);
                    resultRV.setVisibility(View.VISIBLE);
                    matchList = response.body().getM();
                    resultRV.setAdapter(new Free_fire_result_adapter(ResultActivity.this, getApplicationContext(), matchList));
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    resultRV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {
                //Log.d("allresult",new Gson().toJson(t.toString()));
                noDataLayout.setVisibility(View.VISIBLE);
                resultRV.setVisibility(View.GONE);
                loader.dismiss();
            }
        });
    }

    private void ludo_func(String type) {
        //Toast.makeText(getApplicationContext(), "Ludo", Toast.LENGTH_SHORT).show();
        loader.show();
        apiService.getLudoResultListResponse(secret_id, api_token, type).enqueue(new Callback<Ludo_game_result_list>() {
            @Override
            public void onResponse(Call<Ludo_game_result_list> call, Response<Ludo_game_result_list> response) {

                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    resultRV.setVisibility(View.VISIBLE);
                    gameResultList = response.body().getM();
                    adapter = new Ludo_match_result_adapter(gameResultList);
                    adapter.setOnClickListener(ResultActivity.this::OnItemClick, ResultActivity.this::OnImageClick);
                    resultRV.setAdapter(adapter);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    resultRV.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Ludo_game_result_list> call, Throwable t) {
                loader.dismiss();
                noDataLayout.setVisibility(View.VISIBLE);
                resultRV.setVisibility(View.GONE);
            }
        });
    }

    public void ShowList(Integer matchId, String note) {
        Dialog resultDialog = new Dialog(ResultActivity.this);
        resultDialog.setContentView(R.layout.result_option_alert);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = resultDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout showparticipantLayout = resultDialog.findViewById(R.id.showparticipantLayoutID);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(ResultActivity.this);
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
                noteEditText.setText(note);

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
                                        resultDialog.dismiss();
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
            }
        });

        showparticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.dismiss();
                Intent i = new Intent(getApplicationContext(), ShowParticipantActivity.class);
                i.putExtra("matchId", String.valueOf(matchId));
                startActivity(i);
            }
        });


    }

    @Override
    public void OnItemClick(int position) {


        if (check == 0) {

            ludo_onclick_function(position);
        } else if (check == 1) {

            ludo_search_onclick_function(position);

        }
    }

    private void ludo_search_onclick_function(int position) {
        Ludo_game_result_list.M response = ludoSearchList.get(position);
        String matchID = String.valueOf(response.getMatchId());
        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();

        Dialog resultDialog = new Dialog(ResultActivity.this);
        resultDialog.setContentView(R.layout.result_option_alert);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = resultDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout showparticipantLayout = resultDialog.findViewById(R.id.showparticipantLayoutID);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(ResultActivity.this);
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
                noteEditText.setText(response.getNote());
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
                                        resultDialog.dismiss();
                                        noteDialog.dismiss();
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
            }
        });

        showparticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultDialog.dismiss();
                Intent intent = new Intent(ResultActivity.this, Ludo_participant_activity.class);
                intent.putExtra("match_ID", String.valueOf(response.getMatchId()));
                intent.putExtra("title", response.getTitle());
                intent.putExtra("date_time", response.getDate() + " " + response.getTime());
                intent.putExtra("prize", response.getTotalPrize());
                intent.putExtra("room_code", response.getRoomCode());
                intent.putExtra("image_link", response.getImageLink());
                intent.putExtra("image_uploaded_by", response.getImage_uploaded_by());
                startActivity(intent);
            }
        });
    }

    private void ludo_onclick_function(int position) {

        Ludo_game_result_list.M response = gameResultList.get(position);
        String matchID = String.valueOf(response.getMatchId());


        Dialog resultDialog = new Dialog(ResultActivity.this);
        resultDialog.setContentView(R.layout.result_option_alert);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = resultDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout showparticipantLayout = resultDialog.findViewById(R.id.showparticipantLayoutID);

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(ResultActivity.this);
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
                noteEditText.setText(response.getNote());
                AppCompatButton noteUpdateButton = noteDialog.findViewById(R.id.noteUpdateButtonID);

                noteUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteText = noteEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(noteText)) {
                            Toast.makeText(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            loader.show();

                            //Log.d("helloxx", matchID + " " + api_token);
                            apiService.updateLudoNotes(secret_id, api_token, matchID, noteText).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        resultDialog.dismiss();
                                        noteDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Log.d("errorxx", t.getMessage());
                                    Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        showparticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultDialog.dismiss();
                Intent intent = new Intent(ResultActivity.this, Ludo_participant_activity.class);
                intent.putExtra("match_ID", String.valueOf(response.getMatchId()));
                intent.putExtra("title", response.getTitle());
                intent.putExtra("date_time", response.getDate() + " " + response.getTime());
                intent.putExtra("prize", response.getTotalPrize());
                intent.putExtra("room_code", response.getRoomCode());
                intent.putExtra("image_link", response.getImageLink());
                intent.putExtra("image_uploaded_by", response.getImage_uploaded_by());
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnImageClick(int position) {

        if (check == 0) {

            ludo_image_click_function(position);
        } else if (check == 1) {

            ludo_search_image_onclick_function(position);

        }


    }

    private void ludo_search_image_onclick_function(int position) {
        Ludo_game_result_list.M response = ludoSearchList.get(position);

        Dialog dialog = new Dialog(ResultActivity.this);
        dialog.setContentView(R.layout.ludo_result_image_view_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
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
            Picasso.get().load(response.getImageLink()).into(resultImage);
        } catch (Exception e) {

        }
    }

    private void ludo_image_click_function(int position) {
        Ludo_game_result_list.M response = gameResultList.get(position);

        Dialog dialog = new Dialog(ResultActivity.this);
        dialog.setContentView(R.layout.ludo_result_image_view_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
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
            Picasso.get().load(response.getImageLink()).into(resultImage);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Toast.makeText(getApplicationContext(), playTypeID, Toast.LENGTH_SHORT).show();
        check = 0;
        searchEditText.getText().clear();
        getAllOngoingMatches(playTypeID);
    }

    @Override
    public void OnArenaValorItemClick(int position) {
        if (check == 0) {

            arena_valor_onclick_function(position);
        } else if (check == 1) {

            arena_valor_search_onclick_function(position);

        }
    }

    private void arena_valor_onclick_function(int position) {
        Arena_valor_response.M response = arenaValorResultList.get(position);

        Dialog resultDialog = new Dialog(ResultActivity.this);
        resultDialog.setContentView(R.layout.result_option_alert);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = resultDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout showParticipantLayout = resultDialog.findViewById(R.id.showparticipantLayoutID);

        showParticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_arena_valor_participant_activity(String.valueOf(response.matchId));
            }
        });

        updateNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog noteDialog = new Dialog(ResultActivity.this);
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
                noteEditText.setText(response.admin_note);
                AppCompatButton noteUpdateButton = noteDialog.findViewById(R.id.noteUpdateButtonID);

                noteUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String noteText = noteEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(noteText)) {
                            Toast.makeText(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                        } else {
                            loader.show();

                            //Log.d("helloxx", secret_id + " " + String.valueOf(response.matchId) + " " + api_token);
                            apiService.arenaValor_update_notes(secret_id, api_token, String.valueOf(response.matchId), noteText).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        resultDialog.dismiss();
                                        noteDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getM(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Log.d("errorxx", t.getMessage());
                                    Toast.makeText(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void go_to_arena_valor_participant_activity(String matchID) {
        Intent intent = new Intent(getApplicationContext(), ArenaValor_participant_activity.class);
        intent.putExtra("match_id", matchID);
        startActivity(intent);
    }

    private void arena_valor_search_onclick_function(int position) {
        Arena_valor_response.M response = arenaValorSearchList.get(position);

        Dialog resultDialog = new Dialog(ResultActivity.this);
        resultDialog.setContentView(R.layout.result_option_alert);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout updateNotesButton = resultDialog.findViewById(R.id.updateNotesButtonID);
        LinearLayout showParticipantLayout = resultDialog.findViewById(R.id.showparticipantLayoutID);

        showParticipantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_arena_valor_participant_activity(String.valueOf(response.matchId));
            }
        });
    }


}