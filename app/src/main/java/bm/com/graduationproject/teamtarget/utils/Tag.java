package bm.com.graduationproject.teamtarget.utils;

/**
 * Created by bm on 2015/5/13.
 */
public class Tag {

    private int sign;
    private int resource;
    private String name;

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(int resource, int sign,String name) {
        this.resource = resource;
        this.sign = sign;
        this.name=name;
    }
}
