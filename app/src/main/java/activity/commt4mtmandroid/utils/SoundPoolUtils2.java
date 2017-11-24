package activity.commt4mtmandroid.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import activity.commt4mtmandroid.R;


/**
 * Created by zhongwang on 2017/9/8.
 */

public class SoundPoolUtils2 {
    private SoundPool soundPool;

//    //单例模式
//    public static SoundPoolUtils2 getInstance(Context context) {
//        if (soundPoolUtil == null)
//            soundPoolUtil = new SoundPoolUtils2(context);
//        return soundPoolUtil;
//    }

    private SoundPoolUtils2(Context context) {
        soundPool = new SoundPool(3, AudioManager.STREAM_SYSTEM, 0);
        soundPool.load(context, R.raw.ok, 1);
        soundPool.load(context, R.raw.timeout, 1);
    }

    public void play(int number) {
        Log.d("tag", "number " + number);
        soundPool.play(number, 1, 1, 0, 0, 1);
    }
}
