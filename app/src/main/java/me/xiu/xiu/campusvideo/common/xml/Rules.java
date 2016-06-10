package me.xiu.xiu.campusvideo.common.xml;

import android.os.Bundle;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import me.xiu.xiu.campusvideo.ui.fragment.BannerFragment;
import me.xiu.xiu.campusvideo.ui.fragment.VideosFragment;

/**
 * Created by felix on 16/4/9.
 */
public class Rules {

    public interface PUBLIC_CLASS {
        ParseRule RULE_BANNER = new ParseRule(Xml.EDU_BANNER, Xml.TAG_GKK, 15);
        ParseRule RULE_VIDEOS = new ParseRule(Xml.PUBLIC_CLASS_DATE, Xml.TAG_M, Integer.MAX_VALUE);
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
        PageRule[] RULES = {
                new PageRule("热播", PageRule.Page.BANNER, BannerFragment.newArgument(Xml.MOVIE_BANNER, Xml.TAG_M, 15, Xml.MOVIE_ACTION_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("内地", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.MOVIE_MAINLAND_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("欧美", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.MOVIE_XVIDEO_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("港台", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.MOVIE_HKTW_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("日韩", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.MOVIE_KKC_DATE, Xml.TAG_M, Integer.MAX_VALUE))
        };
    }

    public interface TELEPLAY {
        PageRule[] RULES = {
                new PageRule("热播", PageRule.Page.BANNER, BannerFragment.newArgument(Xml.TELEPLAY_BANNER, Xml.TAG_M, 15, Xml.TELEPLAY_SYNC_HOT, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("内地", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TELEPLAY_MAINLAND_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("欧美", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TELEPLAY_XVIDEO_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("港台", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TELEPLAY_HKTW_DATE, Xml.TAG_M, Integer.MAX_VALUE)),
                new PageRule("日韩", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TELEPLAY_KKC_DATE, Xml.TAG_M, Integer.MAX_VALUE))
        };
    }

    public interface ANIME {
        PageRule[] RULES = {
                new PageRule("新番", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.ANIME_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("新番")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                })),
                new PageRule("连载", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.ANIME_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("连载")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                })),
                new PageRule("完结", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.ANIME_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("完结")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                })),
                new PageRule("剧场版", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.ANIME_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("剧场版")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                })),
        };
    }

    public interface TV_SHOW {
        PageRule[] RULES = {
                new PageRule("综艺节目", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TV_SHOW_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("综艺节目")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                })),
                new PageRule("MV演唱会", PageRule.Page.VIDEOS, VideosFragment.newArgument(Xml.TV_SHOW_DATE, Xml.TAG_M, Integer.MAX_VALUE, new Filter<XmlObject>() {
                    @Override
                    public XmlObject call(XmlObject xmlObject) {
                        List<Bundle> bundles = new ArrayList<>();
                        Bundle[] elements = xmlObject.getElements();
                        for (Bundle element : elements) {
                            if (element.getString("e", "").contains("mv演唱会")) {
                                bundles.add(element);
                            }
                        }
                        xmlObject.setElements(bundles.toArray(new Bundle[bundles.size()]));
                        return xmlObject;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                }))
        };
    }
}
