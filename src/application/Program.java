package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		
		Department department = new Department(1, "Marketing");
		
		Seller seller = new Seller(2, "Yago", "goltara@mail.com", new Date(), 20000.0, department);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(seller);
	}
}
