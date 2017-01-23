package com.bindlesoft.calculatorsimulator;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

public class pov extends AppCompatActivity {

    private CameraView mCameraView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pov);
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //SurfaceView sv = manager.getCameraIdList()[0];

    }

    public void exit(View view) {
        super.finish();
    }
}
