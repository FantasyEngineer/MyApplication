package com.hjg.hjgapplife.activity.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.hjg.baseapp.widget.dialog.BottomDialog;
import com.hjg.hjgapplife.activity.baseRender.BaseOtherRenderListActivity;

public class DialogMainActivity extends BaseOtherRenderListActivity {
    String content = "    韩雪，1983年1月11日出生于江苏省苏州市姑苏区，中国内地女演员、歌手、影视制作人，毕业于上海戏剧学院表演系。2000年，韩雪参加并获得香港嘉禾影视公司主办的“世纪之星”影视歌新人大赛全国金奖，随后出演其首部大荧幕作品《浪漫樱花》，同年，韩雪以文化考试第一名的成绩被上海戏剧学院表演系录取；2003年12月，因在古装武侠剧《飞刀又见飞刀》中扮演冷小星而受到关注；2004年10月15日，发行个人首张音乐专辑《飘雪》，正式开始乐坛旅程，并凭借其获得东方风云榜年度最佳新人奖;\n" +
            "    2007年2月，韩雪以歌手的身份首度登上春晚舞台，独唱歌曲《竹林风》[9]  ；2008年，韩雪第二次登上春晚舞台，表演小品《街头卫士》[10]  ；2009年年底成立韩雪工作室[11]  ；2010年，韩雪第三次登上春晚舞台并出演语言类节目《不能让他走》[12]  ；2015年2月，凭借抗战剧《冲出月亮岛》获得江苏电视剧颁奖典礼年度最佳女演员奖[13]  ；2016年8月5日，主演灵异惊悚电影《古曼》上映[14]  。";

    @Override
    protected void initTitle() {
        topBarManage.iniTop(true, "Dialog展示");
    }

    @Override
    public void initDataList() {
        dataList.add("从底部弹出的dialog，支持下拉消失");
        dataList.add("NormalDialog");
        dataList.add("MaterialDialog");
        dataList.add("ActionSheetDialog");
    }

    @Override
    public void OnItemClick(int i) {
        switch (i) {
            case 0:
                BottomDialog bottomDialog = new BottomDialog(activity);
                bottomDialog.showDialog(content);
                break;
            case 1:
                NormalDialog normalDialog = new NormalDialog(activity);
                normalDialog.content("这是一个dialog");
                normalDialog.btnNum(2);//控制按钮数量
                normalDialog.setTitle("这是一个温馨提示");
                normalDialog.btnText("按钮左", "按钮右");//控制按钮名称
                normalDialog.setOnBtnClickL(() -> {//控制按钮事件
                    Toast.makeText(activity, "按钮左点击了", Toast.LENGTH_SHORT).show();
                    normalDialog.dismiss();
                }, () -> {
                    Toast.makeText(activity, "按钮右点击了", Toast.LENGTH_SHORT).show();
                    normalDialog.dismiss();
                });
                normalDialog.setCanceledOnTouchOutside(false);
                normalDialog.show();
                break;
            case 2:
                MaterialDialog materialDialog = new MaterialDialog(activity);
                materialDialog.btnText("确定").btnNum(1).setOnBtnClickL(() -> {
                    Toast.makeText(activity, "按钮点击了", Toast.LENGTH_SHORT).show();
                    materialDialog.dismiss();
                });
                materialDialog.content("这是个materialDialog");
                materialDialog.show();
                break;
            case 3:
                String[] stringItems = {"CUSTOM", "ALPHA", "SCALE", "SLIDE_BOTTOM"};
                ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
                dialog.isTitleShow(false);
                dialog.setOnOperItemClickL((parent, view, position, id) -> {
                    Toast.makeText(activity, stringItems[position], Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                dialog.show();
        }
    }
}
