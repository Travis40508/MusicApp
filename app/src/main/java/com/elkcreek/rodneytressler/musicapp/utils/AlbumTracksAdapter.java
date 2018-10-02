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
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.AlbumTracksViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Track> trackList;
    private AlbumTracksCallback albumTracksCallback;
    private List<MusicApi.Track> fullTrackList;

    public AlbumTracksAdapter(List<MusicApi.Track> trackList, MainViewModel mainViewModel) {
        this.trackList = trackList;
        this.mainViewModel = mainViewModel;
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

        @BindView(R.id.position_track_item)
        protected TextView trackPosition;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        public AlbumTracksViewHolder(@NonNull View itemView) {
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
                    albumTracksCallback.onPlayClicked(track);
                }
            };
        }
    }

    public interface AlbumTracksCallback {
        void onPlayClicked(MusicApi.Track track);
    }
}
