package com.hoyotech.ctgames.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.viewdef.DynamicImage;
import com.hoyotech.ctgames.viewdef.LotteryView;
import com.hoyotech.ctgames.viewdef.RotateListener;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-3
 * Time: 下午7:39
 * To change this template use File | Settings | File Templates.
 */
public class BigWheelAwardActivity extends Activity implements RotateListener, View.OnClickListener {

    //文字提示
    private TextView title;
    //中奖显示
    private TextView info;
    //抽奖转盘
    private LotteryView lotteryView;
    //指针按钮
    private DynamicImage arrowBtn;

    private int[] itemColor;//选项颜色
    private String[] itemText;//选项文字
    private int[] allWheelAwardColors = new int[] {
            R.color.wheel_award_color1,
            R.color.wheel_award_color2,
            R.color.wheel_award_color3,
            R.color.wheel_award_color4,
            R.color.wheel_award_color5,
            R.color.wheel_award_color6,
            R.color.wheel_award_color7,
            R.color.wheel_award_color8,
            R.color.wheel_award_color9,
            R.color.wheel_award_color10}; // 转盘奖品的颜色

    public ArrayList<String> arrayList;
    private float surfacViewWidth = 0;
    private float surfacViewHeight = 0;

    private float radius;
    private boolean hasMeasured = false;

    private SoundPool soundPool = null;
    private int explosionId = 0;    //内存加载ID
    private int playSourceId = 0;    //播放ID

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actity_layout_big_wheel_award);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);


        final FrameLayout layout = (FrameLayout)findViewById(R.id.wheel_award);
        ViewTreeObserver vto = layout.getViewTreeObserver();


        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            public boolean onPreDraw()
            {
                if (hasMeasured == false)
                {
                    radius = findViewById(R.id.bg).getMeasuredWidth() / 2 * (200/211f);

                    //获取到宽度和高度后，可用于计算
                    hasMeasured = true;

                    initView();

                }
                return true;
            }
        });

    }

    protected void onResume() {
        super.onResume();
        if (soundPool == null) {
            //指定声音池的最大音频流数目为10，声音品质为5
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            //载入音频流，返回在池中的id
            explosionId = soundPool.load(this, R.raw.music, 1);
        }
    }

    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.stop(explosionId);
            soundPool.release();
            soundPool = null;
        }
        if (lotteryView != null) {
            lotteryView.rotateDisable();
        }
    }

    /**
     * Description:初始化界面元素
     */
    public void initView() {
        initItem();
        title = (TextView) this.findViewById(R.id.title);
        info = (TextView) this.findViewById(R.id.info);
        arrowBtn = (DynamicImage) this.findViewById(R.id.arrowBtn);
        lotteryView = (LotteryView) this.findViewById(R.id.lotteryView);

        arrowBtn.setOnClickListener(this);
        lotteryView.initAll(itemColor, itemText, radius);
        lotteryView.setRotateListener(this);
        lotteryView.start();

        surfacViewHeight = lotteryView.getHeight();
        surfacViewWidth = lotteryView.getWidth();

        Log.d("Log", "width = " + surfacViewWidth + ":height = " + surfacViewHeight);
    }

    /**
     * Description:初始化转盘的颜色，奖品的图片，文字等信息
     * 颜色和奖品文字一一对应
     * 在这个地方设置获奖的奖项
     */
    public void initItem() {

        // 图片和文字在之前已经获取到，直接拿来用
        // 转盘选项的颜色
        int totalAwardCount = 8;
        itemColor = new int[totalAwardCount];
        for (int i = 0; i < totalAwardCount; i++) {
            itemColor[i] = allWheelAwardColors[i % allWheelAwardColors.length];
        }

        // 转盘选项的名称
        itemText = new String[]{"恭喜发财", "智能手机", "5元话费", "2元话费", "1元话费", "恭喜发财"};
    }

    /**
     * Description:转盘开始旋转
     */
    public void begin(float speed, int group, boolean isRoating) {
        lotteryView.setDirection(speed, group, isRoating);
        lotteryView.rotateEnable();
        /*播放音频，第二个参数为左声道音量;第三个参数为右声道音量;
		  第四个参数为优先级；第五个参数为循环次数，0不循环，-1循环;
		  第六个参数为速率，速率    最低0.5最高为2，1代表正常速度  */
        playSourceId = soundPool.play(explosionId, 1, 1, 0, -1, 1);
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            info.setText((CharSequence) msg.obj);
            if (!lotteryView.isRotateEnabled()) {
                soundPool.stop(playSourceId);
                title.setText("恭喜您获得");
                arrowBtn.stopRotation();
            }
        }

        ;
    };

    @Override
    public void showEndRotate(String str) {
        Message msg = new Message();
        msg.obj = str;
        handler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {
        // 没有旋转状态
        if (!lotteryView.isRotateEnabled()) {
            title.setText("抽奖按钮变红时按下更容易中奖哦");

            // 在扣除了一定的金币之后就开始下载
            begin(Math.abs(50), 8, false);

            //arrowBtn.startRoation(new int[]{R.drawable.arrow_green, R.drawable.arrow_red}, 200);
        }
        //旋转状态
        else {
            //一直旋转状态
            if (!lotteryView.isRoating()) {

                //在这个地方设置获奖的奖项
                lotteryView.setAwards(0);

                //设置为缓慢停止
                lotteryView.setRoating(true);
                title.setText("");
            }
        }
    }
}