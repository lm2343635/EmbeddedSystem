package edu.ut.softlab.camerasample;

import android.content.Context;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by limeng on 4/21/16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mSurfaceHolder;
    Camera mCamera;

    public CameraPreview(Context context) {
        super(context);
        mSurfaceHolder=getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            int openCameraType= Camera.CameraInfo.CAMERA_FACING_BACK;
            if(openCameraType<=Camera.getNumberOfCameras()) {
                mCamera=Camera.open(openCameraType);
                mCamera.setPreviewDisplay(holder);
            } else {
                Log.d("CameraSample", "cannot bind camera");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setPreviewSize(width, height);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();;
        mCamera=null;
    }

    protected void setPreviewSize(int width, int height) {
        Camera.Parameters parameters=mCamera.getParameters();
        List<Camera.Size> supported=parameters.getSupportedPreviewSizes();
        if(supported!=null) {
            for(Camera.Size size: supported) {
                if(size.width<=width&&size.height<=height) {
                    parameters.setPreviewSize(size.width, size.height);
                    mCamera.setParameters(parameters);
                    break;
                }
            }
        }
    }
}
