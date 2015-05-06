package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/4.
 */
public class Task {

    private int id;
    private String name;
    private String description;
    private int creator;
    private String deadline;
    private String comment;
    private int tag;
    private int distributeTo;
    private int taskListId;
    private int projectId;

    public Task() {
    }

    public Task(int id, String name,String description, int creator, String deadline, String comment, int tag, int distributeTo, int taskListId, int projectId) {
        this.id = id;
        this.name=name;
        this.description = description;
        this.creator = creator;
        this.deadline = deadline;
        this.comment = comment;
        this.tag = tag;
        this.distributeTo = distributeTo;
        this.taskListId = taskListId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getDistributeTo() {
        return distributeTo;
    }

    public void setDistributeTo(int distributeTo) {
        this.distributeTo = distributeTo;
    }

    public int getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
