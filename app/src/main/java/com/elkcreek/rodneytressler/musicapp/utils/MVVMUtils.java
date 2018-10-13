package com.elkcreek.rodneytressler.musicapp.utils;

import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class MVVMUtils {

    @BindingAdapter(value = {"imageUrl", "imageObject"}, requireAll = false)
    public static void loadImages(ImageView imageView, String imageUrl, Object imageObject) {
        int width = 250;
        int height = 300;
        Drawable genericDrawable;

        RequestBuilder<Bitmap> requestBuilder = Glide.with(imageView.getContext()).asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.encodeFormatOf(Bitmap.CompressFormat.PNG))
                .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .transition(BitmapTransitionOptions.withCrossFade());

        if (imageObject instanceof MusicApi.SimilarArtist || imageObject instanceof MusicApi.SimilarTrack || imageObject instanceof MusicApi.Album || imageObject instanceof MusicApi.TopTrack) {
            width = 100;
            height = 150;
            requestBuilder.apply(RequestOptions.circleCropTransform());
        }

        if (imageObject instanceof MusicApi.SimilarArtist || imageObject instanceof MusicApi.Artist) {
            genericDrawable = imageView.getResources().getDrawable(R.drawable.generic_band);
        } else if (imageObject instanceof MusicApi.Album) {
            genericDrawable = imageView.getResources().getDrawable(R.drawable.generic_album);
        } else {
            genericDrawable = imageView.getResources().getDrawable(R.drawable.generic_track);
        }

        requestBuilder
                .apply(RequestOptions.overrideOf(width, height))
                .apply(RequestOptions.errorOf(genericDrawable))
                .into(imageView);
    }

    @BindingAdapter(value = {"data", "recyclerViewAdapter", "searchText"}, requireAll = false)
    public static void recyclerViewLoading(RecyclerView recyclerView, List<Object> list, RecyclerViewAdapter recyclerViewAdapter, String searchedText) {
        if (searchedText == null) {
            recyclerViewAdapter.setAdapterItems(list);
        }

        Object listItem = null;
        if(list != null && !list.isEmpty()) {
            listItem = list.get(0);
        }

        if(recyclerView.getLayoutManager() instanceof GridLayoutManager && (listItem instanceof MusicApi.SimilarArtist || listItem instanceof MusicApi.SimilarTrack)) {
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(recyclerView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 3);
        } else if(recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(recyclerView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        }

        List<Object> searchedTrackList = new ArrayList<>();
        if (searchedText != null) {
            for (Object item : list) {
                if (((MusicApi.Track) item).getTrackName().toLowerCase().contains(searchedText.toLowerCase())) {
                    searchedTrackList.add(item);
                    recyclerViewAdapter.setAdapterItems(searchedTrackList);
                }
            }
        }
    }


    @BindingAdapter({"artistViewPagerAdapter", "tabLayout"})
    public static void loadViewPager(ViewPager viewPager, FragmentPagerAdapter adapter, TabLayout tabLayout) {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("tabLayoutVisibility")
    public static void checkTabLayoutVisibility(TabLayout tabLayout, TrackViewPagerAdapter adapter) {
        int orientation = tabLayout.getContext().getResources().getConfiguration().orientation;
        boolean isYoutubeScreenVisible = adapter.getItem(1).getUserVisibleHint();

        if(orientation == Configuration.ORIENTATION_LANDSCAPE && isYoutubeScreenVisible) {
            ((MainActivity)tabLayout.getContext()).getSupportActionBar().hide();
            tabLayout.setVisibility(View.GONE);
        } else {
            ((MainActivity)tabLayout.getContext()).getSupportActionBar().show();
            tabLayout.setVisibility(View.VISIBLE);
        }
    }
}
