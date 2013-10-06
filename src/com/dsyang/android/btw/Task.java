package com.dsyang.android.btw;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Task {
    private static final String JSON_TEXT = "text";
    private static final String JSON_DATE = "dateCreated";

    private String mText;
    private Date mCreated;

    public Task (String text) {
        mText = text;
        mCreated = new Date();
    }

    public Task (JSONObject object) throws JSONException {
        mText = object.getString(JSON_TEXT);
        mCreated = new Date(object.getLong(JSON_DATE));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_TEXT, mText);
        json.put(JSON_DATE, mCreated.getTime());

        return json;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

}
