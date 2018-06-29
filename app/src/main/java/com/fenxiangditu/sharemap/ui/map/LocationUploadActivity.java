package com.fenxiangditu.sharemap.ui.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenxiangditu.sharemap.ui.adapter.ImageSelectedAdapter;
import com.fenxiangditu.sharemap.ui.base.BaseActivity;
import com.fenxiangditu.sharemap.ui.widget.RectImageView;
import com.fenxiangditu.sharemap.utils.BrandUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/06/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LocationUploadActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.recycler_img)
    RecyclerView mRecyclerImg;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.btn_location_confirm)
    Button mBtnLocationConfirm;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.tv_add_locations)
    TextView mTvAddLocations;
    @BindView(R.id.tv_my_locations)
    TextView mTvMyLocations;
    @BindView(R.id.rl_add)
    RelativeLayout mRlAdd;
    private ImageSelectedAdapter mImagesAdapter;

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_upload;
    }

    @Override
    protected boolean initToolbar() {
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mRecyclerImg.setLayoutManager(new GridLayoutManager(this, 4));
        mImagesAdapter = new ImageSelectedAdapter(this, 9);
        mRecyclerImg.setAdapter(mImagesAdapter);
        initClick8();
    }

    private int mCount;
    private void initClick8() {
        mImagesAdapter.setOnImageHandleListener(new ImageSelectedAdapter.OnImageHandleListener() {
            @Override
            public void previewImage(String imagePath, RectImageView iv) {
                PreviewSingleImageActivity.startActivity(LocationUploadActivity.this, imagePath, iv);
            }

            @Override
            public void addImages(int count) {
//                show(count);
                mCount = count;
                showEditPhotoWindow(mBtnLocationConfirm);

            }
        });

    }

    @OnClick(R.id.rl_add)
    public void onViewClicked() {

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

    private Uri mImageUri;
    private int TAKE_PHOTO = 100;
    private int SELECT_ALBUM = 101;
    private static final int PERMISSON_REQUESTCODE = 0;
    private PopupWindow pw;
    private View.OnClickListener pop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (pw != null) {
                pw.dismiss();
            }
            switch (v.getId()) {
                case R.id.tv_photo://相册
                    checkPerm();
                    break;
                case R.id.tv_photograph://拍照
                    photograph();
                    break;
                default:
                    break;
            }
        }
    };

    private void photograph() {
        //使用相机拍照显示图片，这里保存在该app的关联目录--缓存目录下，所以无需进行外置SD卡的读取权限
        //注意：调用系统相册拍照无需使用相机权限
        //但是由于4.4以前的系统访问关联目录--缓存目录需要sd卡权限，我们为了兼容老版本加上。。

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + ".jpg";
        File outputImage = new File(getExternalCacheDir(), imageFileName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            mImageUri = FileProvider.getUriForFile(LocationUploadActivity.this,
                    "com.fenxiangditu.sharemap.fileprovider",
                    outputImage);
        } else {
            mImageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    /**
     * 检查权限
     */
    private void checkPerm() {
        String[] params = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, params)) {

            ImageChooseActivity.startForResult(LocationUploadActivity.this, mCount, SELECT_ALBUM);

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permission), PERMISSON_REQUESTCODE, params);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSON_REQUESTCODE) {
            checkPerm();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showMissingPermissionDialog();
        }else {

        }
    }

    public void settingPermissionActivity() {
        //判断是否为小米系统
        if (TextUtils.equals(BrandUtils.getSystemInfo().getOs(), BrandUtils.SYS_MIUI)) {
            Intent miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            miuiIntent.putExtra("extra_pkgname", getPackageName());
            //检测是否有能接受该Intent的Activity存在
            List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(miuiIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                startActivityForResult(miuiIntent, PERMISSON_REQUESTCODE);
                return;
            }
        }
        //如果不是小米系统 则打开Android系统的应用设置页
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingPermissionActivity();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

}
