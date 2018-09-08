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
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class SimilarTracksAdapter extends RecyclerView.Adapter<SimilarTracksAdapter.SimilarTracksViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Track> similarTracks;
    private SimilarTracksAdapterCallback callback;

    public SimilarTracksAdapter(RequestManager glide, List<MusicApi.Track> similarTracks) {
        this.similarTracks = similarTracks;
        this.glide = glide;
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
            glide.asBitmap()
                    .load(track.getArtistImage().get(2).getImageUrl())
                    .apply(RequestOptions.errorOf(R.drawable.no_image_available))
                    .apply(RequestOptions.overrideOf(100, 150))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(similarTrackImage);
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
