package com.elkcreek.rodneytressler.musicapp.utils;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {

    private final RequestManager glide;
    private List<MusicApi.Album> albumList;
    private AlbumCallback callback;

    public AlbumsAdapter(RequestManager glide, List<MusicApi.Album> albumList) {
        this.albumList = albumList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_album, viewGroup, false);
        return new AlbumsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsViewHolder albumsViewHolder, int position) {
        albumsViewHolder.bindView(albumList.get(position));
        albumsViewHolder.itemView.setOnClickListener(albumsViewHolder.onAlbumClicked(albumList.get(position)));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void setAlbumCallback(AlbumCallback albumCallback) {
        this.callback = albumCallback;
    }

    public class AlbumsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_album_item)
        protected ImageView albumCover;

        @BindView(R.id.text_album_name)
        protected TextView albumName;

        public AlbumsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(MusicApi.Album album) {
            glide.asBitmap()
                    .load(album.getTrackImage().get(2).getImageUrl())
                    .apply(RequestOptions.formatOf(PREFER_ARGB_8888))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(albumCover);
            albumName.setText(album.getAlbumName());
        }

        public View.OnClickListener onAlbumClicked(MusicApi.Album album) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.albumClicked(album);
                }
            };
        }
    }

    public interface AlbumCallback {
        void albumClicked(MusicApi.Album album);
    }
}
