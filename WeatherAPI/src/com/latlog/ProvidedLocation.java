package com.latlog;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProvidedLocation {

@SerializedName("location")
@Expose
private String location;

public String getLocation() {
return location;
}

public void setLocation(String location) {
this.location = location;
}

}