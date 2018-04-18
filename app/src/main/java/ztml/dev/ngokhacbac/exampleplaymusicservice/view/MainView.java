package ztml.dev.ngokhacbac.exampleplaymusicservice.view;

import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.model.Song;

public interface MainView {
    void displaySongsSucess(List<Song> songs);

    void displaySongsFail(String mess);
}
