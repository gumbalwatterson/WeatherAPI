package com.latlog;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

@SerializedName("info")
@Expose
private Info info;
@SerializedName("options")
@Expose
private Options options;
@SerializedName("results")
@Expose
private List<Result> results = null;

public Info getInfo() {
return info;
}

public void setInfo(Info info) {
this.info = info;
}

public Options getOptions() {
return options;
}

public void setOptions(Options options) {
this.options = options;
}

public List<Result> getResults() {
return results;
}

public void setResults(List<Result> results) {
this.results = results;
}

}