package com.gis.lbs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.gis.lbs.pojo.IPBean;
import com.gis.lbs.pojo.IPList;
import com.gis.lbs.utils.ProxyIPUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class HttpTest {


	@Test
	public void bikeTest() throws ClientProtocolException, IOException{
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build(); 
		//post请求
		HttpPost httpPost = new HttpPost("https://mwx.mobike.com/mobike-api/rent/nearbyBikesInfo.do");
		//设置请求头
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Mobile/14E304 MicroMessenger/6.5.7 NetType/WIFI Language/zh_CN");
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
		httpPost.setHeader("Referer","https://servicewechat.com/wx80f809371ae33eda/23/page-frame.html");
		//设置参数
		List<NameValuePair> list = new LinkedList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("longitude", "112.93886");
        BasicNameValuePair param2 = new BasicNameValuePair("latitude", "28.22778");
        BasicNameValuePair param3 = new BasicNameValuePair("citycode", "731");
        BasicNameValuePair param4 = new BasicNameValuePair("errMsg", "getMapCenterLocation:ok");
        list.add(param1);
        list.add(param2);
        list.add(param3);
        list.add(param4);
        // 使用URL实体转换工具
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
		//返回结果
		CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String message = EntityUtils.toString(entity);
		System.out.println(message);
	}

	/**
	 * 测试西刺代理 目前ip已挂
	 */
	@Test
	public  void IPProxyTest(){
		//获取链接
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		String url = "https://www.xicidaili.com/wt/"+1;
		//发起Get请求
		HttpGet httpGet = new HttpGet(url);
		//设置请求头
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
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
		//System.out.println(html);
		//解析HTML
		Document document = Jsoup.parse(html);
		//获取ip列表
		Elements elements = document.select("table#ip_list > tbody > tr");
		//去掉表头
		elements.remove(0);
		//获取表信息
		List<IPBean> ipList = new ArrayList<IPBean>();
		for(Element element:elements) {
			//System.out.println(element.html());
			IPBean ipBean = new IPBean();
			Elements elementsByTag = element.getElementsByTag("td");
			//获取IP
			String ip = elementsByTag.get(1).text();;
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
		}
		System.out.println(ipList.size());
	}

	/**
	 * 测试米扑代理
	 */
	@Test
	public void IPProxyTest1() {
		//获取链接 取第一页前10个
		//获取表信息
		List<IPBean> ipList = new ArrayList<IPBean>();
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		String url = "https://proxy.mimvp.com/freesecret.php";
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
		Document document = Jsoup.parse(html);
		Elements elements = document.select("tbody > tr");
		for(Element element:elements){
			String ip = element.getElementsByTag("td").get(1).text();
		}
	}

	@Test
	public void testSuccessProxy(){
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		String url = "https://www.ipip.net/ip.html";
		//发起Get请求
		HttpPost httpPost = new HttpPost(url);
		try {
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity);
			System.out.println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		//爬取ip
		List<IPBean> ipList = null;
		try {
				ipList = ProxyIPUtils.getProxyIP("https://www.xicidaili.com/wt/");
				//过滤IP前
				System.out.println("过滤IP前数量:" + ipList.size());
				//启动多线程过滤ip
				for (final IPBean ipBean : ipList) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							boolean flag = ProxyIPUtils.filterIPBean(ipBean);
							if (flag) {
								//若ip有效
								IPList.add(ipBean);
							}
							IPList.increase();
						}
					}).start();
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true){
			// 判断所有副线程是否完成
			if (IPList.getCount() == ipList.size()){
				System.out.println("有效数量：" + IPList.getSize());
				break;
			}
		}
		System.out.println(IPList.getIpBeanList());
	}

}
