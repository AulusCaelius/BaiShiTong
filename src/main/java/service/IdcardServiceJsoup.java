package service;

import Interface.IdcardService;
import model.Idcard;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IdcardServiceJsoup implements IdcardService {
    @Override
    public void mainMehtod(Scanner scanner, PrintWriter pw) {
        pw.println("请输入身份证号码\r\n");
        pw.println("输入q返回主菜单\r\n");
        pw.flush();
        String inputline = scanner.nextLine();

        if (inputline.equalsIgnoreCase("q")) {
            NetSocketService.mainMeau(pw);
            String msg = scanner.nextLine();
            NetSocketService.deelMainMene(scanner, pw, msg);

        }

        boolean sign = checkIcardIsValid(inputline);
        if (!sign) {
            pw.println("您输入的身份证号码不合法，请重新输入");
            pw.flush();
            mainMehtod(scanner, pw);
        }
        String content = getContent(inputline);
        pw.println(content);
        pw.flush();

        mainMehtod(scanner, pw);
    }

    private boolean checkIcardIsValid(String idcard) {
        boolean sign = true;
        if (idcard.length() != 18) {
            sign = false;
        }
        return sign;

    }

    public String getContent(String idcardNumber) {
        Idcard idcard = new Idcard();
        idcard.setIdcardNumer(idcardNumber);
        String content = "";
        String url = "http://qq.ip138.com/idsearch/index.asp?userid=" + idcardNumber
                + "&action=idcard&B1=%B2%E9+%D1%AF";
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements elements = doc.getElementsByAttributeValue("class", "tdc2");
            if (elements.size() >= 3) {
                idcard.setSex(elements.get(0).text());
                idcard.setBirthday(elements.get(1).text());
                idcard.setAddress(elements.get(2).text());
            }
            content = idcard.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}
