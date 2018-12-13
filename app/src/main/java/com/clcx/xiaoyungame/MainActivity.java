package com.clcx.xiaoyungame;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private RelativeLayout activity_main;
    private List<User> lefts;
    private List<User> rights;
    private Button changeit;
    private Button falldown;
    private static final int[] ICS = {R.mipmap.ic1, R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap
            .ic_launcher};

    private static final int CENTER_MARGIN = 100;
    private static final int LEFT_MARGIN = 50;
    private static final int BOTTOM_MARGIN = 400;
    private static final int IMG_MARGIN = 8;
    private static final int CHANGE_ANIM_TIME = 400;//交换的动画时间
    private int imgSize = 0;
    private static final int NUMBER = 3;//一边放几个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = findViewById(R.id.activity_main);
        changeit = findViewById(R.id.changeit);
        falldown = findViewById(R.id.falldown);
        lefts = new LinkedList<>();
        rights = new LinkedList<>();
        //计算图片尺寸，一边NUMBER个来计算
        imgSize = (ws()[0] - CENTER_MARGIN - LEFT_MARGIN - LEFT_MARGIN - ((NUMBER * 2 + 2) * IMG_MARGIN)) / (NUMBER *
                2);
    }

    public void add(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int a = 0; a < 50; a++) {
                    final int count = a;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int rand = new Random().nextInt(2);
                            if (rand == 0) {
                                addimg(lefts, LEFT, count);
                            } else {
                                addimg(rights, RIGHT, count);
                            }

                        }
                    });
                    SystemClock.sleep(20);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeit.setEnabled(true);
                        falldown.setEnabled(true);
                    }
                });

            }
        }).start();
    }

    public void falldown(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int a=0;a<rights.size();a+=3){
                    final int acount=a;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fall(rights,acount);
                        }
                    });
                    SystemClock.sleep(200);
                }
                rights.clear();
            }
        }).start();
    }

    public void changeit(View v) {
        randomMove();
        fillBlank();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int a = 0; a < 20; a++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int a = 0; a < new Random().nextInt(8) + 2; a++) {
                                randomMove();
                            }

                        }
                    });
                    SystemClock.sleep(50 + new Random().nextInt(300));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fillBlank();
                    }
                });
            }
        }).start();
    }

    private void fall(List<User> list,int acount) {
        for (int b = 0; b < 3; b++) {
            if (acount > list.size() - 1) {
                break;
            }
            final User us = list.get(acount);
            ViewCompat.animate(us.getIv()).scaleX(0.1f).scaleY(0.5f).setDuration(CHANGE_ANIM_TIME).start();
            ViewCompat.animate(us.getTv()).scaleX(0.1f).scaleY(0.5f).setDuration(CHANGE_ANIM_TIME).setListener
                    (new ViewPropertyAnimatorListener() {


                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            activity_main.removeView(us.getIv());
                            activity_main.removeView(us.getTv());
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    }).start();
            acount++;
        }
    }

    private void randomMove() {
        int rand = new Random().nextInt(2);
        if (rand == 0) {
            if (lefts.size() > 0) {
                int randpo = new Random().nextInt(lefts.size());
                change(randpo, LEFT);
            }
        } else {
            if (rights.size() > 0) {
                int randpo1 = new Random().nextInt(rights.size());
                change(randpo1, RIGHT);
            }
        }
        fillBlank();
    }

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private void addimg(List<User> us, int leftright, int count) {
        int resid = ICS[new Random().nextInt(ICS.length)];
        ImageView iv = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(imgSize, imgSize);
        iv.setLayoutParams(params);
        iv.setImageResource(resid);

        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params1);
        tv.setTextSize(10);
        tv.setTextColor(Color.BLACK);

        activity_main.addView(iv);
        activity_main.addView(tv);
        int position = us.size();
        int x = 0;
        int y = 0;
        String name = "left" + count;
        int pos = position % NUMBER;
        if (leftright == LEFT) {
            x = LEFT_MARGIN + (IMG_MARGIN) + pos * (imgSize + IMG_MARGIN);
        } else {
            name = "right" + count;
            int rightx = LEFT_MARGIN + CENTER_MARGIN + NUMBER * (imgSize + IMG_MARGIN) + IMG_MARGIN;
            x = rightx + LEFT_MARGIN + (IMG_MARGIN) + pos * (imgSize + IMG_MARGIN) - imgSize / 2;
        }
        y = ws()[1] - ((position / NUMBER) * (imgSize + IMG_MARGIN) + BOTTOM_MARGIN);

        ViewCompat.animate(iv).translationX(x).translationY(y).setDuration(CHANGE_ANIM_TIME).start();
        ViewCompat.animate(tv).translationX(x).translationY(y).setDuration(CHANGE_ANIM_TIME).start();
        tv.setText(name);
        us.add(new User(name, resid, tv, iv, position, x, y));
    }

    private void change(int position, int leftright) {
        if (leftright == LEFT) {
            exchange(position, lefts, rights, LEFT);
        } else {
            exchange(position, rights, lefts, RIGHT);
        }
    }

    /**
     * l1 = LEFT:lefts RIGHT:rights
     * l2 反过来
     *
     * @param position
     * @param l1
     * @param l2
     */
    private void exchange(int position, List<User> l1, List<User> l2, int leftright) {
        User us = new User();
        int x = 0;
        int y = 0;
        boolean flag = false;
        User move = l1.get(position);
        if (!move.isNullposition()) {
            us.copy(move);
            for (int a = 0; a < l2.size(); a++) {
                if (l2.get(a).isNullposition()) {
                    x = l2.get(a).getaX();
                    y = l2.get(a).getaY();
                    us.setPosition(a);
                    l2.remove(a);
                    l2.add(a, us);
                    flag = true;
                }
            }
            if (!flag) {
                us.setPosition(l2.size());
                int rightx = 0;
                int margincount = 0;
                if (leftright == LEFT) {
                    rightx = LEFT_MARGIN + CENTER_MARGIN + NUMBER * (imgSize + IMG_MARGIN) + IMG_MARGIN;
                    margincount = imgSize / 2;
                }
                int pos = l2.size() % NUMBER;
                x = rightx + LEFT_MARGIN + (IMG_MARGIN) + pos * (imgSize + IMG_MARGIN) - margincount;
                y = ws()[1] - ((l2.size() / NUMBER) * (imgSize + IMG_MARGIN) + BOTTOM_MARGIN);
                l2.add(us);
            }
            move.setNullposition(true);
            //如果移动的是末尾，那么不用置空，直接删掉
            if (position == l1.size() - 1) {
                l1.remove(l1.size() - 1);
            }
        }
        us.setaX(x);
        us.setaY(y);
        ViewCompat.animate(us.getIv()).translationX(x).translationY(y).setDuration(CHANGE_ANIM_TIME)
                .start();
        ViewCompat.animate(us.getTv()).translationX(x).translationY(y).setDuration(CHANGE_ANIM_TIME)
                .start();
    }

    private int[] ws() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

    /**
     * 补位
     */
    private void fillBlank() {
        for (int a = 0; a < lefts.size(); a++) {
            if (lefts.get(a).isNullposition()) {
                lefts.get(a).resetUser(lefts.get(lefts.size() - 1));
                lefts.get(a).setNullposition(false);
                ViewCompat.animate(lefts.get(a).getIv()).translationX(lefts.get(a).getaX()).translationY(lefts.get(a)
                        .getaY()).setDuration(CHANGE_ANIM_TIME)
                        .start();
                ViewCompat.animate(lefts.get(a).getTv()).translationX(lefts.get(a).getaX()).translationY(lefts.get(a)
                        .getaY()).setDuration(CHANGE_ANIM_TIME)
                        .start();
                lefts.remove(lefts.size() - 1);
            }
        }
        for (int a = 0; a < rights.size(); a++) {
            if (rights.get(a).isNullposition()) {
                rights.get(a).resetUser(rights.get(rights.size() - 1));
                rights.get(a).setNullposition(false);
                ViewCompat.animate(rights.get(a).getIv()).translationX(rights.get(a).getaX()).translationY(rights.get
                        (a).getaY()).setDuration(CHANGE_ANIM_TIME)
                        .start();
                ViewCompat.animate(rights.get(a).getTv()).translationX(rights.get(a).getaX()).translationY(rights.get
                        (a).getaY()).setDuration(CHANGE_ANIM_TIME)
                        .start();
                rights.remove(rights.size() - 1);
            }
        }
    }
}
