package com.elkcreek.rodneytressler.musicapp.utils;

import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import androidx.navigation.Navigation;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class MVVMUtils {

    @BindingAdapter("artistImageUrl")
    public static void loadArtistImage(ImageView imageView, MusicApi.Artist artist) {

        if(artist != null && artist.getArtistImages() != null) {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(artist.getArtistImages().get(2).getImageUrl())
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);

            imageView.setBackgroundColor(Color.BLACK);
        } else {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageView.getResources().getDrawable(R.drawable.generic_band))
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);
            imageView.setBackgroundColor(Color.WHITE);
        }
    }

    @BindingAdapter("similarArtist")
    public static void loadSimilarArtistImage(ImageView imageView, MusicApi.Artist artist) {
        Glide.with(imageView.getContext()).asBitmap()
                .load(artist.getArtistImages().get(2).getImageUrl())
                .apply(RequestOptions.errorOf(R.drawable.no_image_available))
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.overrideOf(100, 150))
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("data")
    public static void loadArtists(RecyclerView recyclerView, List<MusicApi.Artist>artists) {
        ArtistAdapter artistAdapter = new ArtistAdapter(artists);
        recyclerView.setAdapter(artistAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), recyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2));
        artistAdapter.notifyDataSetChanged();
    }

    @BindingAdapter("similarArtistData")
    public static void loadSimilarArtists(RecyclerView recyclerView, List<MusicApi.Artist> similarArtists) {
        SimilarArtistAdapter similarArtistAdapter = new SimilarArtistAdapter(similarArtists);
        recyclerView.setAdapter(similarArtistAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        similarArtistAdapter.notifyDataSetChanged();
    }

}
