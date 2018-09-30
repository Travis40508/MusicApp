package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemTracksBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import java.util.List;
import butterknife.ButterKnife;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Track> trackList;

    public TracksAdapter(List<MusicApi.Track> trackList, MainViewModel mainViewModel) {
        this.trackList = trackList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTracksBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_tracks, viewGroup, false);
        return new TracksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksViewHolder tracksViewHolder, int position) {
        MusicApi.Track track = trackList.get(position);
        EventHandlers handler = new EventHandlers();
        String trackPosition = String.valueOf(trackList.indexOf(track) + 1);
        String artistName = track.getArtist().getArtistName();
        tracksViewHolder.binding.setArtistName(artistName);
        tracksViewHolder.binding.setTrackPosition(trackPosition);
        tracksViewHolder.binding.setTrack(track);
        tracksViewHolder.binding.setMainViewModel(mainViewModel);
        tracksViewHolder.binding.setHandler(handler);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {

        private final ItemTracksBinding binding;

        public TracksViewHolder(ItemTracksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
