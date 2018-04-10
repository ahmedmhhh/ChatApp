package utils;


import android.app.Activity;
import android.content.Intent;

import com.example.ahmed.chatapp.LoginActivity;

import Model.User;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Session {
    private static Session ourInstance = new Session();
    private  Realm realm;

    public static Session getInstance() {
        if(ourInstance==null) {
            ourInstance = new Session();
        }
        return ourInstance;
    }

    private Session() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }
    public void loginUser(final User user){
        if(realm.where(User.class).findFirst()==null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                }
            });

        }else{
            logout();
            loginUser(user);
        }
    }

    private void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }

    public boolean isUserLoggedIn(){
        return realm.where(User.class).findFirst() !=null;
    }
    public User getUser(){
        return realm.where(User.class).findFirst();
    }
    public  void logoutAndGoTologin(Activity activity){
        logout();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }
}
