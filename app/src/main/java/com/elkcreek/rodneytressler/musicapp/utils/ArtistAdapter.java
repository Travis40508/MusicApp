package com.elkcreek.rodneytressler.musicapp.utils;

import android.content.Context;
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
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Artist> artistList;
    private Callback callback;

    public ArtistAdapter(RequestManager glide, List<MusicApi.Artist> artistList) {
        this.artistList = artistList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_artist, viewGroup, false);
        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int position) {
        artistViewHolder.bindArtist(artistList.get(position));
        artistViewHolder.artistImage.setOnClickListener(artistViewHolder.onInfoButtonClicked(artistList.get(position)));
    }


    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void setAdapterCallback(Callback callback) {
        this.callback = callback;
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

        @BindView(R.id.image_artist_item)
        protected ImageView artistImage;

        @BindView(R.id.text_artist_name)
        protected TextView artistText;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindArtist(MusicApi.Artist artist) {
            glide.asBitmap()
                    .load(artist.getArtistImages().get(2).getImageUrl())
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(artistImage);
            artistText.setText(artist.getArtistName());
        }

        public View.OnClickListener onInfoButtonClicked(MusicApi.Artist artist) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onArtistInfoClicked(artist);
                }
            };
        }
    }

    public interface Callback {
        void onArtistInfoClicked(MusicApi.Artist artist);
    }
}
