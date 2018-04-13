package com.jt.sharemap.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;

/** 文件相关 Created by miaozy on 2016/10/11. */
public class FileUtils {

  /**
   * 如果文件不存在，就创建文件；
   *
   * @param path 文件路径
   * @return
   */
  public static File createIfNotExist(String path) {
    File file = new File(path);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return file;
  }

  /**
   * 创建目录
   *
   * @param dirName //文件夹全路径
   */
  public static File creatDir(String dirName) {
    File dir = new File(dirName);
    dir.mkdir();
    return dir;
  }

  /**
   * 文件追加写入：使用FileWriter
   *
   * @param fileName
   * @param content
   */
  public static void append(String path, String fileName, String content) {

    String str = path + fileName;
    try {
      //函数中的第二个参数表示以追加形式写文件；
      FileWriter writer = new FileWriter(str, true);
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 从文件中读取数据
   *
   * @param file
   * @return
   */
  public static byte[] readBytes(String file) {
    try {
      FileInputStream fis = new FileInputStream(file);
      int len = fis.available();
      byte[] buffer = new byte[len];
      fis.read(buffer);
      fis.close();
      return buffer;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  /**
   * 从文件中读取数据，返回类型是字符串String类型
   *
   * @param file 文件路径
   * @param charset 读取文件时使用的字符集，如utf-8、GBK等；
   * @return
   */
  public static String readString(String file, String charset) {
    byte[] data = readBytes(file);
    String ret = null;

    try {
      ret = new String(data, charset);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return ret;
  }

  /**
   * 读入文件
   *
   * @param file
   * @return
   */
  public static FileInputStream readStream(String file) {
    try {
      FileInputStream fis = new FileInputStream(file);
      return fis;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  /**
   * 向文件中写入数据
   *
   * @param filePath 目标文件全路径；
   * @param data 要写入的数据
   * @return 0表示成功写入 1表示没找到文件； 2表示IO异常
   */
  public static boolean writeBytes(String filePath, byte[] data) {
    try {
      FileOutputStream fos = new FileOutputStream(filePath);
      fos.write(data);
      fos.close();
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  /**
   * 向文件中写入字符串String类型的内容；
   *
   * @param file 文件路径
   * @param content 文件内容
   * @param charset 写入使用的字符集
   */
  public static void writeString(String file, String content, String charset) {
    try {
      byte[] data = content.getBytes(charset);
      writeBytes(file, data);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * 写入文件
   *
   * @param in
   * @param file
   */
  public static void writeFile(InputStream in, File file) throws IOException {
    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

    if (file != null && file.exists()) file.delete();

    FileOutputStream out = new FileOutputStream(file);
    byte[] buffer = new byte[1024 * 128];
    int len = -1;
    while ((len = in.read(buffer)) != -1) {
      out.write(buffer, 0, len);
    }
    out.flush();
    out.close();
    in.close();
  }

  ///获取文件大小
  public static int getFileSize(File file) {

    InputStream is = null;
    int size = 0;
    try {
      is = new FileInputStream(file);
      size = is.available();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return size;
  }

  /**
   * 读取File中的内容
   *
   * @param file 请务必保证file文件已经存在
   * @return file中的内容
   */
  public static String getText(File file) {
    if (!file.exists()) {
      return null;
    }
    StringBuilder text = new StringBuilder();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        text.append(line);
        text.append('\n');
      }

    } catch (IOException e) {
      Log.e(TAG, e.toString());
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return text.toString();
  }

  /**
   * get all fil
   *
   * @param path 要获取的根目录；
   */
  public static List<String> getFile(String path, List<String> list) {
    File file = new File(path);
    if (file.exists() == false) {
      return list;
    } else {
      if (file.isFile()) {
        list.add(file.getName());
        return list;
      }
      if (file.isDirectory()) {
        File[] childFile = file.listFiles();
        if (childFile == null || childFile.length == 0) {
          return list;
        }
        for (File f : childFile) {
          getFile(f.getAbsolutePath(), list);
        }
      }
    }

    return list;
  }

  /**
   * 移动文件
   *
   * @param srcFileName 源文件完整路径；
   * @param destDirName 目的目录完整路径
   * @return 文件移动成功返回true，否则返回false
   */
  public boolean moveFile(String srcFileName, String destDirName) {

    File srcFile = new File(srcFileName);
    if (!srcFile.exists() || !srcFile.isFile()) return false;

    File destDir = new File(destDirName);
    if (!destDir.exists()) destDir.mkdirs();

    String distPath = destDirName + File.separator + srcFile.getName();

    return srcFile.renameTo(new File(distPath));
  }

  /**
   * 删除文件和文件夹
   *
   * @param path 要删除的根目录；
   */
  public static void deleteFile(String path) {
    File file = new File(path);
    if (file.exists() == false) {
      return;
    } else {
      if (file.isFile()) {
        file.delete();
        return;
      }
      if (file.isDirectory()) {
        File[] childFile = file.listFiles();
        if (childFile == null || childFile.length == 0) {
          file.delete();
          return;
        }
        for (File f : childFile) {
          deleteFile(f.getAbsolutePath());
        }
        file.delete();
      }
    }
  }

  /**
   * 删除文件
   *
   * @param path
   */
  public static void delFile(String path) {
    if (path == null) return;

    File file = new File(path);
    if (file.exists()) {
      file.delete();
    }
  }

  /*
   * 通过Uri找到File
   *
   * @param context
   * @param uri
   * @return
   */
  public static File uri2File(Activity context, Uri uri) {
    File file;
    String[] project = {MediaStore.Images.Media.DATA};
    Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);
    if (actualImageCursor != null) {
      int actual_image_column_index =
          actualImageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      actualImageCursor.moveToFirst();
      String img_path = actualImageCursor.getString(actual_image_column_index);
      file = new File(img_path);
    } else {
      file = new File(uri.getPath());
    }
    if (actualImageCursor != null) actualImageCursor.close();
    return file;
  }
}
