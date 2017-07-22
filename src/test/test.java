package test;
import java.io.File;
import java.util.Scanner;

import javax.imageio.stream.FileImageOutputStream;

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
		
		int postsIndex = d.postsIndex;
		
		Posts p = new Posts(postsIndex);
	
		File image = new File("C:\\Users\\안준영\\Desktop\\DSC00565.jpg");
		p.setImage(image);
		
		d.insertPosts(p);
		
		byte[] buffer = d.getImageByIndex(1);
		FileImageOutputStream imageOutput = new FileImageOutputStream(new File("C:\\Users\\안준영\\Desktop\\DSC00565new.jpg"));
        imageOutput.write(buffer, 0, buffer.length);
        imageOutput.close();

	}

}
