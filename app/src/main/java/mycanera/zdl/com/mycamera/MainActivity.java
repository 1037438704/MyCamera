package mycanera.zdl.com.mycamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.FileNotFoundException;

import static android.media.MediaRecorder.VideoSource.CAMERA;

/**
 * @author dell-pc
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICTURE = 200;
    private Button button;
    private ImageView imagView;
    private PopupWindow window;
    private View contentView;
    private TextView text_camera;
    private TextView text_photo;
    private TextView text_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 用于PopupWindow的View
        contentView = LayoutInflater.from(this).inflate(R.layout.layout_popuwindow, null, false);
        initView();
        initData();
    }

    private void initView() {
        button = findViewById(R.id.button);
        imagView = findViewById(R.id.imagView);

        text_camera = contentView.findViewById(R.id.text_camera);
        text_photo = contentView.findViewById(R.id.text_photo);
        text_finish = contentView.findViewById(R.id.text_finish);

        text_finish.setOnClickListener(this);
        text_photo.setOnClickListener(this);
        text_camera.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void initData() {
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击popupWindow外是否响应
        window.setFocusable(false);
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(v, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                lightoff();
                break;
                //调用相机
            case R.id.text_camera:
                windowCamera();
                window.dismiss();
                lighton();
                break;
                //调用相册
            case R.id.text_photo:
                windowPhoto();
                break;
                //取消
            case R.id.text_finish:
                window.dismiss();
                lighton();
                break;
            default:
        }
    }

    private void windowCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA);
    }

    private void windowPhoto() {
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, PICTURE);

        window.dismiss();
        lighton();
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            // 拍照
            Bundle bundle = data.getExtras();
            // 获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            Glide.with(this).load(bitmap).apply(new RequestOptions().circleCrop()).into(imagView);

        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            /**
             * 调用图库
             */
            Uri selectedImage = data.getData();
            Glide.with(this).load(selectedImage).apply(new RequestOptions().circleCrop()).into(imagView);
        }
    }
}
