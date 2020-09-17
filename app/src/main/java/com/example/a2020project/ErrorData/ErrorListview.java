package com.example.a2020project.ErrorData;

public class ErrorListview {

    private String errData;
    private String errDataInform;


    public void seterrData(String data) {
        this.errData = data;
    }
    public void seterrDataInform(String inform) {
        this.errDataInform = inform;
    }

    public String geterrData() {
        return errData;
    }
    public String geterrDataInform() {
        return errDataInform;
    }

}
