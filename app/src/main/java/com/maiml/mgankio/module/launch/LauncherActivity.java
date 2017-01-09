package com.maiml.mgankio.module.launch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maiml.mgankio.MainActivity;
import com.maiml.mgankio.R;
import com.maiml.mgankio.base.BaseActivity;
import com.maiml.mgankio.http.di.DataManager;

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {


    private LauncherContract.Presenter mLauncherPresenter = new LauncherPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    public void onResume() {
        super.onResume();
        mLauncherPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLauncherPresenter.unsubscribe();
    }
    @Override
    public void toHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public DataManager getDataManager() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
