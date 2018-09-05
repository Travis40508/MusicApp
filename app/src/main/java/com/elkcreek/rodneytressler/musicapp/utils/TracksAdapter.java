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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Track> trackList;
    private TracksCallback tracksCallback;
    private List<MusicApi.Track> fullTrackList;

    public TracksAdapter(RequestManager glide, List<MusicApi.Track> trackList) {
        this.trackList = trackList;
        this.glide = glide;
        fullTrackList = trackList;
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
        tracksViewHolder.itemView.setOnClickListener(tracksViewHolder.onPlayClicked(trackList.get(position)));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setPlayCallback(TracksCallback tracksCallback) {
        this.tracksCallback = tracksCallback;
    }

    public void showSearchedTracks(String searchText) {
        List<MusicApi.Track> searchedTrackList = new ArrayList<>();
        for(MusicApi.Track item : fullTrackList) {
            if(item.getTrackName().toLowerCase().contains(searchText.toLowerCase())) {
                searchedTrackList.add(item);
                trackList = searchedTrackList;
                notifyDataSetChanged();
            }
        }
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.position_track_item)
        protected TextView trackPosition;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        public TracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTrack(MusicApi.Track track) {
            String position = String.valueOf(trackList.indexOf(track) + 1);
            trackPosition.setText(position);
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
