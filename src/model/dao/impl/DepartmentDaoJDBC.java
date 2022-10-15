package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! Something wrong happened...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				System.out.println("Update done! Rows affected: " + rowsAffected);
			} else {
				throw new DbException("Unexpected error! Something went wrong...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM department");

			rs = st.executeQuery();

			List<Department> departmentList = new ArrayList<Department>();

			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				departmentList.add(dep);
			}

			return departmentList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt(1));
		dep.setName(rs.getString(2));
		return dep;
	}

}
