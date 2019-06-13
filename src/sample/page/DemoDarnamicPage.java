package sample.page;

import sample.entity.HttpContext;
import sample.entity.HttpResponse;
import sample.entity.IHttpHandler;

public class DemoDarnamicPage implements IHttpHandler {

    @Override
    public void ProcessRequest(HttpContext context) {
        StringBuilder sbText = new StringBuilder();
        sbText.append("<html>");
        sbText.append("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><title>DemoPage</title></head>");
        sbText.append("<body style='margin:10px auto;text-align:center;'>");
        sbText.append("<h1>用户信息填写</h1>");
        sbText.append("<Form method='post' action='PostResponse.aspx'>");
        sbText.append("<table align='center' cellpadding='1' cellspacing='1'><thead><tr><td>属性</td><td>值</td></tr></thead>");
        sbText.append("<tbody>");
        sbText.append("<tr>");
        sbText.append("<td>");
        sbText.append("<label>姓名：</label>");
        sbText.append("</td>");
        sbText.append("<td>");
        sbText.append("<input type='text' name='name'>");
        sbText.append("</td>");
        sbText.append("</tr>");

        sbText.append("<tr>");
        sbText.append("<td>");
        sbText.append("<label>年龄：</label>");
        sbText.append("</td>");
        sbText.append("<td>");
        sbText.append("<input type='text' name='age'>");
        sbText.append("</td>");
        sbText.append("</tr>");

        sbText.append("</tbody></table>");
        sbText.append("<input type='submit'/>");
        sbText.append("</Form>");
        sbText.append("</body>");
        sbText.append("</html>");

        HttpResponse response = context.getResponse();
        response.setStateCode("200");
        response.setStateDescription("OK");
        response.setContentType("text/html");
        response.setBody(sbText.toString().getBytes());

    }
}
