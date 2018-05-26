package com.company;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.hfut.dmic.contentextractor.News;
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

    public Index(String writeFile){
        this.writeFile = writeFile;
    }

    public void createIndex(List<SDUNews> newsList) throws IOException {

        //initialize lucence index
        String pathFile = new File(writeFile).getAbsolutePath();
        Directory directory = FSDirectory.open(Paths.get(pathFile));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        for(int i = 0; i < newsList.size(); i++){
            Document doc = new Document();
            newsList.get(i).sort = i;

            TextField titleField = new TextField("title", newsList.get(i).getTitle(), Store.YES);
            doc.add(titleField);

            TextField timeField = new TextField("time", newsList.get(i).getTime(), Store.YES);
            doc.add(timeField);

            StringField urlField = new StringField("url", newsList.get(i).getUrl(), Store.YES);
            doc.add(urlField);

            TextField contentField = new TextField("content", newsList.get(i).getContent(), Store.YES);
            doc.add(contentField);

            StringField authorField = new StringField("author", newsList.get(i).getAuthor(), Store.YES);
            doc.add(authorField);

            StringField editorField = new StringField("editor", newsList.get(i).getEditor(), Store.YES);
            doc.add(editorField);

            TextField sourceField = new TextField("source", newsList.get(i).getSource(), Store.YES);
            doc.add(sourceField);

            TextField clickField = new TextField("click", Integer.toString(newsList.get(i).getClick()), Store.YES);
            doc.add(clickField);

            StringField photoField = new StringField("photography", newsList.get(i).getPhotography(), Store.YES);
            doc.add(photoField);

            TextField sortField = new TextField("sort", Integer.toString(newsList.get(i).sort),Store.YES);
            doc.add(sortField);

            indexWriter.addDocument(doc);
        }

        indexWriter.commit();
        indexWriter.close();
    }

    public List search(String keywords,List<SDUNews> nNewsList, int hitsPage) throws IOException, ParseException{
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
            newsList.add(nNewsList.get(pos));
            timeNewsList.add(nNewsList.get(pos));
            clickNewsList.add(nNewsList.get(pos));
            reNewsList.add(nNewsList.get(pos));
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
            return n1.getTime().compareTo(n2.getTime());
        }
    }
    public class SortByClick implements Comparator{
        public int compare(Object object1, Object object2){
            SDUNews n1 = (SDUNews) object1;
            SDUNews n2 = (SDUNews) object2;
            if(n1.getClick() < n2.getClick())
                return -1;
            else if(n1.getClick() == n2.getClick())
                return 0;
            else return 1;
        }
    }
    public class SortByRelece implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            SDUNews n1 = (SDUNews) o1;
            SDUNews n2 = (SDUNews) o2;
            if(n1.sort < n2.sort)
                return -1;
            else if(n1.sort == n2.sort)
                return 0;
            else return 1;
        }
    }
}
