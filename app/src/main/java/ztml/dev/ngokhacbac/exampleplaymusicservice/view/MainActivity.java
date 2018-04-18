package ztml.dev.ngokhacbac.exampleplaymusicservice.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Random;

import ztml.dev.ngokhacbac.exampleplaymusicservice.R;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;
import ztml.dev.ngokhacbac.exampleplaymusicservice.presenter.MainPresenter;
import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.Mediaplayer;
import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.MediaplayerListenner;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.adapter.AdapterListSongRecycler;

public class MainActivity extends AppCompatActivity implements MainView {
    private RecyclerView mRecycleListSong;
    private AdapterListSongRecycler mAdapterListSongRecycler;
    private MainPresenter mMainPresenter;
    private RemoteViews remoteView;
    private List<Song> songxs;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleListSong = findViewById(R.id.recyclerViewSongs);
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.LoadData();
        Button button = findViewById(R.id.notifi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomNotification(songxs.get(0));

            }
        });

    }

    @Override
    public void displaySongsSucess(final List<Song> songs) {
        songxs = songs;
        mAdapterListSongRecycler = new AdapterListSongRecycler(songs);
        mRecycleListSong.setAdapter(mAdapterListSongRecycler);
        mRecycleListSong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleListSong.scrollToPosition(0);
        mAdapterListSongRecycler.setOnItemClickListenner(new AdapterListSongRecycler.OnItemClickListenner() {
            @Override
            public void onClick(View itemView, final int position) {

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
                        mRecycleListSong.scrollToPosition(positionNextSong);
                        mAdapterListSongRecycler.notifyItemChanged(positionNextSong);
                    }
                });
            }
        });
    }

    public void CustomNotification(Song song) {
        Log.i("TAG", "Notifi" + song.getmName() + "  " + song.getmAuthor());

        remoteView = new RemoteViews(getPackageName(), R.layout.notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)

                .setContent(remoteView)
                .setSmallIcon(R.mipmap.ic_play_song);
        remoteView.setImageViewResource(R.id.imageSongNotifi, song.getmImageAvatarSong());
        remoteView.setTextViewText(R.id.textViewSongNameNotifi, song.getmName());
        remoteView.setTextViewText(R.id.textViewAuthorNotifi, song.getmAuthor());

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationmanager.notify(0, notification);

        Log.i("TAG", "Notifi finish");
    }

    @Override
    public void displaySongsFail(String mess) {

    }

}
