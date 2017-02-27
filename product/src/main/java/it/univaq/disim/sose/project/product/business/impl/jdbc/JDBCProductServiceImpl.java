package it.univaq.disim.sose.project.product.business.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.project.product.business.BusinessException;
import it.univaq.disim.sose.project.product.business.ProductService;
import it.univaq.disim.sose.project.product.business.model.Product;

@Service
public class JDBCProductServiceImpl implements ProductService {
	private static Logger LOGGER = LoggerFactory.getLogger(JDBCProductServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public Product getProduct(int id) throws BusinessException {
		String sql = "SELECT * FROM products WHERE id=?";
		LOGGER.info(sql);	
		
		Product result = new Product();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(!rs.next())
				throw new BusinessException("Product not found");
			
			result.setId( rs.getInt("id") );
			result.setName( rs.getString("name") );
			result.setDescription( rs.getString("description") );
			result.setRemainingQuantity( rs.getInt("remaining_quantity") );
			result.setUnitPrice( rs.getDouble("unit_price") );
			result.setImage( rs.getString("image") );
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public List<Product> getProducts() throws BusinessException {
		String sql = "SELECT * FROM products";
		LOGGER.info(sql);
		
		List<Product> result = new ArrayList<Product>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			Product product;
			
			while (rs.next()) {
				product = new Product();
				product.setId( rs.getInt("id") );
				product.setName( rs.getString("name") );
				product.setDescription( rs.getString("description") );
				product.setRemainingQuantity( rs.getInt("remaining_quantity") );
				product.setUnitPrice( rs.getDouble("unit_price") );
				product.setImage( rs.getString("image") );
				
				result.add(product);
			}
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	@Override
	public void updateProduct(Product product) throws BusinessException {
		String sql = "UPDATE products "
				+ "SET name=?, description=?, remaining_quantity=?, unit_price=?, image=? "
				+ "WHERE id=?";		
		LOGGER.info(sql);	
		
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = dataSource.getConnection();			
			st = con.prepareStatement(sql);
			
			st.setString(1, product.getName());
			st.setString(2, product.getDescription());
			st.setInt(3, product.getRemainingQuantity());
			st.setDouble(4, product.getUnitPrice());
			st.setString(5, product.getImage());
			st.setInt(6, product.getId());
			
			st.executeUpdate();
		
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
