package com.elkcreek.rodneytressler.musicapp.utils;

public class Constants {
    public static final String BASE_URL = "https://ws.audioscrobbler.com";
    public static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/";
    public static final String LYRICS_BASE_URL = "https://api.lyrics.ovh/v1/";
    public static final String API_KEY = "a8960a28bf9866251a301af8c95b234c";
    public static final String YOUTUBE_API_KEY = "AIzaSyCbS_9gcgFNop0nSaV9bBddviOXUUQShAc";
    public static final String ARTIST_UID_KEY = "artist_uid_key";
    public static final String ARTIST_NAME_KEY = "artist_name_key";
    public static final String TRACK_UID_KEY = "track_uid_key";
    public static final String TRACK_NAME_KEY = "track_name_key";
    public static final String ALBUM_NAME_KEY = "album_name_key";
    public static final String ALBUM_UID_KEY = "album_uid_key";
    public static final String ALBUM_IMAGE_URL_KEY = "album_image_url_key";
    public static final String DATABASE_KEY = "database_key";
    public static final String SHAREDPREFKEY = "shared_pref_key";
    public static final String WEEKOFYEAR = "week_of_year";
    public static final String YEAR = "year";


    //Fragment Tags
    public static final String SEARCH_FRAGMENT_TAG = "search_fragment_tag";
    public static final String BIO_FRAGMENT_TAG = "bio_fragment_tag";
    public static final String TRACKS_FRAGMENT_TAG = "tracks_fragment_tag";
    public static final String PLAY_TRACK_FRAGMENT_TAG = "play_track_fragment_tag";
    public static final String ALBUMS_TAG = "albums_tag";
    public static final String YOUTUBE_TAG = "youtube_tag";
    public static final String ALBUM_TRACKS_TAG = "album_tracks_tag";
    public static final String ARTIST_MAIN_TAG = "artist_main_tag";
    public static final String ALBUM_MAIN_TAG = "album_main_tag";
    public static final String ALBUM_BIO_TAG = "album_bio_tag";
    public static final String YOUTUBE_VIDEO_TAG = "youtube_video_tag";
    public static final String TRACK_MAIN_TAG = "track_main_tag";
    public static final String SEARCH_MAIN_TAG = "search_main_tag";

    //Retrofit
    public static final String MUSIC_RETROFIT = "music_retrofit";
    public static final String YOUTUBE_RETROFIT = "youtube_retrofit";
    public static final String LYRICS_RETROFIT = "lyrics_retrofit";

    //Error Message
    public static final String UNABLE_TO_LOAD_VIDEO = "Unable to load video. Please try again";
    public static final String NO_SUMMARY_AVAILABLE_TEXT = "No summary available.";
    public static final String NO_CONTENT_AVAILABLE_TEXT = "No content available.";
    public static final String NO_LYRICS_AVAILABLE = "Sorry, no lyrics available for this particular track! Please try again at a later time";

    //Action Bar Default Titles
    public static final String SEARCH_TITLE = "Search";
    public static final String EMPTY_TEXT = "";

    //String Values
    public static final String READ_MORE = "Read More";
    public static final String COLLAPSE = "Collapse";

    //Null Values
    public static final String NO_ARTIST_BIO_AVAILABLE = "Sorry, no biography available for this artist. :(";
    public static final String NO_ALBUM_BIO_AVAILABLE = "Sorry, no biography available for this album. :(";
    public static final String NO_TRACK_BIO_AVAILABLE = "Sorry, no biography available for this track. :(";
    //Albums no Albums Available
    //All Tracks no Tracks Available
    //Album Tracks no Tracks available for this album
    public static final String NO_SIMILAR_ARTISTS = "No similar artists found.";
    public static final String NO_SIMILAR_TRACKS = "No similar tracks found.";
    //No Artist Bio Image
    //No Album Bio Image
    //No Track Bio Image
    //No Youtube Video
    public static final String NO_LYRICS = "No lyrics found";
    public static final String TOP_TRACKS = "Today\'s Top Tracks";

    public static String getYoutubeFragmentTag(int viewPagerId) {
        return "android:switcher:" + viewPagerId + ":" + 1;
    }
}
