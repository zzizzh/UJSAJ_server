package com.example.myapplication.DB;

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
import javax.swing.ImageIcon;

import com.example.myapplication.ProblemDomain.Location;
import com.example.myapplication.ProblemDomain.Posts;
import com.example.myapplication.ProblemDomain.User;
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

public class DBManager {

	int userIndex;
	int postsIndex;
	int recentIndex; // �뼱�뵆 耳곗쓣�떆 寃뚯떆臾� 媛��닔
	int seeIndex; // �옄�떊�씠 蹂� �씤�뜳�뒪
	String MongoDB_IP = "127.0.0.1";
	// String MongoDB_IP = "222.104.203.106";
	int MongoDB_PORT = 27017;
	String DB_NAME = "db";

	MongoClient mongoClient = new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
	DB db = mongoClient.getDB(DB_NAME);
	public DBCollection userCollection = db.getCollection("User");
	DBCollection postsCollection = db.getCollection("Posts");
	DBCollection locationCollection = db.getCollection("Location");

	public DBManager() {

		DBCursor userCursor = userCollection.find();
		DBCursor postsCursor = postsCollection.find();

		// count all users in DB
		userIndex = (int) userCollection.count();

		// count all posts in DB
		postsIndex = (int) postsCollection.count();

		recentIndex = (int) postsCollection.count();
		seeIndex = (int) postsCollection.count();
	}

	public int getUserIndex() {
		return userIndex;
	}

	public int getPostsIndex() {
		return postsIndex;
	}

	public ArrayList<Posts> refreshTimeLine() throws Exception {
		ArrayList<Posts> P = new ArrayList<Posts>();
		int dbIndex = (int) postsCollection.count();
<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java
		
		if (dbIndex > 0) {
			if (dbIndex > 10) {
				for (int i = 0; i < 10; i++) {
					Posts posts = this.getPostsByIndex(recentIndex);
					System.out.println("recentIndex : " + recentIndex + " " + posts.toString());
					byte[] arr = this.getImageByIndex(recentIndex);
					
					if (arr != null) {
						posts.setIImage(arr);	
					}else{
						posts.setIImage(null);
					}
					
					// posts.setIImage(this.getImageByIndex(recentIndex));
					P.add(posts);
					recentIndex--;
				}
			}
		}
		seeIndex = recentIndex;
		
=======
		System.out.println(dbIndex);
		int num = recentIndex;
		System.out.println(num);
		if (num < dbIndex) {
		
			if (dbIndex - num < 10) {
				for (int i = 0; i < dbIndex - num; i++) {
					
					Posts posts = this.getPostsByIndex(recentIndex);
					byte[] arr = this.getImageByIndex(recentIndex);
					
					if (arr != null) {
						posts.setIImage(arr);	
					}else{
						posts.setIImage(null);
					}
					
					// posts.setIImage(this.getImageByIndex(recentIndex));
					P.add(posts);
					recentIndex++;
				}
			} else {
				for (int i = 0; i < 10; i++) {
					
					Posts posts = this.getPostsByIndex(recentIndex);
					byte[] arr = this.getImageByIndex(recentIndex);
					if (arr != null) {
						posts.setIImage(arr);
					} 
					else{
						posts.setIImage(null);
					}
					// posts.setIImage(this.getImageByIndex(recentIndex));
					P.add(this.getPostsByIndex(recentIndex));
					recentIndex++;
				}
			}
		}

>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java
		return P;
	}

	public ArrayList<Posts> getMorePosts() throws Exception {
		ArrayList<Posts> p = new ArrayList<Posts>();

		int k = seeIndex;
		if (seeIndex < 10) {
<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java
			for (; seeIndex > 0; seeIndex--) {

=======
			for (int i = 0; i < k; i++) {
				
				seeIndex--;
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java
				Posts posts = this.getPostsByIndex(seeIndex);
				byte[] arr = this.getImageByIndex(seeIndex);
				if (arr != null) {
					posts.setIImage(arr);
				} else {
					posts.setIImage(null);
				}
<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java

=======
			
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java
				// posts.setIImage(this.getImageByIndex(seeIndex));
				p.add(posts);
			}
		} else {
<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java
			for (; seeIndex > seeIndex - 10; seeIndex--) {

=======
			for (int i = 0; i < 10; i++) {
				
				seeIndex--;
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java
				Posts posts = this.getPostsByIndex(seeIndex);
				byte[] arr = this.getImageByIndex(seeIndex);
				if (arr != null) {
					posts.setIImage(arr);
				} else {
					posts.setIImage(null);
				}

				// posts.setIImage(this.getImageByIndex(seeIndex));
				p.add(posts);
			}
		}

		return p;
	}

	public void insertUser(User user) {

		BasicDBObject document = new BasicDBObject();
		// index
		document.put("index", userIndex);
		userIndex++;
		// ID
		document.put("ID", user.getId());
		// PW
		document.put("PW", user.getPw());
		// LikeList
		document.put("LikeList", user.getLikeList());
		// MyList
		document.put("MyList", user.getMyList());

		// DB
		userCollection.insert(document);

	}

	public User getUserByIndex(int index) {
		User user = new User();

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("index", index);

		DBCursor cursorId = userCollection.find(idQuery);

		if (cursorId.hasNext()) {
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

	public void updateUser(User user) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("index", user.getUserIndex());
		updateQuery.put("ID", user.getId());
		updateQuery.put("PW", user.getPw());
		updateQuery.put("LikeList", user.getLikeList());
		updateQuery.put("MyList", user.getMyList());

		BasicDBObject searchQuery = new BasicDBObject().append("index", user.getUserIndex());

		//
		userCollection.update(searchQuery, updateQuery);

	}

	//
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

	//
	public User getUserByID(String id) {

		User user = new User();

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("ID", id);

		DBCursor cursorId = userCollection.find(idQuery);

		if (cursorId.hasNext()) {
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

	//
	public void insertPosts(Posts posts) {

		BasicDBObject document = new BasicDBObject();
		document.put("index", postsIndex);
		this.insertLocation(posts.getLocationInfo());
		postsIndex++;
		document.put("URL", posts.getUrl());
		document.put("Artist", posts.getArtist());
		document.put("Song", posts.getSong());
		document.put("Comment", posts.getComment());
		document.put("PostsID", posts.getPostsID());
		document.put("Like", posts.getLike());
		document.put("CreateTime", posts.getCreateTime());

		if (posts.getFImage() != null) {
			try {

				String newFileName = posts.getFileName();

				File imageFile = posts.getFImage();

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
		}

		// document.put("Image", posts.getImage());

		// Insert Data01
		postsCollection.insert(document);

	}

	//
	public void updatePostsList(Posts posts) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("index", posts.getPostsIndex());
		updateQuery.put("URL", posts.getUrl());
		updateQuery.put("Artist", posts.getArtist());
		updateQuery.put("Song", posts.getSong());
		updateQuery.put("Comment", posts.getComment());
		updateQuery.put("PostsID", posts.getPostsID());
		updateQuery.put("Like", posts.getLike());
		updateQuery.put("CreateTime", posts.getCreateTime());

		BasicDBObject searchQuery = new BasicDBObject().append("index", posts.getPostsIndex());
		postsCollection.update(searchQuery, updateQuery);

	}

	public void deletePosts(int index) {
		DBObject document = new BasicDBObject();
		document.put("index", index);
		postsCollection.remove(document);
	}

	// index
	public Posts getPostsByIndex(int index) {

		Posts posts = new Posts();

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("index", index);

		DBCursor cursorId = postsCollection.find(idQuery);

		if (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				posts.setPostsIndex((Integer) check.get("index"));

				Location location = new Location();
				BasicDBObject locQuery = new BasicDBObject();
				locQuery.put("index", index);

				DBCursor cursorLoc = locationCollection.find(locQuery);
				if (cursorLoc.hasNext()) {
					DBObject checkLoc = null;
					checkLoc = cursorLoc.next();
					if (checkLoc != null) {
						location.setBigLocation((int) checkLoc.get("BigLocation"));
						location.setMidLocation((int) checkLoc.get("MidLocation"));
						location.setSmallLocation((int) checkLoc.get("SmallLocation"));
						location.setContentID((int) checkLoc.get("ContentID"));
						location.setContentTypeID((int) checkLoc.get("ContentTypeID"));
						location.setTitle((String) checkLoc.get("Title"));

					}
				}
				posts.setLocationInfo(location);
				posts.setUrl((String) check.get("URL"));
				posts.setArtist((String) check.get("Artist"));
				posts.setSong((String) check.get("Song"));
				posts.setComment((String) check.get("Comment"));
				posts.setPostsID((int) check.get("PostsID"));
				posts.setLike((int) check.get("Like"));
				posts.setCreateTime((long) check.get("CreateTime"));
			}
		}
		return posts;
	}

	// index
	public byte[] getImageByIndex(int index) throws Exception {

		Posts posts = new Posts();
		byte[] Iimage;
		GridFS gfsPhoto = new GridFS(db, "Image");

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("index", index);

		DBCursor cursorId = postsCollection.find(idQuery);

		while (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {

				try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

					GridFSDBFile imageForOutput = gfsPhoto.findOne(Integer.toString(index));
					if (imageForOutput == null) {
						return null;
					}
					imageForOutput.writeTo(outputStream);

					BufferedImage bimage;
					Image newImage;

					ImageInputStream iis = ImageIO
							.createImageInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
					Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
					if (iter.hasNext()) {
						ImageReader reader = (ImageReader) iter.next();
						reader.setInput(iis);
					}

<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java
					Iimage = outputStream.toByteArray();
					return Iimage;
=======
					return outputStream.toByteArray();
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java

				} catch (IOException e) {
					throw new Exception("Cannot retrieve content of gridFsFile [" + index + "]", e);
				}

			}
		}
		return null;
	}

	/*
	 * int bigLocation; int midLocation; int smallLocation; int contentID; int
	 * contentTypeID; String title;
	 */

	public void insertLocation(Location location) {

		BasicDBObject document = new BasicDBObject();
		document.put("index", postsIndex);
		document.put("BigLocation", location.getBigLocation());
		document.put("MidLocation", location.getMidLocation());
		document.put("SmallLocation", location.getSmallLocation());
		document.put("ContentID", location.getContentID());
		document.put("ContentTypeID", location.getContentTypeID());
		document.put("Title", location.getTitle());
		// DB
		locationCollection.insert(document);

	}

	public ArrayList<Posts> getPostsByLocation(Location location) {

		ArrayList<Integer> i = new ArrayList<Integer>();
		ArrayList<Posts> p = new ArrayList<Posts>();

		BasicDBObject cquery = new BasicDBObject();

		cquery.put("ContentID", location.getContentID());

		DBCursor ccursor = locationCollection.find(cquery);
		while (ccursor.hasNext() && i.size() < 10) {
			int index = (int) ccursor.next().get("index");
			i.add(index);
		}
		if (i.size() < 10) {
			BasicDBObject squery = new BasicDBObject();

			squery.put("ContentID", new BasicDBObject("$ne", location.getContentID()));
			squery.put("SmallLocation", location.getSmallLocation());

			DBCursor cursor = locationCollection.find(squery);
			while (cursor.hasNext() && i.size() < 10) {
				int index = (int) cursor.next().get("index");
				i.add(index);
			}
		}
		if (i.size() < 10) {
			BasicDBObject mquery = new BasicDBObject();

			mquery.put("ContentID", new BasicDBObject("$ne", location.getContentID()));
			mquery.put("SmallLocation", new BasicDBObject("$ne", location.getSmallLocation()));
			mquery.put("MidLocation", location.getMidLocation());

			DBCursor mcursor = locationCollection.find(mquery);
			while (mcursor.hasNext() && i.size() < 10) {
				int index = (int) mcursor.next().get("index");
				i.add(index);
			}
		}
		if (i.size() < 10) {
			BasicDBObject bquery = new BasicDBObject();

			bquery.put("ContentID", new BasicDBObject("$ne", location.getContentID()));
			bquery.put("SmallLocation", new BasicDBObject("$ne", location.getSmallLocation()));
			bquery.put("MidLocation", new BasicDBObject("$ne", location.getMidLocation()));
			bquery.put("BigLocation", location.getBigLocation());

			DBCursor bcursor = locationCollection.find(bquery);
			while (bcursor.hasNext() && i.size() < 10) {
				int index = (int) bcursor.next().get("index");
				i.add(index);
			}
		}
		for (int j = 0; j < i.size(); j++) {
			Posts posts = this.getPostsByIndex(i.get(j));
			p.add(posts);
		}
		return p;
	}

	public ArrayList<Posts> getPostsByOption(int option, int value) {
		ArrayList<Integer> i = new ArrayList<Integer>();
		ArrayList<Posts> p = new ArrayList<Posts>();

		if (option == 0) {
			BasicDBObject cquery = new BasicDBObject();

			cquery.put("SmallLocation", value);

			DBCursor ccursor = locationCollection.find(cquery);

			while (ccursor.hasNext() && i.size() < 10) {
				int index = (int) ccursor.next().get("index");
				i.add(index);
			}
		} else if (option == 1) {
			BasicDBObject cquery = new BasicDBObject();

			cquery.put("MidLocation", value);

			DBCursor ccursor = locationCollection.find(cquery);

			while (ccursor.hasNext() && i.size() < 10) {
				int index = (int) ccursor.next().get("index");
				i.add(index);
			}
		} else if (option == 2) {
			BasicDBObject cquery = new BasicDBObject();

			cquery.put("BigLocation", value);

			DBCursor ccursor = locationCollection.find(cquery);

			while (ccursor.hasNext() && i.size() < 10) {
				int index = (int) ccursor.next().get("index");
				i.add(index);
			}
		} else if (option == 3) {
			BasicDBObject cquery = new BasicDBObject();

			cquery.put("ContentID", value);

			DBCursor ccursor = locationCollection.find(cquery);

			while (ccursor.hasNext() && i.size() < 10) {
				int index = (int) ccursor.next().get("index");
				i.add(index);
			}
<<<<<<< HEAD:src/com/example/myapplication/DB/DBManager.java
		} else {
			return p;
		}

=======
		} else{
			return p;
		}
		
>>>>>>> 619d7eb1fd82982bb59d5c0395da8b1bf0fbe25c:src/DB/DBManager.java
		for (int j = 0; j < i.size(); j++) {
			Posts posts = this.getPostsByIndex(i.get(j));
			p.add(posts);
		}
		return p;
	}
}