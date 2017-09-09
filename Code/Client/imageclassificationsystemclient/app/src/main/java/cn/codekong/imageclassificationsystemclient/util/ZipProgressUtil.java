package cn.codekong.imageclassificationsystemclient.util;

public class ZipProgressUtil {
    /***
     * 解压通用方法
     *
     * @param zipFileString
     *      文件路径
     * @param outPathString
     *      解压路径
     * @param listener
     *      加压监听
     */
    public static void UnZipFile(final String zipFileString, final String outPathString, final ZipListener listener) {
        Thread unZipMainThread = new UnZipMainThread(zipFileString, outPathString, listener);
        unZipMainThread.start();
    }

    public static void ZipFile(String sourceDir, String outPath, String zipName, ZipListener zipListener){
        Thread zipMainThread = new ZipMainThread(sourceDir, outPath, zipName, zipListener);
        zipMainThread.start();
    }

    public interface ZipListener {
        /**
         * 开始解压
         */
        void zipStart();

        /**
         * 解压成功
         */
        void zipSuccess();

        /**
         * 解压进度
         */
        void zipProgress(int progress);

        /**
         * 解压失败
         */
        void zipFail();
    }
} 