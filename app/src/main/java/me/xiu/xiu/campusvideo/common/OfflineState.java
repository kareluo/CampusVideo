package me.xiu.xiu.campusvideo.common;

/**
 * Created by felix on 16/4/27.
 */
public enum OfflineState {
    WAITING(0),
    OFFLINING(1),
    PAUSE(2),
    DONE(3),
    ERROR(4);

    public int value;

    OfflineState(int value) {
        this.value = value;
    }

    public boolean canPause() {
        return this == WAITING || this == OFFLINING;
    }

    public static boolean pause(int value) {
        return WAITING.value == value || OFFLINING.value == value;
    }

    public static boolean resume(int value) {
        return WAITING.value == value || PAUSE.value == value || ERROR.value == value;
    }

    public static OfflineState valueOf(int value) {
        for (OfflineState state : values()) {
            if (state.value == value)
                return state;
        }
        return WAITING;
    }
}
