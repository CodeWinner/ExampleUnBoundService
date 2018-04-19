package ztml.dev.ngokhacbac.exampleplaymusicservice.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ztml.dev.ngokhacbac.exampleplaymusicservice.utils.Mediaplayer;

public class Notification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case "play_pause":
              // if (Mediaplayer.getInstance(context).getSongPlaying().getCurrentDuration() != 0)
                    Mediaplayer.getInstance(context).Pause();
              //      Mediaplayer.getInstance(context).fastForward(Mediaplayer.getInstance(context).getSongPlaying().getCurrentDuration());
                //   notifiBroadCastListenner.pauseSong(Mediaplayer.getInstance(context).getPositionPlayingSong());
                Log.i("XXX", "play_pause");

                break;
            case "previous_intent":
                //   notifiBroadCastListenner.preSong(Mediaplayer.getInstance(context).getPositionPlayingSong());
                int prePosition = 0;
                if (Mediaplayer.getInstance(context).getPositionPlayingSong() == 0)
                    prePosition = Mediaplayer.getInstance(context).getList().size()-1;
                else if (Mediaplayer.getInstance(context).getPositionPlayingSong() == Mediaplayer.getInstance(context).getList().size())
                    prePosition = 0;
                else
                    prePosition = Mediaplayer.getInstance(context).getPositionPlayingSong() - 1;
                Mediaplayer.getInstance(context).PlaySong(Mediaplayer.getInstance(context).getList().get(prePosition), prePosition);
                Log.i("XXX", "previous_intent"+prePosition);
                break;
            case "next_intent":
//                int nextPosition = 0;
//                if (Mediaplayer.getInstance(context).getPositionPlayingSong() == 0)
//                    nextPosition = Mediaplayer.getInstance(context).getList().size()-1;
//                else if (Mediaplayer.getInstance(context).getPositionPlayingSong() == Mediaplayer.getInstance(context).getList().size())
//                    nextPosition = 0;
//                else
//                    nextPosition = Mediaplayer.getInstance(context).getPositionPlayingSong() - 1;
//                Mediaplayer.getInstance(context).PlaySong(Mediaplayer.getInstance(context).getList().get(nextPosition), nextPosition);
//                Log.i("XXX", "next_intent");
                break;
        }
    }
}
