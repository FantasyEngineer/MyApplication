package com.hjg.baseapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hjg.baseapp.R;


/**
 * Created by xiaohong.peng on 2017/5/24.
 * 自定义数字加减控件
 */

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = "AmountView";
    private int amount = 1; //购买数量
    private int goods_storage = 10; //商品库存

    private OnAmountChangeListener mListener;

    private EditText etAmount;
    private ImageButton btnDecrease;
    private ImageButton btnIncrease;
    private int minNum = 0;//最小值

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount_scenario, this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnDecrease = (ImageButton) findViewById(R.id.btnDecrease);
        btnIncrease = (ImageButton) findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
//            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
//            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }


    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "点击");
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            Log.d(TAG, "btnDecrease");
            if (amount > minNum) {
                amount--;
                Log.d(TAG, "amount:" + amount);
                etAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
            Log.d(TAG, "btnIncrease");
            if (amount < goods_storage) {
                amount++;
                Log.d(TAG, "amount:" + amount);
                etAmount.setText(amount + "");
            }
        }
        Log.d(TAG, "etAmount.getText():" + etAmount.getText());
        etAmount.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            etAmount.setText(goods_storage + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    //设置是否可输入
    public void setFocus(boolean en) {
        etAmount.setFocusable(en);
    }

    /*设置默认的数量*/
    public void setDefaultNum(int DefaultNum) {
        etAmount.setText(String.valueOf(DefaultNum));
    }

    /*设置最小的数量*/
    public void setMinNum(int num) {
        minNum = num;
    }
}
