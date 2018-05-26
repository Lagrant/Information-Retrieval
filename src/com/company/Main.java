package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scan = new Scanner(System.in);
        String keywords;
//        keywords = scan.nextLine();
        keywords = "山东大学";

        String path = "D:\\study\\Information Retrieval\\sdu_html_extract_2";
        String writeToPath = "D:\\study\\Information Retrieval\\resource";
        List<SDUNews> newsList;
        List<SDUNews> resultList= new ArrayList<>();

        ReadFile rf = new ReadFile(path);
        newsList = rf.getNewsList();

        Index index = new Index(writeToPath);
        try{
            index.createIndex(newsList);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            resultList = index.search(keywords,newsList,500);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i < resultList.size(); i++){
            System.out.println((i+1)+". "+resultList.get(i).getTitle()+"\t"+resultList.get(i).getTime());
        }
    }
}
