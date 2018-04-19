package ztml.dev.ngokhacbac.exampleplaymusicservice.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Random;

import ztml.dev.ngokhacbac.exampleplaymusicservice.R;
import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.Mediaplayer;
import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.MediaplayerListenner;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.MainActivity;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.adapter.AdapterListSongRecycler;

public class MyService extends Service {
    public final IBinder iBinder = new LocalPlayMusic();
    private Notification mNotification;
    public MyService() {

    }

    public class LocalPlayMusic extends Binder {
        public MyService getService() {

            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "onCreate SERVICE");

        //  mMediaPlayer
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("TAG", "STOP SERVICE");
    }

    public int playMusicOnActivity(final List<Song> songs, int position, final AdapterListSongRecycler mAdapterListSongRecycler) {
        final int[] positionToScroll = {-1};
        if (songs.get(position).isPlaying() == true) {
            songs.get(position).setPlaying(false);
            Mediaplayer.getInstance(getApplicationContext()).Pause();
            mAdapterListSongRecycler.notifyItemChanged(position);
        } else {

            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).isPlaying() == true) {
                    songs.get(i).setPlaying(false);
                    Mediaplayer.getInstance(getApplicationContext()).Pause();
                    mAdapterListSongRecycler.notifyItemChanged(i);
                }
            }
            songs.get(position).setPlaying(true);
            Mediaplayer.getInstance(getApplicationContext()).Create(songs.get(position));
            Mediaplayer.getInstance(getApplicationContext()).PlaySong(songs.get(position), position);

            //    mRecycleListSong.scrollToPosition(position);
        }
        mAdapterListSongRecycler.notifyItemChanged(position);
        Mediaplayer.getInstance(getApplicationContext()).setOnMediaListenner(new MediaplayerListenner() {
            @Override
            public void ProcessPlayCurrent(int durationSong, int currentDuration, int positionSong) {
                Log.i("TAG", currentDuration + "s + " + durationSong + "   " + positionSong);
                songs.get(positionSong).setDuration(durationSong);
                songs.get(positionSong).setCurrentDuration(currentDuration);
                mAdapterListSongRecycler.notifyItemChanged(positionSong);
            }

            @Override
            public void FinishSong(int positionSongFinish) {
                // Random song
                Mediaplayer.getInstance(getApplicationContext()).Pause();
                songs.get(positionSongFinish).setPlaying(false);
                songs.get(positionSongFinish).setCurrentDuration(0);
                mAdapterListSongRecycler.notifyItemChanged(positionSongFinish);
                int positionNextSong = (new Random()).nextInt(songs.size());
                songs.get(positionNextSong).setPlaying(true);
                Mediaplayer.getInstance(getApplicationContext()).Create(songs.get(positionNextSong));
                Mediaplayer.getInstance(getApplicationContext()).PlaySong(songs.get(positionNextSong), positionNextSong);
                //   mRecycleListSong.scrollToPosition(positionNextSong);
                positionToScroll[0] = positionNextSong;
                mAdapterListSongRecycler.notifyItemChanged(positionNextSong);
            }
        });
        return positionToScroll[0];
    }

    /**
     * Lưu ý :
     * - remote view không được set cứng size cho remote view trong xml
     * - setSmallIcon : với icon phải nhỏ
     */
    public void CustomNotification(Song song) {
        Log.i("TAG", "Notifi" + song.getmName() + "  " + song.getmAuthor());
        // set Intent to open app on notification click.
        Intent openAppIntent = new Intent(this, MainActivity.class);

        // call broadcast when any control of notification is clicked.
        //Intent closeNotification = new Intent("close_notification");
        Intent playPauseIntent = new Intent("play_pause");
        Intent previousInt = new Intent("previous_intent");
        Intent nextInt = new Intent("next_intent");

     //   PendingIntent pendingCloseIntent = PendingIntent.getBroadcast(this, 0, closeNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingPlayPauseIntent = PendingIntent.getBroadcast(this, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingPreviousIntent = PendingIntent.getBroadcast(this, 0, previousInt, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(this, 0, nextInt, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingOpenIntent = PendingIntent.getActivity(this, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentIntent(pendingOpenIntent)
                .setContent(remoteView);

        remoteView.setImageViewResource(R.id.imageSongNotifi, song.getmImageAvatarSong());
        remoteView.setTextViewText(R.id.textViewSongNameNotifi, song.getmName());
        remoteView.setTextViewText(R.id.textViewAuthorNotifi, song.getmAuthor());

        // Using RemoteViews to bind custom layouts into Notification

        remoteView.setOnClickPendingIntent(R.id.imageButtonPlayNotifi, pendingPlayPauseIntent);
        remoteView.setOnClickPendingIntent(R.id.imageButtonNextNotifi, pendingNextIntent);
        remoteView.setOnClickPendingIntent(R.id.imageButtonPreNotifi, pendingPreviousIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(0, notification);

        Log.i("TAG", "Notifi finish");
    }


}
