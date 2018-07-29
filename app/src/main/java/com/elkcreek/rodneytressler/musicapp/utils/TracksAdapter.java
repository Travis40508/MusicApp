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

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private List<MusicApi.Track> trackList;
    private TracksCallback tracksCallback;

    public TracksAdapter(List<MusicApi.Track> trackList) {
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tracks, viewGroup, false);
        return new TracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksViewHolder tracksViewHolder, int position) {
        tracksViewHolder.bindTrack(trackList.get(position));
        tracksViewHolder.trackPlayButton.setOnClickListener(tracksViewHolder.onPlayClicked(trackList.get(position)));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setPlayCallback(TracksCallback tracksCallback) {
        this.tracksCallback = tracksCallback;
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_track_item)
        protected ImageView trackImage;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        @BindView(R.id.button_play)
        protected ImageView trackPlayButton;

        public TracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTrack(MusicApi.Track track) {
            Glide.with(itemView).load(track.getImageUrl()).into(trackImage);
            trackName.setText(track.getTrackName());
        }

        public View.OnClickListener onPlayClicked(MusicApi.Track track) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tracksCallback.onPlayClicked(track);
                }
            };
        }
    }

    public interface TracksCallback {
        void onPlayClicked(MusicApi.Track track);
    }
}
