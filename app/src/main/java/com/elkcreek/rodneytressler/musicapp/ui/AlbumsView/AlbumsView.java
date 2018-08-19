package com.elkcreek.rodneytressler.musicapp.ui.AlbumsView;

import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.utils.BaseView;

import java.util.List;

public interface AlbumsView extends BaseView {
    void reAttachAlbumsFragment();

    void showTopAlbums(List<MusicApi.Album> albumList);

    void showAlbumTracks(String artistName, String artistUID, String albumName, String albumUid);
}
