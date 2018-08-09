package service;

import Interface.MobilePhoneService;
import model.Mobile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MobilePhoneServiceImpl implements MobilePhoneService {

    @Override
    public void mainMehtod(Scanner scanner, PrintWriter pw) {
        pw.println("请输入手机号码\r\n");
        pw.println("输入q返回到主菜单\r\n");
        pw.flush();
        String inputLine = scanner.nextLine();
        // 如果输入的是q，返回到主菜单
        if (inputLine.equalsIgnoreCase("q")) {
            NetSocketService.mainMeau(pw);
            String msg = scanner.nextLine();
            NetSocketService.deelMainMene(scanner, pw, msg);
        }
        // 检查手机号码是否合法
        boolean sign = checkMobileIsValid(inputLine);
        if (!sign) {
            // 如果不合法，提示用户不合法
            pw.println("您输入的手机号码不合法，请重新输入");
            pw.flush();
            mainMehtod(scanner, pw);
    }
        String content = getContent(inputLine);
        pw.println("服务端响应：" + content);
        pw.flush();

        mainMehtod(scanner, pw);

}
    public boolean checkMobileIsValid(String mobile) {
        boolean sign = true;
        if (mobile.length() != 11) {
            sign = false;
        }
        String[] numArray = mobile.split("");
        for (int i = 0; i < numArray.length; i++) {
            try {
                Integer.parseInt(numArray[i]);
            } catch (Exception e) {
                return false;
            }
        }
        if (!mobile.startsWith("1")) {
            sign = false;
        }
        return sign;
    }


    public String getContent(String mobile) {
        String content = "";
        String url = "http://www.ip138.com:8080/search.asp?mobile=" + mobile + "&action=mobile";
        Mobile mobileObject = new Mobile();
        mobileObject.setPhoneNumber(mobile);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elemnts = doc.getElementsByAttributeValue("class", "tdc2");
            if (elemnts.size() > 2) {
                String address = elemnts.get(1).text();
                String brand = elemnts.get(2).text();
                mobileObject.setAddress(address);
                mobileObject.setBrand(brand);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        content = mobileObject.toString();
        return content;
    }



}
