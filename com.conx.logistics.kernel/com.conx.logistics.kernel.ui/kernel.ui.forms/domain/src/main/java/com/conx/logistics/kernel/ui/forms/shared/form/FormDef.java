package com.conx.logistics.kernel.ui.forms.shared.form;

public class FormDef {
    
    private String formUrl;
    private String jsonContent;
    
    public FormDef(String formUrl, String jsonContent) {
        this();
        this.formUrl = formUrl;
        this.jsonContent = jsonContent;
    }

    public FormDef() {
    }
    
    public String getFormUrl() {
        return formUrl;
    }
    
    public String getJsonContent() {
        return jsonContent;
    }
    
    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }
    
    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }
}
