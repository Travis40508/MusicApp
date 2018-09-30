package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemAlbumBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Album> albumList;

    public AlbumsAdapter(List<MusicApi.Album> albumList, MainViewModel mainViewModel) {
        this.albumList = albumList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemAlbumBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_album, viewGroup, false);
        return new AlbumsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsViewHolder albumsViewHolder, int position) {
        MusicApi.Album album = albumList.get(position);
        EventHandlers handler = new EventHandlers();
        MusicApi.Artist artist = album.getArtistResponse();
        String imageUrl = album.getTrackImage().get(2).getImageUrl();
        albumsViewHolder.binding.setMainViewModel(mainViewModel);
        albumsViewHolder.binding.setAlbum(album);
        albumsViewHolder.binding.setHandler(handler);
        albumsViewHolder.binding.setArtist(artist);
        albumsViewHolder.binding.setImageUrl(imageUrl);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class AlbumsViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlbumBinding binding;

        public AlbumsViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
