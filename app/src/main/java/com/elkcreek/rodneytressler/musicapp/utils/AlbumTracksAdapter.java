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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.AlbumTracksViewHolder> {

    private final String imageUrl;
    private List<MusicApi.Track> trackList;
    private AlbumTracksCallback albumTracksCallback;
    private List<MusicApi.Track> fullTrackList;

    public AlbumTracksAdapter(List<MusicApi.Track> trackList, String imageUrl) {
        this.trackList = trackList;
        this.imageUrl = imageUrl;
        fullTrackList = trackList;
    }

    @NonNull
    @Override
    public AlbumTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tracks, viewGroup, false);
        return new AlbumTracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumTracksViewHolder tracksViewHolder, int position) {
        tracksViewHolder.bindTrack(trackList.get(position));
        tracksViewHolder.itemView.setOnClickListener(tracksViewHolder.onPlayClicked(trackList.get(position)));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public void setPlayCallback(AlbumTracksCallback tracksCallback) {
        this.albumTracksCallback = tracksCallback;
    }

    public void showSearchedTracks(String searchText) {
        List<MusicApi.Track> searchedTrackList = new ArrayList<>();
        for (MusicApi.Track item : fullTrackList) {
            if (item.getTrackName().toLowerCase().contains(searchText.toLowerCase())) {
                searchedTrackList.add(item);
                trackList = searchedTrackList;
                notifyDataSetChanged();
            }
        }
    }

    public class AlbumTracksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_track_item)
        protected ImageView trackImage;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        public AlbumTracksViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTrack(MusicApi.Track track) {
            Glide.with(itemView).load(imageUrl).into(trackImage);
            trackName.setText(track.getTrackName());
        }

        public View.OnClickListener onPlayClicked(MusicApi.Track track) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    albumTracksCallback.onPlayClicked(track);
                }
            };
        }
    }

    public interface AlbumTracksCallback {
        void onPlayClicked(MusicApi.Track track);
    }
}
