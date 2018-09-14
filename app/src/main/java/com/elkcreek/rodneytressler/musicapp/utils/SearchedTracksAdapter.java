package com.elkcreek.rodneytressler.musicapp.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class SearchedTracksAdapter extends RecyclerView.Adapter<SearchedTracksAdapter.SearchedTracksViewHolder> {

    private List<MusicApi.SearchedTrack> searchedTrackList;
    private RequestManager glide;
    private SearchedTrackCallback callback;

    public SearchedTracksAdapter(List<MusicApi.SearchedTrack> searchedTrackList, RequestManager glide) {
        this.searchedTrackList = searchedTrackList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public SearchedTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top_track, viewGroup, false);
        return new SearchedTracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedTracksViewHolder searchedTracksViewHolder, int position) {
        MusicApi.SearchedTrack searchedTrack = searchedTrackList.get(position);
        searchedTracksViewHolder.bindSearchedTrack(searchedTrack);
        searchedTracksViewHolder.itemView.setOnClickListener(searchedTracksViewHolder.onSearchedTrackClicked(searchedTrack));
    }

    @Override
    public int getItemCount() {
        return searchedTrackList.size();
    }

    public void setSearchedCallback(SearchedTrackCallback callback) {
        this.callback = callback;
    }

    public class SearchedTracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        @BindView(R.id.text_artist_name)
        protected TextView artistName;

        @BindView(R.id.image_track_item)
        protected ImageView artistImage;


        public SearchedTracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindSearchedTrack(MusicApi.SearchedTrack searchedTrack) {
            trackName.setText(searchedTrack.getTrackName());
            artistName.setText(searchedTrack.getArtist());

            glide.asBitmap()
                    .load(searchedTrack.getArtistImage().get(2).getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.overrideOf(100, 150))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(artistImage);
        }

        public View.OnClickListener onSearchedTrackClicked(MusicApi.SearchedTrack searchedTrack) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onSearchedTrackClicked(searchedTrack);
                }
            };
        }
    }

    public interface SearchedTrackCallback {
        void onSearchedTrackClicked(MusicApi.SearchedTrack track);
    }
}
