package mycanera.zdl.com.mycamera;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 失败的封装 相机和 popupWindow 无法连在一起
 * @author dell-pc
 * @date 2018/6/28
 */

public class CameraUtils {
    /**
     *
     * @param contentView  第一个参数是在你调用的那个界面声明一个View
     * @param window  声明一个PopupWindow
     * @param window  上下文对象
     * @param textView  你的TextView 文本
     * @param button  你的按钮
     * 没有为null
     * */
    public static void getCameraUtils(Context context , View contentView,PopupWindow window,  TextView textView, Button button) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_popuwindow, null, false);

        //创建PopupWindow对象，其中：
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
}
