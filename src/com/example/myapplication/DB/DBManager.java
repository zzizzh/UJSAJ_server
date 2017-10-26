package com.example.myapplication.DB;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.example.myapplication.ProblemDomain.Location;
import com.example.myapplication.ProblemDomain.Posts;
import com.example.myapplication.ProblemDomain.Music;
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
	DBCollection songCollection = db.getCollection("Song");
	
	public DBManager() {

		DBCursor userCursor = userCollection.find();
		DBCursor postsCursor = postsCollection.find();
		
		userCollection.createIndex("ID");
		songCollection.createIndex("songID");
		postsCollection.createIndex("index");
		// count all users in DB
		userIndex = (int) userCollection.count() ;

		// count all posts in DB
		postsIndex = (int) postsCollection.count() ;

		recentIndex = (int) postsCollection.count() ;
		seeIndex = (int) postsCollection.count() - 1;
		
	}

	public int getUserIndex() {
		return userIndex;
	}

	public int getPostsIndex() {
		return postsIndex;
	}

	public ArrayList<Posts> refreshTimeLine() throws Exception {
		ArrayList<Posts> P = new ArrayList<Posts>();
		recentIndex = (int) postsCollection.count() - 1;
		int dbIndex = recentIndex;

		if (dbIndex >= 0) {
			if (dbIndex >= 7) {
				for (int i = 0; i < 7; i++) {
					Posts posts = getPostsByIndex(recentIndex);
					/*
					try {
						File file = new File("C:\\Users\\안준영\\Desktop\\DB사진\\" + posts.getPostsIndex() + ".png");
						InputStream is = new FileInputStream(file);
						long length = file.length();

						byte[] bytes = new byte[(int) length];
						int offset = 0;
						int numRead = 0;
						while (offset < bytes.length
								&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
							offset += numRead;
						}
						posts.setIImage(bytes);
						if (offset < bytes.length) {
							throw new IOException("Could not completely read file " + file.getName());
						}
						is.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// posts.setIImage(this.getImageByIndex(recentIndex));
					 * 
					 */
					P.add(posts);
					recentIndex--;
				}
			}
			else{
				for(int i=0;i<=dbIndex;i++){
					Posts posts = getPostsByIndex(recentIndex);
					/*
					File file = new File("C:\\Users\\안준영\\Desktop\\DB사진\\" + posts.getPostsIndex() + ".png");
					if(file != null){
						
						InputStream is = new FileInputStream(file);
						long length = file.length();

						byte[] bytes = new byte[(int) length];
						int offset = 0;
						int numRead = 0;
						while (offset < bytes.length
								&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
							offset += numRead;
						}
						posts.setIImage(bytes);
						if (offset < bytes.length) {
							throw new IOException("Could not completely read file " + file.getName());
						}
						is.close();
					} 
					
					posts.setIImage(this.getImageByIndex(recentIndex));
					*/
					P.add(posts);
					recentIndex--;
				}
			}
			
		}
		seeIndex = recentIndex;

		return P;
	}

	
	
	public ArrayList<Posts> getMorePosts() throws Exception {
		ArrayList<Posts> p = new ArrayList<Posts>();

		int k = seeIndex;
		if (seeIndex < 7) {
			for (; seeIndex >= 0; seeIndex--) {

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
		} else {
			for (; seeIndex > seeIndex - 5; seeIndex--) {

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
	public void insertUserImage(User user,File Fimage){
		if (user.getFImage() != null) {
			try {

				String newFileName = Integer.toString(user.getUserIndex());

				File imageFile = Fimage;

				// create a "photo" namespace
				GridFS gfsPhoto = new GridFS(db, "UserImage");

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

	}
	public User getUserByIndex(int index) throws Exception {
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
				
				ArrayList<Integer> arrL1 = new ArrayList<Integer>();
				ArrayList<Integer> arrL2 = new ArrayList<Integer>();
				
				arrL1 = (ArrayList<Integer>)(check.get("LikeList"));
				
				for(int i=0; i<arrL1.size();i++){
					arrL2.add(arrL1.get(i));
				}
		
			
				user.setUserLikeList(arrL2);
			
				
				ArrayList<Integer> arrM1 = new ArrayList<Integer>();
				ArrayList<Integer> arrM2 = new ArrayList<Integer>();
				arrM1 = (ArrayList<Integer>)(check.get("MyList"));
				
				for(int i=0;i<arrM1.size();i++){
					arrM2.add(arrM1.get(i));
				}
				user.setUserMyList(arrM2);

				user.setIImage(this.getUserImageByIndex(user.getUserIndex()));
				/*
				BasicDBObject locQuery = new BasicDBObject();
				locQuery.put("index", index);
				try { // 내컴퓨터에서 받는거
					File file = new File("C:\\Users\\안준영\\Desktop\\DB사진\\프로필사진\\" + user.getUserIndex() + ".png");
					InputStream is = new FileInputStream(file);
					long length = file.length();

					byte[] bytes = new byte[(int) length];
					int offset = 0;
					int numRead = 0;
					while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
						offset += numRead;
					}
					user.setIImage(bytes);
					if (offset < bytes.length) {
						throw new IOException("Could not completely read file " + file.getName());
					}
					is.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				return user;
			}
			
		}
		return null;
	}	// index
	public byte[] getUserImageByIndex(int index) throws Exception { // 디비에서 받는거

		User user = new User();
		byte[] Iimage;
		GridFS gfsPhoto = new GridFS(db, "UserImage");

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("index", index);

		DBCursor cursorId = userCollection.find(idQuery);

		while (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {

				try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

					GridFSDBFile imageForOutput = gfsPhoto.findOne(Integer.toString(index));
					if (imageForOutput == null) {
						System.out.println("imageForOutput : null");
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

					Iimage = outputStream.toByteArray();
					return Iimage;

				} catch (IOException e) {
					throw new Exception("Cannot retrieve content of gridFsFile [" + index + "]", e);
				}

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

	public User getUserByID(String id) throws Exception {
		User user = new User();

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("ID", id);
		
		DBCursor cursorId = userCollection.find(idQuery);
		
		if (cursorId.hasNext()) {
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				int n = (int) check.get("index");
				user.setUserIndex(n);
				user.setUserId((String) check.get("ID"));
				user.setUserPw((String) check.get("PW"));
				ArrayList<Integer> arrL1 = new ArrayList<Integer>();
				ArrayList<Integer> arrL2 = new ArrayList<Integer>();
				
				arrL1 = (ArrayList<Integer>)(check.get("LikeList"));
				
				for(int i=0; i<arrL1.size();i++){
					arrL2.add(arrL1.get(i));
				}
		
			
				user.setUserLikeList(arrL2);
			
				
				ArrayList<Integer> arrM1 = new ArrayList<Integer>();
				ArrayList<Integer> arrM2 = new ArrayList<Integer>();
				arrM1 = (ArrayList<Integer>)(check.get("MyList"));
				
				for(int i=0;i<arrM1.size();i++){
					arrM2.add(arrM1.get(i));
				}
				user.setUserMyList(arrM2);
				user.setIImage(getUserImageByIndex(n)); 
				return user;
			}
		}
		return null;
	}

	//
	public void insertPosts(Posts posts) {

		BasicDBObject document = new BasicDBObject();
		document.put("postsID", postsIndex);
		this.insertLocation(posts.getLocationInfo());
		postsIndex++;
		document.put("songID", posts.getMusic().getMusicId());
		this.insertSong(posts.getMusic());
		document.put("Comment", posts.getComment());
		document.put("userID", posts.getUserID());
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

	public void insertSong(Music song) {

		BasicDBObject songQuery = new BasicDBObject();
		songQuery.put("songID", song.getMusicId());
		DBCursor cursorSong = songCollection.find(songQuery);
		if (cursorSong.hasNext()) {
			return;
		}
		
		BasicDBObject document = new BasicDBObject();
		document.put("songID", song.getMusicId());
		document.put("SongName", song.getMusicName());
		document.put("albumID", song.getAlbumId());
		document.put("AlbumName", song.getAlbumName());
		document.put("ArtistID", song.getArtistId());
		document.put("ArtistName", song.getArtistName());
		document.put("MenuID", song.getMenuId());
		
		songCollection.insert(document);

	}
	
	public void updateSong(Music song) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("songID", song.getMusicId());
		updateQuery.put("SongName", song.getMusicName());
		updateQuery.put("albumID", song.getAlbumId());
		updateQuery.put("AlbumName", song.getAlbumName());
		updateQuery.put("ArtistID",song.getArtistId());
		updateQuery.put("ArtistName",  song.getArtistName());
		updateQuery.put("MenuID", song.getMenuId());
		BasicDBObject searchQuery = new BasicDBObject().append("index", song.getMusicId());
		songCollection.update(searchQuery, updateQuery);

	}
	
	//
	public void updatePostsList(Posts posts) {

		BasicDBObject updateQuery = new BasicDBObject();

		updateQuery.put("postsID", posts.getPostsIndex());
		
		this.updateSong(posts.getMusic());
		updateQuery.put("Comment", posts.getComment());
		updateQuery.put("userID", posts.getUserID());
		updateQuery.put("Like", posts.getLike());
		updateQuery.put("CreateTime", posts.getCreateTime());

		BasicDBObject searchQuery = new BasicDBObject().append("index", posts.getPostsIndex());
		postsCollection.update(searchQuery, updateQuery);

	}

	public void deletePosts(int index) {
		DBObject document = new BasicDBObject();
		document.put("postsID", index);
		postsCollection.remove(document);
	}

	// index
	public Posts getPostsByIndex(int index) throws Exception {
		Posts posts = null;

		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("postsID", index);

		DBCursor cursorId = postsCollection.find(idQuery);

		if (cursorId.hasNext()) {
			posts = new Posts();
			DBObject check = null;
			check = cursorId.next();
			if (check != null) {
				posts.setPostsIndex((int) check.get("postsID"));
				posts.setIImage(this.getImageByIndex(index));
				/*
				try {
					File file = new File("C:\\Users\\안준영\\Desktop\\DB사진\\" + posts.getPostsIndex() + ".png");
					InputStream is = new FileInputStream(file);
					long length = file.length();

					byte[] bytes = new byte[(int) length];
					int offset = 0;
					int numRead = 0;
					while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
						offset += numRead;
					}
					posts.setIImage(bytes);
					if (offset < bytes.length) {
						throw new IOException("Could not completely read file " + file.getName());
					}
					is.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				Location location = new Location();
				BasicDBObject locQuery = new BasicDBObject();
				locQuery.put("index", index);
				DBCursor cursorLoc = locationCollection.find(locQuery);
				if (cursorLoc.hasNext()) {
					DBObject checkLoc = null;
					checkLoc = cursorLoc.next();
					if (checkLoc != null) {
						location.setBigLocation((String) checkLoc.get("BigLocation"));
						location.setMidLocation((String) checkLoc.get("MidLocation"));
						location.setSmallLocation((String) checkLoc.get("SmallLocation"));
						location.setContentID((int) checkLoc.get("ContentID"));
						location.setContentTypeID((int) checkLoc.get("ContentTypeID"));
						location.setTitle((String) checkLoc.get("Title"));

					}
				}
				posts.setLocationInfo(location);
				
				posts.setMusicID((int) check.get("songID"));
				
				Music song = new Music();
				BasicDBObject songQuery = new BasicDBObject();
				songQuery.put("songID", posts.getMusicID());
				DBCursor cursorSong = songCollection.find(songQuery);
				if (cursorSong.hasNext()) {
					DBObject checkSong = null;
					checkSong = cursorSong.next();
					if (checkSong != null) {
						song.setMusicId((int) checkSong.get("songID"));
						song.setMusicName((String) checkSong.get("SongName"));
						song.setAlbumId((int) checkSong.get("albumID"));
						song.setAlbumName((String) checkSong.get("AlbumName"));
						song.setArtistId((int) checkSong.get("ArtistID"));
						song.setArtistName((String) checkSong.get("ArtistName"));
						song.setMenuId((int) checkSong.get("MenuID"));
					}
				}
				
				posts.setMusic(song);
				
				posts.setComment((String) check.get("Comment"));
				posts.setUserID((int) check.get("userID"));
				posts.setLike((int) check.get("Like"));
				posts.setCreateTime((String) check.get("CreateTime"));
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

					Iimage = outputStream.toByteArray();
					return Iimage;

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

	public ArrayList<Posts> getPostsByLocation(Location location) throws Exception {

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

	public ArrayList<Posts> getPostsByOption(int option, String value) throws Exception {
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

			cquery.put("ContentID", Integer.parseInt(value));

			DBCursor ccursor = locationCollection.find(cquery);

			while (ccursor.hasNext() && i.size() < 10) {
				int index = (int) ccursor.next().get("index");
				i.add(index);
			}
		} else {
			return p;
		}

		for (int j = 0; j < i.size(); j++) {
			Posts posts = this.getPostsByIndex(i.get(j));
			p.add(posts);
		}
		return p;
	}
}