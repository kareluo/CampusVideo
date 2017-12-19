package me.xiu.xiu.campusvideo.dao.preference;

import com.google.gson.reflect.TypeToken;

/**
 * Created by felix on 2017/11/10 上午10:42.
 */

public enum Preferences {

    VERSION(Preference.Type.STRING);

    private int type = Preference.Type.STRING.type;

    private int group = Preference.Group.DEFAULT.group;

    private TypeToken<?> token;

    Preferences() {
        this(Preference.Type.STRING, Preference.Group.DEFAULT, null);
    }

    Preferences(Preference.Type type) {
        this(type, Preference.Group.DEFAULT, null);
    }

    Preferences(Preference.Type type, TypeToken<?> token) {
        this(type, Preference.Group.DEFAULT, token);
    }

    Preferences(Preference.Type type, Preference.Group group, TypeToken<?> token) {
        this.type = type.type;
        this.group = group.group;
        this.token = token;
    }


}
