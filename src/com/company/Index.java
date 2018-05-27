package com.company;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.hfut.dmic.contentextractor.News;
import com.sleepycat.persist.impl.SimpleFormat;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

import javax.print.Doc;


public class Index {
    private String writeFile; //"D:\\study\\Information Retrieval\\resource"
    private ReadFile rf;
    File f;
    File[] files;

    public Index(String readFile, String writeFile, ReadFile rf){
        this.writeFile = writeFile;
        this.rf = rf;
        f = new File(readFile);
        files = f.listFiles();
    }

    public void createIndex() throws IOException {
        //read web content

        //initialize lucence index
        String pathFile = new File(writeFile).getAbsolutePath();
        Directory directory = FSDirectory.open(Paths.get(pathFile));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        int sort = 0;
        for(File file : files){
            SDUNews sduNews = rf.getNewsList(file);
            Document doc = new Document();
            sduNews.sort = sort++;

            TextField titleField = new TextField("title", sduNews.getTitle(), Store.YES);
            doc.add(titleField);

            TextField timeField = new TextField("time", sduNews.getTime(), Store.YES);
            doc.add(timeField);

            StringField urlField = new StringField("url", sduNews.getUrl(), Store.YES);
            doc.add(urlField);

            TextField contentField = new TextField("content", sduNews.getContent(), Store.YES);
            doc.add(contentField);

            StringField authorField = new StringField("author", sduNews.getAuthor(), Store.YES);
            doc.add(authorField);

            StringField editorField = new StringField("editor", sduNews.getEditor(), Store.YES);
            doc.add(editorField);

            TextField sourceField = new TextField("source", sduNews.getSource(), Store.YES);
            doc.add(sourceField);

            TextField clickField = new TextField("click", Integer.toString(sduNews.getClick()), Store.YES);
            doc.add(clickField);

            StringField photoField = new StringField("photography", sduNews.getPhotography(), Store.YES);
            doc.add(photoField);

            TextField sortField = new TextField("sort", Integer.toString(sduNews.sort),Store.YES);
            doc.add(sortField);

            indexWriter.addDocument(doc);
        }

        indexWriter.commit();
        indexWriter.close();
    }

    public List search(String keywords, int hitsPage) throws IOException, ParseException{
        String[] fields = {"title","time","url","content","author","editor","source","click","photography"};
        Analyzer analyzer = new StandardAnalyzer();
        String documentPath = new File(writeFile).getAbsolutePath();
        Directory directory = FSDirectory.open(Paths.get(documentPath));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        Query q = parser.parse(keywords);
        TopScoreDocCollector tsdc = TopScoreDocCollector.create(hitsPage);
        indexSearcher.search(q, tsdc);
        ScoreDoc[] hits = tsdc.topDocs().scoreDocs;
        List<SDUNews> newsList = new ArrayList<>();
        List<SDUNews> timeNewsList = new ArrayList<>();
        List<SDUNews> clickNewsList = new ArrayList<>();
        List<SDUNews> reNewsList = new ArrayList<>();
        for(int i = 0; i < hits.length; i++){
            Document d = indexSearcher.doc(hits[i].doc);
            int pos = Integer.parseInt(d.get("sort"));
            newsList.add(rf.getNewsList(files[pos]));
            timeNewsList.add(rf.getNewsList(files[pos]));
            clickNewsList.add(rf.getNewsList(files[pos]));
            reNewsList.add(rf.getNewsList(files[pos]));
        }

//        Collections.sort(timeNewsList, new SortByTime());
//        Collections.sort(clickNewsList, new SortByClick());
        timeNewsList.sort(new SortByTime());
        clickNewsList.sort(new SortByClick());
        for(int i = 0; i < newsList.size(); i++){
            for(int j = 0; j < timeNewsList.size(); j++){
                for(int k = 0; k < clickNewsList.size(); k++){
                    if(newsList.get(i).equals(timeNewsList.get(j)) && newsList.get(i).equals(clickNewsList.get(k))){
                        reNewsList.get(i).sort =(int)((i+0.2*j+0.1*k)/3);
                    }
                }
            }
        }
//        Collections.sort(reNewsList, new SortByRelece());
        reNewsList.sort(new SortByRelece());
        indexReader.close();
        return reNewsList;
    }

    public class SortByTime implements Comparator{
        public int compare(Object object1, Object object2){
            SDUNews n1 = (SDUNews) object1;
            SDUNews n2 = (SDUNews) object2;
//            return n1.getTime().compareTo(n2.getTime());
            return compare(n1.getTime(), n2.getTime());
        }
        private int compare(String s1, String s2){
            int[] time1 = extractTime(s1);
            int[] time2 = extractTime(s2);
            if(time1[0] > time2[0])
                return 1;
            else if(time1[0] < time2[0])
                return -1;
            else if(time1[1] > time2[1])
                return 1;
            else if(time1[1] < time2[1])
                return -1;
            else if(time1[2] > time2[2])
                return 1;
            else if(time1[2] < time2[2])
                return -1;
            else return 0;
        }
        private int[] extractTime(String s){
            String[] ts = s.split("年");
            int[] time = new int[3];
            switch (ts.length){
                case 2:
                    return extractTimeByCharacter(ts);
                case 1:
                    return extractTimeBySymbol(ts);
                default:
                    break;
            }
            return time;
        }
        private int[] extractTimeByCharacter(String[] s){
            int[] time = new int[3];
            time[0] = Integer.parseInt(s[0]);
            String[] mon = s[1].split("月");
            time[1] = Integer.parseInt(mon[0]);
            String day = mon[1].split("日")[0];
            time[2] = Integer.parseInt(day);
            return time;
        }
        private int[] extractTimeBySymbol(String[] s){
            int[] time = new int[3];
            String[] date = s[0].split("-");
            String d = date[2].split(" ")[0];
            if(date.length != 3) {
                System.err.println("invalid date format");
                return time;
            }
            time[0] = Integer.parseInt(date[0]);
            time[1] = Integer.parseInt(date[1]);
            time[2] = Integer.parseInt(d);
            return time;
        }
    }
    public class SortByClick implements Comparator{
        public int compare(Object object1, Object object2){
            SDUNews n1 = (SDUNews) object1;
            SDUNews n2 = (SDUNews) object2;
            return compare(n1.getClick(), n2.getClick());
        }
        private int compare(int c1, int c2){
            if(c1 < c2)
                return -1;
            else if(c1 > c2)
                return 1;
            else return 0;
        }
    }
    public class SortByRelece implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            SDUNews n1 = (SDUNews) o1;
            SDUNews n2 = (SDUNews) o2;
            return compare(n1.sort, n2.sort);
        }
        private int compare(int s1, int s2){
            if(s1 < s2)
                return -1;
            else if(s1 > s2)
                return 1;
            else return 0;
        }
    }
}
