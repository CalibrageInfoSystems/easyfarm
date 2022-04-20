package com.cis.easyfarm.cloudHelper;


import com.cis.easyfarm.Objects.Notifications;
import com.cis.easyfarm.model.DevisetokenResopnse;
import com.cis.easyfarm.model.GetDistricts;
import com.cis.easyfarm.model.GetGender;
import com.cis.easyfarm.model.GetMandals;
import com.cis.easyfarm.model.GetOwnershipStatus;
import com.cis.easyfarm.model.GetPlotStatus;
import com.cis.easyfarm.model.GetRoles;
import com.cis.easyfarm.model.GetStates;
import com.cis.easyfarm.model.GetVillages;
import com.cis.easyfarm.model.GetVillagesbypincode;
import com.cis.easyfarm.model.LoginResponse;
import com.cis.easyfarm.model.NotificationResponse;
import com.cis.easyfarm.model.RegistrationResponse;
import com.cis.easyfarm.model.plotregistationResponse;
import com.google.gson.JsonObject;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {


    @GET
    Observable<GetStates> getstates(@Url String url);


    @GET
    Observable<GetDistricts> getdistricts(@Url String url);

    @GET
    Observable<GetMandals> getmandals(@Url String url);

    @GET
    Observable<GetVillages> getvillages(@Url String url);

    @GET
    Observable<GetGender> getender(@Url String url);

    @GET
    Observable<GetRoles> getrole(@Url String url);
    @GET
    Observable<GetVillagesbypincode> getvillagesbypincode(@Url String url);

    @GET
    Observable<GetPlotStatus> getstatus(@Url String url);

    @GET
    Observable<GetOwnershipStatus> getownership(@Url String url);

    @POST(Config.plotRegistration)
    Observable<plotregistationResponse> plotRegistrationPage(@Body JsonObject data);




    @POST(Config.externalRegistration)
    Observable<RegistrationResponse> externalRegistrationPage(@Body JsonObject data);

    @POST(Config.syncNotifications)
    Observable<NotificationResponse> notificationsPage(@Body JsonObject data);

    @POST(Config.UpdateDeviseToken)
    Observable<DevisetokenResopnse>gettokenresponse(@Body JsonObject data);

    @POST(Config.login_url)
    Observable<LoginResponse>getLoginPage (@Body JsonObject data);

    @POST(Config.mobile_login_url)
    Observable<LoginResponse>getuserLogin (@Body JsonObject data);


//    @POST(Config.login_url)
//    Observable<LoginResponse>getLoginPage (@Body JsonObject data);
//    @POST(APIConstantURL.UpdateDeviseToken)
//    Observable<DevisetokenResopnse>gettokenresponse(@Body JsonObject data);


}
