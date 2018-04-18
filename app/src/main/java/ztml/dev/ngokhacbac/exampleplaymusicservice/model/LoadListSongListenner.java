package ztml.dev.ngokhacbac.exampleplaymusicservice.model;

import java.util.List;

public interface LoadListSongListenner {
    void CreateListSuccess(List<Song> songs);
    void CreateListFail(String mess);
}
