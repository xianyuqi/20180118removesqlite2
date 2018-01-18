package com.example.administrator.a20180118removesqlite;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private Button btn_get, btn_install;
    private static String DATABASE_PATH = "";
    private static String DATABASE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhleper=new DBHelper(MainActivity.this);
                dbhleper.openDatabase();
                Toast.makeText(MainActivity.this,"successful",Toast.LENGTH_SHORT).show();
            }
        });
        
        
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyDataBaseToSD();
            }
        });
    }


    private void init() {
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_install = (Button) findViewById(R.id.btn_install);

    }
    public static void copyDataBaseToSD(){
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return ;
        }

        File dbFile = new File(MvpApplication.getApplication().getDatabasePath("jokebook")+".db");
        File file  = new File(Environment.getExternalStorageDirectory(), "seeker.db");

        FileChannel inChannel = null,outChannel = null;

        try {
            file.createNewFile();
            inChannel = new FileInputStream(dbFile).getChannel();
            outChannel = new FileOutputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
           // LogUtils.e(TAG, "copy dataBase to SD error.");
            e.printStackTrace();
        }finally{
            try {
                if (inChannel != null) {
                    inChannel.close();
                    inChannel = null;
                }
                if(outChannel != null){
                    outChannel.close();
                    outChannel = null;
                }
            } catch (IOException e) {
               // LogUtils.e(TAG, "file close error.");
                e.printStackTrace();
            }
        }
    }



    public void createDatabase() {
        try {
            String databaseFilename = DATABASE_PATH + DATABASE_NAME;
            // TODO: 2018/1/18
            String rootDirectory = "";


            File dir = new File(rootDirectory);
            if (!dir.exists()) {
                dir.mkdir();
                if (!(new File(databaseFilename)).exists()) {
                    InputStream is = getResources().openRawResource(R.raw.jokebook);
                    FileOutputStream fos = new FileOutputStream(databaseFilename);
                    byte[] buffer = new byte[7168];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();

                }
            }
        } catch (Exception e) {

        }
    }


}

