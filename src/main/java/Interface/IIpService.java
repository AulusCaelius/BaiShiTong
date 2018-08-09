package Interface;

import java.io.PrintWriter;
import java.util.Scanner;

public interface IIpService {
    /**
     *
     * 介绍：ip地址查询的主方法，进入到这个方法中，我们可以跟客户端进行交互，可输入内容也可输出
     * @param scanner 客户端输入流
     * @param pw 客户端输出流
     */
    public void mainMethod(Scanner scanner, PrintWriter pw);
    public String getContent(Scanner scanner, PrintWriter pw,String ip);

}
