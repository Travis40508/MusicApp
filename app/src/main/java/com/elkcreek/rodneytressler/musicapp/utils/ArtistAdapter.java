package com.elkcreek.rodneytressler.musicapp.utils;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.RequestManager;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemArtistBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Artist> artistList;

    public ArtistAdapter(List<MusicApi.Artist> artistList, MainViewModel mainViewModel) {
        this.artistList = artistList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemArtistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_artist, viewGroup, false);
        return new ArtistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int position) {
        EventHandlers eventHandlers = new EventHandlers();
        MusicApi.Artist artist = artistList.get(position);
        artistViewHolder.binding.setArtist(artist);
        artistViewHolder.binding.setMainViewModel(mainViewModel);
        artistViewHolder.binding.setHandler(eventHandlers);
    }


    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void setArtistList(List<MusicApi.Artist> artistList) {
        this.artistList = artistList;
        notifyDataSetChanged();
    }

    public void clearList() {
        artistList.clear();
        notifyDataSetChanged();
    }

    public void addToList(List<MusicApi.Artist> moreArtistList) {
        artistList.addAll(moreArtistList);
        notifyDataSetChanged();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        private final ItemArtistBinding binding;

        public ArtistViewHolder(ItemArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
