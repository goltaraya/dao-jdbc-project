package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		// TEST 1
		System.out.println("===== TEST 1: department findById =====");
		Department department = departmentDao.findById(4);
		System.out.println(department);

		// TEST 2
		System.out.println("\n===== TEST 2: department findAll =====");
		List<Department> list = departmentDao.findAll();
		for (Department dep : list) {
			System.out.println(dep);
		}

		// TEST 3
		System.out.println("\n===== TEST 3: department insert =====");
		Department newDepartment = new Department(null, "Design");
		departmentDao.insert(newDepartment);
		System.out.println("Department: " + newDepartment.getName() + ", Id: " + newDepartment.getId());

		// TEST 4
		System.out.println("\n===== TEST 4: department update =====");
		department = departmentDao.findById(7);
		department.setName("Hotel");
		departmentDao.update(department);
	}
}
