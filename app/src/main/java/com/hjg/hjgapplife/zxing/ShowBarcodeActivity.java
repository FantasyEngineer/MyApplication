//package com.hjg.hjgapplife.zxing;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.zxing.WriterException;
//import com.yitong.android.activity.YTBaseActivity;
//import com.yitong.common.zxing.utils.BarcodeCreateUtil;
//import com.yitong.common.zxing.utils.TopBarManage;
//import com.yitong.logs.Logs;
//import com.yitong.mbank.app.R;
//
//public class ShowBarcodeActivity extends YTBaseActivity{
//	private final String TAG = this.getClass().getSimpleName();
//	private String message;
//	private String extraMessage;
//
//	ImageView mShowBarcodeImage;
//
//	private TopBarManage topBarManage;
//
//	public static void open(Context context,String message,String extraMessage){
//		Intent intent = new Intent(context,ShowBarcodeActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putString("message", message);
//		bundle.putString("extraMessage", extraMessage);
//		intent.putExtras(bundle);
//		context.startActivity(intent);
//	}
//
//	@Override
//	protected int getContentLayout() {
//		return R.layout.show_barcode;
//	}
//
//	@Override
//	protected void initGui() {
//		mShowBarcodeImage = (ImageView) findViewById(R.id.show_barcode_image);
//		View topBar = (View) findViewById(R.id.topBar);
//		if (topBar != null) {
//			topBarManage = new TopBarManage(this, topBar);
//			topBarManage.initTopBarTitle("二维码扫描");
//			topBarManage.setLeftButton("返回", true, new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					finish();
//				}
//			});
//		}
//	}
//
//	@Override
//	protected void initAction() {
//
//	}
//
//	@Override
//	protected void initData() {
//		message = getIntent().getExtras().getString("message");
//		extraMessage = getIntent().getExtras().getString("extraMessage");
//		createBarCode();
//	}
//
//	private void createBarCode(){
//		try {
//			Bitmap bit = getStringBitmap();
//			if(getStringBitmap() == null){
//				mShowBarcodeImage.setImageBitmap(BarcodeCreateUtil.createBarcode(message, 300,300));
//			}else{
//				mShowBarcodeImage.setImageBitmap(BarcodeCreateUtil.createBarcodeWithLogo(bit,message,300,300));
//			}
//		} catch (WriterException e) {
//			Logs.d(TAG, e.getMessage());
//		}
//	}
//
//	private Bitmap getStringBitmap(){
//		if("".equals(extraMessage)){
//			return null;
//		}
//		try{
//			View view = LayoutInflater.from(this).inflate(R.layout.barcode_center, null);
//			TextView textView = (TextView) view.findViewById(R.id.barcode_text);
//			textView.setText(extraMessage);
//			view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//	        view.buildDrawingCache();
//	        Bitmap bitmap = view.getDrawingCache();
//	        return bitmap;
//		}catch(Exception e){
//			Logs.d(TAG, e.getMessage());
//			return null;
//		}
//	}
//}
