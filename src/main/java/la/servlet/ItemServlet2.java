package la.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import la.bean.ItemBean;
import la.dao.DAOException;
import la.dao.ItemDAO2;

@WebServlet("/ItemServlet2")
public class ItemServlet2 extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			// リクエストパラメータの文字子コード（エンコーディング）を設定
			request.setCharacterEncoding("UTF-8");
			// パラメータの解析
			String action = request.getParameter("action");
			// モデルのDAOを生成
			ItemDAO2 dao = new ItemDAO2();
			// パラメータなしの場合は全レコード表示
			if (action == null || action.length() == 0) {
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			} 
			// addは追加
			else if (action.equals("add")) {
				String name = request.getParameter("name");
				int price = Integer.parseInt(request.getParameter("price"));
				dao.addItem(name, price);
				// 追加後、全レコード表示
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// sortはソート
			else if (action.equals("sort")) {
				String key = request.getParameter("key");
				List<ItemBean> list;
				if(key.equals("price_asc")) {
					list = dao.sortPrice(true);
				} else {
					list = dao.sortPrice(false);
				}
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}
			// searchは検索
			else if (action.equals("search")) {
				String priceMinSt = request.getParameter("priceMin");
				String priceMaxSt = request.getParameter("price");
				
				String name2 = request.getParameter("name2");
				
				HttpSession session = request.getSession();
				session.setAttribute("name", name2);
				session.setAttribute("minPrice", priceMinSt);
				session.setAttribute("maxPrice", priceMaxSt);
				
					
				
				if(name2 != "") {
					if (priceMinSt != "" && priceMaxSt != "") { // 両方に値あり
						int priceMin = Integer.parseInt(request.getParameter("priceMin"));
						int price = Integer.parseInt(request.getParameter("price"));
						List<ItemBean>list = dao.findByPrice5(priceMin, price, name2);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else if (priceMinSt != "" && priceMaxSt == "") { //　最小値のみ値あり
						int priceMin = Integer.parseInt(request.getParameter("priceMin"));
						List<ItemBean>list = dao.findByPrice6(priceMin, name2);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else if (priceMinSt == "" && priceMaxSt != ""){ // 最大値のみ値あり
						int price = Integer.parseInt(request.getParameter("price"));
						List<ItemBean>list = dao.findByPrice4(price, name2);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else if (priceMinSt == "" && priceMaxSt == ""){
						List<ItemBean>list = dao.findByPrice7(name2);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else {
						return;
					}
				} else if (name2 == ""){
					if (priceMinSt != "" && priceMaxSt != "") { // 両方に値あり
						int priceMin = Integer.parseInt(request.getParameter("priceMin"));
						int price = Integer.parseInt(request.getParameter("price"));
						List<ItemBean>list = dao.findByPrice2(priceMin, price);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else if (priceMinSt != "" && priceMaxSt == "") { //　最小値のみ値あり
						int priceMin = Integer.parseInt(request.getParameter("priceMin"));
						List<ItemBean>list = dao.findByPrice3(priceMin);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else if (priceMinSt == "" && priceMaxSt != ""){ // 最大値のみ値あり
						int price = Integer.parseInt(request.getParameter("price"));
						List<ItemBean>list = dao.findByPrice(price);
						// Listをリクエストスコープに入れてJSPへフォーワードする
						request.setAttribute("items", list);
						gotoPage(request, response, "/showItem2.jsp");
					} else {
						return;
					}
				}
				
			}
			
//			else if (action.equals("search")) {
//				int minPrice = 0;
//				int maxPrice = 0;
//				try{
//					String priceMinSt = request.getParameter("priceMin");
//					String priceMaxSt = request.getParameter("price");
//					minPrice = Integer.parseInt(request.getParameter(priceMinSt));
//					maxPrice = Integer.parseInt(request.getParameter(priceMaxSt));
//				}catch(NumberFormatException e) {
//					e.printStackTrace();
//					
//				}
//				List<ItemBean>list = dao.findByPrice2(minPrice, maxPrice);
//				// Listをリクエストスコープに入れてJSPへフォーワードする
//				request.setAttribute("items", list);
//				gotoPage(request, response, "/showItem2.jsp");
//			} 
			
			
			// deleteは削除
			else if (action.equals("delete")) {
				int code = Integer.parseInt(request.getParameter("code"));
				dao.deleteByPrimaryKey(code);
				// 削除後、全レコード表示
				List<ItemBean> list = dao.findAll();
				// Listをリクエストスコープに入れてJSPへフォーワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}