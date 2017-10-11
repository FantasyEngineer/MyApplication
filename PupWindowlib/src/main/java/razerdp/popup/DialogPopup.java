package razerdp.popup;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import razerdp.basepopup.BasePopupWindow;
import razerdp.library.R;

/**
 * 客串一下dialog
 */
public class DialogPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView title;
    private TextView ok;
    private TextView cancel;
    private TextView content;

    public DialogPopup(Activity context) {
        super(context);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        setViewClickListener(this, ok, cancel);
        //不允许点击外部消失
        setDismissWhenTouchOuside(false);
        //不允许响应返回键
        setBackPressEnable(false);
        //全屏
        setPopupWindowFullScreen(true);


    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_dialog);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    public void setSingleBtn(String btnName, View.OnClickListener onClickListener) {
        if (!btnName.isEmpty()) {
            ok.setText(btnName);
        }
        ok.setOnClickListener(onClickListener);
        cancel.setVisibility(View.GONE);
    }

    public void setDoubleBtn(String leftbtnName, View.OnClickListener onleftClickListener, String rightbtnName, View.OnClickListener onrightClickListener) {
        ok.setText(leftbtnName);
        cancel.setText(rightbtnName);
        ok.setOnClickListener(onleftClickListener);
        cancel.setOnClickListener(onrightClickListener);
    }

    public void setTitleAndContent(String title1, String content1) {
        title.setText(title1);
        content.setText(content1);
    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.ok:
//                Toast.makeText(getContext(),"click the ok button", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.cancel:
//                Toast.makeText(getContext(),"click the cancel button", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }

    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss();
                return true;
            }

            return false;
        }
    };


}
