package com.yonbor.baselib.utils;
/************************************************************************
 *History:
 *
 *1.Id:none 咨询聊天 chenkai 20170916
 *  Description: create
 *
 ************************************************************************/
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * 从相机相册获取图片
 */
public class CameraPhotoUtil {

    /**
     * 
     * @param baseContext
     * @param file
     * @return
     */
    public static Intent cameraIntent(Context baseContext, File file){
        Intent getImageByCamera = new Intent(
                "android.media.action.IMAGE_CAPTURE");
//                            cameraUri = Uri.fromFile(getFile());
        Uri cameraUri = FileUriPermissionCompat.adaptUriAndGrantPermission(baseContext,
                getImageByCamera, file);
        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                cameraUri);
        getImageByCamera.putExtra(
                "android.intent.extra.videoQuality", 0);
        return getImageByCamera;
    }
    public static Intent photoPickIntent(){
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        }
        
        return intent;
    }
}
