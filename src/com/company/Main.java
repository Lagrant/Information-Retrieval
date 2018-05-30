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
        List<SDUNews> resultList= new ArrayList<>();


        Index index = new Index(path, writeToPath, new ReadFile());
//        try{
//            index.createIndex();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            resultList = index.search(keywords,500);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(int i = 0; i < resultList.size(); i++){
            System.out.println((i+1)+". "+resultList.get(i).getTitle()+"\t"+resultList.get(i).getTime()+"\t"+resultList.get(i).getClick());
        }
    }
}
