package webServices;


import Model.MainResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("login-user.php")
    Call<MainResponse> loginUser(@Body User user);
    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);
}
