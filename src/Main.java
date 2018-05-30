import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.io.IOException;
import java.sql.*; //导入数据库处理所有库
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection cn = null;
    private static String path = "D:\\study\\Information Retrieval\\sdu_html_extract_2";
    private static String writeToPath = "D:\\study\\Information Retrieval\\resource";
    //test
//    private static String path = "D:\\study\\Information Retrieval\\new_L2";
//    private static String writeToPath = "D:\\study\\Information Retrieval\\new_L2_resource";

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Index index = new Index(path, writeToPath, new ReadFile());
            String query = request.getParameter("inputMessage");
            if(query == null ||query.trim().length() == 0)
                response.sendRedirect("index.jsp");
            else {
                Date begin = new Date();
                List<SDUNews> results = index.search(query, 500);
                SDUNews[] arrayResults = list2Array(results);

                if(results.size() == 0){
                    request.setAttribute("noResult","没有找到相关内容");
                    RequestDispatcher rd = request.getRequestDispatcher("noresult.jsp");
                    rd.forward(request, response);
                } else {
                    int hitsPage = results.size();
                    request.setAttribute("news total", hitsPage);
                    for(int i = 0; i < hitsPage; i++){
                        request.setAttribute("news title"+i, arrayResults[i].getTitle());
                        request.setAttribute("news url"+i, arrayResults[i].getUrl());
                        request.setAttribute("news content"+i, arrayResults[i].getContent());
                        request.setAttribute("news date"+i, arrayResults[i].getTime());
                        request.setAttribute("news click"+i, arrayResults[i].getClick());
                    }
                    Date end = new Date();
                    long timeOfSearch = end.getTime() - begin.getTime();
                    request.setAttribute("search time",""+timeOfSearch);
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Content-Type","text/html;charset=utf-8");
                    RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
                    rd.forward(request,response);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            cn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private SDUNews[] list2Array(List<SDUNews> resutls){
        SDUNews[] arrayResults = new SDUNews[resutls.size()];
        for(int i = 0; i < resutls.size(); i++){
            arrayResults[i] = resutls.get(i);
        }
        return arrayResults;
    }

    public static void main(String[] args) {
        // write your code here
        String keywords;
//        keywords = scan.nextLine();
        keywords = "山东大学";


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
