package me.xiu.xiu.campusvideo.work.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by felix on 2017/12/19 下午10:35.
 */

/*
<root>
    <a>冰与火的青春(每天更新3集)</a>
    <b>687474703A2F2F6D382E6E65746B75752E636F6D2F642F6C786A2F62696E67797568756F646571696E676368756E2F2A2A2E6D7034</b>
    <c>贾乃亮,颖儿,杜淳,熊乃瑾,刘烨</c>
    <d>潘镜丞</d>
    <e>偶像,都市</e>
    <f>金融才子共谱热血青春，贾乃亮领衔80后职场奋斗</f>
    <g>电视剧</g>
    <h>http://tv.sohu.com</h>
    <i>大陆</i>
    <z>1</z>
    <v>2015-7-12</v>
    <yd>0</yd>
    <ydd>0</ydd>
</root>
*/

@Root(name = "m", strict = false)
public class Video {

    @Element(name = "a", required = false)
    private String name;

    @Element(name = "b", required = false)
    private String id;

    @Element(name = "c", required = false)
    private String actors;

    @Element(name = "d", required = false)
    private String director;

    @Element(name = "e", required = false)
    private String e;

    @Element(name = "f", required = false)
    private String brief;

    @Element(name = "g", required = false)
    private String type;

    @Element(name = "h", required = false)
    private String source;

    @Element(name = "i", required = false)
    private String i;

    @Element(name = "z", required = false)
    private String z;

    @Element(name = "v", required = false)
    private String v;

    @Element(name = "yd", required = false)
    private String yd;

    @Element(name = "ydd", required = false)
    private String ydd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getYd() {
        return yd;
    }

    public void setYd(String yd) {
        this.yd = yd;
    }

    public String getYdd() {
        return ydd;
    }

    public void setYdd(String ydd) {
        this.ydd = ydd;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                '}';
    }
}
