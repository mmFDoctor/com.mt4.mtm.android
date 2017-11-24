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
        SpannableString spannableString = new SpannableString(str);
        //设置最后两位大显示
        if (str.length()>3){
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.5f);
            spannableString.setSpan(sizeSpan1,spannableString.length()-3,spannableString.length()-1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        //设置上标
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        spannableString.setSpan(superscriptSpan,spannableString.length()-1,spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置上标大小
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan,spannableString.length()-1,spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
