package activity.commt4mtmandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import activity.commt4mtmandroid.R;


/**
 * 自定义dialog
 */
public class MyDialog extends Dialog {

    private Context context;
    private static MyDialog dialog;
    private ImageView ivProgress;


    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }
    //显示dialog的方法
    public static MyDialog showDialog(Context context){
        dialog = new MyDialog(context, R.style.MyDialog);//dialog样式
        dialog.setContentView(R.layout.layout_mydialog_view);//dialog布局文件
        dialog.setCanceledOnTouchOutside(true);//点击外部不允许关闭dialog
        return dialog;
    }

}
