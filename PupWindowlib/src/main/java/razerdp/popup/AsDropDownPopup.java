package razerdp.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import razerdp.basepopup.BasePopupWindow;
import razerdp.library.R;
import razerdp.listener.OnItemListener;

/**
 * 展示在某view下在的list形式的pup
 */
public class AsDropDownPopup extends BasePopupWindow implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View popupView;
    private ListView asdown_list;
    private OnItemListener onItemListener;
    private ArrayList<String> dataList;
    Activity context;

    public AsDropDownPopup(Activity context, ArrayList<String> dataList) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;
        setAutoLocatePopup(true);
        this.dataList = dataList;
        bindEvent();
    }


    @Override
    protected Animation initShowAnimation() {
        return getDefaultAlphaAnimation();
    }


    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_asdown_list, null);
        return popupView;
    }

    @Override
    public void showPopupWindow(View v) {
        super.showPopupWindow(v);
        showOnDown(v);
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    private void bindEvent() {
        if (popupView != null) {
            asdown_list = (ListView) popupView.findViewById(R.id.asdown_list);
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.popup_asdown_list_item, dataList);
            asdown_list.setAdapter(arrayAdapter);
            asdown_list.setOnItemClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (onItemListener != null) {
            onItemListener.onItem(adapterView, view, i, l);
        }
    }

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
