package la.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import la.bean.CategoryBean;
import la.bean.ItemBean;
import la.dao.DAOException;
import la.dao.ItemDAO;

@WebServlet("/ShowItemServlet")
public class ShowItemServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // パラメータの解析
            String action = request.getParameter("action");
            int currentPage = 1;
            int maxPage;
            
            if (action == null || action.length() == 0 || action.equals("top")) {
                // topまたはパラメータなしの場合はトップページを表示 
                gotoPage(request, response, "/top.jsp");
            } else if (action.equals("list")) {
            	// リクエストパラメータの取得と型変換
                int categoryCode = Integer.parseInt(request.getParameter("code"));
                
                if(request.getParameter("page") != null) {
                	int page = Integer.parseInt(request.getParameter("page"));
                	currentPage = page;
                }
                
                // DAOのインスタンス化
                ItemDAO dao = new ItemDAO();
                List<ItemBean> list = dao.findByCategory(categoryCode, currentPage);
                int count = dao.countByCategory(categoryCode);
                
                if (count % 10 != 0) {
                	maxPage = (count/10) + 1;
                } else {
                	maxPage = count/10;
                }
                
                // Listをリクエストスコープに入れてJSPへフォーワードする
                request.setAttribute("items", list);
                request.setAttribute("count", count);
                request.setAttribute("maxPage", maxPage);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("action", action);
                request.setAttribute("code", categoryCode);
                gotoPage(request, response, "/list.jsp");
            } else if (action.equals("detail")){
            	// リクエストパラメータの取得と型変換
                int code = Integer.parseInt(request.getParameter("code"));
                // DAOのインスタンス化
                ItemDAO dao = new ItemDAO();
                ItemBean bean  = dao.findByPrimaryKey(code);
                // Listをリクエストスコープに入れてJSPへフォーワードする
                request.setAttribute("items", bean);
                gotoPage(request, response, "/item.jsp");
            } else if(action.equals("search")) {
            	// リクエストパラメータの取得と型変換
                String keyword = request.getParameter("keyword");
                
                if(request.getParameter("page") != null) {
                	int page = Integer.parseInt(request.getParameter("page"));
                	currentPage = page;
                }
                // DAOのインスタンス化
                ItemDAO dao = new ItemDAO();
                List<ItemBean> list = dao.findByName(keyword, currentPage);
                int count = dao.countByName(keyword);
                
                
                if (count % 10 != 0) {
                	maxPage = (count/10) + 1;
                } else {
                	maxPage = count/10;
                }
                
                
                
                
                // Listをリクエストスコープに入れてJSPへフォーワードする
                request.setAttribute("items", list);
                request.setAttribute("count", count);
                request.setAttribute("maxPage", maxPage);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("action", action);
                gotoPage(request, response, "/list.jsp");
//            } else if() {
            	
            } else {
                request.setAttribute("message", "正しく操作してください。");
                gotoPage(request, response, "/errInternal.jsp");
            }
        } catch (DAOException e) {
            e.printStackTrace();
            request.setAttribute("message", "内部エラーが発生しました。");
            gotoPage(request, response, "/errInternal.jsp");
        }
    }

    private void gotoPage(HttpServletRequest request,
            HttpServletResponse response, String page) throws ServletException,
            IOException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    public void init() throws ServletException {
        try {
            // カテゴリ一覧は最初にアプリケーションスコープへ入れる
            ItemDAO dao = new ItemDAO();
            List<CategoryBean> list = dao.findAllCategory();
            getServletContext().setAttribute("categories", list);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}