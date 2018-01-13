package com.example.administrator.magiccube;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.GLText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActitvity extends AppCompatActivity {
    private Handler mHandler;
    private MagicCubeSurfaceView view;
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    private float moveUnitPixel = 20;
    private Map<Integer, List<Integer>> fingerMoveResult;
    private AutoRotateThread autoRotateThread;
    private RandomDisturbThread randomDisturbThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MagicCubeGlobalValue.screenWidth = dm.widthPixels;
        MagicCubeGlobalValue.screenHeight = dm.heightPixels;
        view = new MagicCubeSurfaceView(this);
        MagicCubeGlobalValue.stepCount = 0;
        setContentView(view);
        new RenderDispatchThread().start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MagicCubeGlobalValue.isUserRotating) {
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
            fingerMoveResult = MagicCube.getSingleInstance().getTouchInfo(x1, y1);
            if (fingerMoveResult == null || fingerMoveResult.size() == 0) {
                return true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (fingerMoveResult == null || fingerMoveResult.size() == 0) {
                return true;
            }
            if (fingerMoveResult.get(Const.AUTO_ROTATE_MSG) != null) {
                mHandler.obtainMessage(Const.AUTO_ROTATE_MSG, "").sendToTarget();
                return true;
            } else if (fingerMoveResult.get(Const.RANDOM_DISTURB_MSG) != null) {
                mHandler.obtainMessage(Const.RANDOM_DISTURB_MSG, "").sendToTarget();
                return true;
            }
            if (MagicCubeGlobalValue.isRotating) return true;
            x2 = event.getX();
            y2 = event.getY();
            float diff_y1y2 = y1 - y2;
            float diff_y2y1 = y2 - y1;
            float diff_x1x2 = x1 - x2;
            float diff_x2x1 = x2 - x1;
            if (diff_y1y2 > moveUnitPixel && diff_y1y2 >= Math.abs(diff_x1x2) && diff_y1y2 >= Math.abs(diff_x2x1)) {
                mHandler.obtainMessage(translateMoveMsgToRotateMsg(Const.UP, fingerMoveResult, diff_y1y2), "").sendToTarget();
            } else if (diff_y2y1 > moveUnitPixel && diff_y2y1 >= Math.abs(diff_x1x2) && diff_y2y1 >= Math.abs(diff_x2x1)) {
                mHandler.obtainMessage(translateMoveMsgToRotateMsg(Const.DOWN, fingerMoveResult, diff_y1y2), "").sendToTarget();
            } else if (diff_x1x2 > moveUnitPixel && diff_x1x2 >= Math.abs(diff_y1y2) && diff_x1x2 >= Math.abs(diff_y2y1)) {
                mHandler.obtainMessage(translateMoveMsgToRotateMsg(Const.LEFT, fingerMoveResult, diff_y1y2), "").sendToTarget();
            } else if (diff_x2x1 > moveUnitPixel && diff_x2x1 >= Math.abs(diff_y1y2) && diff_x2x1 >= Math.abs(diff_y2y1)) {
                mHandler.obtainMessage(translateMoveMsgToRotateMsg(Const.RIGHT, fingerMoveResult, diff_y1y2), "").sendToTarget();
            }
            return true;
        }
        return true;
    }

    public int translateMoveMsgToRotateMsg(int moveDirection, Map<Integer, List<Integer>> touchInfoMap, float diff_y1y2) {
        int retMsg = -1;
        int face = -1;
        if (touchInfoMap == null || touchInfoMap.size() == 0) {
            return retMsg;
        }
        List<Integer> faces = new ArrayList<>();
        for (Integer key : touchInfoMap.keySet()) {
            face = key;
            faces = touchInfoMap.get(key);
            break;
        }
        if (moveDirection == Const.UP || moveDirection == Const.DOWN) {
            if (face == Const.FRONT_FACE) {
                if (faces.contains(Const.RIGHT_FACE)) {
                    retMsg = moveDirection == Const.UP ? Const.RIGHT_FACE_CLOCKWISE : Const.RIGHT_FACE_ANTI_CLOCKWISE;
                } else if (faces.contains(Const.LEFT_FACE)) {
                    retMsg = moveDirection == Const.UP ? Const.LEFT_FACE_CLOCKWISE : Const.LEFT_FACE_ANTI_CLOCKWISE;
                } else {
                    retMsg = moveDirection == Const.UP ? Const.ROTATE_X_WHOLE_CLOCKWISE : Const.ROTATE_X_WHOLE_ANTI_CLOCKWISE;
                }
            } else if (face == Const.RIGHT_FACE) {
                if (faces.contains(Const.BACK_FACE)) {
                    retMsg = moveDirection == Const.UP ? Const.BACK_FACE_ANTI_CLOCKWISE : Const.BACK_FACE_CLOCKWISE;
                } else if (faces.contains(Const.FRONT_FACE)) {
                    retMsg = moveDirection == Const.UP ? Const.FRONT_FACE_ANTI_CLOCKWISE : Const.FRONT_FACE_CLOCKWISE;
                } else {
                    retMsg = moveDirection == Const.UP ? Const.ROTATE_Z_WHOLE_ANTI_CLOCKWISE : Const.ROTATE_Z_WHOLE_CLOCKWISE;
                }
            }
        } else if (moveDirection == Const.LEFT || moveDirection == Const.RIGHT) {
            if (face == Const.RIGHT_FACE || face == Const.FRONT_FACE) {
                if (faces.contains(Const.TOP_FACE)) {
                    retMsg = moveDirection == Const.RIGHT ? Const.TOP_FACE_ANTI_CLOCKWISE : Const.TOP_FACE_CLOCKWISE;
                } else if (faces.contains(Const.BOTTOM_FACE)) {
                    retMsg = moveDirection == Const.RIGHT ? Const.BOTTOM_FACE_ANTI_CLOCKWISE : Const.BOTTOM_FACE_CLOCKWISE;
                } else {
                    retMsg = moveDirection == Const.RIGHT ? Const.ROTATE_Y_WHOLE_ANTI_CLOCKWISE : Const.ROTATE_Y_WHOLE_CLOCKWISE;
                }
            } else if (face == Const.TOP_FACE) {
                if (diff_y1y2 > moveUnitPixel && moveDirection == Const.RIGHT) {
                    if (faces.contains(Const.RIGHT_FACE)) {
                        retMsg = Const.RIGHT_FACE_CLOCKWISE;
                    } else if (faces.contains(Const.LEFT_FACE)) {
                        retMsg = Const.LEFT_FACE_CLOCKWISE;
                    } else {
                        retMsg = Const.ROTATE_X_WHOLE_CLOCKWISE;
                    }
                } else if (diff_y1y2 < -moveUnitPixel && moveDirection == Const.LEFT) {
                    if (faces.contains(Const.RIGHT_FACE)) {
                        retMsg = Const.RIGHT_FACE_ANTI_CLOCKWISE;
                    } else if (faces.contains(Const.LEFT_FACE)) {
                        retMsg = Const.LEFT_FACE_ANTI_CLOCKWISE;
                    } else {
                        retMsg = Const.ROTATE_X_WHOLE_ANTI_CLOCKWISE;
                    }
                } else if (diff_y1y2 > moveUnitPixel && moveDirection == Const.LEFT) {
                    if (faces.contains(Const.FRONT_FACE)) {
                        retMsg = Const.FRONT_FACE_ANTI_CLOCKWISE;
                    } else if (faces.contains(Const.BACK_FACE)) {
                        retMsg = Const.BACK_FACE_ANTI_CLOCKWISE;
                    } else {
                        retMsg = Const.ROTATE_Z_WHOLE_ANTI_CLOCKWISE;
                    }
                } else if (diff_y1y2 < -moveUnitPixel && moveDirection == Const.RIGHT) {
                    if (faces.contains(Const.FRONT_FACE)) {
                        retMsg = Const.FRONT_FACE_CLOCKWISE;
                    } else if (faces.contains(Const.BACK_FACE)) {
                        retMsg = Const.BACK_FACE_CLOCKWISE;
                    } else {
                        retMsg = Const.ROTATE_Z_WHOLE_CLOCKWISE;
                    }
                }
            }
        }
        return retMsg;
    }

    public class RenderDispatchThread extends Thread {
        @Override
        public void run() {
            //建立消息循环的步骤
            Looper.prepare();//1、初始化Looper
            mHandler = new Handler() {//2、绑定handler到CustomThread实例的Looper对象
                public void handleMessage(Message msg) {//3、定义处理消息的方法
                    if (msg.what == Const.AUTO_ROTATE_MSG ) {
                        if (!MagicCubeGlobalValue.isAutoRotating&& !MagicCubeGlobalValue.isRandomDisturbing&&!isThreadRunning(randomDisturbThread)) {
                            if (autoRotateThread == null || !autoRotateThread.isAlive()) {
                                MagicCubeGlobalValue.setInfoBeforeAutoRotating();
                                autoRotateThread = new AutoRotateThread();
                                autoRotateThread.start();
                            }
                        } else {
                            MagicCubeGlobalValue.setInfoAfterAutoRotating();
                        }
                    } else if (msg.what == Const.RANDOM_DISTURB_MSG) {
                        if (!MagicCubeGlobalValue.isRandomDisturbing && !MagicCubeGlobalValue.isAutoRotating&&!isThreadRunning(autoRotateThread)) {
                            if (randomDisturbThread == null || !randomDisturbThread.isAlive()) {
                                MagicCubeGlobalValue.setInfoBeforeRandomDisturbing();
                                randomDisturbThread = new RandomDisturbThread();
                                randomDisturbThread.start();
                            }
                        } else {
                            MagicCubeGlobalValue.setInfoAfterRandomDisturbing();
                        }
                    } else if (!MagicCubeGlobalValue.isRotating&&!isThreadRunning(autoRotateThread)&&!isThreadRunning(randomDisturbThread)) {
                        MagicCubeGlobalValue.setInfoBeforeUserRotating();
                        userRotating(msg.what);
                        MagicCubeGlobalValue.setInfoAfterUserRotating();
                    }
                }
            };
            Looper.loop();//4、启动消息循环
        }
    }
    public boolean isThreadRunning(Thread thread){
        if(thread!=null&&thread.isAlive()) return true;
        return false;
    }
    public void userRotating(int rotateMsg) {
        MagicCubeGlobalValue.setInfoBeforeRotating();
        MagicCubeRotate.rotateAnimation(rotateMsg, view);
        MagicCube.getSingleInstance().drawCountStep();
        MagicCubeGlobalValue.setInfoAfterRotating();
        MagicCube.getSingleInstance().fillNextStepTipInfo(rotateMsg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MagicCubeGlobalValue.isRandomDisturbing = false;
        MagicCubeGlobalValue.isAutoRotating = false;
        MagicCubeGlobalValue.isRotating = false;
    }
}
