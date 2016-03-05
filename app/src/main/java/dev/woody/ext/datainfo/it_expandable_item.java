package dev.woody.ext.datainfo;

/**
 * Created by imjaehyun on 15. 8. 20..
 */
public class it_expandable_item {
    public String title;
    public String contents;
    public String date;

    public it_expandable_item(String title, String contents){
        this.title = title;
        this.contents = contents;
        this.date = date;
    }
    public it_expandable_item(){}

    public void setTitle(String title){ this.title = title; }
    public void setContents(String contents){ this.contents = contents; }
    public void setDate(String date){ this.date = date; }

    public String getTitle(){ return title; }
    public String getContents(){ return contents; }
    public String getDate(){ return date; }
}
