package com.advisemetech.adviseme;

import ir.mirrajabi.searchdialog.core.Searchable;

public class All_UserMmber  implements Searchable {

    String name,uid,prof,url,  requestKey, mTitle= "Search for an Adviser";
    public All_UserMmber(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String getTitle() {
        return mTitle;
    }


    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }
}
