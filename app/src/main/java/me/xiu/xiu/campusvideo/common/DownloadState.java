package me.xiu.xiu.campusvideo.common;

/**
 * Created by felix on 16/4/27.
 */
public enum DownloadState {
    WAITING(0),
    DOWNLOADING(1),
    PAUSE(2),
    DONE(3),
    ERROR(4);

    public int value;

    DownloadState(int value) {
        this.value = value;
    }

    public static DownloadState valueOf(int value) {
        for (DownloadState state : values()) {
            if (state.value == value)
                return state;
        }
        return WAITING;
    }
}
