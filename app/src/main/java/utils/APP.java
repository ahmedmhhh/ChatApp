package utils;

import android.app.Application;

import io.realm.Realm;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
