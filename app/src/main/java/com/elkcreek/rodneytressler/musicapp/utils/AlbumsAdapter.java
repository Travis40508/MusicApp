package com.elkcreek.rodneytressler.musicapp.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elkcreek.rodneytressler.musicapp.R;
import com.elkcreek.rodneytressler.musicapp.repo.network.MusicApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> {

    private List<MusicApi.Album> albumList;

    public AlbumsAdapter(List<MusicApi.Album> albumList) {
        this.albumList = albumList;
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
    }

    @Override
    public int getItemCount() {
        return albumList.size();
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
            Glide.with(itemView.getContext()).load(album.getTrackImage().get(2).getImageUrl()).into(albumCover);
            albumName.setText(album.getAlbumName());
        }
    }
}
