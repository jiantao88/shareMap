package com.fenxiangditu.sharemap.ui.base;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/31
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseTakePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    private static final String TAG = BaseTakePhotoActivity.class.getSimpleName();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private PopupWindow pw;
    private Uri imageUri;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
            configCompress(takePhoto);

        }
        return takePhoto;
    }

    private void configTakePhotoOption(TakePhoto takePhoto, int multiple) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //builder.setWithOwnGallery(multiple == 1 ? true : false);
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config =
                new CompressConfig.Builder()
                        .setMaxSize(102400)
                        .setMaxPixel(800)
                        .enableReserveRaw(true)
                        .create();
        takePhoto.onEnableCompress(config, true);
    }

    @Override
    public void takeSuccess(TResult result) {
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    //显示选择图片弹窗
    protected void showEditPhotoWindow(View view) {
        View contentView = getLayoutInflater().inflate(R.layout.popup_window_title_image, null);
        pw = new PopupWindow(contentView, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight(), true);
        //设置popupwindow背景
        pw.setBackgroundDrawable(new ColorDrawable());
        pw.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        //处理popupwindow
        popupwindowselectphoto(contentView);
    }

    //初始化控件和控件的点击事件
    private void popupwindowselectphoto(View contentView) {
        TextView tv_select_pic = (TextView) contentView.findViewById(R.id.tv_photo);
        TextView tv_pai_pic = (TextView) contentView.findViewById(R.id.tv_photograph);
        TextView tv_cancl = (TextView) contentView.findViewById(R.id.tv_cancle);
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.dialog_ll);
        tv_select_pic.setOnClickListener(pop);
        tv_pai_pic.setOnClickListener(pop);
        tv_cancl.setOnClickListener(pop);
        layout.setOnClickListener(pop);
    }

    private View.OnClickListener pop = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (pw != null) {
                pw.dismiss();
            }
            switch (v.getId()) {
                case R.id.tv_photo://相册
                    takePhoto.onPickFromGallery();
                    break;
                case R.id.tv_photograph://拍照
                    takePhoto.onPickFromCapture(imageUri);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        File fileBase =
                new File(
                        Environment.getExternalStorageDirectory(),
                        "/"
                                + getResources().getString(R.string.app_name)
                                + "/"
                                + System.currentTimeMillis()
                                + ".jpg");
        if (!fileBase.getParentFile().exists()) {
            fileBase.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(fileBase);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private CropOptions getCropOptions() {
        CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
    /*    getTakePhoto().onPickFromDocumentsWithCrop(imageUri,cropOptions);
        CropOptions.Builder builder = new CropOptions.Builder();
        int with = DementionUtil.getScreenWidthInPx(AMApplication.getAppInstance());
        builder.setOutputX(800).setOutputY(800);
        builder.setWithOwnCrop(true);*/
        return cropOptions;
    }

}
