package io.vov.vitamio;

import android.view.View;

/**
 * Created by felix on 16/6/15.
 */
public interface MediaControlable {

    void show();

    void show(int duration);

    void show(boolean anime);

    void hide();

    void hide(boolean anime);

    void finish();

    void setEnabled(boolean enable);

    void setMediaPlayer(MediaPlayerControlable controlable);

    void setAnchorView(View view);

    void setFileName(String name);

    boolean isShowing();
}
