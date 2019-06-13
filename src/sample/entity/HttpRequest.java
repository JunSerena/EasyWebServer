package sample.entity;

import java.util.Dictionary;
import java.util.HashMap;

public class HttpRequest {
    public HttpRequest(String requestText){
        String[] lines = requestText.replace("\r\n", "\r").split("\r");
        String[] requestLines = lines[0].split(" ");
        // 获取HTTP请求方式、请求的URL地址、HTTP协议版本
        if(requestLines.length >= 2)
        {
            setHttpMethod(requestLines[0]);
            setUrl(requestLines[1]);      //如 /index.jsp
            setHttpVersion(requestLines[2]);
        }
        HeaderMap = new HashMap<String, String>();
        BodyMap = new HashMap<String, String>();
        boolean bodyFlag=false;
        for (int i=1;i<lines.length;i++){
            if (lines[i].equals("")){
                bodyFlag=true;
                continue;
            }
            if (!bodyFlag){
                String header = lines[i].substring(0,lines[i].indexOf(":"));
                String value = lines[i].substring(lines[i].indexOf(":")+1);
                HeaderMap.put(header,value);
            }else {
                String[] entities = lines[i].split("&");
                for (String item: entities) {
                    String enHeader = item.substring(0,item.indexOf("="));
                    String enValue="";
                    if (item.length()!=item.indexOf("=")+1)
                        enValue = item.substring(item.indexOf("=")+1);
                    BodyMap.put(enHeader,enValue);
                }
            }

        }

    }
    // 请求方式：GET or POST?
    private String HttpMethod;
    // 请求URL
    private String Url;
    // Http协议版本
    private String HttpVersion;
    // 请求头
    private HashMap<String, String> HeaderMap;
    // entity body (用于post方法)
    private HashMap<String, String> BodyMap;

    public String getHttpMethod() {
        return HttpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        HttpMethod = httpMethod;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getHttpVersion() {
        return HttpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        HttpVersion = httpVersion;
    }

    public HashMap<String, String> getHeaderDictionary() {
        return HeaderMap;
    }

    public HashMap<String, String> getBodyDictionary() {
        return BodyMap;
    }

}