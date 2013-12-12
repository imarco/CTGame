package com.hoyotech.ctgames.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hoyotech.ctgames.http.AndroidHttpClient;
import com.hoyotech.ctgames.util.CTGameConstans;
import com.hoyotech.ctgames.util.NetworkUtils;
import com.hoyotech.ctgames.util.StorageUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载任务管理<br/>
 * Notice： 点击下载按钮或者继续下载按钮之后，首先判断网络是否连接，
 * 并检查是否是电信3G连接方式，如果不是，需要进行提示后再开始下载
 * Created by GGCoke on 13-12-6.
 */
public class DownloadTask extends AsyncTask<Void, Integer, Long> {
    public static final String ACTION_DOWNLOAD = "com.hoyotech.ctgames.service.DownloadTask";
    private static final String TAG = DownloadTask.class.getSimpleName();
    private static final String TEMP_SUFFIX = ".download";
    private long id;
    private Context context;
    private String url;    // 下载文件url
    private File file;     // 下载目的文件
    private File tmpFile;  // 下载临时文件
    private boolean downloadOnly3G;// 是否只允许3G下载

    private URL srcURL;
    private RandomAccessFile outputStream;
    private DownloadTaskListener listener;

    private long downloadSize;          // 本次更新时下载大小
    private long previousFileSize;      // 已下载大小
    private long totalSize;             // 目标文件大小
    private long downloadPercent;       // 下载进度
    private long networkSpeed;          // 下载速度
    private long previousTime;          // 开始下载时间
    private long totalTime;             // 下载所用时间
    private Throwable error = null;     // 下载发生的异常
    private boolean interrupt = false;  // 下载是否暂停

    private AndroidHttpClient client;
    private HttpGet httpGet;
    private HttpResponse response;

    private boolean is3G;

    public DownloadTask(Context context, String url, String path, boolean downloadOnly3G) throws MalformedURLException {
        this(context, url, path, downloadOnly3G, null);
    }

    public DownloadTask(Context context, String url, String path, boolean downloadOnly3G, DownloadTaskListener listener) throws MalformedURLException {
        super();
        this.id = System.currentTimeMillis();
        this.context = context;
        this.url = url;
        this.srcURL = new URL(url);
        String fileName = new File(srcURL.getFile()).getName();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.file = new File(path, fileName);
        this.tmpFile = new File(path, fileName + TEMP_SUFFIX);
        this.downloadOnly3G = downloadOnly3G;
        this.listener = listener;
    }

    public long getId() { return id; }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

    public File getTmpFile() {
        return tmpFile;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public long getDownloadPercent() {
        return downloadPercent;
    }

    public long getDownloadSize() {
        return downloadSize + previousFileSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDownloadSpeed() {
        return this.networkSpeed;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public void setDownloadOnly3G(boolean downloadOnly3G) {
        this.downloadOnly3G = downloadOnly3G;
    }

    public void setListener(DownloadTaskListener listener) {
        this.listener = listener;
    }

    public DownloadTaskListener getListener() {
        return this.listener;
    }

    @Override
    protected void onPreExecute() {
        previousTime = System.currentTimeMillis();
        if (null != listener) {
            listener.preDownload(this);
        }
    }


    @Override
    protected Long doInBackground(Void... voids) {
        long result = -1;
        try {
            result = download();
        } catch (Exception e) {
            this.error = e;
        } finally {
            if (null != client) {
                client.close();
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (progress.length > 1) {
            totalSize = progress[1];
            if (-1 == totalSize) {
                // 下载发生异常
                if (null != listener) {
                    // 在listener的error msg里判断，如果是DOWNLOAD_ERROR_WITH_WIFI，
                    // 需要暂停下载，提示用户切换到3G
                    listener.errorDownload(this, error);
                }
            }
        } else {
            totalTime = System.currentTimeMillis() - previousTime;
            downloadSize = progress[0];
            downloadPercent = (downloadSize + previousFileSize) * 100 / totalSize;
            networkSpeed = downloadSize / totalTime;
            if (listener != null)
                listener.updateProcess(this);
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        if (-1 == result || null != error) {
            // 下载中发生错误
            if (CTGameConstans.DEBUG) {
                Log.e(TAG, "Download failed: " + (null == error ? "" : error.getMessage()));
            }

            if (null != listener) {
                listener.errorDownload(this, error);
            }
            return;
        }

        if (!interrupt) {
            // 下载成功，将临时文件重命名为正式文件
            tmpFile.renameTo(file);
            if (null != listener) {
                listener.finishDownload(this);
            }
        }
    }

    @Override
    public void onCancelled() {
        super.onCancelled();
        interrupt = true;
    }

    private long download() throws Exception {
        if (CTGameConstans.DEBUG) {
            Log.i(TAG, "Start to download " + url);
        }

        if (!NetworkUtils.isNetworkAvailable(context)) {
            throw new Exception(String.valueOf(CTGameConstans.DOWNLOAD_ERROR_NO_NETWORK));
        }

        if (!NetworkUtils.is3GNetwork(context) && downloadOnly3G) {
            throw new Exception(String.valueOf(CTGameConstans.DOWNLOAD_ERROR_WITH_WIFI));
        }

        client = AndroidHttpClient.newInstance("CTGame.DownloadTask");
        httpGet = new HttpGet(url);
        response = client.execute(httpGet);
        totalSize = response.getEntity().getContentLength();

        if (file.exists() && totalSize == file.length()) {
            // 文件已经存在，并且是同一个版本，删掉之后重新下载
            if (CTGameConstans.DEBUG) {
                Log.i(TAG, "File already exists. Skipped downloading file");
            }
            file.delete();
        } else if (tmpFile.exists()) {
            // 已经下载了一部分，续传
            client.close();
            client = AndroidHttpClient.newInstance("CTGame.DownloadTask");
            httpGet.addHeader("Range", "bytes=" + tmpFile.length() + "-");
            previousFileSize = tmpFile.length();
            response = client.execute(httpGet);

            if (CTGameConstans.DEBUG) {
                Log.i(TAG, "File is not complete, go on downloading");
            }
        }

        long availableStorage = StorageUtils.getAvailableStorage();
        if (CTGameConstans.DEBUG) {
            Log.i(TAG, "Avaiable storage: " + availableStorage + ". File size: " + totalSize);
        }

        if (totalSize - tmpFile.length() > availableStorage) {
            if (CTGameConstans.DEBUG) {
                Log.i(TAG, "No enough storage. Avaiable storage: " + availableStorage + ". Reauired storage: " + (totalSize - tmpFile.length()));
            }
            throw new Exception(String.valueOf(CTGameConstans.DOWNLOAD_ERROR_NO_MEMORY));
        }

        outputStream = new ProgressReportingRandomAccessFile(tmpFile, "rw");
        publishProgress(0, (int)totalSize);
        InputStream input = response.getEntity().getContent();
        return copy(input, outputStream);
    }

    public int copy(InputStream in, RandomAccessFile out) throws Exception {
        if (null == in || null == out) {
            return -1;
        }

        byte[] buffer = new byte[CTGameConstans.DOWNLOAD_BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(in, CTGameConstans.DOWNLOAD_BUFFER_SIZE);
        if (CTGameConstans.DEBUG) {
            Log.v(TAG, "Downloaded size: " + out.length());
        }

        int count = 0, n = 0;
        long errorBlockTimePreviousTime = -1, expireTime = 0;
        try {
            out.seek(out.length());
            while (!interrupt) {
                n = bis.read(buffer, 0, CTGameConstans.DOWNLOAD_BUFFER_SIZE);
                if (-1 == n) {
                    break;
                }
                out.write(buffer, 0, n);
                count += n;

                if (0 == networkSpeed) {
                    // 下载速度为0
                    if (errorBlockTimePreviousTime > 0) {
                        expireTime = System.currentTimeMillis() - errorBlockTimePreviousTime;
                        if (expireTime > CTGameConstans.CONNECTION_TIMEOUT) {
                            throw new Exception(String.valueOf(CTGameConstans.DOWNLOAD_ERROR_CONNECTION_TIMEOUT));
                        }
                    } else {
                        errorBlockTimePreviousTime = System.currentTimeMillis();
                    }
                } else {
                    expireTime = 0;
                    errorBlockTimePreviousTime = -1;
                }
            }
        } finally {
            client.close();;
            client = null;
            out.close();
            bis.close();
            in.close();
        }

        return count;
    }

    private final class ProgressReportingRandomAccessFile extends RandomAccessFile {
        private int progress = 0;
        public ProgressReportingRandomAccessFile(File file, String mode) throws FileNotFoundException {
            super(file, mode);
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {
            super.write(buffer, offset, count);
            progress += count;
            publishProgress(progress);
        }
    }
}
