package com.firebase.ginggingi.memoappwithserver.DataModels;

/**
 * Created by GingGingI on 2018-04-09.
 */

public class MemoDataModel {
    private int idx;
    private String Time;
    private String Title;
    private String Content;

    public void  setDatas(int idx, String Time, String Title, String Content){
        this.idx = idx;
        this.Time = Time;
        this.Title = Title;
        this.Content = Content;
    }

    public int getIdx() {
        return idx;
    }
    public String getTime() {
        return Time;
    }
    public String getTitle() {
        return Title;
    }
    public String getContent() {
        return Content;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
    public void setTime(String time) {
        Time = time;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public void setContent(String content) {
        Content = content;
    }
}
