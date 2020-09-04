package com.gis.lbs;

import com.gis.lbs.pojo.IPBean;
import com.gis.lbs.pojo.IPList;
import com.gis.lbs.utils.ProxyIPUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class LBSApp {

	public static void main(String[] args) {
        //启动容器前，爬取ip
        /*List<IPBean> ipList = null;
        try {
            ipList = ProxyIPUtils.getProxyIP("https://proxy.mimvp.com/freesecret.php");
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


        while (true){
        // 判断所有副线程是否完成
        if (IPList.getCount() == ipList.size()){
            System.out.println("有效数量：" + IPList.getSize());
            break;
        }
    }
    System.out.println(IPList.getIpBeanList());*/

        //启动容器
        SpringApplication.run(LBSApp.class, args);
	}
	
}
