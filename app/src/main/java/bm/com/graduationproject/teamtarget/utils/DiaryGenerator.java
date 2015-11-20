package bm.com.graduationproject.teamtarget.utils;

/**
 * Created by bm on 2015/5/26.
 */
public class DiaryGenerator {


    public static String finishTask(String userName,String taskName){

        String str;
        str=userName+"完成了"+taskName;
        return str;
    }

    public static  String addComment(String userName,String taskName){
        String str;
        str=userName+"在"+taskName+"添加了评论";
        return str;
    }
}
