package ztml.dev.ngokhacbac.exampleplaymusicservice.model;

import java.util.ArrayList;
import java.util.List;

import ztml.dev.ngokhacbac.exampleplaymusicservice.R;

public class CreateListSong {
    List<Song> songs;
    private LoadListSongListenner mLoadListSongListenner;

    public CreateListSong(LoadListSongListenner loadListSongListenner) {
        this.mLoadListSongListenner = loadListSongListenner;
    }

    public void CreateList() {
        songs = new ArrayList<>();
        songs.add(new Song("Cô gái m52.", "Huy, Tùng Viu", R.raw.cogaim52, R.drawable.cogaim52,false,0,0));
        songs.add(new Song("Cùng anh", "Ngọc Dolil, Hagi, STee", R.raw.cunganhngocdolilhagitee, R.drawable.cunganh,false,0,0));
        songs.add(new Song("Mình cưới nhau đi", "Huỳnh James, Pjnboys", R.raw.minhcuoinhaudihuynhjamespjnboys, R.drawable.minhcuoinhaudi,false,0,0));
        songs.add(new Song("Ngắm hoa lệ rơi", "Châu Khải Phong", R.raw.ngamhoaleroichaukhaiphong, R.drawable.ngamhoaleroi,false,0,0));
        songs.add(new Song("Quan trọng là thần thái.", "OnlyC-Karik", R.raw.quantronglathanthaionlyckarik, R.drawable.quantronglathanthai,false,0,0));
        songs.add(new Song("Cô gái m52.", "Huy, Tùng Viu", R.raw.cogaim52, R.drawable.cogaim52,false,0,0));
        songs.add(new Song("Cùng anh", "Ngọc Dolil, Hagi, STee", R.raw.cunganhngocdolilhagitee, R.drawable.cunganh,false,0,0));
        songs.add(new Song("Mình cưới nhau đi", "Huỳnh James, Pjnboys", R.raw.minhcuoinhaudihuynhjamespjnboys, R.drawable.minhcuoinhaudi,false,0,0));
        songs.add(new Song("Ngắm hoa lệ rơi", "Châu Khải Phong", R.raw.ngamhoaleroichaukhaiphong, R.drawable.ngamhoaleroi,false,0,0));
        songs.add(new Song("Quan trọng là thần thái.", "OnlyC-Karik", R.raw.quantronglathanthaionlyckarik, R.drawable.quantronglathanthai,false,0,0));
        songs.add(new Song("Cô gái m52.", "Huy, Tùng Viu", R.raw.cogaim52, R.drawable.cogaim52,false,0,0));
        songs.add(new Song("Cùng anh", "Ngọc Dolil, Hagi, STee", R.raw.cunganhngocdolilhagitee, R.drawable.cunganh,false,0,0));
        songs.add(new Song("Mình cưới nhau đi", "Huỳnh James, Pjnboys", R.raw.minhcuoinhaudihuynhjamespjnboys, R.drawable.minhcuoinhaudi,false,0,0));
        songs.add(new Song("Ngắm hoa lệ rơi", "Châu Khải Phong", R.raw.ngamhoaleroichaukhaiphong, R.drawable.ngamhoaleroi,false,0,0));
        songs.add(new Song("Quan trọng là thần thái.", "OnlyC-Karik", R.raw.quantronglathanthaionlyckarik, R.drawable.quantronglathanthai,false,0,0));
        mLoadListSongListenner.CreateListSuccess(songs);
    }
}
