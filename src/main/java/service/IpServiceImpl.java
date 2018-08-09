package service;

import Interface.IIpService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class IpServiceImpl implements IIpService {
    @Override
    public void mainMethod(Scanner scanner, PrintWriter pw) {
        pw.println("请输入ip地址:\r\n");
        pw.println("输入q返回到主菜单\r\n");
        pw.flush();

        String ip = scanner.nextLine();
        if (ip.equalsIgnoreCase("q")){
            NetSocketService.mainMeau(pw);
            String msg = scanner.nextLine();
            NetSocketService.deelMainMene(scanner,pw,msg);
        }
            boolean sign = checkIp(ip);
            if (!sign){
                pw.println("输入ip地址不合法，请重新输入");
                pw.flush();
                mainMethod(scanner,pw);
            }
            String content = getContent(scanner,pw,ip);
            pw.println("服务端响应："+content);
            pw.flush();
            mainMethod(scanner,pw);
        }

    /**
     *
     * @param ip
     * @return true:合法，false:不合法
     */
    public boolean checkIp(String ip){
        boolean sign = true;
        if (ip.length()>15){
            sign = false;
        }
        // split方法 将一个字符串分割为子字符串，然后将结果作为字符串数组返回。
        // split(separator,limit); separator:分隔符，确定分割字符串的界限 limit 该值用来限制返回数组中的元素个数
        // 例子：String[] xxAraay = xx.split(".","")
        //
        String[] ipArray = ip.split("\\.");
        if (ipArray.length!=4){
            sign = false;
        }
        for (int i = 0;i<ipArray.length;i++){
            Integer num =0;
           try {
               num = Integer.parseInt(ipArray[i]);
           }catch (Exception e){
               sign = false;
           }
            if (num>255||num<0){
               sign = false;
            }
        }

        return sign;
    }

    /**
     * 获取输入的ip地址
     * @param scanner
     * @param pw
     */
    @Override
    public String getContent(Scanner scanner, PrintWriter pw,String ip) {
        String content = getContentFromURL(ip);
        String spName = getSpNameFromContent(content);
        return spName;
    }

    /**
     * 获取ip信息
     * @param ip
     * @return
     */
    public String getContentFromURL(String ip){
        String webUrl = "http://www.ip138.com/ips138.asp?ip="+ip+"&action=2";
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(webUrl);
            URLConnection conn = url.openConnection();
            Scanner s = new Scanner(conn.getInputStream(),"gb2312");
            while (s.hasNextLine()){
                sb.append(s.nextLine()).append("\r\n");
            }
            s.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     *
     * @param content
     * @return
     */
    public String getSpNameFromContent(String content){
        String spName = "";
        if(content.contains("本站数据：")&&content.contains("</li>")){
            int beginIndex = content.indexOf("本站数据：");
            int endIndex = content.indexOf("</li>");
            spName = content.substring(beginIndex+5,endIndex);
        }
        return spName;
    }
}
