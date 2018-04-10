package Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject{
    @SerializedName("username")
    public  String username;
    @SerializedName("password")
    public  String password;
    @SerializedName("email")
    public String email;
}
