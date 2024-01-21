package cc.newex.commons.openapi.specs.enums;

/**
 * Content-Type: MediaType: Internet Media Type.<br/>
 * Created by newex-team on 2018/1/31 17:49.
 */
public enum ContentTypeEnum {

    TEXT_HTML("text/html", "HTML format"),
    TEXT_HTML_UTF8("text/html;charset=UTF-8", "HTML format, utf-8 encoding."),
    TEXT_PLAIN("text/plain", "Plain text format"),
    TEXT_XML("text/xml", "XML format"),
    APPLICATION_JSON("application/json", "JSON data format"),
    APPLICATION_JSON_UTF8("application/json;charset=UTF-8", "JSON data format, utf-8 encoding."),
    APPLICATION_OCTET_STREAM("application/octet-stream", "Binary stream data (such as common file downloads)"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", "The form form data is encoded as the key/value format to the server (the format of the default submission data for the form)"),
    MULTIPART_FORM_DATA("multipart/form-data", "When the file is uploaded in the form form."),;

    private final String contentType;
    private final String comment;

    ContentTypeEnum(final String contentType, final String comment) {
        this.contentType = contentType;
        this.comment = comment;
    }

    public String getContentType() {
        return this.contentType;
    }
}
