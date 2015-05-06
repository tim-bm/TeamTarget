package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/1.
 */
public class Project {

    private int Id;
    private String Name;
    private String ownership;
    private String publicity;
    private int creator;
    private int finishStatus;

    public Project(){

    }


    public Project(int id, String name, String ownership, String publicity, int creator, int finishStatus) {
        Id = id;
        Name = name;
        this.ownership = ownership;
        this.publicity = publicity;
        this.creator = creator;
        this.finishStatus = finishStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getPublicity() {
        return publicity;
    }

    public void setPublicity(String publicity) {
        this.publicity = publicity;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(int finishStatus) {
        this.finishStatus = finishStatus;
    }
}
