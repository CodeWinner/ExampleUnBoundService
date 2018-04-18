package ztml.dev.ngokhacbac.exampleplaymusicservice.utils;

public interface MediaplayerListenner {
    void ProcessPlayCurrent(int durationSong,int currentDuration,int positionSong);
    void FinishSong(int positionSongFinish);
}
