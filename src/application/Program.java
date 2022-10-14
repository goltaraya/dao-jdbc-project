package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		// TEST 1
		System.out.println("===== TEST 1: seller.findById =====");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);

		// TEST 2
		System.out.println("\n===== TEST 2: seller.findByDepartment =====");
		Department dep = new Department(1, null);
		List<Seller> sellerList = sellerDao.findByDepartment(dep);
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}

		// TEST 3
		
	}
}
