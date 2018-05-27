package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile {

    public ReadFile(){

    }

    private SDUNews loadFile(File file) {
        SDUNews sduNews = new SDUNews();
        try {
                StringBuffer sb = new StringBuffer();
                InputStream isr = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(isr));

                String line = "";
                line = br.readLine();
                sduNews.setUrl(line);

                line = br.readLine();
                sduNews.setTitle(line);

                line = br.readLine();
                sduNews.setTime(line);

                line = br.readLine();
                if(line == null)
                    sduNews.setClick(0);
                else sduNews.setClick(Integer.parseInt(line));

                line = br.readLine();
                sduNews.setAuthor(line);

                line = br.readLine();
                sduNews.setPotography(line);

                line = br.readLine();
                sduNews.setSource(line);

                line = br.readLine();
                sduNews.setEditor(line);

                while (br.readLine() == null){
                    continue;
                }

                while((line = br.readLine()) != null){
                    if(matchPicture(line))
                        break;
                    sb.append(line);
                    sb.append("\n");
                }
                sduNews.setContent(sb.toString());
                isr.close();
                br.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        return sduNews;
    }

    private boolean matchPicture(String line){
        String regex = "http://www.view.sdu.edu.cn/+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
            return true;
        else return false;
    }

    public SDUNews getNewsList(File file) {
        return loadFile(file);
    }

}
