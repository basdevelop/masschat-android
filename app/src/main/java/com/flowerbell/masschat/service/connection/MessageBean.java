package com.flowerbell.masschat.service.connection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wsli on 2017/8/1.
 */

public class MessageBean {
    public static final  int MSG_TYPE_CTL_REGISTER          = 1;
    public static final  int MSG_TYPE_CTL_NEWUSERONLINE     = 2;
    public static final  int MSG_TYPE_CTL_OFFLINE           = 3;
    public static final  int MSG_TYPE_DATA_TEXT             = 4;

    String  from;
    String  to;
    int     type;
    String  data;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", data='" + data + '\'' +
                '}';
    }

    public String toJSONString() {
        try {

            JSONObject object = new JSONObject();
            object.put("from", from);
            object.put("to", to);
            object.put("type", type);
            object.put("data", data);
            return object.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
