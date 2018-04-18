package ztml.dev.ngokhacbac.exampleplaymusicservice.presenter;

import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.model.CreateListSong;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.LoadListSongListenner;
import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;
import ztml.dev.ngokhacbac.exampleplaymusicservice.view.MainView;

public class MainPresenter implements LoadListSongListenner {
    private MainView mMainView;
    private CreateListSong mCreateListSong;

    public MainPresenter(MainView mMainView) {
        this.mMainView = mMainView;
        mCreateListSong = new CreateListSong(this);
    }

    public void LoadData() {
        mCreateListSong.CreateList();
    }

    @Override
    public void CreateListSuccess(List<Song> songs) {
        mMainView.displaySongsSucess(songs);
    }

    @Override
    public void CreateListFail(String mess) {
        mMainView.displaySongsFail(mess);
    }
}
