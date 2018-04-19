package ztml.dev.ngokhacbac.exampleplaymusicservice.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.R;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.MyService;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;
import ztml.dev.ngokhacbac.exampleplaymusicservice.presenter.MainPresenter;

import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.Mediaplayer;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.adapter.AdapterListSongRecycler;

public class MainActivity extends AppCompatActivity implements MainView {
    private RecyclerView mRecycleListSong;
    private AdapterListSongRecycler mAdapterListSongRecycler;
    private MainPresenter mMainPresenter;
    private RemoteViews remoteView;
    public static  List<Song> listSong;
    private MyService mMyServicePlay;
    private boolean binded = false;
    private Notification mNotification;

    ServiceConnection weatherServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalPlayMusic ibinder = (MyService.LocalPlayMusic) service;
            mMyServicePlay = ibinder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Tạo đối tượng Intent cho WeatherService.
        Intent intent = new Intent(this, MyService.class);
        // Gọi method bindService(..) để giàng buộc dịch vụ với giao diện.
        this.bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleListSong = findViewById(R.id.recyclerViewSongs);
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.LoadData();

        mAdapterListSongRecycler = new AdapterListSongRecycler(listSong);
        mRecycleListSong.setAdapter(mAdapterListSongRecycler);
        mRecycleListSong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleListSong.scrollToPosition(Mediaplayer.getInstance(this).getPositionPlayingSong());
        mAdapterListSongRecycler.setOnItemClickListenner(new AdapterListSongRecycler.OnItemClickListenner() {
            @Override
            public void onClick(View itemView, final int position) {
                int toScrollPosition = mMyServicePlay.playMusicOnActivity(listSong, position, mAdapterListSongRecycler);
                if (toScrollPosition != -1) mRecycleListSong.scrollToPosition(toScrollPosition);
            }
        });
    }

    @Override
    public void displaySongsSucess(final List<Song> songs) {
        listSong = songs;
        Mediaplayer.getInstance(getApplicationContext()).setList(songs);
    }

    @Override
    public void displaySongsFail(String mess) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binded) {
            // Hủy giàng buộc kết nối với dịch vụ.
            this.unbindService(weatherServiceConnection);
            binded = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop");
        if (Mediaplayer.getInstance(getApplicationContext()).getSongPlaying() != null)
            mMyServicePlay.CustomNotification(listSong.get(Mediaplayer.getInstance(getApplicationContext()).getPositionPlayingSong()));
    }
}
