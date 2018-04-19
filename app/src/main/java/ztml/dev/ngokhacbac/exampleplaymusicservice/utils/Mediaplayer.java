package ztml.dev.ngokhacbac.exampleplaymusicservice.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;


public class Mediaplayer {
    private Context context;
    private static MediaPlayer mediaPlayer;
    private MediaplayerListenner mediaplayerListenner;
    private static Mediaplayer INSTANCE = null;
    private PlayDuration playDuration;
    private Song songPlaying;
    private int positionPlayingSong = 0;
    private List<Song> list;
    public Mediaplayer(Context context) {
        this.context = context;
    }

    public List<Song> getList() {
        return list;
    }

    public void setList(List<Song> list) {
        this.list = list;
    }

    public void setOnMediaListenner(MediaplayerListenner mediaplayerListenner) {
        this.mediaplayerListenner = mediaplayerListenner;
    }

    //
    public static Mediaplayer getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Mediaplayer(context);
        }
        return INSTANCE;
    }

    public void Create(Song song) {
        mediaPlayer = MediaPlayer.create(context, song.getmSongRaw());
    }

    // Tua bài hát (phát tiếp bài hát từ vị trí pos trở đi)
    public void fastForward(int pos) {
        mediaPlayer.seekTo(pos);
        mediaPlayer.start();
    }

    public void PlaySong(Song song, int position) {
        songPlaying = song;
        positionPlayingSong = position;
        if (song.getCurrentDuration() != 0) {
            fastForward(song.getCurrentDuration());
        } else {
            mediaPlayer.start();
            mediaPlayer.getCurrentPosition();
            song.setCurrentDuration(0);
        }

        playDuration = new PlayDuration();
        /**
         * @param 0 : lấy tổng thời gian của bài hát
         * @param 1 : lấy thời gian bắt đầu phát
         * @param 2 : position của bài hát
         * */
        playDuration.execute(mediaPlayer.getDuration(), song.getCurrentDuration(), position);

    }

    public int getPositionPlayingSong() {
        return positionPlayingSong;
    }

    public Song getSongPlaying() {
        return songPlaying;
    }

    public void setSongPlaying(Song song) {
        this.songPlaying = song;
    }

    public void Pause() {
        mediaPlayer.pause();
        playDuration.cancel(true);
    }

    class PlayDuration extends AsyncTask<Integer, Integer, Integer> {
        public PlayDuration() {
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Log.i("TAG", "  playing" + integers[0]);
            int duration = integers[1];
            while (duration < integers[0]) {
                if (isCancelled()) return null;
                SystemClock.sleep(100);
                publishProgress(duration += 100, integers[0], integers[2]);
            }
            return integers[2];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("TAG", "  play");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mediaplayerListenner.ProcessPlayCurrent(values[1], values[0], values[2]);
            //Log.i("TAG", values[1] + "s  222");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            this.cancel(true);
            mediaplayerListenner.FinishSong(integer);
            setSongPlaying(null);
            Log.i("TAG", "  finish");
        }
    }
}
