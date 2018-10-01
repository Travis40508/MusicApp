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

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.databinding.ItemSimilarTracksBinding;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class SimilarTracksAdapter extends RecyclerView.Adapter<SimilarTracksAdapter.SimilarTracksViewHolder> {

    private final MainViewModel mainViewModel;
    private List<MusicApi.Track> similarTracks;

    public SimilarTracksAdapter(List<MusicApi.Track> similarTracks, MainViewModel mainViewModel) {
        this.similarTracks = similarTracks;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public SimilarTracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSimilarTracksBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_similar_tracks, viewGroup, false);
        return new SimilarTracksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarTracksViewHolder similarTracksViewHolder, int position) {
        MusicApi.Track track = similarTracks.get(position);
        String imageUrl = track.getArtistImage().get(2).getImageUrl();
        String artistName = track.getArtist().getArtistName();
        EventHandlers handler = new EventHandlers();

        similarTracksViewHolder.binding.setArtistName(artistName);
        similarTracksViewHolder.binding.setHandler(handler);
        similarTracksViewHolder.binding.setImageUrl(imageUrl);
        similarTracksViewHolder.binding.setTrack(track);
        similarTracksViewHolder.binding.setMainViewModel(mainViewModel);
    }

    @Override
    public int getItemCount() {
        return similarTracks.size();
    }

    public class SimilarTracksViewHolder extends RecyclerView.ViewHolder {

        private final ItemSimilarTracksBinding binding;

        public SimilarTracksViewHolder(ItemSimilarTracksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindSimilarTrack(MusicApi.Track track) {
//            Glide.with(this).asBitmap()
//                    .load(track.getArtistImage().get(2).getImageUrl())
//                    .apply(RequestOptions.errorOf(R.drawable.no_image_available))
//                    .apply(RequestOptions.overrideOf(100, 150))
//                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
//                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
//                    .apply(RequestOptions.circleCropTransform())
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
//                    .transition(BitmapTransitionOptions.withCrossFade())
//                    .into(similarTrackImage);
//            similarTrackArtistName.setText(track.getArtist().getArtistName());
//            similarTrackName.setText(track.getTrackName());
        }
    }
}
