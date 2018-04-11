package webServices;


import java.util.List;

import Model.ChatRoom;
import Model.LoginResponse;
import Model.MainResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {
    @POST("login-user.php")
    Call<LoginResponse> loginUser(@Body User user);
    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);

    @POST("add-chat-rooms.php")
    Call<MainResponse> addChatRooms(@Body ChatRoom chatRoom);

    @FormUrlEncoded
    @POST("delete-chat-room.php")
    Call<MainResponse> deleteChatRoom(@Field("id") int roomID);

    @POST("get-all-chat-rooms.php")
    Call<List<ChatRoom>> getAllChatRooms();
}
