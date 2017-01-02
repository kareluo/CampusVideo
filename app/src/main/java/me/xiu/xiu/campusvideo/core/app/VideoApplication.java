package me.xiu.xiu.campusvideo.core.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by felix on 15/9/18.
 */
public class VideoApplication extends TinkerApplication {

    public VideoApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                "me.xiu.xiu.campusvideo.core.app.VideoApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
