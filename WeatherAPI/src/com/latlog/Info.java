package com.latlog;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

@SerializedName("statuscode")
@Expose
private Integer statuscode;
@SerializedName("copyright")
@Expose
private Copyright copyright;
@SerializedName("messages")
@Expose
private List<Object> messages = null;

public Integer getStatuscode() {
return statuscode;
}

public void setStatuscode(Integer statuscode) {
this.statuscode = statuscode;
}

public Copyright getCopyright() {
return copyright;
}

public void setCopyright(Copyright copyright) {
this.copyright = copyright;
}

public List<Object> getMessages() {
return messages;
}

public void setMessages(List<Object> messages) {
this.messages = messages;
}

}