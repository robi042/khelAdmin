package code.fortomorrow.kheloNowAdmin.API;


import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Accept_money_history_response;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.AddMoneyListResponse;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_accepted_list_response;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_history_response;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneywithdrawMoney.Accept_and_rejected_list_response;
import code.fortomorrow.kheloNowAdmin.Model.AddNewMatch.AddNewMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.AddWithdrawHistory.Add_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info_response;
import code.fortomorrow.kheloNowAdmin.Model.BuySell.Buy_sell_response;
import code.fortomorrow.kheloNowAdmin.Model.Check_admin_status_response;
import code.fortomorrow.kheloNowAdmin.Model.EarnHistory.Earn_history_response;
import code.fortomorrow.kheloNowAdmin.Model.GameType.GameTypeResponse;
import code.fortomorrow.kheloNowAdmin.Model.HostHistory.Sub_admin_host_history_response;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.Model.LoginResponse.LoginResponse;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_ongoing_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_result_list;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_participant_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_uploaded_image_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_user_statistics_response;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.OngoingMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.PlayType.Play_Type;
import code.fortomorrow.kheloNowAdmin.Model.PopUp.Pop_up_response;
import code.fortomorrow.kheloNowAdmin.Model.Profit.Profit_response;
import code.fortomorrow.kheloNowAdmin.Model.Promoter.Promoter_response;
import code.fortomorrow.kheloNowAdmin.Model.EarnHistory.Refer_income_response;
import code.fortomorrow.kheloNowAdmin.Model.RefreshToken.RefreshToken;
import code.fortomorrow.kheloNowAdmin.Model.Rules.RulesResponse;
import code.fortomorrow.kheloNowAdmin.Model.ShowParticipant.ShowparticipantResponse;
import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_information_response;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_response;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.Model.Tournament.Tournament_list_response;
import code.fortomorrow.kheloNowAdmin.Model.User.Get_user_response;
import code.fortomorrow.kheloNowAdmin.Model.UserAbove.User_above_response;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.WithDrawResponse;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.Withdraw_history_response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("admin/login")
    Call<LoginResponse> getLoginResponse(@Field("user_name") String user_name,
                                         @Field("password") String password,
                                         @Field("auth_code") String authCode);

    @FormUrlEncoded
    @POST("admin/get_games")
    Call<GameTypeResponse> getmatchType(@Field("secret_id") String secret_id,
                                        @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST("admin/play_type")
    Call<Play_Type> getPlayType(@Field("secret_id") String secret_id,
                                @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("admin/get_add_money_request")
    Call<AddMoneyListResponse> getAddmoneyReq(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("payment_method") String payment_method);


    @FormUrlEncoded
    @POST("admin/rejected_add_money")
    Call<SorkariResponse> getRejectedAdmoney(@Field("secret_id") String secret_id,
                                             @Field("api_token") String api_token,
                                             @Field("add_money_id") String add_money_id);

    @FormUrlEncoded
    @POST("admin/update_payment_number")
    Call<SorkariResponse> updatePaymentNumber(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("type") String typ,
                                              @Field("first_number") String first_number,
                                              @Field("second_number") String second_number);

    @FormUrlEncoded
    @POST("admin/accepted_add_money")
    Call<SorkariResponse> getAcceptMoney(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("add_money_id") String add_money_id);

    @FormUrlEncoded
    @POST("admin/accepted_withdraw_money")
    Call<SorkariResponse> getAcceptedWithdraw(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("withdraw_money_id") String add_money_id,
                                              @Field("tranjection_id") String tranjection_id);

    @FormUrlEncoded
    @POST("admin/rejected_withdraw_money")
    Call<SorkariResponse> rejectedWithDraw(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("withdraw_money_id") String add_money_id);

    @FormUrlEncoded
    @POST("admin/get_withdraw_money_request_type")
    Call<WithDrawResponse> getWithDrawList(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("payment_method") String payment_method);

    @FormUrlEncoded
    @POST("admin/get_automated_cs_match_list")
    Call<OngoingMatchResponse> get_automated_cs_match_list(@Field("secret_id") String secret_id,
                                                           @Field("api_token") String api_token,
                                                           @Field("game_id") String game_id,
                                                           @Field("playing_type_id") String playing_type_id);

    @FormUrlEncoded
    @POST("admin/get_created_matches_list")
    Call<OngoingMatchResponse> getCreatedMatch(@Field("secret_id") String secret_id,
                                               @Field("api_token") String api_token,
                                               @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/get_rules")
    Call<RulesResponse> getRules(@Field("secret_id") String secret_id,
                                 @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("admin/get_fireefire_rules")
    Call<RulesResponse> getFreeFireRules(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("admin/get_ongoing_matches_list")
    Call<OngoingMatchResponse> getOnGoingResponse(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/get_result_matches_list")
    Call<OngoingMatchResponse> getResultMatches(@Field("secret_id") String secret_id,
                                                @Field("api_token") String api_token,
                                                @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/move_to_ongoing")
    Call<SorkariResponse> movetoOngoing(@Field("secret_id") String secret_id,
                                        @Field("api_token") String api_token,
                                        @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/move_to_result")
    Call<SorkariResponse> moveToResult(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("match_id") String match_id);
    @FormUrlEncoded
    @POST("admin/back_to_created_list")
    Call<SorkariResponse> back_to_created_list(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/update_rules")
    Call<SorkariResponse> updateRules(@Field("secret_id") String secret_id,
                                      @Field("api_token") String api_token,
                                      @Field("rule") String rule);

    @FormUrlEncoded
    @POST("admin/update_freefire_rules")
    Call<SorkariResponse> updateFreeFireRules(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("rule") String rule);

    @FormUrlEncoded
    @POST("admin/update_tournament_rules")
    Call<SorkariResponse> updateDailyScrimsRules(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("rule") String rule);

    @FormUrlEncoded
    @POST("admin/update_room_info")
    Call<SorkariResponse> updateRoomInfo(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("room_id") String room_id,
                                         @Field("room_pass") String room_pass,
                                         @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/get_result_match_info")
    Call<ShowparticipantResponse> getAllParticipants(@Field("secret_id") String secret_id,
                                                     @Field("api_token") String api_token,
                                                     @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/giving_match_result")
    Call<SorkariResponse> updateResultsParticipant(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("match_id") String match_id,
                                                   @Field("user_id") String user_id,
                                                   @Field("kill") String kill,
                                                   @Field("winning_money") String winning_money,
                                                   @Field("rank") String rank);

    @FormUrlEncoded
    @POST("admin/refunded")
    Call<SorkariResponse> updateRefund(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("match_id") String match_id,
                                       @Field("user_id") String user_id,
                                       @Field("refund_amount") String refund_amount);

    @FormUrlEncoded
    @POST("admin/refresh_token")
    Call<RefreshToken> getRefreshToken(@Field("secret_id") String secret_id,
                                       @Field("auth_code") String auth_code);

    @FormUrlEncoded
    @POST("admin/delete_match")
    Call<SorkariResponse> getDeleteMatchRespose(@Field("secret_id") String secret_id,
                                                @Field("api_token") String auth_code,
                                                @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/NotificationForall")
    Call<SorkariResponse> sendNotification(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("title") String title,
                                           @Field("message") String message);

//    @FormUrlEncoded
//    @POST("admin/refresh_token")
//    Call<RefreshToken> getRefreshToken(@Field("secret_id") String secret_id, @Field("auth_code") String auth_code);


    //hasFirstPrize,first_prize,hasSecondPrize,second_prize,hasThirdPrize,third_prize,
    // hasFourthPrize,fourth_prize,hasFifthPrize,fifth_prize,hasSixthPrize,sixth_prize,
    // hasSeventhPrize,seventh_prize,hasEightthPrize,eightth_prize,hasNinethPrize,nineth_prize,hasTenthPrize,tenth_prize
    @FormUrlEncoded
    @POST("admin/add_new_match")
    Call<AddNewMatchResponse> getAddMatchResponse(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("date") String date,
                                                  @Field("entry_fee") String entry_fee,
                                                  @Field("map") String map,
                                                  @Field("per_kill") String per_kill,
                                                  @Field("title") String title,
                                                  @Field("total_player") String total_player,
                                                  @Field("total_prize") String total_prize,
                                                  @Field("hasFirstPrize") String hasFirstPrize,
                                                  @Field("first_prize") String first_prize,
                                                  @Field("hasSecondPrize") String hasSecondPrize,
                                                  @Field("second_prize") String second_prize,
                                                  @Field("hasThirdPrize") String hasThirdPrize,
                                                  @Field("third_prize") String third_prize,
                                                  @Field("hasFourthPrize") String hasFourthPrize,
                                                  @Field("fourth_prize") String fourth_prize,
                                                  @Field("hasFifthPrize") String hasFifthPrize,
                                                  @Field("fifth_prize") String fifth_prize,
                                                  @Field("hasSixthPrize") String hasSixthPrize,
                                                  @Field("sixth_prize") String sixth_prize,
                                                  @Field("hasSeventhPrize") String hasSeventhPrize,
                                                  @Field("seventh_prize") String seventh_prize,
                                                  @Field("hasEightthPrize") String hasEightthPrize,
                                                  @Field("eightth_prize") String eightth_prize,
                                                  @Field("hasNinethPrize") String hasNinethPrize,
                                                  @Field("nineth_prize") String nineth_prize,
                                                  @Field("hasTenthPrize") String hasTenthPrize,
                                                  @Field("tenth_prize") String tenth_prize,
                                                  @Field("game_id") String game_id,
                                                  @Field("playType_id") String playType_id);

    ///admin/add_automated_cs_match
    @FormUrlEncoded
    @POST("admin/add_automated_cs_match")
    Call<AddNewMatchResponse> add_automated_cs_match(@Field("secret_id") String secret_id,
                                                     @Field("api_token") String api_token,
                                                     @Field("date") String date,
                                                     @Field("time") String time,
                                                     @Field("entry_fee") String entry_fee,
                                                     @Field("map") String map,
                                                     @Field("per_kill") String per_kill,
                                                     @Field("title") String title,
                                                     @Field("match_number") String match_number,
                                                     @Field("total_match") String total_match,
                                                     @Field("same_time_match") String same_time_match,
                                                     @Field("gap_time") String gap_time,
                                                     @Field("total_player") String total_player,
                                                     @Field("total_prize") String total_prize,
                                                     @Field("hasFirstPrize") String hasFirstPrize,
                                                     @Field("first_prize") String first_prize,
                                                     @Field("hasSecondPrize") String hasSecondPrize,
                                                     @Field("second_prize") String second_prize,
                                                     @Field("hasThirdPrize") String hasThirdPrize,
                                                     @Field("third_prize") String third_prize,
                                                     @Field("hasFourthPrize") String hasFourthPrize,
                                                     @Field("fourth_prize") String fourth_prize,
                                                     @Field("hasFifthPrize") String hasFifthPrize,
                                                     @Field("fifth_prize") String fifth_prize,
                                                     @Field("hasSixthPrize") String hasSixthPrize,
                                                     @Field("sixth_prize") String sixth_prize,
                                                     @Field("hasSeventhPrize") String hasSeventhPrize,
                                                     @Field("seventh_prize") String seventh_prize,
                                                     @Field("hasEightthPrize") String hasEightthPrize,
                                                     @Field("eightth_prize") String eightth_prize,
                                                     @Field("hasNinethPrize") String hasNinethPrize,
                                                     @Field("nineth_prize") String nineth_prize,
                                                     @Field("hasTenthPrize") String hasTenthPrize,
                                                     @Field("tenth_prize") String tenth_prize,
                                                     @Field("game_id") String game_id,
                                                     @Field("playType_id") String playType_id);

    @FormUrlEncoded
    @POST("admin/update_match_info")
    Call<SorkariResponse> getUpdateMatchRes(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("match_time") String match_time,
                                            @Field("entry_fee") String entry_fee,
                                            @Field("map") String map,
                                            @Field("per_kill_rate") String per_kill,
                                            @Field("title") String title,
                                            @Field("total_player") String total_player,
                                            @Field("total_prize") String total_prize,
                                            @Field("first_prize") String first_prize,
                                            @Field("second_prize") String second_prize,
                                            @Field("third_prize") String third_prize,
                                            @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/add_ludo_match")
    Call<AddNewMatchResponse> getLudoAddMatchResponse(@Field("secret_id") String secret_id,
                                                      @Field("api_token") String api_token,
                                                      @Field("date") String date,
                                                      @Field("time") String time,
                                                      @Field("title") String title,
                                                      @Field("entry_fee") String entry_fee,
                                                      @Field("total_player") String total_player,
                                                      @Field("total_prize") String total_prize,
                                                      @Field("host_app") String gameType,
                                                      @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("admin/add_new_ludo_match")
    Call<AddNewMatchResponse> auto_add_new_ludo_match(@Field("secret_id") String secret_id,
                                                      @Field("api_token") String api_token,
                                                      @Field("date") String date,
                                                      @Field("time") String time,
                                                      @Field("title") String title,
                                                      @Field("entry_fee") String entry_fee,
                                                      @Field("total_player") String total_player,
                                                      @Field("total_prize") String total_prize,
                                                      @Field("host_app") String gameType,
                                                      @Field("game_id") String gameID,
                                                      @Field("match_number") String match_number,
                                                      @Field("total_match") String total_match,
                                                      @Field("same_time_match") String same_time_match,
                                                      @Field("gap_time") String gap_time);

    @FormUrlEncoded
    @POST("admin/get_created_ludo_match_list")
    Call<Ludo_ongoing_response> getLudoMatchListResponse(@Field("secret_id") String secret_id,
                                                         @Field("api_token") String api_token,
                                                         @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("admin/update_ludo_roomcode")
    Call<SorkariResponse> setLudoRoomCodeResponse(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("match_id") String match_id,
                                                  @Field("room_code") String room_code);

    @FormUrlEncoded
    @POST("admin/remove_ludo_match")
    Call<SorkariResponse> removeLudoMatchResponse(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("match_id") String match_id);


    @FormUrlEncoded
    @POST("admin/move_ludo_ongoing")
    Call<SorkariResponse> moveLudoOnGoingResponse(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/move_ludo_result")
    Call<SorkariResponse> moveLudoResultResponse(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("match_id") String match_id);
    @FormUrlEncoded
    @POST("admin/ludo_back_to_created_list")
    Call<SorkariResponse> ludo_back_to_created_list(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/get_ongoing_ludo_match_list")
    Call<Ludo_ongoing_response> getLudoOnGoingListResponse(@Field("secret_id") String secret_id,
                                                           @Field("api_token") String api_token,
                                                           @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("admin/get_result_ludo_match_list")
    Call<Ludo_game_result_list> getLudoResultListResponse(@Field("secret_id") String secret_id,
                                                          @Field("api_token") String api_token,
                                                          @Field("game_id") String gameID);

    @FormUrlEncoded
    @POST("admin/get_ludo_result_info")
    Call<Ludo_participant_response> getLudoParticipantList(@Field("secret_id") String secret_id,
                                                           @Field("api_token") String api_token,
                                                           @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/update_ludo_result")
    Call<SorkariResponse> updateLudoResult(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("match_id") String match_id,
                                           @Field("winning_money") String winning_money,
                                           @Field("rank") String rank,
                                           @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/give_ludo_refund")
    Call<SorkariResponse> updateLudoRefund(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("match_id") String match_id,
                                           @Field("refund_amount") String refund_amount,
                                           @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/update_ludo_rules")
    Call<SorkariResponse> updateLudoRules(@Field("secret_id") String secret_id,
                                          @Field("api_token") String api_token,
                                          @Field("rule") String rule);


    @FormUrlEncoded
    @POST("admin/get_user")
    Call<Get_user_response> getUserdata(@Field("secret_id") String secret_id,
                                        @Field("api_token") String api_token,
                                        @Field("phone_or_username") String phone_or_username);

    @FormUrlEncoded
    @POST("admin/block_unblock_user")
    Call<SorkariResponse> blockUnBlockUser(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/search_account_activity")
    Call<Add_money_accepted_list_response> getAddMoneyAcceptedList(@Field("secret_id") String secret_id,
                                                                   @Field("api_token") String api_token,
                                                                   @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("admin/update_youtube_link")
    Call<SorkariResponse> updateHowToJoinLudoLink(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("link") String link);

    @FormUrlEncoded
    @POST("admin/update_result_done")
    Call<SorkariResponse> updateLudoResultStatus(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("match_id") String match_id);

    /*/admin/edit_ludo_match
secret_id, api_token, date, time, title, entry_fee, total_player, total_prize, host_app*/

    @FormUrlEncoded
    @POST("admin/edit_ludo_match")
    Call<SorkariResponse> updateLudoMatch(@Field("secret_id") String secret_id,
                                          @Field("api_token") String api_token,
                                          @Field("match_id") String match_id,
                                          @Field("date") String date,
                                          @Field("time") String time,
                                          @Field("title") String title,
                                          @Field("entry_fee") String entry_fee,
                                          @Field("total_player") String total_player,
                                          @Field("total_prize") String total_prize,
                                          @Field("host_app") String host_app);

    @FormUrlEncoded
    @POST("admin/get_sub_admins")
    Call<Sub_admin_response> getSubAdmins(@Field("secret_id") String secret_id,
                                          @Field("api_token") String api_token,
                                          @Field("admin_type") String admin_type);

    @FormUrlEncoded
    @POST("admin/block_unblock_sub_admin")
    Call<SorkariResponse> controlSubAdmins(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("sub_admin_id") String sub_admin_id);

    @FormUrlEncoded
    @POST("admin/check_add_admin_permission")
    Call<Check_admin_status_response> checkAdminStatus(@Field("secret_id") String secret_id,
                                                       @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST("admin/add_new_admin")
    Call<SorkariResponse> addNewAdmin(@Field("secret_id") String secret_id,
                                      @Field("api_token") String api_token,
                                      @Field("type") String type,
                                      @Field("user_name") String user_name,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("admin/create_slider")
    Call<SorkariResponse> createSlider(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("title") String title,
                                       @Field("image") String image,
                                       @Field("link") String link);

    //ludo-app/v1/upload-slider
    @FormUrlEncoded
    @POST("admin/ludo-app/v1/upload-slider")
    Call<SorkariResponse> khelo_ludo_slider_upload(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("title") String title,
                                                   @Field("image") String image,
                                                   @Field("link") String link);

    @FormUrlEncoded
    @POST("admin/get_slider_list")
    Call<Slider_list_response> getSlider(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("status") String status);

    //ludo-app/v1/get-slider-list

    @FormUrlEncoded
    @POST("admin/ludo-app/v1/slider-list")
    Call<Slider_list_response> khelo_ludo_slider_list(@Field("secret_id") String secret_id,
                                                      @Field("api_token") String api_token,
                                                      @Field("status") String status);

    /*/admin/active_inactive_slider
secret_id, api_token, slider_id*/

    @FormUrlEncoded
    @POST("admin/active_inactive_slider")
    Call<SorkariResponse> updateSlider(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("slider_id") String slider_id);

    //admin/ludo-app/v1/active-inactive-slider
    @FormUrlEncoded
    @POST("admin/ludo-app/v1/active-inactive-slider")
    Call<SorkariResponse> khelo_ludo_update_slider(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("slider_id") String slider_id);

    //add_popup
    @FormUrlEncoded
    @POST("admin/add_popup")
    Call<SorkariResponse> createPopUp(@Field("secret_id") String secret_id,
                                      @Field("api_token") String api_token,
                                      @Field("type") String type,
                                      @Field("text") String text,
                                      @Field("image") String image,
                                      @Field("link") String link);

    @FormUrlEncoded
    @POST("admin/update_popup_status")
    Call<Pop_up_response> updatePopUpStatus(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token);


    @FormUrlEncoded
    @POST("admin/get_popup_status")
    Call<Pop_up_response> getPopUpStatus(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("admin/update_user_balance")
    Call<SorkariResponse> updateUserBalance(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("user_id") String user_id,
                                            @Field("total_balance") String total_balance,
                                            @Field("deposit_balance") String deposit_balance,
                                            @Field("winning_balance") String winning_balance);


    //create match test

    @FormUrlEncoded
    @POST("admin/new_add_new_match")
    Call<AddNewMatchResponse> createMatches(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("date") String date,
                                            @Field("entry_fee") String entry_fee,
                                            @Field("map") String map,
                                            @Field("per_kill") String per_kill,
                                            @Field("title") String title,
                                            @Field("total_player") String total_player,
                                            @Field("total_prize") String total_prize,
                                            @Field("first_prize") String first_prize,
                                            @Field("second_prize") String second_prize,
                                            @Field("third_prize") String third_prize,
                                            @Field("game_id") String game_id,
                                            @Field("playType_id") String playType_id);

    //admin/add_new_notice

    @FormUrlEncoded
    @POST("admin/add_new_notice")
    Call<SorkariResponse> updateNotice(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("title") String title);

    @FormUrlEncoded
    @POST("admin/update_support_numbers")
    Call<SorkariResponse> updateSupportNumber(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("type") String type,
                                              @Field("number") String number);

    ///admin/get_withdraw_info_history
    //secret_id, api_token, user_name

    @FormUrlEncoded
    @POST("admin/get_withdraw_info_history")
    Call<Withdraw_history_response> getWithdrawHistory(@Field("secret_id") String secret_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("admin/get_add_info_history")
    Call<Add_money_history_response> getAddMoneyUserHistory(@Field("secret_id") String secret_id,
                                                            @Field("api_token") String api_token,
                                                            @Field("user_name") String user_name);

    ///admin/update_ludo_message
    //secret_id, api_token_match_id

    @FormUrlEncoded
    @POST("admin/update_ludo_message")
    Call<SorkariResponse> updateLudoMessage(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("match_id") String match_ID,
                                            @Field("message") String message);

    @FormUrlEncoded
    @POST("admin/check_joined_player")
    Call<Joined_player_list_response> getJoinedPlayerList(@Field("secret_id") String secret_id,
                                                          @Field("api_token") String api_token,
                                                          @Field("match_id") String match_ID);

    //admin/check_ludo_join_list
    @FormUrlEncoded
    @POST("admin/check_ludo_join_list")
    Call<Joined_player_list_response> getJoinedLudoPlayerList(@Field("secret_id") String secret_id,
                                                              @Field("api_token") String api_token,
                                                              @Field("match_id") String match_ID);

    ///admin/remove_user

    @FormUrlEncoded
    @POST("admin/remove_user")
    Call<SorkariResponse> removeUser(@Field("secret_id") String secret_id,
                                     @Field("api_token") String api_token,
                                     @Field("match_id") String match_ID,
                                     @Field("user_id") String user_ID);

    //admin/refund_before_match
    @FormUrlEncoded
    @POST("admin/refund_before_match")
    Call<SorkariResponse> refundBeforeMatch(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("match_id") String match_ID,
                                            @Field("user_id") String user_ID,
                                            @Field("refund_amount") String refund_amount);

    ///admin/remove_user_from_ludo
    @FormUrlEncoded
    @POST("admin/remove_user_from_ludo")
    Call<SorkariResponse> removeLudoUser(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("match_id") String match_ID,
                                         @Field("user_id") String user_ID);

    ///admin/refund_before_ludo_start
    @FormUrlEncoded
    @POST("admin/refund_before_ludo_start")
    Call<SorkariResponse> refundBeforeLudoMatch(@Field("secret_id") String secret_id,
                                                @Field("api_token") String api_token,
                                                @Field("match_id") String match_ID,
                                                @Field("user_id") String user_ID,
                                                @Field("refund_amount") String refund_amount);

    //admin/see_accept_and_rejected_list
    @FormUrlEncoded
    @POST("admin/see_accept_and_rejected_list")
    Call<Accept_and_rejected_list_response> getAccept_and_Rejected_list(@Field("secret_id") String secret_id,
                                                                        @Field("api_token") String api_token,
                                                                        @Field("type") String type,
                                                                        @Field("status") String status);

    @FormUrlEncoded
    @POST("admin/add_link")
    Call<SorkariResponse> addHowToLink(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("type") String type,
                                       @Field("link") String link);

    ///admin/send_image_notifications
    //secret_id, api_token, image, title, message
    @FormUrlEncoded
    @POST("admin/send_image_notifications")
    Call<SorkariResponse> sendImageNotification(@Field("secret_id") String secret_id,
                                                @Field("api_token") String api_token,
                                                @Field("image") String image,
                                                @Field("title") String title,
                                                @Field("message") String message);

    //admin/send_match_notification

    @FormUrlEncoded
    @POST("admin/send_match_notification")
    Call<SorkariResponse> sendMatchNotification(@Field("secret_id") String secret_id,
                                                @Field("api_token") String api_token,
                                                @Field("match_id") String match_ID);

    //notification_for_ludo_match
    @FormUrlEncoded
    @POST("admin/notification_for_ludo_match")
    Call<SorkariResponse> sendLudoMatchNotification(@Field("secret_id") String secret_id,
                                                    @Field("api_token") String api_token,
                                                    @Field("match_id") String match_ID);

    @FormUrlEncoded
    @POST("admin/update_admin_note_ludo")
    Call<SorkariResponse> updateLudoNotes(@Field("secret_id") String secret_id,
                                          @Field("api_token") String api_token,
                                          @Field("match_id") String matchID,
                                          @Field("note") String note);

    @FormUrlEncoded
    @POST("admin/update_admin_note_freefire")
    Call<SorkariResponse> updateFreeFireNotes(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("match_id") String matchID,
                                              @Field("note") String note);


    @FormUrlEncoded
    @POST("admin/update_all_rules")
    Call<SorkariResponse> updateAllRules(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("rule") String rule,
                                         @Field("type") String type);

    @FormUrlEncoded
    @POST("admin/view_ludo_image")
    Call<Ludo_uploaded_image_response> getUploadedLudoImages(@Field("secret_id") String secret_id,
                                                             @Field("api_token") String api_token,
                                                             @Field("match_id") String matchID);

    //admin/send_message
    @FormUrlEncoded
    @POST("admin/send_message")
    Call<SorkariResponse> sendMessageToUser(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("message") String message,
                                            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/send_a_message_to_user")
    Call<SorkariResponse> send_a_message_to_user(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("message") String message,
                                                 @Field("user_id") String user_id);

    //buy-and-sell/add_products
    @FormUrlEncoded
    @POST("admin/buy-and-sell/add_products")
    Call<SorkariResponse> addButAndSellItem(@Field("secret_id") String secret_id,
                                            @Field("api_token") String api_token,
                                            @Field("title") String title,
                                            @Field("description") String description,
                                            @Field("price") String price,
                                            @Field("link") String link,
                                            @Field("watch_link") String watch_link,
                                            @Field("discount") String discount,
                                            @Field("type") String type,
                                            @Field("image") String image);

    ///buy-and-sell/get_all_products
    @FormUrlEncoded
    @POST("admin/buy-and-sell/get_all_products")
    Call<Buy_sell_response> getBuySellItem(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("type") String type);

    ///admin/buy-and-sell/update_percent_amount
    //secret_id, api_token, product_id, discount

    @FormUrlEncoded
    @POST("admin/buy-and-sell/update_percent_amount")
    Call<SorkariResponse> updatePercentAmount(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token,
                                              @Field("product_id") String product_id,
                                              @Field("discount") String discount);

    ///admin/buy-and-sell/remove_product
    //secret_id, api_token, product_id
    @FormUrlEncoded
    @POST("admin/buy-and-sell/remove_product")
    Call<SorkariResponse> removeProduct(@Field("secret_id") String secret_id,
                                        @Field("api_token") String api_token,
                                        @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("admin/sub_admin_withdraw_history")
    Call<Sub_admin_information_response> sub_admin_withdraw_history(@Field("secret_id") String secret_id,
                                                                    @Field("api_token") String api_token,
                                                                    @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("admin/check_sub_admin_withdraw_history")
    Call<Sub_admin_withdraw_history_response> check_sub_admin_withdraw_history(@Field("secret_id") String secret_id,
                                                                               @Field("api_token") String api_token,
                                                                               @Field("sub_admin_id") String sub_admin_id);

    @FormUrlEncoded
    @POST("admin/get_subadmin_withdraw_daily_history")
    Call<Sub_admin_withdraw_history_response> get_sub_admin_withdraw_daily_history(@Field("secret_id") String secret_id,
                                                                                   @Field("api_token") String api_token,
                                                                                   @Field("sub_admin_id") String sub_admin_id,
                                                                                   @Field("date") String date,
                                                                                   @Field("method") String method);

    @FormUrlEncoded
    @POST("admin/get_add_amount_history")
    Call<Sub_admin_withdraw_history_response> get_add_amount_history(@Field("secret_id") String secret_id,
                                                                     @Field("api_token") String api_token,
                                                                     @Field("sub_admin_id") String sub_admin_id,
                                                                     @Field("date") String date,
                                                                     @Field("method") String method);

    @FormUrlEncoded
    @POST("admin/get_user_ludo_statastic")
    Call<Ludo_user_statistics_response> get_user_ludo_statistics(@Field("secret_id") String secret_id,
                                                                 @Field("api_token") String api_token,
                                                                 @Field("user_name") String user_name,
                                                                 @Field("date") String date);


    @FormUrlEncoded
    @POST("admin/add_promoter")
    Call<SorkariResponse> add_promoter(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token,
                                       @Field("name") String name,
                                       @Field("user_name") String user_name,
                                       @Field("phone") String phone,
                                       @Field("password") String password);

    ///admin/get_promoter_list
    @FormUrlEncoded
    @POST("admin/get_promoter_list")
    Call<Promoter_response> get_promoter_list(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token);

    ///admin/update_promoter_info
    //secret_id,api_token,promoter_id,user_name,name,phone,hasPassword,password
    @FormUrlEncoded
    @POST("admin/update_promoter_info")
    Call<SorkariResponse> update_promoter_info(@Field("secret_id") String secret_id,
                                               @Field("api_token") String api_token,
                                               @Field("promoter_id") String promoter_id,
                                               @Field("user_name") String user_name,
                                               @Field("name") String name,
                                               @Field("phone") String phone,
                                               @Field("hasPassword") String hasPassword,
                                               @Field("password") String password);

    //admin/arena-of-valor/add_match
    //secret_id,api_token,date,time,title,entry_fee,total_player,total_prize,version,game_id,playingType_id

    @FormUrlEncoded
    @POST("admin/arena-of-valor/add_match")
    Call<SorkariResponse> addNewValorMatch(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token,
                                           @Field("date") String date,
                                           @Field("time") String time,
                                           @Field("title") String title,
                                           @Field("entry_fee") String entry_fee,
                                           @Field("total_player") String total_player,
                                           @Field("total_prize") String total_prize,
                                           @Field("version") String version,
                                           @Field("game_id") String game_id,
                                           @Field("playingType_id") String playingType_id);

    @FormUrlEncoded
    @POST("admin/arena-of-valor/edit_match")
    Call<SorkariResponse> editValorMatch(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token,
                                         @Field("match_id") String match_id,
                                         @Field("date") String date,
                                         @Field("time") String time,
                                         @Field("title") String title,
                                         @Field("entry_fee") String entry_fee,
                                         @Field("total_player") String total_player,
                                         @Field("total_prize") String total_prize,
                                         @Field("version") String version);

    @FormUrlEncoded
    @POST("admin/arena-of-valor/get_created_match_list")
    Call<Arena_valor_response> arenaValorMatchList(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/arena-of-valor/delete_match")
    Call<SorkariResponse> arenaValorDelete_match(@Field("secret_id") String secret_id,
                                                 @Field("api_token") String api_token,
                                                 @Field("match_id") String match_id);

    @FormUrlEncoded
    @POST("admin/arena-of-valor/update_roomcode")
    Call<SorkariResponse> arenaValorUpdate_roomcode(@Field("secret_id") String secret_id,
                                                    @Field("api_token") String api_token,
                                                    @Field("match_id") String match_id,
                                                    @Field("room_code") String room_code);

    //move_to_ongoing
    @FormUrlEncoded
    @POST("admin/arena-of-valor/move_to_ongoing")
    Call<SorkariResponse> arenaValorMove_to_ongoing(@Field("secret_id") String secret_id,
                                                    @Field("api_token") String api_token,
                                                    @Field("match_id") String match_id);

    //get_ongoing_list
    //secret_id, api_token, game_id
    @FormUrlEncoded
    @POST("admin/arena-of-valor/get_ongoing_list")
    Call<Arena_valor_response> arenaValorGet_ongoing_list(@Field("secret_id") String secret_id,
                                                          @Field("api_token") String api_token,
                                                          @Field("game_id") String game_id);

    //move_to_result
    @FormUrlEncoded
    @POST("admin/arena-of-valor/move_to_result")
    Call<SorkariResponse> arenaValorMove_to_result(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("match_id") String match_id);

    //get_result_list
    @FormUrlEncoded
    @POST("admin/arena-of-valor/get_result_list")
    Call<Arena_valor_response> arenaValorGet_result_list(@Field("secret_id") String secret_id,
                                                         @Field("api_token") String api_token,
                                                         @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/arena-of-valor/get_result_match_info")
    Call<Arena_valor_result_match_info_response> arenaValor_get_result_match_info(@Field("secret_id") String secret_id,
                                                                                  @Field("api_token") String api_token,
                                                                                  @Field("match_id") String match_id);

    ///admin/arena-of-valor/get_each_result_match_info
    //secret_id,api_token,user_id,match_id

    @FormUrlEncoded
    @POST("admin/arena-of-valor/get_each_result_match_info")
    Call<Arena_valor_result_match_info_response> arenaValor_get_each_result_match_info(@Field("secret_id") String secret_id,
                                                                                       @Field("api_token") String api_token,
                                                                                       @Field("user_id") String user_id,
                                                                                       @Field("match_id") String match_id);

    //giving_refund
//secret_id,api_token,refund_amount
    @FormUrlEncoded
    @POST("admin/arena-of-valor/giving_refund")
    Call<SorkariResponse> arenaValor_giving_refund(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("user_id") String user_id,
                                                   @Field("match_id") String matchID,
                                                   @Field("refund_amount") String refund_amount);

    ///admin/arena-of-valor/giving_result
    //secret_id,api_token,user_id,match_id,winning_money,rank
    @FormUrlEncoded
    @POST("admin/arena-of-valor/giving_result")
    Call<SorkariResponse> arenaValor_giving_result(@Field("secret_id") String secret_id,
                                                   @Field("api_token") String api_token,
                                                   @Field("user_id") String user_id,
                                                   @Field("match_id") String matchID,
                                                   @Field("winning_money") String winning_money,
                                                   @Field("rank") String rank);

    //update_nodes
    //secret_id,api_token,match_id,note
    @FormUrlEncoded
    @POST("admin/arena-of-valor/update_nodes")
    Call<SorkariResponse> arenaValor_update_notes(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("match_id") String match_id,
                                                  @Field("note") String note);

    ///admin/update_done_result
    //secret_id, api_token,match_id
    @FormUrlEncoded
    @POST("admin/update_done_result")
    Call<SorkariResponse> update_done_result(@Field("secret_id") String secret_id,
                                             @Field("api_token") String api_token,
                                             @Field("match_id") String match_id);

    //tournament
    ///admin/tournament/add_new_tournament
    @FormUrlEncoded
    @POST("admin/tournament/add_new_tournament")
    Call<SorkariResponse> add_tournament_match(@Field("secret_id") String secret_id,
                                               @Field("api_token") String api_token,
                                               @Field("title") String title,
                                               @Field("total_prize") String total_prize,
                                               @Field("start_date") String start_date,
                                               @Field("end_date") String end_date,
                                               @Field("entry_fee") String entry_fee,
                                               @Field("total_team") String total_team,
                                               @Field("hasFirstPrize") String hasFirstPrize,
                                               @Field("first_prize") String first_prize,
                                               @Field("hasSecondPrize") String hasSecondPrize,
                                               @Field("second_prize") String second_prize,
                                               @Field("hasThirdPrize") String hasThirdPrize,
                                               @Field("third_prize") String third_prize,
                                               @Field("hasFourthPrize") String hasFourthPrize,
                                               @Field("fourth_prize") String fourth_prize,
                                               @Field("hasFifthPrize") String hasFifthPrize,
                                               @Field("fifth_prize") String fifth_prize,
                                               @Field("hasSixthPrize") String hasSixthPrize,
                                               @Field("sixth_prize") String sixth_prize,
                                               @Field("hasSeventhPrize") String hasSeventhPrize,
                                               @Field("seventh_prize") String seventh_prize,
                                               @Field("hasEightthPrize") String hasEightthPrize,
                                               @Field("eightth_prize") String eightth_prize,
                                               @Field("hasNinethPrize") String hasNinethPrize,
                                               @Field("nineth_prize") String nineth_prize,
                                               @Field("hasTenthPrize") String hasTenthPrize,
                                               @Field("tenth_prize") String tenth_prize,
                                               @Field("hasEleventhPrize") String hasEleventhPrize,
                                               @Field("eleventh_prize") String eleventh_prize,
                                               @Field("hasTwelvePrize") String hasTwelvePrize,
                                               @Field("twelveth_prize") String twelveth_prize);

    @FormUrlEncoded
    @POST("admin/tournament/get_tournament_list")
    Call<Tournament_list_response> get_tournament_matches(@Field("secret_id") String secret_id,
                                                          @Field("api_token") String api_token);

    //balance history
    @FormUrlEncoded
    @POST("admin/get_daily_profit_freefire")
    Call<Profit_response> get_free_fire_profit_history(@Field("secret_id") String secret_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("date") String date,
                                                       @Field("game_id") String game_id);

    //get_daily_profit_ludo
    @FormUrlEncoded
    @POST("admin/get_daily_profit_ludo")
    Call<Profit_response> get_daily_profit_ludo(@Field("secret_id") String secret_id,
                                                @Field("api_token") String api_token,
                                                @Field("date") String date,
                                                @Field("game_id") String game_id);

    @FormUrlEncoded
    @POST("admin/get_user_avobe_500_info")
    Call<User_above_response> get_user_above(@Field("secret_id") String secret_id,
                                             @Field("api_token") String api_token,
                                             @Field("amount") String amount);

    @FormUrlEncoded
    @POST("admin/income_from_refer")
    Call<Refer_income_response> user_income_from_refer(@Field("secret_id") String secret_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/get_freefire_earn_history")
    Call<Earn_history_response> free_fire_earn_history(@Field("secret_id") String secret_id,
                                                       @Field("api_token") String api_token,
                                                       @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/get_ludo_earn_history")
    Call<Earn_history_response> ludo_earn_history(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token,
                                                  @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/get_add_withdraw_history")
    Call<Earn_history_response> get_add_withdraw_history(@Field("secret_id") String secret_id,
                                                         @Field("api_token") String api_token,
                                                         @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/get_add_money_daily_history")
    Call<Accept_money_history_response> get_add_money_daily_history(@Field("secret_id") String secret_id,
                                                                    @Field("api_token") String api_token,
                                                                    @Field("date") String date);

    @FormUrlEncoded
    @POST("admin/sub_admin_host_history")
    Call<Sub_admin_host_history_response> get_sub_admin_host_history(@Field("secret_id") String secret_id,
                                                                     @Field("api_token") String api_token,
                                                                     @Field("date") String date);
    @FormUrlEncoded
    @POST("admin/get_all_add_withdraw_history_bu_admin")
    Call<Add_withdraw_history_response> get_all_add_withdraw_history_bu_admin(@Field("secret_id") String secret_id,
                                                                   @Field("api_token") String api_token,
                                                                    @Field("user_id") String user_id,
                                                                   @Field("type") String type);
    @FormUrlEncoded
    @POST("admin/get_total_user")
    Call<SorkariResponse> get_total_user(@Field("secret_id") String secret_id,
                                           @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_new_user")
    Call<SorkariResponse> get_new_user(@Field("secret_id") String secret_id,
                                         @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_pass_change_req")
    Call<SorkariResponse> get_pass_change_req(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_new_active_user")
    Call<SorkariResponse> get_new_active_user(@Field("secret_id") String secret_id,
                                       @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_old_active_user")
    Call<SorkariResponse> get_old_active_user(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("admin/get_today_firefire_join")
    Call<SorkariResponse> get_today_firefire_join(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_today_ludo_join")
    Call<SorkariResponse> get_today_ludo_join(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_today_profit")
    Call<SorkariResponse> get_today_profit(@Field("secret_id") String secret_id,
                                                  @Field("api_token") String api_token);
    @FormUrlEncoded
    @POST("admin/get_monthly_profit")
    Call<SorkariResponse> get_monthly_profit(@Field("secret_id") String secret_id,
                                              @Field("api_token") String api_token);
}
