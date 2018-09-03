package com.elkcreek.rodneytressler.musicapp.utils;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class SimilarArtistAdapter extends RecyclerView.Adapter<SimilarArtistAdapter.SimilarArtistViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Artist> similarArtistList;
    private BiosCallback biosCallback;

    public SimilarArtistAdapter(RequestManager glide, List<MusicApi.Artist> similarArtistList) {
        this.similarArtistList = similarArtistList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public SimilarArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_similar_artist, viewGroup, false);
        return new SimilarArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarArtistViewHolder similarArtistViewHolder, int position) {
        similarArtistViewHolder.bindSimilarArtist(similarArtistList.get(position));
        similarArtistViewHolder.itemView.setOnClickListener(similarArtistViewHolder.onSimilarArtistClicked(similarArtistList.get(position)));
    }

    @Override
    public int getItemCount() {
        return similarArtistList.size();
    }

    public void setCallback(BiosCallback biosCallback) {
        this.biosCallback = biosCallback;
    }

    public class SimilarArtistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_similar_artist_image)
        protected ImageView similarArtistImage;

        @BindView(R.id.item_similar_artist_name)
        protected TextView similarArtistName;

        public SimilarArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindSimilarArtist(MusicApi.Artist artist) {
            glide.asBitmap()
                    .load(artist.getArtistImages().get(2).getImageUrl())
                    .apply(RequestOptions.overrideOf(100, 150))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(similarArtistImage);
            similarArtistName.setText(artist.getArtistName());
        }

        public View.OnClickListener onSimilarArtistClicked(MusicApi.Artist artist) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biosCallback.onSimilarArtistClicked(artist);
                }
            };
        }
    }

    public interface BiosCallback {
        void onSimilarArtistClicked(MusicApi.Artist artist);
    }
}
