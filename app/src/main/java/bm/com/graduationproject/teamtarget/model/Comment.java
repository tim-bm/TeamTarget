package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/12.
 */
public class Comment{

    private int id;
    private int userId;
    private int taskId;
    private String content;
    private String date;


    public Comment(){

    }

    public Comment(int id, int userId, int taskId, String content, String date) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
        this.content = content;
        this.date = date;
    }

    public Comment(int id, int userId, int taskId) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
