package sample.entity;

import sample.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpCommonHandler implements IHttpHandler{
    @Override
    public void ProcessRequest(HttpContext context) {
        // 1.获取网站根路径
        //if(String.IsNullOrEmpty(context.Request.Url))
        HttpRequest request = context.getRequest();
        System.out.println("testtttt:"+request.getUrl());
        if(request.getUrl()==null && request.getUrl().equals(""))
        {
            return;
        }
        String bastPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            bastPath = java.net.URLDecoder.decode(bastPath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String Url = request.getUrl();
        String fileName = bastPath+Url;
        String fileExtension = Url.substring(Url.lastIndexOf("."));
        // 2.处理动态文件请求
        if(fileExtension.equals(".aspx") || fileExtension.equals(".ashx"))
        {
            String className = "sample.page."+Url.substring(Url.lastIndexOf("/")+1, Url.lastIndexOf("."));
            try {
                Class demoClass = Class.forName(className);
                IHttpHandler handler = (IHttpHandler)demoClass.newInstance();
                handler.ProcessRequest(context);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                HttpResponse response = context.getResponse();
                response.setStateCode( "404");
                response.setStateDescription("Not Found");
                response.setContentType("text/html");
                String notExistHtml = bastPath+"notfound.html";
                try {
                    response.setBody(FileUtils.getContent(notExistHtml));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return;
        }
        // 3.处理静态文件请求
        File file = new File(fileName);
        HttpResponse response = context.getResponse();
        if (!file.exists())
        {
            response.setStateCode( "404");
            response.setStateDescription("Not Found");
            response.setContentType("text/html");
            String notExistHtml = bastPath+"notfound.html";
            try {
                response.setBody(FileUtils.getContent(notExistHtml));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStateCode( "200");
            response.setStateDescription("OK");
            response.setContentType( GetContenType(fileExtension));
            try {
                response.setBody(FileUtils.getContent(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据文件扩展名获取内容类型
    public String GetContenType(String fileExtension)
    {
        String type = "text/html; charset=UTF-8";
        switch (fileExtension)
        {
            case ".aspx":
            case ".html":
            case ".htm":
                type = "text/html; charset=UTF-8";
                break;
            case ".png":
                type = "image/png";
                break;
            case ".gif":
                type = "image/gif";
                break;
            case ".jpg":
            case ".jpeg":
                type = "image/jpeg";
                break;
            case ".css":
                type = "text/css";
                break;
            case ".js":
                type = "application/x-javascript";
                break;
            default:
                type = "text/plain; charset=UTF-8";
                break;
        }
        return type;
    }


}
