package me.xiu.xiu.campusvideo.work.model.video;

/**
 * Created by felix on 16/3/30.
 */
public interface VInfo {

    String getVId();

    String getName();

    String getDescription();

    Quality getQuality();

    enum Quality {
        NONE(""),
        BLUE("蓝光版");

        String type;

        Quality(String type) {
            this.type = type;
        }

        public static Quality value(String type) {
            for (Quality quality : values()) {
                if (quality.type.equals(type)) {
                    return quality;
                }
            }
            return NONE;
        }
    }
}
