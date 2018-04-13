package ztml.dev.ngokhacbac.exampleplaymusicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private MediaPlayer mMediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG", "START SERVICE");
        mMediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "onCreate SERVICE");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cogaim52);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        Log.i("TAG", "STOP SERVICE");
    }
}
