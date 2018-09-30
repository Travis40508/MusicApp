package com.elkcreek.rodneytressler.musicapp.di.modules;

import com.elkcreek.rodneytressler.musicapp.ui.AlbumBioView.AlbumBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AlbumMainView.AlbumMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AlbumTracksView.AlbumTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.albumsview.AlbumsFragment;
import com.elkcreek.rodneytressler.musicapp.ui.AllTracksView.AllTracksFragment;
import com.elkcreek.rodneytressler.musicapp.ui.artistbioview.ArtistBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.ArtistMainView.ArtistMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.artistsearchview.ArtistSearchFragment;
import com.elkcreek.rodneytressler.musicapp.ui.mainview.MainActivity;
import com.elkcreek.rodneytressler.musicapp.ui.SearchMainView.SearchMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackBioView.TrackBioFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackMainView.TrackMainFragment;
import com.elkcreek.rodneytressler.musicapp.ui.TrackSearchView.TrackSearchFragment;
import com.elkcreek.rodneytressler.musicapp.ui.YoutubeView.YoutubeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector
    abstract ArtistBioFragment contributesBioFragmentInjector();

    @ContributesAndroidInjector
    abstract ArtistSearchFragment contributesSearchFragmentInjector();

    @ContributesAndroidInjector
    abstract AllTracksFragment contributesTracksFragmentInjector();

    @ContributesAndroidInjector
    abstract TrackBioFragment contributesPlayTrackFragmentInjector();

    @ContributesAndroidInjector
    abstract AlbumsFragment contributesAlbumsFragmentInjector();

    @ContributesAndroidInjector
    abstract AlbumTracksFragment contributesAlbumTracksFragmentInjector();

    @ContributesAndroidInjector
    abstract ArtistMainFragment contributesArtistMainFragmentInjector();

    @ContributesAndroidInjector
    abstract AlbumMainFragment contributesAlbumMainFragmentInjector();

    @ContributesAndroidInjector
    abstract AlbumBioFragment contributesAlbumBioFragmentInjector();

    @ContributesAndroidInjector
    abstract YoutubeFragment contributesYoutubeFragmentInjector();

    @ContributesAndroidInjector
    abstract TrackMainFragment contributesTrackMainFragmentInjector();

    @ContributesAndroidInjector
    abstract TrackSearchFragment contributesTrackSearchFragmentInjector();

    @ContributesAndroidInjector
    abstract SearchMainFragment contributesSearchMainFragmentInjector();
}
