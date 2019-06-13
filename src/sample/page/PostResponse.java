package sample.page;

import sample.entity.HttpContext;
import sample.entity.HttpRequest;
import sample.entity.HttpResponse;
import sample.entity.IHttpHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PostResponse implements IHttpHandler {
    @Override
    public void ProcessRequest(HttpContext context) {
        StringBuilder sbText = new StringBuilder();
        HttpRequest request=context.getRequest();
        HashMap map = request.getBodyDictionary();
        sbText.append("<html>");
        sbText.append("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><title>DemoPage</title></head>");
        sbText.append("<body style='margin:10px auto;text-align:center;'>");
        sbText.append("<h1>信息列表</h1>");
        sbText.append("<table align='center' cellpadding='1' cellspacing='5' board='1' ><thead><tr><td>属性</td><td>值</td></tr></thead>");
        sbText.append("<tbody>");
        for (Object key : map.keySet()){
            sbText.append("<tr>");
            sbText.append("<td>");
            sbText.append(key.toString());
            sbText.append("</td>");
            sbText.append("<td>");
            sbText.append(map.get(key));
            sbText.append("</td>");
            sbText.append("</tr>");
        }
        sbText.append("</tbody></table>");
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        sbText.append("<h3>更新时间："+ dateFormat.format(date)+"</h3>");

        sbText.append("</body>");
        sbText.append("</html>");

        HttpResponse response = context.getResponse();
        response.setStateCode("200");
        response.setStateDescription("OK");
        response.setContentType("text/html");
        response.setBody(sbText.toString().getBytes());

    }
}
