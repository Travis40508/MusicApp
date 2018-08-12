package com.elkcreek.rodneytressler.musicapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<MusicApi.Artist> artistList;
    private Callback callback;

    public ArtistAdapter(List<MusicApi.Artist> artistList) {
        this.artistList = artistList;
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
//        artistViewHolder.infoButton.setOnClickListener(artistViewHolder.onInfoButtonClicked(artistList.get(position)));
        artistViewHolder.artistImage.setOnClickListener(artistViewHolder.onInfoButtonClicked(artistList.get(position)));
        artistViewHolder.musicButton.setOnClickListener(artistViewHolder.onMusicButtonClicked(artistList.get(position)));
    }


    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void setAdapterCallback(Callback callback) {
        this.callback = callback;
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_artist_item)
        protected ImageView artistImage;

        @BindView(R.id.text_artist_name)
        protected TextView artistText;

//        @BindView(R.id.button_info)
//        protected ImageView infoButton;

        @BindView(R.id.button_music)
        protected ImageView musicButton;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindArtist(MusicApi.Artist artist) {
            Glide.with(itemView).load(artist.getArtistImages().get(2).getImageUrl()).into(artistImage);
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

        public View.OnClickListener onMusicButtonClicked(MusicApi.Artist artist) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onArtistMusicClicked(artist);
                }
            };
        }
    }

    public interface Callback {
        void onArtistInfoClicked(MusicApi.Artist artist);
        void onArtistMusicClicked(MusicApi.Artist artist);
    }
}
