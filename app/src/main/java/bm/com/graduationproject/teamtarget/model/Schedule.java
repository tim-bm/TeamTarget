package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/22.
 */
public class Schedule {
    private int id;
    private String date;
    private String name;
    private String content;

    public Schedule(){

    }

    public Schedule(int id, String date, String name, String content) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.content = content;
    }

    public Schedule(String date, String name, String content) {
        this.date = date;
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
