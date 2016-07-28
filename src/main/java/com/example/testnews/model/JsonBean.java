package com.example.testnews.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */

public class JsonBean {
    private String reason;
    private NewsBean result;

    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NewsBean getResult() {
        return result;
    }

    public void setResult(NewsBean result) {
        this.result = result;
    }
}
