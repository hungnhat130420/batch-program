package org.batch.params;

public class JobParams {
    private String paramKey;
    private String paramValue;

    public JobParams(String paramKey, String paramValue) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "JobParams{" +
                "paramKey='" + paramKey + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
