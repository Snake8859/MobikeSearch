package com.gis.lbs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.gis.lbs.pojo.IPBean;
import com.gis.lbs.pojo.IPList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gis.lbs.mapper.LBSMapper;
import com.gis.lbs.pojo.Bike;
import com.gis.lbs.pojo.Lesson;

@RestController
public class LBSControl {

	@Autowired
	private LBSMapper lbsMapper;

	private List<IPBean> ipList = IPList.getIpBeanList();
	
	@RequestMapping("/getLessonById/{id}")
	public Lesson getLessonById(@PathVariable int id){
		return lbsMapper.getLessonsById(id);
	}
	
	@RequestMapping("/getNearByBike")
	@ResponseBody
	public String getNearByBike(String longitude,String latitude,String cityCode) throws ClientProtocolException, IOException{

		//System.out.println("代理池ip的数量:"+ipList.size());
		//判断代理是否成功

		//设置代理
		//Random r = new Random();
		//int i = r.nextInt(IPList.getSize());
		//HttpHost proxy = new HttpHost(ipList.get(i).getIp(),ipList.get(i).getPort());
		HttpHost proxy = new HttpHost("58.47.9.28",43220);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build(); 
		//post请求
		HttpPost httpPost = new HttpPost("https://mwx.mobike.com/mobike-api/rent/nearbyBikesInfo.do");
		//设置代理
		//httpPost.setConfig(config);
		//设置请求头
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Mobile/14E304 MicroMessenger/6.5.7 NetType/WIFI Language/zh_CN");
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
		httpPost.setHeader("Referer","htstps://servicewechat.com/wx80f809371ae33eda/23/page-frame.html");
		
		//设置参数
		List<NameValuePair> list = new LinkedList<NameValuePair>();
        BasicNameValuePair param1 = new BasicNameValuePair("longitude", longitude);
        BasicNameValuePair param2 = new BasicNameValuePair("latitude", latitude);
        BasicNameValuePair param3 = new BasicNameValuePair("citycode", cityCode);
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
		//System.out.println(message);
		try {
			//关闭连接
			httpPost.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return message;
	}

	@RequestMapping("/queryLBSAll")
	public List<Lesson> queryLBSAll(){
		return  lbsMapper.queryLessons();
	}





	
}
