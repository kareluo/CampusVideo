package io.vov.vitamio;

/**
 * Created by felix on 16/6/15.
 */
public interface MediaPlayerControlable {
    
    void start();

    void pause();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    int getBufferPercentage();
}
