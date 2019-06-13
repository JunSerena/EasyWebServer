package sample.entity;

public class HttpResponse {
    // 响应状态码
    private String StateCode;
    // 响应状态描述
    private String StateDescription;
    // 响应内容类型
    private String ContentType;
    //响应报文的正文内容
    private byte[] Body = new byte[1024];
    // 生成响应头信息
    public byte[] GetResponseHeader(){
            String strResponseHeader =
                    "HTTP/1.1 "+ getStateCode()+" " + getStateDescription()+"\r\n" +
                            "Content-Type: "+getContentType()+"\r\n"+
                            "Accept-Ranges: bytes\r\n" +
                            "Server: Microsoft-IIS/7.5\r\n" +
                            "X-Powered-By: ASP.NET\r\n"+
                            "Date: "+ System.currentTimeMillis()+"\r\n"+
                            "Content-Length: "+getBody().length+"\r\n"+
                            "\r\n";
            return strResponseHeader.getBytes();
        }
    public String getStateCode() {
        return StateCode;
    }
    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
    public String getStateDescription() {
        return StateDescription;
    }
    public void setStateDescription(String stateDescription) {
        StateDescription = stateDescription;
    }
    public String getContentType() {
        return ContentType;
    }
    public void setContentType(String contentType) {
        ContentType = contentType;
    }
    public byte[] getBody() {
        return Body;
    }
    public void setBody(byte[] body) {
        Body = body;
    }
}
