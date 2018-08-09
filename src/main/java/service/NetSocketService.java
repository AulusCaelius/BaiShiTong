package service;


import Interface.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NetSocketService {
    public static void startServer() {
        System.out.println("8866端口已启动，网络已启动");
        try {
            ServerSocket serverSocket = new ServerSocket(8866);
            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                mainMeau(pw);

                Scanner scanner = new Scanner(socket.getInputStream());
                String inputline = scanner.nextLine();
                deelMainMene(scanner,pw,inputline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void mainMeau(PrintWriter pw){
            String content = "欢迎进入百事通平台\r\n";
            content += "1.查询ip\r\n";
            content += "2.查询身份证\r\n";
            content += "3.查询手机号码信息\r\n";
            content += "4.获取电影下载地址\r\n";
            content += "5.查询城市天气\r\n";
            pw.print(content);
            pw.flush();

    }

    public static void deelMainMene(Scanner scanner,PrintWriter pw, String inputline){
        switch (inputline){
            case "1":
                //IP模块
                pw.println("欢迎进入IP查询系统\r\n");
                pw.flush();
                IIpService ipService = new IpServiceImpl();
                ipService.mainMethod(scanner,pw);
                break;
            case "2":
                //身份证模块
                pw.println("欢迎进入身份证查询系统\r\n");
                pw.flush();
                IdcardService idcardService = new IdcardServiceJsoup();
                idcardService.mainMehtod(scanner, pw);
                break;
            case "3":
                //手机号码模块
                pw.println("欢迎进入手机号码查询系统\r\n");
                pw.flush();
                MobilePhoneService mobileService = new MobilePhoneServiceImpl();
                mobileService.mainMehtod(scanner, pw);
                break;
            case "4":
                //电影查询模块
                pw.println("欢迎进入电影下载地址查询系统\r\n");
                pw.flush();
                MovieService ms = new MovieServiceImpl();
                ms.mainMehtod(scanner, pw);
                break;
            case "5":
                //城市天气模块
                pw.println("欢迎进入城市天气查询系统\r\n");
                pw.flush();
                WeatherService ws = new WeatherServiceImpl();
                ws.mainMehtod(scanner, pw);
                break;

            default:
                mainMeau(pw);
                break;
        }
    }
}
