package app;

import jakarta.persistence.Persistence;

public class CreateDb {
	public static void main(String[] args) {
		try {
			Persistence.createEntityManagerFactory("STT11_NguyenVanGiau_20053331_20");
			System.out.println("Connected");
		}catch (Exception ex){
			System.out.println("Disconnect error: " + ex);
		}
	}
}
