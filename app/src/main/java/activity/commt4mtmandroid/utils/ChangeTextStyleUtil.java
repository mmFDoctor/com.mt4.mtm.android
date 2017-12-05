package activity.commt4mtmandroid.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/9.
 */

public class ChangeTextStyleUtil {
    public static  void changeTextStyle(TextView textView, String str) {
        //小数点后只有两位 两位都大写显示 不显示上标
        SpannableString spannableString = new SpannableString(str);
        int i = str.length()-str.indexOf(".");
        if ((str.length()-str.indexOf("."))>3){
            //设置最后两位大显示
            if (str.length() > 3) {
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan1, spannableString.length() - 3, spannableString.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            //设置上标
            SuperscriptSpan superscriptSpan = new SuperscriptSpan();
            spannableString.setSpan(superscriptSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            //设置上标大小
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
            spannableString.setSpan(sizeSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        }else {
            if (str.length() > 3) {
                RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.5f);
                spannableString.setSpan(sizeSpan1, spannableString.length() - 2, spannableString.length() - 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString);
    }
}
