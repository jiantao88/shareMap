/*
 ******************************* Copyright (c)*********************************\
 **
 **                 (c) Copyright 2015, 蒋朋, china, qd. sd
 **                          All Rights Reserved
 **
 **                           By()
 **
 **
 **-----------------------------------版本信息------------------------------------
 ** 版    本: V0.1
 **
 **------------------------------------------------------------------------------
 ********************************End of Head************************************\
 */

package com.fenxiangditu.sharemap.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/** 文 件 名: ImageUtil 创 建 人: 蒋朋 创建日期: 16-8-22 11:36 描 述: 修 改 人: 修改时间： 修改备注： */
public class ImageUtils {
  /**
   * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
   *
   * @param context
   * @param imageUri
   */
  @TargetApi(19)
  public static String getImageAbsolutePath(Context context, Uri imageUri) {
    if (context == null || imageUri == null) return null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
        && DocumentsContract.isDocumentUri(context, imageUri)) {
      if (isExternalStorageDocument(imageUri)) {
        String docId = DocumentsContract.getDocumentId(imageUri);
        String[] split = docId.split(":");
        String type = split[0];
        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/" + split[1];
        }
      } else if (isDownloadsDocument(imageUri)) {
        String id = DocumentsContract.getDocumentId(imageUri);
        Uri contentUri =
            ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
        return getDataColumn(context, contentUri, null, null);
      } else if (isMediaDocument(imageUri)) {
        String docId = DocumentsContract.getDocumentId(imageUri);
        String[] split = docId.split(":");
        String type = split[0];
        Uri contentUri = null;
        if ("image".equals(type)) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = new String[] {split[1]};
        return getDataColumn(context, contentUri, selection, selectionArgs);
      }
    } // MediaStore (and general)
    else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
      // Return the remote address
      if (isGooglePhotosUri(imageUri)) return imageUri.getLastPathSegment();
      return getDataColumn(context, imageUri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
      return imageUri.getPath();
    }
    return null;
  }

  public static String getDataColumn(
          Context context, Uri uri, String selection, String[] selectionArgs) {
    Cursor cursor = null;
    String column = MediaStore.Images.Media.DATA;
    String[] projection = {column};
    try {
      cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
      if (cursor != null && cursor.moveToFirst()) {
        int index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(index);
      }
    } finally {
      if (cursor != null) cursor.close();
    }
    return null;
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is ExternalStorageProvider.
   */
  public static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is DownloadsProvider.
   */
  public static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is MediaProvider.
   */
  public static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is Google Photos.
   */
  public static boolean isGooglePhotosUri(Uri uri) {
    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
  }

  public static Bitmap stringtoBitmap(String string) {
    //将字符串转换成Bitmap类型
    Bitmap bitmap = null;
    try {
      byte[] bitmapArray;
      bitmapArray = Base64.decode(string, Base64.DEFAULT);
      bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return bitmap;
  }

  public static String bitmaptoString(Bitmap bitmap) {
    //将Bitmap转换成字符串
    String string = null;
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
    byte[] bytes = bStream.toByteArray();
    string = Base64.encodeToString(bytes, Base64.DEFAULT);
    return string;
  }
}
