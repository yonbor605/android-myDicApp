package com.yonbor.mydicapp.activity.app.home.selector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yonbor.baselib.utils.DensityUtil;
import com.yonbor.baselib.utils.ImageUtil;
import com.yonbor.baselib.widget.AppActionBar;
import com.yonbor.baselib.widget.PictureLay;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.activity.base.BaseActivity;
import com.yonbor.mydicapp.widget.matisse.GifSizeFilter;
import com.yonbor.mydicapp.widget.matisse.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 拍照、单选
 */
@Route(path = "/home/selector/imageVideoSelector")
public class ImageVideoSelectorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.zhihu)
    Button zhihu;
    @BindView(R.id.dracula)
    Button dracula;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layImg)
    PictureLay pictureLay;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tv_room_to_pic)
    TextView tvRoomToPic;

    private static final int REQUEST_CODE_CHOOSE = 23;
    private UriAdapter mAdapter;
    final int MAX_PIC_NUM = 9;//最大数量
    int picMargin;//图片间距
    int picHeight;
    int picWidth;
    int addDrawable = R.drawable.add_picture;
    int delDrawable = R.drawable.delete_pic;
    private List<Uri> mSelected;
    String imgDir;//上传图片目录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_video_selector);
        ButterKnife.bind(this);
        findView();
    }


    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("拍照&单选");
        actionBar.setBackAction(new AppActionBar.Action() {

            @Override
            public void performAction(View view) {
                back();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });

        actionBar.addAction(new AppActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return "多选";
            }

            @Override
            public void performAction(View view) {
                Intent intent = new Intent(baseActivity, ImageVideoSelector2Activity.class);
                startActivity(intent);
            }
        });

        imgDir = application.getStoreImageDir("test");
        tvRoomToPic.setText("0/" + MAX_PIC_NUM);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter = new UriAdapter());

        zhihu.setOnClickListener(this);
        dracula.setOnClickListener(this);

        picMargin = DensityUtil.dip2px(5);
        picHeight = DensityUtil.dip2px(100);
        picWidth = DensityUtil.dip2px(100);
        pictureLay.setDefault(MAX_PIC_NUM, picMargin, picWidth, picHeight, addDrawable, delDrawable,
                new PictureLay.Listener() {
                    @Override
                    public void onPicClick(PictureLay pictureLay, ImageView picView, ImageView delView) {
                        if (!pictureLay.hasCurData()) {
                            checkCameraPermission();
                        }
                    }

                    @Override
                    public void onDelClick(PictureLay pictureLay, ImageView picView, ImageView delView) {
                        pictureLay.deletePic();
                        tvRoomToPic.setText(pictureLay.getPictureCount() + "/" + MAX_PIC_NUM);
                    }
                });
        pictureLay.addDefPic();
    }

    @SuppressLint("CheckResult")
    private void checkCameraPermission() {
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            takeOrChoosePhoto();
                        } else {
                            showToast("获取相机权限失败");
                        }
                    }
                });
    }

    @Override
    public void onClick(final View v) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    switch (v.getId()) {
                        case R.id.zhihu:
                            takeOrChoosePhoto();
                            break;
                        case R.id.dracula:
                            choosePhoto();
                            break;
                    }
                    mAdapter.setData(null, null);
                } else {
                    showToast(R.string.permission_request_denied);
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void choosePhoto() {
        Matisse.from(ImageVideoSelectorActivity.this)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(1)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void takeOrChoosePhoto() {
        Matisse.from(ImageVideoSelectorActivity.this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.yonbor.mydicapp.fileprovider", "test"))
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));


            mSelected = Matisse.obtainResult(data);
            handleImage(mSelected.get(0));
        }
    }

    /**
     * 只能单张图片的处理，适合拍照、单选上传，不适合一次多选
     *
     * @param uri
     */
    private void handleImage(final Uri uri) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                Bitmap bitmapShow = ImageUtil.resampleImage(getContentResolver(), uri, picWidth, true);
                Bitmap bitmapSave = ImageUtil.resampleImage(getContentResolver(), uri, 1280, false);
                String imgName = imgDir + System.currentTimeMillis() + ".jepg";
                ImageUtil.saveBitmap(bitmapSave, imgName);
                bitmapSave.recycle();
                bitmapSave = null;
                e.onNext(bitmapShow);
                e.onNext(imgName);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//指定doOnSubscribe的线程
                .compose(RxLifecycle.bindUntilEvent(this.lifecycle(), ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    Bitmap bitmap;
                    String imgName;

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof Bitmap) {
                            this.bitmap = (Bitmap) o;
                        }
                        if (o instanceof String) {
                            this.imgName = (String) o;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadingDialog();
                        pictureLay.setPic(bitmap, imgName, uri);
                        tvRoomToPic.setText(pictureLay.getPictureCount() + "/" + MAX_PIC_NUM);
                    }
                });

    }


    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private List<Uri> mUris;
        private List<String> mPaths;

        void setData(List<Uri> uris, List<String> paths) {
            mUris = uris;
            mPaths = paths;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            holder.mUri.setText(mUris.get(position).toString());
            holder.mPath.setText(mPaths.get(position));

            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {

            private TextView mUri;
            private TextView mPath;

            UriViewHolder(View contentView) {
                super(contentView);
                mUri = (TextView) contentView.findViewById(R.id.uri);
                mPath = (TextView) contentView.findViewById(R.id.path);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (pictureLay != null) {
            pictureLay.clear();
        }
        super.onDestroy();
    }
}
