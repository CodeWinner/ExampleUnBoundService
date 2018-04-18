package ztml.dev.ngokhacbac.exampleplaymusicservice.view.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.R;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;

public class AdapterListSongRecycler extends RecyclerView.Adapter<AdapterListSongRecycler.ViewHolder> {
    private List<Song> songs;
    // private MediaPlayer mMediaPlayer;
    public OnItemClickListenner mOnItemClickListenner;

    public interface OnItemClickListenner {
        void onClick(View itemView, int position);
    }

    public AdapterListSongRecycler(List<Song> songs) {
        this.songs = songs;
    }

    public void setOnItemClickListenner(OnItemClickListenner mOnItemClickListenner) {
        this.mOnItemClickListenner = mOnItemClickListenner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_list_song, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.mImageViewAvatar.setImageResource(song.getmImageAvatarSong());
        holder.mTextViewSongName.setText(song.getmName());
        holder.mTextViewAuthor.setText(song.getmAuthor());
        if (song.isPlaying() == false) {
            holder.mImageButtonPlay.setImageResource(R.mipmap.ic_play_song);
        } else {
            holder.mImageButtonPlay.setImageResource(R.mipmap.ic_pause_green_a);
        }

        if (song.getDuration() != 0) {
            int process = (song.getCurrentDuration()*1000) / song.getDuration();
            holder.progressBar.setProgress(process);
        } else {
            holder.progressBar.setProgress(0);
        }

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewAvatar;
        private TextView mTextViewSongName;
        private TextView mTextViewAuthor;
        private ImageButton mImageButtonPlay;
        private ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageViewAvatar = itemView.findViewById(R.id.imageSong);
            mTextViewSongName = itemView.findViewById(R.id.textViewSongName);
            mTextViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            mImageButtonPlay = itemView.findViewById(R.id.imageButtonPlay);
            progressBar = itemView.findViewById(R.id.progressBar);
            mImageButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListenner.onClick(v, getLayoutPosition());
                }
            });
        }
    }

}
