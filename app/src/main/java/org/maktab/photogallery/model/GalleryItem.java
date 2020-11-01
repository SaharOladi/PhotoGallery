package org.maktab.photogallery.model;

import com.google.gson.annotations.SerializedName;

public class GalleryItem {

    @SerializedName("id")
    private String mId;

    @SerializedName("owner")
    private String mOwner;

    @SerializedName("secret")
    private String mSecret;

    @SerializedName("server")
    private String mServer;

    @SerializedName("farm")
    private int mFarm;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("ispublic")
    private int mIsPublic;

    @SerializedName("isfriend")
    private int mIsFriend;

    @SerializedName("isfamily")
    private int mIsFamiliy;

    @SerializedName("url_s")
    private String mUrl;

    @SerializedName("height_s")
    private int mHeightS;

    @SerializedName("width_s")
    private int mWidthS;

    //Getter & Setter
    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(String server) {
        mServer = server;
    }

    public Integer getFarm() {
        return mFarm;
    }

    public void setFarm(Integer farm) {
        mFarm = farm;
    }

    public Integer getIsPublic() {
        return mIsPublic;
    }

    public void setIsPublic(Integer isPublic) {
        mIsPublic = isPublic;
    }

    public Integer getIsFriend() {
        return mIsFriend;
    }

    public void setIsFriend(Integer isFriend) {
        mIsFriend = isFriend;
    }

    public Integer getIsFamiliy() {
        return mIsFamiliy;
    }

    public void setIsFamiliy(Integer isFamiliy) {
        mIsFamiliy = isFamiliy;
    }

    public Integer getHeightS() {
        return mHeightS;
    }

    public void setHeightS(Integer heightS) {
        mHeightS = heightS;
    }

    public Integer getWidthS() {
        return mWidthS;
    }

    public void setWidthS(Integer widthS) {
        mWidthS = widthS;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    //Constructor
    public GalleryItem() {
    }

    public GalleryItem(String id, String owner, String secret, String server, Integer farm,
                       String title, Integer isPublic, Integer isFriend, Integer isFamiliy,
                       String url, Integer heightS, Integer widthS) {
        mId = id;
        mOwner = owner;
        mSecret = secret;
        mServer = server;
        mFarm = farm;
        mTitle = title;
        mIsPublic = isPublic;
        mIsFriend = isFriend;
        mIsFamiliy = isFamiliy;
        mUrl = url;
        mHeightS = heightS;
        mWidthS = widthS;
    }

    public GalleryItem(String id, String title, String url) {
        mId = id;
        mTitle = title;
        mUrl = url;
    }

    public String toStringConvert(){

        String result = "{"
                + "id:" + mId
                + "owner:" + mOwner
                + "secret:" + mSecret
                + "server:" + mServer
                + "farm:" + mFarm
                + "title:" + mTitle
                + "ispublic:" + mIsPublic
                + "isfriend:" + mIsFriend
                + "isfamily:" + mIsFamiliy
                + "url_s:" + mUrl
                + "height_s:" + mHeightS
                + "width_s:" + mWidthS
                + "}";
        return result;
    }


    /**
    private String mId;
    private String mTitle;
    private String mUrl;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public GalleryItem() {
    }

    public GalleryItem(String id, String title, String url) {
        mId = id;
        mTitle = title;
        mUrl = url;
    }
     **/
}
