package service;

import Interface.MovieService;
import model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MovieServiceImpl implements MovieService {

    @Override
    public void mainMehtod(Scanner scanner, PrintWriter pw) {
        pw.println("请输入电影名称\r\n");
        pw.println("输入q返回到主菜单\r\n");
        pw.flush();
        String inputLine = scanner.nextLine();
        if (inputLine.equalsIgnoreCase("q")) {
            NetSocketService.mainMeau(pw);
            String msg = scanner.nextLine();
            NetSocketService.deelMainMene(scanner, pw, msg);
        }
        String content = getContent(inputLine);
        pw.println("服务端响应：" + content);
        pw.flush();

        mainMehtod(scanner, pw);
    }
    public String getContent(String movieName) {
        String content = "";
        List<Movie> list = this.getMovieList(movieName);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Movie movie = (Movie) iterator.next();
            content+=movie.toString();
        }
        return content;
    }
    public String getDownloadUrlFromWeburl(String weburl) {
        String content = "";
        try {
            Document doc = Jsoup.connect(weburl).get();
            Elements elements = doc.getElementsByAttributeValue("bgcolor", "#fdfddf");
            if (elements.size() == 1) {
                Elements es = elements.get(0).getElementsByTag("a");
                content = es.get(0).attr("href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    public List<Movie> getMovieList(String name) {
        List<Movie> movieList = new ArrayList<>();
        String url = "";
        try {
            url = "http://s.ygdy8.com/plus/so.php?kwtype=0&searchtype=title&keyword="
                    + URLEncoder.encode(name, "gb2312");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        System.out.println(url);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByAttributeValue("class", "co_content8");
            if (elements.size() == 1) {
                Elements es = elements.get(0).getElementsByAttributeValueStarting("href", "/html");
                System.out.println(es.size());
                for (int i = 0; i < es.size(); i++) {
                    Movie movie = new Movie();

                    String href = es.get(i).attr("href");
                    System.out.println(href);
                    movie.setName(name);
                    movie.setTitle(es.get(i).text());

                    String downloadUrl = this.getDownloadUrlFromWeburl("http://s.ygdy8.com" + href);
                    movie.setUrl(downloadUrl);
                    movieList.add(movie);
                }

            }
            System.out.println(elements.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieList;
}
}
