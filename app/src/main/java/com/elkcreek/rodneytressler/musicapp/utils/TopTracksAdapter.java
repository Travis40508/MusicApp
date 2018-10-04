package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.DataBindingUtil;
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
import com.elkcreek.rodneytressler.musicapp.databinding.ItemTopTrackBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.TopTracksViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Track> topTracksList;
    private TopTrackCallback callback;

    public TopTracksAdapter(List<MusicApi.Track> topTracksList, MainViewModel mainViewModel) {
        this.topTracksList = topTracksList;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public TopTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTopTrackBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_top_track, viewGroup, false);
        return new TopTracksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTracksViewHolder topTracksViewHolder, int position) {
        EventHandlers handler = new EventHandlers();
        MusicApi.Track track = topTracksList.get(position);
        String imageUrl = track.getArtistImage().get(2).getImageUrl();
        String artistName = track.getArtist().getArtistName();

        topTracksViewHolder.binding.setArtistName(artistName);
        topTracksViewHolder.binding.setImageUrl(imageUrl);
        topTracksViewHolder.binding.setTrack(track);
        topTracksViewHolder.binding.setHandler(handler);
        topTracksViewHolder.binding.setMainViewModel(mainViewModel);
    }

    @Override
    public int getItemCount() {
        return topTracksList.size();
    }

    public void setAdapterCallback(TopTrackCallback callback) {
        this.callback = callback;
    }

    public class TopTracksViewHolder extends RecyclerView.ViewHolder {

        private final ItemTopTrackBinding binding;
        @BindView(R.id.text_artist_name)
        protected TextView artistName;

        @BindView(R.id.text_track_name)
        protected TextView trackName;

        @BindView(R.id.image_track_item)
        protected ImageView trackImage;

        public TopTracksViewHolder(ItemTopTrackBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTrack(MusicApi.Track track) {
//            glide.asBitmap()
//                    .load(track.getArtistImage().get(2).getImageUrl())
//                    .apply(RequestOptions.circleCropTransform())
//                    .apply(RequestOptions.overrideOf(100, 150))
//                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
//                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
//                    .transition(BitmapTransitionOptions.withCrossFade())
//                    .into(trackImage);

//            artistName.setText(track.getArtist().getArtistName());
//            trackName.setText(track.getTrackName());
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
