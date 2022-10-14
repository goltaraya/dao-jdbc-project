package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		
		Department department = new Department(1, "Marketing");
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(2);
		
		System.out.println(seller);
	}
}
