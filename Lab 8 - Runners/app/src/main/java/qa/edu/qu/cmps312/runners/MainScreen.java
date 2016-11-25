package qa.edu.qu.cmps312.runners;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MainScreen extends AppCompatActivity {

    private Handler mHandler = new Handler();
    public int[] imgIds = {R.drawable.s1, R.drawable.s2, R.drawable.s3,
            R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7, R.drawable.s8
    };
    ImageView mImgView;
    boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgView = (ImageView) findViewById(R.id.image_view);
    }

    public void clickMe(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.runnable_btn:
                stop = false;
                new Thread(new animate(handler))
                        .start();
                break;
            case R.id.async_btn:
                stop = false;
                new AnimateAsync().execute();
                break;
            case R.id.stop_btn:
                stop = true;
                break;
        }

    }

    static class UIHandler extends Handler {
        WeakReference<MainScreen> mParent;

        public UIHandler(WeakReference<MainScreen> parent) {
            mParent = parent;
        }

        @Override
        public void handleMessage(Message msg) {
            MainScreen parent = mParent.get();
            if (null != parent) {
                if (msg.what == 1)
                    parent.getImageView().setImageResource(parent.imgIds[(Integer) msg.obj]);
            }
        }
    }


    Handler handler = new UIHandler(new WeakReference<MainScreen>(
            this));


    private class animate implements Runnable {

        private final Handler handler;

        animate(Handler handler) {
            this.handler = handler;
        }

        public void run() {


            for (int i = 0; i < 1000; i++) {
                if (stop) break;

                Message msg = handler.obtainMessage(1, i % 8);
                handler.sendMessage(msg);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    class AnimateAsync extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++) {
                if (stop) break;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i % 8);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("TAG", String.valueOf(values[0]));
            int idx = values[0];
            mImgView.setImageResource(imgIds[idx]);
        }


    }

    public ImageView getImageView() {
        return mImgView;
    }

}

