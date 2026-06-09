package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.ItemBean;

public class ItemDAO2 {
	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:sample";
	private String user = "student";
	private String pass = "himitu";
	
	public ItemDAO2() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("ドライバの登録に失敗しました。");
		}
	}

	public List<ItemBean> findAll() throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item";

		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);
			 // SQLの実行
			 ResultSet rs = st.executeQuery();) {
			// 結果の取得
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			// 商品一覧をListとして返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public List<ItemBean> sortPrice(boolean isAscending) throws DAOException {
		// SQL文の作成
		String sql;
		// ソートキーの指定
		if (isAscending)
			sql = "SELECT * FROM item ORDER BY price";
		else
			sql = "SELECT * FROM item ORDER BY price desc";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);
			 // SQLの実行
			 ResultSet rs = st.executeQuery();) {
			// 結果の取得
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			// 商品一覧をListとして返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		} 
	}

	public int addItem(String name, int price) throws DAOException {
		// SQL文の作成
		String sql = "INSERT INTO item(name, price) VALUES(?, ?)";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 商品名と値段の指定：パラメータバインディング
			st.setString(1, name);
			st.setInt(2, price);
			// SQLの実行
			int rows = st.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		} 
	}

	public List<ItemBean> findByPrice(int lePrice) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE price <= ? ";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setInt(1, lePrice);
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice2(int priceMin, int lePrice) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE price BETWEEN ? AND ?" ;
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setInt(1, priceMin);
			st.setInt(2, lePrice);
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice3(int lePrice) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE price >= ?";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setInt(1, lePrice);
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice4(int lePrice, String name2) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE price <= ? AND name LIKE ?";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setInt(1, lePrice);
			st.setString(2, "%" + name2 + "%");
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice5(int priceMin, int lePrice, String name2) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE name LIKE ? AND price BETWEEN ? AND ?" ;
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setString(1, "%" + name2 + "%");
			st.setInt(2, priceMin);
			st.setInt(3, lePrice);
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice6(int lePrice, String name2) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE name LIKE ? AND price >= ?";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setString(1, "%" + name2 + "%");
			st.setInt(2, lePrice);
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	public List<ItemBean> findByPrice7(String name2) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM item WHERE name LIKE ?" ;
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setString(1, "%" + name2 + "%");
			
			try (// SQLの実行
				 ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧をListとして返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
	
	

	public int deleteByPrimaryKey(int key) throws DAOException {
		// SQL文の作成
		String sql = "DELETE FROM item WHERE code = ?";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 主キーの指定
			st.setInt(1, key);
			// SQLの実行
			int rows = st.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		} 
	}
	
	
	public int updateByPrimaryKey(int key, int price) throws DAOException {
		// SQL文の作成
		String sql = " UPDATE item SET price = ? WHERE code = ?";
		
		try (// データベースへの接続
			 Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
			// 主キーの指定
			st.setInt(1, price);
			st.setInt(2, key);
			// SQLの実行
			int rows = st.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		} 
	}
	
//	step05
//	public List<ItemBean> findByNameAndPrice(String name, String minPrice, String maxPrice, String sort)
//		throws DAOException {
//		String sql = "SELECT * FROM item WHERE 1 = 1";
//		
//		if(name != null && name.length() != 0) {
//			sql += "AND name like ?";
//		}
//		if(minPrice != null && minPrice.length() != 0) {
//			sql += "AND prive >= ?";
//		}
//		
//	}
}