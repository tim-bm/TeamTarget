package bm.com.graduationproject.teamtarget.model;

/**
 * Created by bm on 2015/5/26.
 */
public class Diary {
    private int diaryId;
    private String date;
    private int taskId;
    private String content;

    public Diary() {
    }

    public Diary(int diaryId, String date, int taskId, String content) {
        this.diaryId = diaryId;
        this.date = date;
        this.taskId = taskId;
        this.content = content;
    }

    public Diary(int diaryId, String date, String content) {
        this.diaryId = diaryId;
        this.date = date;
        this.content = content;
    }

    public Diary(String date, int taskId, String content) {
        this.date = date;
        this.taskId = taskId;
        this.content = content;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
