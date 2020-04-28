package com.latlog;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Copyright {

@SerializedName("text")
@Expose
private String text;
@SerializedName("imageUrl")
@Expose
private String imageUrl;
@SerializedName("imageAltText")
@Expose
private String imageAltText;

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getImageAltText() {
return imageAltText;
}

public void setImageAltText(String imageAltText) {
this.imageAltText = imageAltText;
}

}



