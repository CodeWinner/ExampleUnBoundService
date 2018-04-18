package ztml.dev.ngokhacbac.exampleplaymusicservice.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.adapter.AdapterListSongRecycler;

public class Mediaplayer {
    private Context context;
    private static MediaPlayer mediaPlayer;
    private MediaplayerListenner mediaplayerListenner;
    private static Mediaplayer INSTANCE = null;
    private PlayDuration playDuration;

    public Mediaplayer(Context context) {
        this.context = context;
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

    public void PlaySong(Song song, int position) {
        mediaPlayer.start();
        mediaPlayer.getCurrentPosition();
        song.setCurrentDuration(0);
        playDuration = new PlayDuration();
        playDuration.execute(mediaPlayer.getDuration(), position);

    }

    public void Pause() {
        mediaPlayer.release();
        playDuration.cancel(true);
    }

    class PlayDuration extends AsyncTask<Integer, Integer, Integer> {
        public PlayDuration() {
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Log.i("TAG", "  playing" + integers[0]);
            int duration = 0;
            while (duration < integers[0]) {
                if (isCancelled()) return null;
                SystemClock.sleep(100);
                publishProgress(duration += 100, integers[0], integers[1]);
            }
            return  integers[1];
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
            Log.i("TAG", "  finish");
        }
    }
}
