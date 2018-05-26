package com.company;

import cn.edu.hfut.dmic.contentextractor.News;

public class SDUNews extends News {
    private String author;
    private String editor;
    private String source;
    private int click;
    private String photography;

    public int sort = 0;

    public void setAuthor(String author) {
        this.author = author;
    }
    public void setEditor(String editor){ this.editor = editor; }
    public void setSource(String source){
        this.source = source;
    }
    public void setClick(int click){
        this.click = click;
    }
    public void setPotography(String photography){ this.photography = photography; }

    public String getAuthor(){
        return this.author;
    }
    public String getEditor(){
        return this.editor;
    }
    public String getSource(){
        return this.source;
    }
    public int getClick() { return this.click; }
    public String getPhotography(){return this.photography;}

}
