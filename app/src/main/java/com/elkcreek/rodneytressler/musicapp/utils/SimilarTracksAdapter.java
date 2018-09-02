package com.elkcreek.rodneytressler.musicapp.utils;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarTracksAdapter extends RecyclerView.Adapter<SimilarTracksAdapter.SimilarTracksViewHolder> {

    private List<MusicApi.Track> similarTracks;
    private SimilarTracksAdapterCallback callback;

    public SimilarTracksAdapter(List<MusicApi.Track> similarTracks) {
        this.similarTracks = similarTracks;
    }

    @NonNull
    @Override
    public SimilarTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_similar_tracks, viewGroup, false);
        return new SimilarTracksViewHolder(itemView);
    }

    public void setCallback(SimilarTracksAdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarTracksViewHolder similarTracksViewHolder, int position) {
        similarTracksViewHolder.bindSimilarTrack(similarTracks.get(position));
        similarTracksViewHolder.itemView.setOnClickListener(similarTracksViewHolder.onTrackClicked(similarTracks.get(position)));
    }

    @Override
    public int getItemCount() {
        return similarTracks.size();
    }

    public class SimilarTracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_similar_track_image)
        protected ImageView similarTrackImage;

        @BindView(R.id.item_similar_track_artist_name)
        protected TextView similarTrackArtistName;

        @BindView(R.id.item_similar_track_track_name)
        protected TextView similarTrackName;

        public SimilarTracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindSimilarTrack(MusicApi.Track track) {
            Glide.with(itemView.getContext()).load(track.getArtistImage().get(2).getImageUrl()).into(similarTrackImage);
            similarTrackArtistName.setText(track.getArtist().getArtistName());
            similarTrackName.setText(track.getTrackName());
        }

        public View.OnClickListener onTrackClicked(MusicApi.Track track) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.similarTrackClicked(track);
                }
            };
        }
    }

    public interface SimilarTracksAdapterCallback {
        void similarTrackClicked(MusicApi.Track track);
    }
}
