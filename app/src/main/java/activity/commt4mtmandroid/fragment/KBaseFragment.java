package activity.commt4mtmandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by fangzhu
 */
public class KBaseFragment extends Fragment {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("isHidden")) {
                getFragmentManager().beginTransaction().hide(this).commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isHidden", isHidden());
    }

    /*仅仅作为 和 Apptabhost 一起使用 在关联的activity 中
    * 具体参考 MainAct  Apptabhost  使用的onFragmentVisible
    * */
    public void onFragmentVisible (boolean isVisible) {

    }

    public void initTitleButton(View rootView) {
//        Button btn_me = (Button) rootView.findViewById(R.id.btn_me);
//        if (btn_me != null) {
//            btn_me.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), SettingActivity.class);
//                    getActivity().startActivity(intent);
//                }
//            });
//        }
    }
}
