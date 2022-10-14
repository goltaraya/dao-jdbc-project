package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		// TEST 1
		System.out.println("===== TEST 1: findById =====");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);

		// TEST 2
		System.out.println("\n===== TEST 2: findByDepartment =====");
		Department dep = new Department(1, null);
		List<Seller> sellerList = sellerDao.findByDepartment(dep);
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}

		// TEST 3
		System.out.println("\n===== TEST 3: findAll =====");
		sellerList = sellerDao.findAll();
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}

		// TEST 4
		System.out.println("\n===== TEST 4: insert =====");
		Seller newSeller = new Seller(null, "Robert Jonson", "robert@gmail.com", new Date(), 1000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("New ID: " + newSeller.getId());
		
		// TEST 5
		System.out.println("\n===== TEST 5: update =====");
		seller = new Seller(2, "Yago Alexandre", "mail@mail.com", new Date(), 1000.0, dep);
		sellerDao.update(seller);
		System.out.println("Update done!");
		
		// TEST 6 
		System.out.println("\n===== TEST 6: delete =====");
		int id = 3;
		sellerDao.deleteById(id);
		System.out.println("Done! Id: " + id + " deleted.");
	}
}
