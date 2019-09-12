package com.cn.jyz.ironshoes.utils;


import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class LogCatLoger {
    private Context mContext = null;
    private String fileName = getNowDateTimeString();
    public LogCatLoger(Context ctx){
        mContext = ctx;
        deleteFolderFile(getRootPath());
    }
    public void doingLoger(){

        new Thread() {
            @Override
            public void run() {
                int pid = android.os.Process.myPid();

                ArrayList<String> getLog = new ArrayList<String>();
                getLog.add("logcat");
                getLog.add("-d");
                getLog.add("-v");
                getLog.add("time");
                getLog.add("|");
                getLog.add("grep");
                getLog.add(pid+"");

                ArrayList<String> clearLog = new ArrayList<String>();
                clearLog.add("logcat");
                clearLog.add("-c");
                int logCount = 0;
                String fName = fileName+"_"+logCount;
                FileOutputStream fos;
                while (true){
                    try {
                        Process process = Runtime.getRuntime().exec(getLog.toArray(new String[getLog.size()]));//抓取当前的缓存日志
                        BufferedReader buffRead = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取输入流
                        Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));//清除是为了下次抓取不会从头抓取
                        String str = null;
                        File logFile = new File(getRootPath()+fName+".txt");//打开文件
                        fos = new FileOutputStream(logFile,true);//true表示在写的时候在文件末尾追加
                        String newline = System.getProperty("line.separator");//换行的字符串

                        while((str=buffRead.readLine())!=null){//循环读取每一行
                            fos.write((str).getBytes());//加上年
                            fos.write(newline.getBytes());//换行
                            logCount++;

                            if(logCount%5000==0){//大于10000行就退出
                                Log.e("LogCatLog","rows:"+logCount);
                                uploadFile(fName+".txt");
                                fName = fileName+"_"+logCount;
                            }
                        }
                        fos.close();
                        fos = null;
                        Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public static String getNowDateTimeString() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return  dateString;
    }
    public static String getRootPath(){
        String rootPath = Environment.getExternalStorageDirectory()+ "/jyz/";
        File f = new File(rootPath);
        if(!f.exists()) f.mkdir();
        return rootPath;
    }
    public boolean uploadFile(String fileName) {
        File file = new File(getRootPath()+fileName);
        if(file==null) return false;

        RequestParams params = new RequestParams();
        try {
            params.put("txt", file, "application/octet-stream");
            SyncHttpClient client = new SyncHttpClient();
            client.setTimeout(60*1000);
            String url = "http://xw.huangmhd.com/Mobile/LogCat?fileName="+fileName;
            Log.e("uploadUrl", url);

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }
                @Override
                public void onFailure(int statusCode, Header[] headers,byte[] responseBody, Throwable error) {

                }
            });
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static void deleteFolderFile(String filePath){
        try {
            File file = new File(filePath);//获取SD卡指定路径
            File[] files = file.listFiles();//获取SD卡指定路径下的文件或者文件夹
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){//如果是文件直接删除
                    File photoFile = new File(files[i].getPath());
                    photoFile.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
