package com.elkcreek.rodneytressler.musicapp.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.ArrayList;
import java.util.List;

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

    @BindingAdapter(value = {"data", "recyclerViewAdapter", "searchText"}, requireAll = false)
    public static void artistRecyclerView(RecyclerView recyclerView, List<Object> list, RecyclerViewAdapter recyclerViewAdapter, String searchedText) {
        recyclerViewAdapter.setAdapterItems(list);
        List<Object> searchedTrackList = new ArrayList<>();

        if(searchedText != null) {
            for (Object item : list) {
                if (((MusicApi.Track) item).getTrackName().toLowerCase().contains(searchedText.toLowerCase())) {
                    searchedTrackList.add(item);
                    recyclerViewAdapter.setAdapterItems(searchedTrackList);
                }
            }
        }
    }

    @BindingAdapter("albumImage")
    public static void loadAlbumImage(ImageView imageView, MusicApi.Album album) {
        Glide.with(imageView.getContext()).asBitmap()
                .load(album.getTrackImage().get(2).getImageUrl())
                .apply(RequestOptions.overrideOf(100, 150))
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("albumBioImage")
    public static void loadAlbumBioImage(ImageView imageView, String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageUrl)
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);

            imageView.setBackgroundColor(Color.BLACK);
        } else {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageView.getResources().getDrawable(R.drawable.generic_album))
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);
            imageView.setBackgroundColor(Color.WHITE);
        }
    }

    @BindingAdapter("trackBioImage")
    public static void loadTrackBioImage(ImageView imageView, String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageUrl)
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);

            imageView.setBackgroundColor(Color.BLACK);
        } else {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageView.getResources().getDrawable(R.drawable.generic_track))
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);
            imageView.setBackgroundColor(Color.WHITE);
        }
    }

    @BindingAdapter("similarTrackImage")
    public static void loadSimilarTrackImage(ImageView imageView, String imageUrl) {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageUrl)
                    .apply(RequestOptions.errorOf(R.drawable.no_image_available))
                    .apply(RequestOptions.overrideOf(100, 150))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);
    }

    @BindingAdapter("topTrackImage")
    public static void loadTopTrackImage(ImageView imageView, String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.overrideOf(100, 150))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);

            imageView.setBackgroundColor(Color.BLACK);
        } else {
            Glide.with(imageView.getContext()).asBitmap()
                    .load(imageView.getResources().getDrawable(R.drawable.generic_track))
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.overrideOf(250, 300))
                    .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .into(imageView);
            imageView.setBackgroundColor(Color.WHITE);
        }
    }


    @BindingAdapter({"artistViewPagerAdapter", "tabLayout"})
    public static void loadArtistViewPager(ViewPager viewPager, FragmentPagerAdapter adapter, TabLayout tabLayout) {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
