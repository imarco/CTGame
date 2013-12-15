package com.hoyotech.ctgames.fragment;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.hoyotech.ctgames.R;
import com.hoyotech.ctgames.viewdef.DynamicImage;
import com.hoyotech.ctgames.viewdef.LotteryView;
import com.hoyotech.ctgames.viewdef.RotateListener;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-14
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class BigWheelFragment extends Fragment implements RotateListener, View.OnClickListener{

    // FIXME
    // 注意这个地方，设置一个转盘需要设置initItem()方法中的项目
    // 也就是 itemColor itemText和hitPercent
    // 其中itemText代表奖项的表项形式，不一定是text可以是图片也可以是两者的混合
    // hitPercent是每一项奖项的中奖概率，参见
    // itemText = new String[]{"土豪金", "幸运豆400", "幸运豆300", "幸运豆200", "幸运豆100", "谢谢参与"};
    // hitPercent = new int[] {0, 60, 60, 80, 100, 9700}; // 中奖概率分别为 0% 0.6% 0.8% 0.6% 0.4% 97% 加起来应该是1
    // 程序会根据这个概率数组自动抽奖
    // public void onClick(View v)方法中设置了开始是扣除幸运豆的操作，以及最后控制转盘停下来的位置

    // 抽奖转盘
    private LotteryView lotteryView;
    // 指针按钮
    private DynamicImage arrowBtn;
    // 指针
    private ImageView arrow;
    // bg
    private ImageView bg;

    private int[] itemColor;//选项颜色
    private String[] itemText;//选项文字
    private int[] itemImage;//选项文字
    private int[] hitPercent; // 命中所占的比重
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

    private int[] awardImageIds = new int[] {
            R.drawable.big_wheel_image_prize_samle,
            R.drawable.big_wheel_image_prize_samle,
            R.drawable.big_wheel_image_prize_samle,
            R.drawable.big_wheel_image_prize_samle,
            R.drawable.big_wheel_image_prize_samle,
            R.drawable.big_wheel_image_prize_samle };

    public ArrayList<String> arrayList;
    private float surfacViewWidth = 0;
    private float surfacViewHeight = 0;

    private SoundPool soundPool = null;
    private int explosionId = 0;    //内存加载ID
    private int playSourceId = 0;    //播放ID


    private boolean hasMeasured = false; // 用于监听已经开始度量事件
    private float radius; // 转盘的半径

    private RelativeLayout relativeLayout; // 整个页面的布局xml layout

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("in onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onPause() {
        System.out.println("in onPause");
        super.onPause();
        if (lotteryView != null) {
            lotteryView.rotateDisable();
        }
    }

    @Override
    public void onStop() {
        System.out.println("in onStop");
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroyView() {
        System.out.println("in onDestroyView");
        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
        hasMeasured = false;
        if (lotteryView != null) {
            lotteryView.rotateDisable();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("in onCreateView");
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_big_wheel, null);

        final FrameLayout layout = (FrameLayout) relativeLayout.findViewById(R.id.wheel_award);
        ViewTreeObserver vto = layout.getViewTreeObserver();

        // 设置按钮的转盘和元素是等比例的
        bg = (ImageView) relativeLayout.findViewById(R.id.bg);
        arrowBtn = (DynamicImage) relativeLayout.findViewById(R.id.arrowBtn);
        lotteryView = (LotteryView) relativeLayout.findViewById(R.id.lotteryView);
        arrow = (ImageView) relativeLayout.findViewById(R.id.arrow);

        int arrowWidth = arrow.getDrawable().getIntrinsicWidth();
        int arrowHeight = arrow.getDrawable().getIntrinsicHeight();
        arrow.getLayoutParams().width = (int)(arrowWidth * 286/410f);
        arrow.getLayoutParams().height = (int)(arrowHeight * 286/410f);

        arrowBtn.setOnClickListener(this);
        lotteryView.setRotateListener(this);

        // 监听可以measured的事件
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            public boolean onPreDraw()
            {

                if (!hasMeasured)
                {
                    initView();
                    hasMeasured = true;
                }
                return true;
            }
        });

        return relativeLayout;
    }


    /**
     * Description:初始化界面元素
     */
    public void initView() {
        initItem();
        radius = bg.getMeasuredWidth() / 2 * (360/410f);
        //获取到宽度和高度后，可用于计算

        lotteryView.initAll(itemColor, itemText, itemImage, radius, hitPercent);
        lotteryView.start();

        surfacViewHeight = lotteryView.getHeight();
        surfacViewWidth = lotteryView.getWidth();

    }

    /**
     * Description:初始化转盘的颜色，奖品的图片，文字，每一项的获奖概率等信息
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
        itemText = new String[]{"土豪金", "幸运豆400", "幸运豆300", "幸运豆200", "幸运豆100", "谢谢参与"};
        itemImage = awardImageIds;
        hitPercent = new int[] {0, 60, 60, 80, 100, 9700}; // 中奖概率分别为0% 0.6% 0.8% 0.6% 0.4% 97%加起来应该是1
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
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //info.setText((CharSequence) msg.obj);
            if (!lotteryView.isRotateEnabled()) {
                if(soundPool != null)
                    soundPool.stop(playSourceId);
                //title.setText("恭喜您获得");
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
            //title.setText("抽奖按钮变红时按下更容易中奖哦");

            // 在扣除了一定的金币之后就开始下载
            begin(Math.abs(50), 8, false);

            //arrowBtn.startRoation(new int[]{R.drawable.arrow_green, R.drawable.arrow_red}, 200);
        }
        //旋转状态
        else {
            //一直旋转状态
            if (!lotteryView.isRoating()) {

                //在这个地方设置获奖的奖项
                //lotteryView.setAwards(0);
                lotteryView.setAwardsByPercent(); // 根据奖项抽中的概率设置最终的获得项目

                //设置为缓慢停止
                lotteryView.setRoating(true);
                //title.setText("");
            }
        }
    }


}
