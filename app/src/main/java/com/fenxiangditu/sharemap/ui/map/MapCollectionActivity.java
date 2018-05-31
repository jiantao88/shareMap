package com.fenxiangditu.sharemap.ui.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenxiangditu.sharemap.manager.GlideLoaderManager;
import com.fenxiangditu.sharemap.ui.base.BaseTakePhotoActivity;
import com.fenxiangditu.sharemap.utils.BitmapUtils;
import com.fenxiangditu.sharemap.utils.ToastUtils;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MapCollectionActivity extends BaseTakePhotoActivity {

    @BindView(R.id.iv_map_cover)
    ImageView mIvMapCover;
    @BindView(R.id.tv_map_cover)
    TextView mTvMapCover;
    @BindView(R.id.et_map_name)
    EditText mEtMapName;
    @BindView(R.id.et_map_introduce)
    EditText mEtMapIntroduce;
    @BindView(R.id.tv_set_map_attri)
    TextView mTvSetMapAttri;
    @BindView(R.id.rb_public)
    RadioButton mRbPublic;
    @BindView(R.id.rb_private)
    RadioButton mRbPrivate;
    @BindView(R.id.rb_cooperation)
    RadioButton mRbCooperation;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.btn_map_confirm)
    Button mBtnMapConfirm;


    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_collection;
    }

    @Override
    protected boolean initToolbar() {
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result != null) {
            TImage ti = result.getImage();
            if (ti != null) {
                loadPathView(ti.getCompressPath());
            }
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        /**
         * 为了处理自定义裁剪   gif图失败的问题
         */
        if (!TextUtils.isEmpty(msg) && msg.contains(".gif")) {
            if (result.getImage() != null && !TextUtils.isEmpty(result.getImage().getOriginalPath())) {
                ToastUtils.showToast(MapCollectionActivity.this,"暂时不支持gif");
            }
        }
    }
    private void loadPathView(String path) {
        File file = new File(path);
        FileInputStream inStream;
        try {
            inStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
//            String frontFile = BitmapUtils.bitmapToBase64(bitmap);
            bitmap = BitmapUtils.GetRoundedCornerBitmap(bitmap);
            mIvMapCover.setImageBitmap(bitmap);
//            mPresenter.setUserImg(frontFile);
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle(getString(R.string.add_map_collection));

    }

    @OnClick({R.id.iv_map_cover, R.id.tv_set_map_attri, R.id.btn_map_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_map_cover:
                showEditPhotoWindow(view);
                break;
            case R.id.tv_set_map_attri:

                break;
            case R.id.btn_map_confirm:
                break;

            default:
                break;
        }
    }




}
