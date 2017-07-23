package DB;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import ProblemDomain.Posts;
import ProblemDomain.User;

public class DBManager {

	public int userIndex;
	public int postsIndex;
	String MongoDB_IP = "127.0.0.1";
	int MongoDB_PORT = 27017;
	String DB_NAME = "db";

	MongoClient mongoClient = new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
	DB db = mongoClient.getDB(DB_NAME);
	DBCollection userCollection = db.getCollection("User");
	DBCollection postsCollection = db.getCollection("Posts");

	public DBManager() {
		int userIndex = 0;
		int postsIndex = 0;

		DBCursor userCursor = userCollection.find();
		DBCursor postsCursor = postsCollection.find();

		while (userCursor.hasNext()) {
			userCursor.next();
			userIndex++;
		}

		while (postsCursor.hasNext()) {
			postsCursor.next();
			postsIndex++;
		}

		this.userIndex = userIndex;
		this.postsIndex = postsIndex;

	}

	public void insertUser(User user) {

		// =========== Make Data01 by BasicDBObject ===========
		BasicDBObject document = new BasicDBObject();
		document.put("index", userIndex);
		userIndex++;

		document.put("ID", user.getId());
		document.put("PW", user.getPw());
		document.put("LikeList", user.getLikeList());
		document.put("MyList", user.getMyList());

		// Insert Data01
		userCollection.insert(document);

	}

	public void updateList(User user) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("index", user.getUserIndex());
		updateQuery.put("ID", user.getId());
		updateQuery.put("PW", user.getPw());
		updateQuery.put("LikeList", user.getLikeList());
		updateQuery.put("MyList", user.getMyList());

		BasicDBObject searchQuery = new BasicDBObject().append("index", user.getUserIndex());
		userCollection.update(searchQuery, updateQuery);

	}

	public String getPWByID(String id) {
		String PW;

		// Check Data in Database
		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("ID", id);

		DBCursor cursorId = userCollection.find(idQuery);
		// DBCursor cursorPw = collection.find(PW);

		while (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				return (String) check.get("PW");
			}
		}

		return null;
	}

	public User getUserByID(String id) {

		User user = new User();

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("ID", id);

		DBCursor cursorId = userCollection.find(idQuery);

		while (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				user.setUserIndex((Integer) check.get("index"));
				user.setUserId((String) check.get("ID"));
				user.setUserPw((String) check.get("PW"));
				user.setUserLikeList((ArrayList<Integer>) check.get("LikeList"));
				user.setUserMyList((ArrayList<Integer>) check.get("MyList"));
				return user;
			}
		}
		return null;
	}

	/*
	 * int postsIndex; Location locationInfo; String url; String artist; String
	 * song; String comment;
	 * 
	 * int postsID; int like;
	 * 
	 * long createTime; Image image;
	 * 
	 */

	public void insertPosts(Posts posts) {

		BasicDBObject document = new BasicDBObject();
		document.put("index", postsIndex);
		postsIndex++;

		document.put("Location", posts.getLocationInfo());
		document.put("URL", posts.getUrl());
		document.put("Artist", posts.getArtist());
		document.put("Song", posts.getSong());
		document.put("Comment", posts.getComment());
		document.put("PostsID", posts.getPostsID());
		document.put("Like", posts.getLike());
		document.put("CreateTime", posts.getCreateTime());

		try {

			String newFileName = posts.getFileName();

			File imageFile = posts.getImage();

			// create a "photo" namespace
			GridFS gfsPhoto = new GridFS(db, "Image");

			// get image file from local drive
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

			// set a new filename for identify purpose
			gfsFile.setFilename(newFileName);

			// save the image file into mongoDB
			gfsFile.save();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// document.put("Image", posts.getImage());

		// Insert Data01
		postsCollection.insert(document);

	}

	public void updatePostsList(Posts posts) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("index", posts.getPostsIndex());
		updateQuery.put("Location", posts.getLocationInfo());
		updateQuery.put("URL", posts.getUrl());
		updateQuery.put("Artist", posts.getArtist());
		updateQuery.put("Song", posts.getSong());
		updateQuery.put("Comment", posts.getComment());
		updateQuery.put("PostsID", posts.getPostsID());
		updateQuery.put("Like", posts.getLike());
		updateQuery.put("CreateTime", posts.getCreateTime());
		updateQuery.put("Image", posts.getImage());

		BasicDBObject searchQuery = new BasicDBObject().append("index", posts.getPostsIndex());
		postsCollection.update(searchQuery, updateQuery);

	}

	public Image getImageByIndex(int index) throws Exception {

		Posts posts = new Posts();

		GridFS gfsPhoto = new GridFS(db, "Image");

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("index", index);

		DBCursor cursorId = postsCollection.find(idQuery);

		while (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				
				try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
					
					GridFSDBFile imageForOutput = gfsPhoto.findOne(Integer.toString(index));
					imageForOutput.writeTo(outputStream);
					
					
					BufferedImage bimage;
					Image newImage;
					
					 ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
			         Iterator<ImageReader> iter=ImageIO.getImageReaders(iis);
			         if (iter.hasNext()) {
			             ImageReader reader = (ImageReader)iter.next();
			             reader.setInput(iis);
			         }
			         bimage = ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));
			         
			         newImage = (Image)bimage;
			         
			         return newImage;
					
				  } catch(IOException e) {
				    throw new Exception("Cannot retrieve content of gridFsFile [" + index + "]", e);
				  }		
				

			}
		}

		return null;
	}

}