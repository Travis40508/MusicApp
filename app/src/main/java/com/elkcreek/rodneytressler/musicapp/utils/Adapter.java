package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemAlbumBinding;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemArtistBinding;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemTopTrackBinding;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemTracksBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final MainViewModel mainViewModel;
    private List<Object> objectList;
    private EventHandlers handler;
    private static final int ARTIST_ITEM = 0;
    private static final int ALBUM_ITEM = 1;
    private static final int TRACK_ITEM = 2;
    private static final int TOP_TRACK_ITEM = 3;

    public Adapter(List<Object> objectList, MainViewModel mainViewModel) {
        this.objectList = objectList;
        this.mainViewModel = mainViewModel;
        handler = new EventHandlers();
    }

    public void setAdapterItems(List<Object> objectList) {
        this.objectList = objectList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object listItem = objectList.get(position);

        if (listItem instanceof MusicApi.Artist) {
            return ARTIST_ITEM;
        } else if (listItem instanceof MusicApi.Album) {
            return ALBUM_ITEM;
        } else if (listItem instanceof MusicApi.Track) {
            if (((MusicApi.Track) listItem).getTrackUid() != null) {
                return TRACK_ITEM;
            } else {
                return TOP_TRACK_ITEM;
            }
        } else {
            return 4;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemViewType) {
        switch (itemViewType) {
            case 0:
                ItemArtistBinding artistBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_artist, viewGroup, false);
                return new ArtistViewHolder(artistBinding);
            case 1:
                ItemAlbumBinding albumBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_album, viewGroup, false);
                return new AlbumsViewHolder(albumBinding);
            case 2:
                ItemTracksBinding trackBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_tracks, viewGroup, false);
                return new TracksViewHolder(trackBinding);
            case 3:
                ItemTopTrackBinding topTrackBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_top_track, viewGroup, false);
                return new TopTracksViewHolder(topTrackBinding);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Object listItem = objectList.get(position);

        if (listItem instanceof MusicApi.Artist) {
            bindArtist((ArtistViewHolder) viewHolder, (MusicApi.Artist)listItem);
        } else if (listItem instanceof MusicApi.Album) {
            bindAlbum((AlbumsViewHolder) viewHolder, (MusicApi.Album) listItem);
        } else if (listItem instanceof MusicApi.Track) {
            if (((MusicApi.Track) listItem).getTrackUid() != null) {
                bindTrack((TracksViewHolder) viewHolder, (MusicApi.Track) listItem);
            } else {
                bindTopTrack((TopTracksViewHolder) viewHolder, (MusicApi.Track) listItem);
            }
        }
    }

    private void bindArtist(ArtistViewHolder viewHolder, MusicApi.Artist artist) {
        viewHolder.binding.setArtist(artist);
        viewHolder.binding.setMainViewModel(mainViewModel);
        viewHolder.binding.setHandler(handler);
    }

    private void bindAlbum(AlbumsViewHolder viewHolder, MusicApi.Album album) {
        MusicApi.Artist artist = album.getArtistResponse();
        String imageUrl = album.getTrackImage().get(2).getImageUrl();
        viewHolder.binding.setMainViewModel(mainViewModel);
        viewHolder.binding.setAlbum(album);
        viewHolder.binding.setHandler(handler);
        viewHolder.binding.setArtist(artist);
        viewHolder.binding.setImageUrl(imageUrl);
    }

    private void bindTrack(TracksViewHolder viewHolder, MusicApi.Track track) {
        String trackPosition = String.valueOf(objectList.indexOf(track) + 1);
        String artistName = track.getArtist().getArtistName();
        viewHolder.binding.setArtistName(artistName);
        viewHolder.binding.setTrackPosition(trackPosition);
        viewHolder.binding.setTrack(track);
        viewHolder.binding.setMainViewModel(mainViewModel);
        viewHolder.binding.setHandler(handler);
    }

    private void bindTopTrack(TopTracksViewHolder viewHolder, MusicApi.Track track) {
        String imageUrl = track.getArtistImage().get(2).getImageUrl();
        String artistName = track.getArtist().getArtistName();
        String trackUid = track.getTrackUid();
        String trackName = track.getTrackName();

        viewHolder.binding.setArtistName(artistName);
        viewHolder.binding.setImageUrl(imageUrl);
        viewHolder.binding.setTrackUid(trackUid);
        viewHolder.binding.setTrackName(trackName);
        viewHolder.binding.setHandler(handler);
        viewHolder.binding.setMainViewModel(mainViewModel);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        private final ItemArtistBinding binding;

        public ArtistViewHolder(ItemArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class AlbumsViewHolder extends RecyclerView.ViewHolder {

        private final ItemAlbumBinding binding;

        public AlbumsViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {

        private final ItemTracksBinding binding;

        public TracksViewHolder(ItemTracksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public class TopTracksViewHolder extends RecyclerView.ViewHolder {

        private final ItemTopTrackBinding binding;

        public TopTracksViewHolder(ItemTopTrackBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
