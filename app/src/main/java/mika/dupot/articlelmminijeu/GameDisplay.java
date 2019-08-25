package mika.dupot.articlelmminijeu;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameDisplay extends SurfaceView implements SurfaceHolder.Callback ,Runnable {


    SurfaceHolder _holder;
    private float _targetX;
    private float _targetY;

    private float _currentX=0;
    private float _currentY=0;

    public GameDisplay(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        _holder = holder;

        Thread drawThread = new Thread(this, "Draw thread");
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {

        int pictureStep = 0;

        Bitmap tuxPicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.sprites_ball);
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;

        int pictureMove=(int)(10*scaledDensity);

        int pictureWidth = (int) (48 * scaledDensity);
        int pictureHeight = (int) (48 * scaledDensity);

        while (true) {


            Canvas canvas = _holder.lockCanvas();

            super.draw(canvas);


            if( _currentX < _targetX){
                _currentX+=pictureMove;
                if(_currentX>_targetX){
                    _currentX=_targetX;
                }
            }else if( _currentX > _targetX ){
                _currentX-=pictureMove;
                if(_currentX <_targetX){
                    _currentX=_targetX;
                }
            }

            if( _currentY < _targetY){
                _currentY+=pictureMove;
                if(_currentY>_targetY){
                    _currentY=_targetY;
                }
            }else if( _currentY > _targetY ){
                _currentY-=pictureMove;
                if(_currentY <_targetY){
                    _currentY=_targetY;
                }
            }

            int pictureX =  (int)_currentX;// 210;
            int pictureY =  (int)_currentY;//10;

            int spriteLeftShift = (pictureStep * pictureWidth);

            Rect pictureRect = new Rect(spriteLeftShift, 0, spriteLeftShift + pictureWidth, pictureHeight);

            pictureStep++;
            if (pictureStep > 4) {
                pictureStep = 0;
            }

            Rect drawRect = new Rect(pictureX, pictureY, (pictureX + pictureWidth), (pictureY + pictureHeight));

            canvas.drawBitmap(tuxPicture, pictureRect, drawRect, null);


            _holder.unlockCanvasAndPost(canvas);



            try {
                Thread.sleep((1000 / 10));
            } catch (InterruptedException e) {
                // ignore
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            _targetX  = event.getX();
            _targetY = event.getY();

        }

        return true;
    }

}
