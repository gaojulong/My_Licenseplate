package cn.lovelqq.julong.my_licenseplate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private static final String TAG="MainActivity";
    private static final String LANGUAGE = "eng";
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        bitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.c7);
        imageView.setImageBitmap(bitmap);





    }

    private void init() {
        imageView=findViewById(android.support.v7.appcompat.R.id.image);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.tv);

        button.setOnClickListener(this);
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
        }
        return sdDir.toString();
    }

    public String doOcr(Bitmap bitmap, String language) {
        TessBaseAPI baseApi = new TessBaseAPI();

        baseApi.init(getSDPath(), language);
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"); // 识别白名单
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?"); // 识别黑名单
//        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);//设置识别模式

        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        baseApi.setImage(bitmap);

        String text = baseApi.getUTF8Text();
        Log.e(TAG,text);

        baseApi.clear();
        baseApi.end();

        return text;
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()) {
        case R.id.button:
           textView.setText( doOcr(bitmap,LANGUAGE));
            break;

    }
    }
}
