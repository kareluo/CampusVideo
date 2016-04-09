package me.xiu.xiu.campusvideo.common.xml;

import me.xiu.xiu.campusvideo.ui.fragment.BannerFragment;

/**
 * Created by felix on 16/4/9.
 */
public class Rules {

    public interface PUBLIC_CLASS {
        ParseRule RULE_BANNER = new ParseRule(Xml.EDU_BANNER, Xml.TAG_GKK, 15);
        ParseRule RULE_VIDEOS = new ParseRule(Xml.PUBLICLASS_DATE, Xml.TAG_M, Integer.MAX_VALUE);
    }

    public interface DOCUMENTARY {
        ParseRule RULE_BANNER = new ParseRule(Xml.EDU_BANNER, Xml.TAG_JLP, 15);
        ParseRule RULE_VIDEOS = new ParseRule(Xml.DOCUMENTARY_DATE, Xml.TAG_M, Integer.MAX_VALUE);
    }

    public interface CATHEDRA {
        ParseRule RULE_BANNER = new ParseRule(Xml.EDU_BANNER, Xml.TAG_JZ, 15);
        ParseRule RULE_VIDEOS = new ParseRule(Xml.CATHEDRA_DATE, Xml.TAG_M, Integer.MAX_VALUE);
    }

    public interface MOVIE {
        ParseRule RULE_BANNER = new ParseRule(Xml.EDU_BANNER, Xml.TAG_JZ, 15);
        ParseRule RULE_VIDEOS = new ParseRule(Xml.CATHEDRA_DATE, Xml.TAG_M, Integer.MAX_VALUE);

        PageRule[] RULES = {
                new PageRule("1", PageRule.Page.BANNER, BannerFragment.newArgument(Xml.MOVIE_BANNER, Xml.TAG_M, 15, Xml.MOVIE_WARFARE_DATE, Xml.TAG_M, Integer.MAX_VALUE))
        };
    }

    public interface TELEPLAY {
        PageRule[] RULES = {
                new PageRule("电视剧", PageRule.Page.BANNER, BannerFragment.newArgument(Xml.TELEPLAY_BANNER, Xml.TAG_M, 15, Xml.TELEPLAY_HKTW_DATE, Xml.TAG_M, Integer.MAX_VALUE))
        };
    }

    public interface ANIME {
        ParseRule RULE = new ParseRule(Xml.ANIME_DATE, Xml.TAG_M, Integer.MAX_VALUE);
    }

    public interface TV_SHOW {
        ParseRule RULE = new ParseRule(Xml.TVSHOW_DATE, Xml.TAG_M, Integer.MAX_VALUE);
    }
}
