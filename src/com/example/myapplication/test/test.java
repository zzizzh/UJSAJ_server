<<<<<<< HEAD:src/com/example/myapplication/test/test.java
package com.example.myapplication.test;
=======
package test;
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/test/test.java
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

<<<<<<< HEAD:src/com/example/myapplication/test/test.java
import com.example.myapplication.DB.DBManager;
import com.example.myapplication.ProblemDomain.Posts;
=======
import DB.DBManager;
import ProblemDomain.Posts;
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/test/test.java
public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBManager d = new DBManager();

		Scanner keyboard = new Scanner(System.in);
		
		//Posts p = new Posts();
		//Location l = new Location();
		
		Posts p1 = new Posts(d.getPostsIndex());		
		File image1 = new File("C:\\Users\\안준영\\Desktop\\DSC00565.jpg");
		p1.setFImage(image1);
		d.insertPosts(p1);
		byte[] arr = d.getImageByIndex(22);
		System.out.println(arr);
		FileOutputStream fos = new FileOutputStream("C:\\Users\\안준영\\Desktop\\DSC00565new.jpg");
		fos.write(arr);
		fos.close();
	/*	
		l.setBigLocation(1234567890);
		l.setMidLocation(1234567890);
		l.setSmallLocation(1234567890);
		
		p.setLocationInfo(l);
		
		d.insertPosts(p);
*/
		
/*
		String string = keyboard.next();
		while (!(string.equals("quit"))) {

			User newUser = d.getUserByID(string);

			System.out.println(newUser.getUserIndex());
			System.out.println(newUser.getId());
			System.out.println(newUser.getPw());
			System.out.println(newUser.getLikeList());
			System.out.println(newUser.getMyList());
			
			String PW = d.getPWByID(string);
			System.out.println(PW);
			string = keyboard.next();
			
			
		}
		
		String id = keyboard.next();

		while (!(id.equals("quit"))) {

			int index = d.userIndex;
			String pw = keyboard.next();

			User user = new User(index, id, pw);
			d.insertUser(user);
			id = keyboard.next();

		}
		Posts p1 = new Posts(d.postsIndex);
		File image1 = null;
		p1.setFImage(image1);
		d.insertPosts(p1);
		*/
		
		/*
		Posts p1 = new Posts(d.postsIndex);		
		File image1 = new File("C:\\Users\\안준영\\Desktop\\DSC00565.jpg");
		p1.setFImage(image1);
		d.insertPosts(p1);
		*/
		/*
		Posts p2 = new Posts(d.postsIndex);
		File image2 = new File("C:\\Users\\안준영\\Desktop\\KakaoTalk_20170713_001838256.jpg");
		p2.setFImage(image2);		
		d.insertPosts(p2);
		*/		
		/*
		Posts p3 = new Posts(d.postsIndex);
		File image3 = new File("C:\\Users\\안준영\\Desktop\\IMG_9773.jpg");
		p3.setFImage(image3);		
		d.insertPosts(p3);
		
			
		Image image = d.getImageByIndex(0);
		BufferedImage bimage = (BufferedImage) image;
        ImageIO.write(bimage, "jpg", new File("C:\\Users\\안준영\\Desktop\\디비에서불러오기.jpg"));
	*/
		
	}

}
