package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); // Get GeneratedId and relate it to the new seller
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE seller " + "SET Name = ?, " + "Email = ?, " + "BirthDate = ?, "
					+ "BaseSalary = ?, " + "DepartmentId = ? " + "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

			st.setInt(1, id);

			st.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");
			statement.setInt(1, id);

			rs = statement.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);

				return seller;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		return null;
	}

	public List<Seller> findByDepartment(Department department) {
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			statement.setInt(1, department.getId());

			rs = statement.executeQuery();

			List<Seller> sellerList = new ArrayList<Seller>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id " + "ORDER BY Id");

			rs = statement.executeQuery();

			List<Seller> sellerList = new ArrayList<Seller>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();

		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);

		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
}
