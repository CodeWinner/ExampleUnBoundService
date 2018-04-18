package ztml.dev.ngokhacbac.exampleplaymusicservice.model;

public class Song {
    private String mName;
    private String mAuthor;
    private int mSongRaw;
    private boolean isPlaying;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }

    private int currentDuration;

    public int getmImageAvatarSong() {
        return mImageAvatarSong;
    }

    public void setmImageAvatarSong(int mImageAvatarSong) {
        this.mImageAvatarSong = mImageAvatarSong;
    }

    private int mImageAvatarSong;

    public Song() {
    }

    public Song(String mName, String mAuthor,
                int mSongRaw, int mImageAvatarSong,
                boolean isPlaying, int currentDuration,
                int duration) {
        this.mName = mName;
        this.mAuthor = mAuthor;
        this.mSongRaw = mSongRaw;
        this.mImageAvatarSong = mImageAvatarSong;
        this.isPlaying = isPlaying;
        this.currentDuration = currentDuration;
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getmSongRaw() {
        return mSongRaw;
    }

    public void setmSongRaw(int mSongRaw) {
        this.mSongRaw = mSongRaw;
    }
}
