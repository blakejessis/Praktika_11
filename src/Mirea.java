import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Mirea {
    public static void main(String[] args) {
        getImages();


    }

    public static void getImages() {
        try {
            Document html = Jsoup.connect("https://www.mirea.ru/").get();
            System.out.println(html.title() + "\n");



            Elements news = html.getElementsByTag("img");


            Files.createDirectory(Path.of("images"));

            for (var item : news)
            {
                if (!item.attr("abs:src").equals("")) {


                    byte[] bytes = Jsoup.connect(item.attr("abs:src")).ignoreContentType(true).execute().bodyAsBytes();


                    if (!item.attr("src").substring(item.attr("src").lastIndexOf("/") + 1).contains(";")) {
                        FileOutputStream out = new FileOutputStream(new File("images/" + item.attr("src")
                                .substring(item.attr("src")
                                        .lastIndexOf("/") + 1)));
                        out.write(bytes);
                        out.close();
                    }
                }
            }

            File imgFolder = new File("images");

            for (File file: imgFolder.listFiles()) {
                System.out.println(file.toString().substring(file.toString().indexOf("\\") + 1));
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}