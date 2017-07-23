package test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import DB.DBManager;
import ProblemDomain.Posts;
import ProblemDomain.User;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBManager d = new DBManager();

		Scanner keyboard = new Scanner(System.in);

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
		File image1 = new File("C:\\Users\\안준영\\Desktop\\DSC00565.jpg");
		p1.setImage(image1);
		d.insertPosts(p1);
				
		Posts p2 = new Posts(d.postsIndex);
		File image2 = new File("C:\\Users\\안준영\\Desktop\\KakaoTalk_20170713_001838256.jpg");
		p2.setImage(image2);		
		d.insertPosts(p2);		
		
		Posts p3 = new Posts(d.postsIndex);
		File image3 = new File("C:\\Users\\안준영\\Desktop\\IMG_9773.jpg");
		p3.setImage(image3);		
		d.insertPosts(p3);
		
		byte[] chunk = d.getImageByIndex(1);
		
		BufferedImage bimage;
		Image newImage;
		
		 ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(chunk));
         Iterator<ImageReader> iter=ImageIO.getImageReaders(iis);
         if (iter.hasNext()) {
             ImageReader reader = (ImageReader)iter.next();
             reader.setInput(iis);
         }
         bimage = ImageIO.read( new ByteArrayInputStream(chunk));
         
         newImage = (Image)bimage;
         
         ImageIO.write(bimage, "jpg", new File("C:\\Users\\안준영\\Desktop\\디비에서불러오기.jpg"));

	}

}
