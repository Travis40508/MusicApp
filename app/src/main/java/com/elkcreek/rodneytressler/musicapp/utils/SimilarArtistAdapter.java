package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemSimilarArtistBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class SimilarArtistAdapter extends RecyclerView.Adapter<SimilarArtistAdapter.SimilarArtistViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Artist> similarArtistList;

    public SimilarArtistAdapter(List<MusicApi.Artist> similarArtistList, MainViewModel mainViewModel) {
        this.similarArtistList = similarArtistList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public SimilarArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSimilarArtistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_similar_artist, viewGroup, false);
        return new SimilarArtistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarArtistViewHolder similarArtistViewHolder, int position) {
        EventHandlers eventHandlers = new EventHandlers();
        MusicApi.Artist artist = similarArtistList.get(position);
        similarArtistViewHolder.binding.setArtist(artist);
        similarArtistViewHolder.binding.setMainViewModel(mainViewModel);
        similarArtistViewHolder.binding.setHandler(eventHandlers);
    }

    @Override
    public int getItemCount() {
        return similarArtistList.size();
    }

    public class SimilarArtistViewHolder extends RecyclerView.ViewHolder {

        private final ItemSimilarArtistBinding binding;

        public SimilarArtistViewHolder(ItemSimilarArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
