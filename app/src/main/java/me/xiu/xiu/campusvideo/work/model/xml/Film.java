package me.xiu.xiu.campusvideo.work.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by felix on 2017/12/25 下午7:46.
 * <p>
 * <root>
 * <name>冰与火的青春(每天更新3集)</name>
 * <director>潘镜丞</director>
 * <actor>贾乃亮,颖儿,杜淳,熊乃瑾,刘烨</actor>
 * <region>大陆</region>
 * <channelid>电视剧</channelid>
 * <filmtype>剧情</filmtype>
 * <publishTime>2015-7-12</publishTime>
 * <languages>国语【中字】</languages>
 * <Contentnumber>36</Contentnumber>
 * <adddate>2015-8-6 13:59:03</adddate>
 * <pinglun1>贾乃亮变恶少调戏颖儿</pinglun1>
 * <pinglun2>金融才子共谱热血青春，贾乃亮领衔80后职场奋斗</pinglun2>
 * <brief>金融危机爆发之际，江长恩趁机收购高氏企业致使高启良走上绝路。十五年后，更名为罗浩的高启良之子，潜入康荣。处处碰壁的江焱第一次体会到了人情冷暖和成长的阵痛。表面冷漠、内心善良的夏冰给予了江焱在逆境中最暖心的帮助，两颗心越走越近。江焱与罗浩也一笑泯恩仇。一群正青春的年轻人携手写下他们最灿烂无悔记忆……</brief>
 * <pl></pl>
 * <jz>1</jz>
 * </root>
 */
@Root(name = "root", strict = false)
public class Film {

    @Element(name = "name", required = false)
    private String name;

    @Element(name = "director", required = false)
    private String director;

    @Element(name = "actor", required = false)
    private String actor;

    @Element(name = "region", required = false)
    private String region;

    @Element(name = "channelid", required = false)
    private String channelid;

    @Element(name = "filmtype", required = false)
    private String filmtype;

    @Element(name = "publishTime", required = false)
    private String publishTime;

    @Element(name = "languages", required = false)
    private String languages;

    @Element(name = "Contentnumber", required = false)
    private String Contentnumber;

    @Element(name = "adddate", required = false)
    private String adddate;

    @Element(name = "pinglun1", required = false)
    private String pinglun1;

    @Element(name = "pinglun2", required = false)
    private String pinglun2;

    @Element(name = "brief", required = false)
    private String brief;

    @Element(name = "pl", required = false)
    private String pl;

    @Element(name = "jz", required = false)
    private String jz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getFilmtype() {
        return filmtype;
    }

    public void setFilmtype(String filmtype) {
        this.filmtype = filmtype;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getContentnumber() {
        return Contentnumber;
    }

    public void setContentnumber(String contentnumber) {
        Contentnumber = contentnumber;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getPinglun1() {
        return pinglun1;
    }

    public void setPinglun1(String pinglun1) {
        this.pinglun1 = pinglun1;
    }

    public String getPinglun2() {
        return pinglun2;
    }

    public void setPinglun2(String pinglun2) {
        this.pinglun2 = pinglun2;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getJz() {
        return jz;
    }

    public void setJz(String jz) {
        this.jz = jz;
    }
}
