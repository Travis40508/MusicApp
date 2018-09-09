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
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.TopTracksViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Track> topTracksList;
    private TopTrackCallback callback;

    public TopTracksAdapter(RequestManager glide, List<MusicApi.Track> topTracksList) {
        this.topTracksList = topTracksList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public TopTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_top_track, viewGroup, false);
        return new TopTracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTracksViewHolder topTracksViewHolder, int position) {
        topTracksViewHolder.bindTrack(topTracksList.get(position));
        topTracksViewHolder.itemView.setOnClickListener(topTracksViewHolder.onTrackClicked(topTracksList.get(position)));
    }

    @Override
    public int getItemCount() {
        return topTracksList.size();
    }

    public void setAdapterCallback(TopTrackCallback callback) {
        this.callback = callback;
    }

    public class TopTracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_artist_name)
        protected TextView artistName;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        @BindView(R.id.image_track_item)
        protected ImageView trackImage;

        public TopTracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTrack(MusicApi.Track track) {
            glide.asBitmap()
                    .load(track.getArtistImage().get(2).getImageUrl())
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(trackImage);

            artistName.setText(track.getArtist().getArtistName());
            trackName.setText(track.getTrackName());
        }

        public View.OnClickListener onTrackClicked(MusicApi.Track track) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onTopTrackClicked(track);
                }
            };
        }
    }

    public interface TopTrackCallback {
        void onTopTrackClicked(MusicApi.Track track);
    }
}
