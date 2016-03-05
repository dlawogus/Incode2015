package dev.woody.ext.datainfo;

/**
 * Created by imjaehyun on 15. 8. 17..
 */
public class it_bw_code_info
{
    String vnumber;
    String config;
    String start;
    String end;
    String url;
    String type;
    String man_hit;
    String women_hit;
    String unknown_hit;
    boolean isChecked;

    public it_bw_code_info(){}
    public it_bw_code_info(String vnum, String conf, String start, String end, String url){
        this.vnumber = vnum;
        this.config = conf;
        this.start = start;
        this.end = end;
        this.url = url;
    }

    public void setIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }
    public void setVnumber(String vnumber) { this.vnumber = vnumber; }
    public void setConfig(String config) { this.config = config; }
    public void setStart(String startDate) { this.start = startDate; }
    public void setEnd(String endDate) { this.end = endDate; }
    public void setUrl(String url) { this.url = url; }
    public void setMan_hit(String man_hit){ this.man_hit = man_hit; }
    public void setWomen_hit(String women_hit){ this.women_hit = women_hit; }
    public void setUnknown_hit(String unknown_hit){ this.unknown_hit = unknown_hit; }
    public void setType(String type){ this.type = type; }

    public String getType(){ return type; }

    public String getVnumber(){
        return vnumber;
    }

    public String getConfig(){
        return config;
    }

    public String getStart(){
        return start;
    }

    public String getEnd(){
        return end;
    }

    public String getUrl(){
        return url;
    }

    public String getMan_hit(){ return man_hit; }

    public String getWomen_hit(){ return women_hit; }

    public String getUnknown_hit(){ return unknown_hit; }

    public boolean getIschecked(){
        return isChecked;
    }
}
