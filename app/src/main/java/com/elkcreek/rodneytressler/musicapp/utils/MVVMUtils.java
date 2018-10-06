package com.elkcreek.rodneytressler.musicapp.utils;

import android.content.res.Configuration;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainActivity;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
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

    @BindingAdapter(value = {"data", "adapter", "searchText"}, requireAll = false)
    public static void artistRecyclerView(RecyclerView recyclerView, List<Object> list, Adapter adapter, String searchedText) {

        adapter.setAdapterItems(list);

        List<Object> searchedTrackList = new ArrayList<>();

        if(searchedText != null) {
            for (Object item : list) {
                if (((MusicApi.Track) item).getTrackName().toLowerCase().contains(searchedText.toLowerCase())) {
                    searchedTrackList.add(item);
                    adapter.setAdapterItems(searchedTrackList);
                }
            }
        }
    }

    @BindingAdapter({"similarArtistData", "mainViewModel"})
    public static void similarArtistRecyclerView(RecyclerView recyclerView, List<MusicApi.Artist> similarArtists, MainViewModel mainViewModel) {
        SimilarArtistAdapter similarArtistAdapter = new SimilarArtistAdapter(similarArtists, mainViewModel);
        recyclerView.setAdapter(similarArtistAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        similarArtistAdapter.notifyDataSetChanged();
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

    @BindingAdapter({"albumData", "mainViewModel"})
    public static void albumRecyclerView(RecyclerView recyclerView, List<MusicApi.Album> albumList, MainViewModel mainViewModel) {
        AlbumsAdapter albumsAdapter = new AlbumsAdapter(albumList, mainViewModel);
        recyclerView.setAdapter(albumsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        albumsAdapter.notifyDataSetChanged();
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

    @BindingAdapter({"allTracksData", "mainViewModel", "searchText"})
    public static void loadAllTracks(RecyclerView recyclerView, List<MusicApi.Track> trackList, MainViewModel mainViewModel, String searchText) {
        TracksAdapter adapter = new TracksAdapter(trackList, mainViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter.notifyDataSetChanged();

        List<MusicApi.Track> searchedTrackList = new ArrayList<>();
        for(MusicApi.Track item : trackList) {
            if(item.getTrackName().toLowerCase().contains(searchText.toLowerCase())) {
                searchedTrackList.add(item);
                adapter = new TracksAdapter(searchedTrackList, mainViewModel);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @BindingAdapter({"albumTracksData", "mainViewModel"})
    public static void loadAlbumTracks(RecyclerView recyclerView, List<MusicApi.Track> trackList, MainViewModel mainViewModel) {
        TracksAdapter adapter = new TracksAdapter(trackList, mainViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter.notifyDataSetChanged();
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

    @BindingAdapter({"similarTrackData", "mainViewModel"})
    public static void loadSimilarTracks(RecyclerView recyclerView, List<MusicApi.Track> similarTracks, MainViewModel mainViewModel) {
        SimilarTracksAdapter adapter = new SimilarTracksAdapter(similarTracks, mainViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        adapter.notifyDataSetChanged();
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

    @BindingAdapter({"topTrackData", "searchedTrackData", "mainViewModel"})
    public static void loadTopTracks(RecyclerView recyclerView, List<MusicApi.Track> topTracks, List<MusicApi.SearchedTrack> searchedTracks, MainViewModel mainViewModel) {
        TopTracksAdapter adapter = new TopTracksAdapter(topTracks, mainViewModel);
        SearchedTracksAdapter searchedTracksAdapter = new SearchedTracksAdapter(searchedTracks, mainViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        if(searchedTracks.size() == 0) {
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setAdapter(searchedTracksAdapter);
            adapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter({"artistViewPagerAdapter", "tabLayout"})
    public static void loadArtistViewPager(ViewPager viewPager, FragmentPagerAdapter adapter, TabLayout tabLayout) {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
