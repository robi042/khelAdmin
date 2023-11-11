package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor.Arena_valor_match_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.FreeFire.Free_fire_game_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.LudoMatchAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.Model.GameType.M;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_ongoing_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCreatedMatches extends AppCompatActivity implements Arena_valor_match_adapter.OnArenaValorItemClickListener, Arena_valor_match_adapter.OnArenaValorItemDeleteListener, LudoMatchAdapter.OnItemClickListener, LudoMatchAdapter.OnNotifyLudoUserListener, LudoMatchAdapter.OnItemDeleteListener, Free_fire_game_adapter.OnNotifyItemClickListener {
    private RecyclerView creatematchRV;
    private APIService apiService;
    private String api_token, secret_id;
    private Spinner typeOfmatches;
    private List<M> gameTypeResponses = new ArrayList<>();
    private List<String> gameType = new ArrayList<>();
    private String checkType = "1";
    private String gameID;// game_type = "-1";
    private LudoMatchAdapter ludoMatchAdapter;
    private List<Ludo_ongoing_response.M> ludoMatchList;
    private List<String> selectedMatchList = new ArrayList<>();
    Dialog loader;
    Calendar cal;
    TextView matchNameText;
    ImageView backButton;
    LinearLayout noMatchlayout;
    Dialog deleteDialog;

    private List<code.fortomorrow.kheloNowAdmin.Model.Ongoing.M> freeFireList = new ArrayList<>();

    private List<Arena_valor_response.M> arenaValorMatchList = new ArrayList<>();
    private Arena_valor_match_adapter arenaValorMatchAdapter;

    private Free_fire_game_adapter allCreatedMatchAdapter;

    TextView deleteAllButton;
    AppCompatButton deleteLayoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_created_matches);

        initAll();


        gameID = getIntent().getStringExtra("play_type");

        //Toast.makeText(getApplicationContext(), playTypeID, Toast.LENGTH_SHORT).show();

        set_title();

        getallCreatedMatch(gameID);

        cal = Calendar.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Toast.makeText(getApplicationContext(), playTypeID, Toast.LENGTH_SHORT).show();

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameID.equals("3") || gameID.equals("4") || gameID.equals("7") || gameID.equals("8")) {
                    //Toast.makeText(getApplicationContext(), "Lodo", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < ludoMatchList.size(); i++) {
                        selectedMatchList.add(String.valueOf(ludoMatchList.get(i).matchId));
                    }

                } else if (gameID.equals("11") || gameID.equals("12")) {
                    //arena_of_valor_matches(gameID);
                    for (int i = 0; i < arenaValorMatchList.size(); i++) {
                        selectedMatchList.add(String.valueOf(arenaValorMatchList.get(i).matchId));
                    }
                } else {
                    //free_fire(gameID);
                    for (int i = 0; i < freeFireList.size(); i++) {
                        selectedMatchList.add(String.valueOf(freeFireList.get(i).getMatchId()));
                    }
                }


                delete_selected_matches(selectedMatchList);
            }
        });

        deleteLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //print_selectedMatchList();
                delete_selected_matches(selectedMatchList);
            }
        });


    }

    private void delete_selected_matches(List<String> matchList) {
        deleteDialog.show();

        Window window = deleteDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteDialog.findViewById(R.id.yesButtonID);
        TextView noButton = deleteDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameID.equals("3") || gameID.equals("4") || gameID.equals("7") || gameID.equals("8")) {
                    delete_ludo_match_from_selected_list(matchList);
                } else if (gameID.equals("11") || gameID.equals("12")) {

                    delete_arena_of_valor_matches(matchList);
                } else {
                    /*free_fire(gameID);
                    for (int i = 0; i < ludoMatchList.size(); i++) {
                        selectedMatchList.add(String.valueOf(freeFireList.get(i).getMatchId()));
                    }*/
                    delete_free_fire_match_from_selected_list(matchList);
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });
    }

    private void delete_arena_of_valor_matches(List<String> matchList) {
        // Log.d("sizexx", String.valueOf(matchList.size()));

        String matchID;
        loader.show();
        for (int i = 0; i < matchList.size(); i++) {
            matchID = matchList.get(i);
            String finalMatchID = matchID;

            apiService.arenaValorDelete_match(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                    //loader.dismiss();

                    if (response.body().getE() == 0) {

                        selectedMatchList.remove(finalMatchID);

                        if (selectedMatchList.size() == 0) {
                            loader.dismiss();
                            deleteDialog.dismiss();
                            deleteLayoutButton.setVisibility(View.GONE);
                            deleteAllButton.setVisibility(View.GONE);
                            Toasty.success(getApplicationContext(), "selected matches removed successfully", Toasty.LENGTH_SHORT).show();

                            arena_of_valor_matches(gameID);
                        } else {
                            //Toast.makeText(AllCreatedMatches.this, String.valueOf(selectedMatchList.size()), Toast.LENGTH_SHORT).show();
                        }
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

    }

    private void delete_free_fire_match_from_selected_list(List<String> matchList) {
        //Log.d("sizexx", String.valueOf(matchList.size()));

        String matchID;
        loader.show();
        for (int i = 0; i < matchList.size(); i++) {
            matchID = matchList.get(i);
            String finalMatchID = matchID;

            apiService.getDeleteMatchRespose(secret_id, api_token, finalMatchID).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                    if (response.body().getE() == 0) {
                        //deleteDialog.dismiss();
                        selectedMatchList.remove(finalMatchID);

                        if (selectedMatchList.size() == 0) {
                            loader.dismiss();
                            deleteDialog.dismiss();
                            deleteLayoutButton.setVisibility(View.GONE);
                            deleteAllButton.setVisibility(View.GONE);
                            Toasty.success(getApplicationContext(), "selected matches removed successfully", Toasty.LENGTH_SHORT).show();

                            free_fire(gameID);
                        } else {
                            //Toast.makeText(AllCreatedMatches.this, String.valueOf(selectedMatchList.size()), Toast.LENGTH_SHORT).show();
                        }

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


    }

    private void delete_ludo_match_from_selected_list(List<String> matchList) {

        String matchID;
        loader.show();
        for (int i = 0; i < matchList.size(); i++) {

            matchID = matchList.get(i);
            String finalMatchID = matchID;
            apiService.removeLudoMatchResponse(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                @Override
                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                    //loader.dismiss();

                    if (response.body().getE() == 0) {
                        selectedMatchList.remove(finalMatchID);

                        if (selectedMatchList.size() == 0) {
                            loader.dismiss();
                            deleteDialog.dismiss();
                            deleteLayoutButton.setVisibility(View.GONE);
                            deleteAllButton.setVisibility(View.GONE);
                            Toasty.success(getApplicationContext(), "selected matches removed successfully", Toasty.LENGTH_SHORT).show();
                            ludo_func(gameID);
                        } else {
                            //Toast.makeText(AllCreatedMatches.this, String.valueOf(selectedMatchList.size()), Toast.LENGTH_SHORT).show();
                        }
                        //ludu_func(playTypeID);
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
    }

    private void set_title() {
        if (gameID.equals("1")) {
            matchNameText.setText("Free Fire (Regular)");
        } else if (gameID.equals("2")) {
            matchNameText.setText("Free Fire (CS Regular)");
        } else if (gameID.equals("3")) {
            matchNameText.setText("Ludo (Regular)");
        } else if (gameID.equals("4")) {
            matchNameText.setText("Ludo (Grand)");
        } else if (gameID.equals("5")) {
            matchNameText.setText("Free Fire (CS Grand)");
        } else if (gameID.equals("6")) {
            matchNameText.setText("Daily Scrims");
        } else if (gameID.equals("7")) {
            matchNameText.setText("Ludo (Premium)");
        } else if (gameID.equals("8")) {
            matchNameText.setText("Ludo (4 Player)");
        } else if (gameID.equals("9")) {
            matchNameText.setText("Free Fire (Premium)");
        } else if (gameID.equals("10")) {
            matchNameText.setText("Free Fire (Grand)");
        } else if (gameID.equals("11")) {
            matchNameText.setText("Arena of Valor (Regular)");
        } else if (gameID.equals("12")) {
            matchNameText.setText("Arena of Valor (Grand)");
        }
    }

    private void getallCreatedMatch(String gameID) {

        if (gameID.equals("3") || gameID.equals("4") || gameID.equals("7") || gameID.equals("8")) {
            //Toast.makeText(getApplicationContext(), "Lodo", Toast.LENGTH_SHORT).show();
            ludo_func(gameID);
        } else if (gameID.equals("11") || gameID.equals("12")) {
            arena_of_valor_matches(gameID);
        } else {
            free_fire(gameID);
        }


    }

    private void free_fire_cs() {
        //game_type = getIntent().getStringExtra("game_type");
        freeFireList.clear();
        loader.show();
        /*apiService.get_automated_cs_match_list(secret_id, api_token, gameID, game_type).enqueue(new Callback<OngoingMatchResponse>() {
            @Override
            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    creatematchRV.setVisibility(View.VISIBLE);
                    noMatchlayout.setVisibility(View.GONE);

                    freeFireList = response.body().getM();
                    allCreatedMatchAdapter = new AllCreatedMatchAdapter(AllCreatedMatches.this, getApplicationContext(), response.body().getM());
                    allCreatedMatchAdapter.setOnClickListener(AllCreatedMatches.this::OnNotifyItemClick);
                    creatematchRV.setAdapter(allCreatedMatchAdapter);
                } else {
                    creatematchRV.setVisibility(View.GONE);
                    noMatchlayout.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {
                loader.dismiss();
            }
        });*/

        apiService.getCreatedMatch(secret_id, api_token, gameID).enqueue(new Callback<OngoingMatchResponse>() {
            @Override
            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    creatematchRV.setVisibility(View.VISIBLE);
                    noMatchlayout.setVisibility(View.GONE);

                    freeFireList = response.body().getM();
                    allCreatedMatchAdapter = new Free_fire_game_adapter(AllCreatedMatches.this, getApplicationContext(), response.body().getM());
                    allCreatedMatchAdapter.setOnClickListener(AllCreatedMatches.this::OnNotifyItemClick);
                    creatematchRV.setAdapter(allCreatedMatchAdapter);
                } else {
                    creatematchRV.setVisibility(View.GONE);
                    noMatchlayout.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void arena_of_valor_matches(String playTypeID) {

        loader.show();
        apiService.arenaValorMatchList(secret_id, api_token, playTypeID).enqueue(new Callback<Arena_valor_response>() {
            @Override
            public void onResponse(Call<Arena_valor_response> call, Response<Arena_valor_response> response) {

                loader.dismiss();
                if (response.body().e == 0 && response.body().m.size() != 0) {
                    creatematchRV.setVisibility(View.VISIBLE);
                    noMatchlayout.setVisibility(View.GONE);
                    deleteAllButton.setVisibility(View.VISIBLE);
                    arenaValorMatchList = response.body().m;
                    //Toast.makeText(getApplicationContext(), "valor"+" "+String.valueOf(arenaValorMatchList.size()), Toast.LENGTH_SHORT).show();
                    arenaValorMatchAdapter = new Arena_valor_match_adapter(arenaValorMatchList, AllCreatedMatches.this);
                    arenaValorMatchAdapter.setOnClickListener(AllCreatedMatches.this::OnArenaValorItemClick, AllCreatedMatches.this::OnArenaValorDeleteClick);
                    creatematchRV.setAdapter(arenaValorMatchAdapter);
                } else {
                    creatematchRV.setVisibility(View.GONE);
                    noMatchlayout.setVisibility(View.VISIBLE);
                    deleteAllButton.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Arena_valor_response> call, Throwable t) {
                loader.dismiss();
                Toasty.error(getApplicationContext(), R.string.something_wrong, Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void free_fire(String gameID) {
        freeFireList.clear();
        loader.show();
        apiService.getCreatedMatch(secret_id, api_token, gameID).enqueue(new Callback<OngoingMatchResponse>() {
            @Override
            public void onResponse(Call<OngoingMatchResponse> call, Response<OngoingMatchResponse> response) {
                //Log.d("allOngoing", new Gson().toJson(response.body().getM()));
                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    creatematchRV.setVisibility(View.VISIBLE);
                    noMatchlayout.setVisibility(View.GONE);
                    deleteAllButton.setVisibility(View.VISIBLE);
                    freeFireList = response.body().getM();
                    allCreatedMatchAdapter = new Free_fire_game_adapter(AllCreatedMatches.this, getApplicationContext(), response.body().getM());
                    allCreatedMatchAdapter.setOnClickListener(AllCreatedMatches.this::OnNotifyItemClick);
                    creatematchRV.setAdapter(allCreatedMatchAdapter);
                } else {
                    creatematchRV.setVisibility(View.GONE);
                    noMatchlayout.setVisibility(View.VISIBLE);
                    deleteAllButton.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<OngoingMatchResponse> call, Throwable t) {
                loader.dismiss();
            }
        });

    }

    private void ludo_func(String type) {

        //Log.d("infoxxx", api_token);
        loader.show();
        apiService.getLudoMatchListResponse(secret_id, api_token, type).enqueue(new Callback<Ludo_ongoing_response>() {
            @Override
            public void onResponse(Call<Ludo_ongoing_response> call, Response<Ludo_ongoing_response> response) {

                loader.dismiss();
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    creatematchRV.setVisibility(View.VISIBLE);
                    noMatchlayout.setVisibility(View.GONE);
                    deleteAllButton.setVisibility(View.VISIBLE);
                    ludoMatchList = new ArrayList<>();
                    ludoMatchList = response.body().getM();
                    ludoMatchAdapter = new LudoMatchAdapter(AllCreatedMatches.this, getApplicationContext(), ludoMatchList);
                    ludoMatchAdapter.setOnClickListener(AllCreatedMatches.this::OnItemClick, AllCreatedMatches.this::OnDeleteClick, AllCreatedMatches.this::OnNotifyLudoClick);
                    creatematchRV.setAdapter(ludoMatchAdapter);
                } else {
                    creatematchRV.setVisibility(View.GONE);
                    noMatchlayout.setVisibility(View.VISIBLE);
                    deleteAllButton.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<Ludo_ongoing_response> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void initAll() {
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", secret_id+" "+api_token);

        apiService = AppConfig.getRetrofit().create(APIService.class);

        creatematchRV = findViewById(R.id.creatematchRV);
        creatematchRV.setHasFixedSize(true);
        creatematchRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        creatematchRV.setItemViewCacheSize(150);

        typeOfmatches = findViewById(R.id.typeOfmatches);
        matchNameText = findViewById(R.id.matchNameTextID);
        backButton = findViewById(R.id.backButton);
        noMatchlayout = findViewById(R.id.noMatchlayoutID);
        deleteAllButton = findViewById(R.id.deleteAllButtonID);

        loader = new Dialog(AllCreatedMatches.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        deleteDialog = new Dialog(AllCreatedMatches.this);
        deleteDialog.setContentView(R.layout.confirmation_alert);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        deleteLayoutButton = findViewById(R.id.deleteLayoutButtonID);
    }

    public void moveToOnoging(Integer matchId, String matchTime, String entryFee, String map, String per_kill_rate, String title, String totalPrize, String firstPrize, String secondPrize, String thirdPrize, String totalPlayer) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AllCreatedMatches.this);
        View mView = AllCreatedMatches.this.getLayoutInflater().inflate(R.layout.custom_match_move_on_going_and_set_password, null);
        LinearLayout mMove = mView.findViewById(R.id.mMove);
        LinearLayout mRoomId = mView.findViewById(R.id.mRoomId);
        LinearLayout mDelete = mView.findViewById(R.id.mDelete);
        LinearLayout edit_match = mView.findViewById(R.id.edit_match);
        LinearLayout joinedPlayerButton = mView.findViewById(R.id.joinedPlayerButtonID);

        mBuilder.setView(mView);
        final AlertDialog dialog1 = mBuilder.create();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();


        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProgressDialog p = new ProgressDialog(AllCreatedMatches.this);
                p.setMessage("Loading..");
                p.show();

                new AlertDialog.Builder(AllCreatedMatches.this).setTitle("Confirm Delete?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        apiService.getDeleteMatchRespose(secret_id, api_token, String.valueOf(matchId)).enqueue(new Callback<SorkariResponse>() {
                                            @Override
                                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                                if (response.body().getE() == 0) {
                                                    p.dismiss();
                                                    dialog1.dismiss();
                                                    Toasty.success(getApplicationContext(), "SuccessFully Deleted Match").show();

                                                    free_fire(gameID);
                                                } else {

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                                Toasty.error(getApplicationContext(), "" + t.toString()).show();
                                            }
                                        });

                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();


            }
        });

        mMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alertDialog = new AlertDialog.Builder(AllCreatedMatches.this).create();
                alertDialog.setTitle("Match Moving to Ongoing");
                alertDialog.setMessage("Do you want to move Ongoing...");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.movetoOngoing(secret_id, api_token, String.valueOf(matchId)).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        dialog1.dismiss();
                                        Toasty.success(getApplicationContext(), "Moved to ongoing").show();

                                        free_fire(gameID);
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
                            }
                        });

                alertDialog.show();


            }
        });

        mRoomId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogSetRoom(Integer.toString(matchId));
                dialog1.dismiss();

            }
        });

        edit_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent i = new Intent(getApplicationContext(), EditMatchActivity.class);
                i.putExtra("title", title);
                i.putExtra("map", map);
                i.putExtra("entryfee", entryFee);
                i.putExtra("time", matchTime);
                i.putExtra("winningprize", totalPrize);
                i.putExtra("perkill", per_kill_rate);
                i.putExtra("match_id", String.valueOf(matchId));
                i.putExtra("firstPrize", firstPrize);
                i.putExtra("secondPrize", secondPrize);
                i.putExtra("thirdPrize", thirdPrize);
                i.putExtra("totalPlayer", totalPlayer);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);
            }
        });

        joinedPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(AllCreatedMatches.this, String.valueOf(matchId), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Joined_player_list_activity.class);
                i.putExtra("match_id", String.valueOf(matchId));
                i.putExtra("type", "free_fire");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);
            }
        });


    }

    void openDialogSetRoom(String matchID) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AllCreatedMatches.this);
        View mView = AllCreatedMatches.this.getLayoutInflater().inflate(R.layout.custom_set_password, null);

        EditText mRoomId = mView.findViewById(R.id.mRoomId);
        EditText mPass = mView.findViewById(R.id.mPassword);
        Button mBtn = mView.findViewById(R.id.mBtn);


        mBuilder.setView(mView);
        final AlertDialog dialog1 = mBuilder.create();
        //dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String roomid = mRoomId.getText().toString();
                String mPassword = mPass.getText().toString();


                if (TextUtils.isEmpty(roomid) && TextUtils.isEmpty(mPassword)) {
                    Toasty.error(getApplicationContext(), "Please give Room id pass").show();
                } else {
                    //Log.d("checkall", "secret_id" + " " + api_token + " " + roomid + " " + mPassword + " " + matchID);
                    apiService.updateRoomInfo(secret_id, api_token, roomid, mPassword, matchID).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            if (response.body().getE() == 0) {
                                dialog1.dismiss();
                                free_fire(gameID);
                                Toasty.success(getApplicationContext(), "Room Id pass Set").show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {

                        }
                    });
                }


            }
        });


    }

    @Override
    public void OnItemClick(int position) {
        Ludo_ongoing_response.M res = ludoMatchList.get(position);

        //Toast.makeText(getApplicationContext(), String.valueOf(res.getMatchId()), Toast.LENGTH_SHORT).show();

        String matchID = String.valueOf(res.getMatchId());
        String prize = res.getTotalPrize();
        String entryFee = res.getEntryFee();
        String title = res.getTitle();
        String date = res.getDate();
        String time = res.getTime();
        String totalPlayer = res.getTotalPlayer();
        String gameType = res.getHostApp();

        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();

        Dialog optionDialog = new Dialog(AllCreatedMatches.this);
        optionDialog.setContentView(R.layout.ludo_click_option_alert);
        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.show();

        Window window = optionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout editMatchLayoutButton = optionDialog.findViewById(R.id.editMatchLayoutButtonID);
        LinearLayout setPasswordLayoutButton = optionDialog.findViewById(R.id.setPasswordLayoutID);
        LinearLayout onGoingLayout = optionDialog.findViewById(R.id.onGoingLayoutID);
        LinearLayout joinedPlayerList = optionDialog.findViewById(R.id.joinedPlayerListID);

        setPasswordLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.ludo_set_password_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                EditText roomCodeText = dialog.findViewById(R.id.roomCodeTextId);

                AppCompatButton setButton = dialog.findViewById(R.id.setButtonID);

                setButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String roomCode = roomCodeText.getText().toString().trim();

                        if (TextUtils.isEmpty(roomCode)) {
                            Toasty.error(getApplicationContext(), "Empty room", Toasty.LENGTH_SHORT).show();
                        } else {

                            //code API
                            apiService.setLudoRoomCodeResponse(secret_id, api_token, matchID, roomCode).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    dialog.dismiss();
                                    if (response.body().getE() == 0) {
                                        ludo_func(gameID);
                                        Toasty.success(getApplicationContext(), "Room Code set", Toasty.LENGTH_SHORT).show();
                                    } else {
                                        Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toasty.error(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });

            }
        });

        editMatchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.ludo_edit_match_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                EditText titleEditText = dialog.findViewById(R.id.titleTextID);
                EditText dateEditText = dialog.findViewById(R.id.dateTextID);
                EditText timeEditText = dialog.findViewById(R.id.timeTextID);
                EditText prizeEditText = dialog.findViewById(R.id.prizeTextID);
                EditText entryFeeEditText = dialog.findViewById(R.id.entryFeeTextID);
                EditText totalPlayerEditText = dialog.findViewById(R.id.totalPlayerTextID);
                EditText gameTypeEditText = dialog.findViewById(R.id.gameTypeTextID);
                AppCompatButton updateButton = dialog.findViewById(R.id.updateButtonID);

                titleEditText.setText(title);
                dateEditText.setText(date);
                timeEditText.setText(time);
                prizeEditText.setText(prize);
                entryFeeEditText.setText(entryFee);
                totalPlayerEditText.setText(totalPlayer);
                gameTypeEditText.setText(gameType);

                dateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(AllCreatedMatches.this, (view1, year, month, dayOfMonth) -> {

                            dateEditText.setText(String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year));

                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                        dpd.show();
                    }
                });

                timeEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog tpd = new TimePickerDialog(AllCreatedMatches.this, (view1, hourOfDay, minute) -> {

                            boolean isPM = (hourOfDay >= 12);

                            int hour = hourOfDay % 12;
                            if (hour == 0)
                                hour = 12;

                            timeEditText.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));

                        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(AllCreatedMatches.this));
                        tpd.show();

                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), dateEditText.getText().toString().trim() + " " + timeEditText.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                        String title = titleEditText.getText().toString().trim();
                        String date = dateEditText.getText().toString().trim();
                        String time = timeEditText.getText().toString().trim();
                        String prize = prizeEditText.getText().toString().trim();
                        String entryFee = entryFeeEditText.getText().toString().trim();
                        String totalPlayer = totalPlayerEditText.getText().toString().trim();
                        String gameType = gameTypeEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(title)) {
                            Toasty.error(getApplicationContext(), "Title empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(prize)) {
                            Toasty.error(getApplicationContext(), "Prize empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(entryFee)) {
                            Toasty.error(getApplicationContext(), "Entry Fee empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(totalPlayer)) {
                            Toasty.error(getApplicationContext(), "Total player empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(gameType)) {
                            Toasty.error(getApplicationContext(), "Game Type empty", Toasty.LENGTH_SHORT).show();
                        } else {
                            //Toasty.success(getApplicationContext(), "Great", Toasty.LENGTH_SHORT).show();
                            // do code

                            loader.show();
                            apiService.updateLudoMatch(secret_id, api_token, matchID, date, time, title, entryFee, totalPlayer, prize, gameType).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();

                                    if (response.body().getE() == 0) {

                                        Toasty.success(getApplicationContext(), "Updated", Toasty.LENGTH_SHORT).show();
                                        optionDialog.dismiss();
                                        dialog.dismiss();
                                        ludo_func(gameID);

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
                    }
                });


            }
        });

        onGoingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.confirmation_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                TextView yesButton = dialog.findViewById(R.id.yesButtonID);
                TextView noButton = dialog.findViewById(R.id.noButtonID);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loader.show();
                        apiService.moveLudoOnGoingResponse(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                loader.dismiss();

                                if (response.body().getE() == 0) {

                                    optionDialog.dismiss();
                                    dialog.dismiss();
                                    Toasty.success(getApplicationContext(), "Match onGoing", Toasty.LENGTH_SHORT).show();

                                    ludo_func(gameID);
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

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

        joinedPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Joined_player_list_activity.class);
                i.putExtra("match_id", String.valueOf(matchID));
                i.putExtra("type", "ludo");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);
            }
        });


    }

    @Override
    public void OnDeleteClick(int position) {
        Ludo_ongoing_response.M res = ludoMatchList.get(position);

        //Toast.makeText(getApplicationContext(), String.valueOf(res.getMatchId()), Toast.LENGTH_SHORT).show();
        String matchID = String.valueOf(res.getMatchId());

        Dialog deleteDialog = new Dialog(AllCreatedMatches.this);
        deleteDialog.setContentView(R.layout.confirmation_alert);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.show();

        Window window = deleteDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteDialog.findViewById(R.id.yesButtonID);
        TextView noButton = deleteDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();

                apiService.removeLudoMatchResponse(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();

                        if (response.body().getE() == 0) {

                            deleteDialog.dismiss();
                            Toasty.success(getApplicationContext(), "Match removed successfully", Toasty.LENGTH_SHORT).show();

                            ludo_func(gameID);
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

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
    }

    @Override
    public void OnNotifyItemClick(int position) {
        //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
        code.fortomorrow.kheloNowAdmin.Model.Ongoing.M response = freeFireList.get(position);

        String matchID = String.valueOf(response.getMatchId());

        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();
        loader.show();
        apiService.sendMatchNotification(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "notification sent", Toasty.LENGTH_SHORT).show();
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

    @Override
    public void OnNotifyLudoClick(int position) {
        Ludo_ongoing_response.M res = ludoMatchList.get(position);
        String matchID = String.valueOf(res.getMatchId());

        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();
        loader.show();
        apiService.sendLudoMatchNotification(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "notification sent", Toasty.LENGTH_SHORT).show();
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

    @Override
    public void OnArenaValorDeleteClick(int position) {
        Arena_valor_response.M response = arenaValorMatchList.get(position);
        String matchID = response.matchId.toString();
        //Toast.makeText(this, matchID, Toast.LENGTH_SHORT).show();
        Dialog deleteDialog = new Dialog(AllCreatedMatches.this);
        deleteDialog.setContentView(R.layout.confirmation_alert);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.show();

        Window window = deleteDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteDialog.findViewById(R.id.yesButtonID);
        TextView noButton = deleteDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();

                apiService.arenaValorDelete_match(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();

                        if (response.body().getE() == 0) {

                            deleteDialog.dismiss();
                            Toasty.success(getApplicationContext(), "Match removed successfully", Toasty.LENGTH_SHORT).show();

                            arena_of_valor_matches(gameID);
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

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

    }

    @Override
    public void OnArenaValorItemClick(int position) {
        Arena_valor_response.M response = arenaValorMatchList.get(position);
        String matchID = response.matchId.toString();


        Dialog optionDialog = new Dialog(AllCreatedMatches.this);
        optionDialog.setContentView(R.layout.arena_valor_option_alert);
        optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        optionDialog.show();

        Window window = optionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        LinearLayout editMatchLayoutButton = optionDialog.findViewById(R.id.editMatchLayoutButtonID);
        LinearLayout setPasswordLayoutButton = optionDialog.findViewById(R.id.setPasswordLayoutID);
        LinearLayout onGoingLayout = optionDialog.findViewById(R.id.onGoingLayoutID);
        LinearLayout joinedPlayerList = optionDialog.findViewById(R.id.joinedPlayerListID);

        setPasswordLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.arena_valor_set_room_code_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                EditText roomCodeText = dialog.findViewById(R.id.roomCodeTextId);

                AppCompatButton setButton = dialog.findViewById(R.id.setButtonID);

                setButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String roomCode = roomCodeText.getText().toString().trim();

                        if (TextUtils.isEmpty(roomCode)) {
                            Toasty.error(getApplicationContext(), "Empty room", Toasty.LENGTH_SHORT).show();
                        } else {

                            //code API
                            apiService.arenaValorUpdate_roomcode(secret_id, api_token, matchID, roomCode).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    dialog.dismiss();
                                    if (response.body().getE() == 0) {
                                        arena_of_valor_matches(gameID);
                                        Toasty.success(getApplicationContext(), "Room Code set", Toasty.LENGTH_SHORT).show();
                                    } else {
                                        Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                    Toasty.error(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });

            }
        });

        onGoingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.confirmation_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                TextView yesButton = dialog.findViewById(R.id.yesButtonID);
                TextView noButton = dialog.findViewById(R.id.noButtonID);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loader.show();
                        apiService.arenaValorMove_to_ongoing(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                            @Override
                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                loader.dismiss();

                                if (response.body().getE() == 0) {

                                    optionDialog.dismiss();
                                    dialog.dismiss();
                                    Toasty.success(getApplicationContext(), "Moved onGoing", Toasty.LENGTH_SHORT).show();

                                    arena_of_valor_matches(gameID);
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

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

        editMatchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(AllCreatedMatches.this);
                dialog.setContentView(R.layout.arena_valor_edit_match);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                EditText titleEditText = dialog.findViewById(R.id.titleTextID);
                EditText dateEditText = dialog.findViewById(R.id.dateTextID);
                EditText timeEditText = dialog.findViewById(R.id.timeTextID);
                EditText prizeEditText = dialog.findViewById(R.id.prizeTextID);
                EditText entryFeeEditText = dialog.findViewById(R.id.entryFeeTextID);
                EditText totalPlayerEditText = dialog.findViewById(R.id.totalPlayerTextID);
                EditText versionText = dialog.findViewById(R.id.versionTextID);
                AppCompatButton updateButton = dialog.findViewById(R.id.updateButtonID);

                titleEditText.setText(response.title);
                dateEditText.setText(response.date);
                timeEditText.setText(response.time);
                prizeEditText.setText(response.totalPrize);
                entryFeeEditText.setText(response.entryFee);
                totalPlayerEditText.setText(response.totalPlayer);
                versionText.setText(response.version);

                dateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(AllCreatedMatches.this, (view1, year, month, dayOfMonth) -> {

                            dateEditText.setText(String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year));

                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                        dpd.show();
                    }
                });

                timeEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog tpd = new TimePickerDialog(AllCreatedMatches.this, (view1, hourOfDay, minute) -> {

                            boolean isPM = (hourOfDay >= 12);

                            int hour = hourOfDay % 12;
                            if (hour == 0)
                                hour = 12;

                            timeEditText.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));

                        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(AllCreatedMatches.this));
                        tpd.show();

                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), dateEditText.getText().toString().trim() + " " + timeEditText.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                        String title = titleEditText.getText().toString().trim();
                        String date = dateEditText.getText().toString().trim();
                        String time = timeEditText.getText().toString().trim();
                        String prize = prizeEditText.getText().toString().trim();
                        String entryFee = entryFeeEditText.getText().toString().trim();
                        String totalPlayer = totalPlayerEditText.getText().toString().trim();
                        String version = versionText.getText().toString().trim();

                        if (TextUtils.isEmpty(title)) {
                            Toasty.error(getApplicationContext(), "Title empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(prize)) {
                            Toasty.error(getApplicationContext(), "Prize empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(entryFee)) {
                            Toasty.error(getApplicationContext(), "Entry Fee empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(totalPlayer)) {
                            Toasty.error(getApplicationContext(), "Total player empty", Toasty.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(version)) {
                            Toasty.error(getApplicationContext(), "Version empty", Toasty.LENGTH_SHORT).show();
                        } else {
                            //Toasty.success(getApplicationContext(), "Great", Toasty.LENGTH_SHORT).show();
                            // do code

                            loader.show();
                            apiService.editValorMatch(secret_id, api_token, matchID, date, time, title, entryFee, totalPlayer, prize, version).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();

                                    if (response.body().getE() == 0) {

                                        Toasty.success(getApplicationContext(), "Updated", Toasty.LENGTH_SHORT).show();
                                        optionDialog.dismiss();
                                        dialog.dismiss();
                                        arena_of_valor_matches(gameID);

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
                    }
                });


            }
        });


        /*joinedPlayerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Joined_player_list_activity.class);
                i.putExtra("match_id", String.valueOf(matchID));
                i.putExtra("type", "ludo");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(i);
            }
        });*/
    }

    public void add_match_in_the_list(String matchID) {
        //deleteAllButton.setVisibility(View.VISIBLE);
        if (selectedMatchList.contains(matchID)) {

        } else {
            selectedMatchList.add(matchID);
        }

        if (selectedMatchList.size() > 0 && deleteLayoutButton.getVisibility() == View.GONE) {
            deleteLayoutButton.setVisibility(View.VISIBLE);
            //selectAllLayout.setVisibility(View.VISIBLE);
        }

        //print_selectedMatchList();
    }

    public void remove_match_from_the_list(String matchID) {
        if (selectedMatchList.contains(matchID)) {
            selectedMatchList.remove(matchID);

            //Toast.makeText(this, String.valueOf(selectedMatchList.size()), Toast.LENGTH_SHORT).show();

            if (selectedMatchList.size() == 0 && deleteLayoutButton.getVisibility() == View.VISIBLE) {
                deleteLayoutButton.setVisibility(View.GONE);
                //selectAllLayout.setVisibility(View.GONE);
            } else {
                deleteLayoutButton.setVisibility(View.VISIBLE);
            }

            //print_selectedMatchList();
        }
    }

    public void print_selectedMatchList() {
        /*for (int i = 0; i < selectedMatchList.size(); i++) {
            Log.d("matchXX", selectedMatchList.get(i) + " Size: " + String.valueOf(selectedMatchList.size()));
        }*/

        Log.d("matchXX", selectedMatchList.toString());
        Log.d("matchxx", " Size: " + String.valueOf(selectedMatchList.size()));

    }

}