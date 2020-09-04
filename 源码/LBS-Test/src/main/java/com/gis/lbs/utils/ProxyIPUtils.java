package com.gis.lbs.utils;

import com.gis.lbs.pojo.IPBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IP代理池工具类
 */
public class ProxyIPUtils {

    /**
     * 获取IP代理池  西刺代理 ip被封杀
     *               米扑代理 端口是图片
     * @param address
     * @return
     * @throws IOException
     */
    public static List<IPBean> getProxyIP(String address) throws IOException {

        //获取链接 取第一页前10个
        //获取表信息
        List<IPBean> ipList = new ArrayList<IPBean>();
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        String url = address;
        //发起Get请求
        HttpGet httpGet = new HttpGet(url);
        //设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String html = null;
        try {
            html = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
            //解析HTML
            Document document = Jsoup.parse(html);
            /*//获取ip列表
            Elements elements = document.select("table#ip_list > tbody > tr");
            //去掉表头
            elements.remove(0);
            for (Element element : elements) {
                //System.out.println(element.html());
                IPBean ipBean = new IPBean();
                Elements elementsByTag = element.getElementsByTag("td");
                //获取IP
                String ip = elementsByTag.get(1).text();
                ;
                ipBean.setIp(ip);
                //获取端口
                String port = elementsByTag.get(2).text();
                ipBean.setPort(Integer.parseInt(port));
                //获得类型
                String type = elementsByTag.get(5).text();
                ipBean.setType(type);
                //获得存活时间
                String lifeTime = elementsByTag.get(8).text();
                ipBean.setLifeTime(lifeTime);
                ipList.add(ipBean);
            }*/
        return  ipList;
    }

    /**
     * 过滤ip
     * @param ipBean
     * @return
     */
    public static boolean filterIPBean(IPBean ipBean){
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getIp(), ipBean.getPort()));
            try {
                URLConnection httpCon = new URL("https://www.baidu.com/").openConnection(proxy);
                httpCon.setConnectTimeout(5000);
                httpCon.setReadTimeout(5000);
                int code = ((HttpURLConnection) httpCon).getResponseCode();
                //System.out.println(code);
                if(code==200){
                    return true;
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(ipBean.getIp()+" 失效");
            }
        return false;
    }

    public static String getMyIP(){
        return "";
    }




}
