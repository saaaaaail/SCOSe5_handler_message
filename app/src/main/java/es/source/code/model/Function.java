package es.source.code.model;

/**
 * Created by sail on 2018/10/7.
 */

public class Function {

    //图标
    private int img;

    //名称
    private String name;

    //标识
    private String tag;

    public Function(int img,String name,String tag){
        this.img=img;
        this.name=name;
        this.tag=tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
