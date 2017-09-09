package cn.codekong.imageclassificationsystemclient.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件线程
 */
public class ZipMainThread extends Thread {
    private static final String TAG = "ZipMainThread";
    private String mSourceDir;
    private String mOutPath;

    private String mZipName;
    private ZipProgressUtil.ZipListener mZipListener;
    private int lastProgress = 0;

    public ZipMainThread(String sourceDir, String outPath, String zipName, ZipProgressUtil.ZipListener listener) {
        this.mSourceDir = sourceDir;
        this.mOutPath = outPath;
        this.mZipName = zipName;
        this.mZipListener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            mZipListener.zipStart();
            //获得要压缩文件夹下面的文件List
            File sourceFile = new File(mSourceDir);
            if (!sourceFile.exists()) {
                mZipListener.zipFail();
                return;
            }
            File outPathFile = new File(mOutPath);
            if (!outPathFile.exists()) {
                outPathFile.mkdirs();
            }
            if (sourceFile.isDirectory()) {
                File[] imgFiles = sourceFile.listFiles();
                File zipFile = new File(mOutPath + File.separator + mZipName);
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(zipFile));
                ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
                //表示处理进度
                int index = 1;
                int imgFilesSize = imgFiles.length;
                for (File anImgFile : imgFiles) {
                    index++;
                    ZipEntry zipEntry = new ZipEntry(anImgFile.getName());
                    zipOutputStream.putNextEntry(zipEntry);

                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(anImgFile));

                    byte[] b = new byte[1024];

                    while (bis.read(b, 0, 1024) != -1) {
                        zipOutputStream.write(b, 0, 1024);
                    }
                    bis.close();
                    zipOutputStream.closeEntry();
                    mZipListener.zipProgress(index / imgFilesSize * 100);
                }
                zipOutputStream.flush();
                zipOutputStream.close();
                mZipListener.zipSuccess();
            }
        } catch (Exception e) {
            mZipListener.zipFail();
        }
    }
} 