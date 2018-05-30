import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;

public class ExtractContent {
    private String content;
    private BufferedWriter bw;
    private FileWriter fw;
    private String fileName;
    private News news;

    public ExtractContent(String content, String fileName, String writeToPath, SDUNews news) throws IOException {
        this.content = content;
        this.news = news;
        File path = new File(writeToPath);
        if(!path.exists())
            path.mkdir();
        this.fileName = fileName;
        writeToPath += fileName.split("\\.")[0];
        writeToPath += ".txt";
        fw = new FileWriter(writeToPath);
        bw = new BufferedWriter(fw);
    }

    private String match(String regex){
        String match = "";
        List<String> list = new ArrayList<String>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            list.add(matcher.group());
        }
        for(int i = 0; i < list.size(); i++){
            match += list.get(i);
        }
        return match;
    }

    private String getTitle(){
        String title = "";
        String regex = "<title>.*?</title>";
        title = match(regex);
        title = title.split("-")[0];

        String delete1 = "<title>";
        Pattern p = Pattern.compile(delete1);
        Matcher m = p.matcher(title);
        title = m.replaceAll("");

        news.setTitle(title);
        return title;
    }

    private String getDate(){
        String regix;
        String date = "";
        regix = "发布日期：.*?    ";
        date = match(regix);

        String delete1 = "发布日期：", delete2 = "    ";
        Pattern p = Pattern.compile(delete1);
        Matcher m = p.matcher(date);
        date = m.replaceAll("");

        p = Pattern.compile(delete2);
        m = p.matcher(date);
        date = m.replaceAll("");

        news.setTime(date);
        return date;
    }

    private String getURL(){
        String url = "http://www.view.sdu.edu.cn/";
        String[] path = fileName.split(" ");
        if(path.length == 2){
            url += "info/";
            url += path[0];
            url += "/";
            String secondPath = path[1].split("_")[0];
            url += secondPath;
            url += ".htm";
        } else {
            String secondPath = fileName.split("_")[0];
            url += secondPath;
            url += ".htm";
        }
        news.setUrl(url);
        return url;
    }

    private String getContent(){
        try {
            news.setContent(ContentExtractor.getNewsByHtml(content).getContent());
        } catch (Exception e){
            e.printStackTrace();
        }
        return news.getContent();
    }

    private String[] getSource(){
        String regex = "来自：.*?&nbsp;&nbsp;&nbsp;&nbsp;";
        String source = "";
        source = match(regex);
        source = source.split("：")[1];
        String[] sources = source.split(" ");
        return sources;
    }

    public void writeToText() throws IOException{
        bw.write(getTitle());
        bw.newLine();
        bw.write(getDate());
        bw.newLine();
        bw.write(getURL());
        bw.newLine();
        bw.write(getContent());
        bw.flush();
        bw.close();
    }
}
