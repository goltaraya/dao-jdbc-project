package application;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Department department = new Department(1, "Marketing");
		
		Seller seller = new Seller(2, "Yago", "goltara@mail.com", new Date(), 20000.0, department);
		
		System.out.println(seller);
	}
}
