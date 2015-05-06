package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/4.
 */
public class TaskList {

    private int id;
    private String name;
    private int projectId;

    public TaskList(){

    }

    public TaskList(int id, String name, int projectId) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
