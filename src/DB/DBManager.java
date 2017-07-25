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

import ProblemDomain.Location;
import ProblemDomain.Posts;
import ProblemDomain.User;

public class DBManager {

	public int userIndex;
	public int postsIndex;
	public int recentIndex; //어플 켰을시 게시물 갯수
	public int seeIndex; //자신이 본 인덱스
	String MongoDB_IP = "127.0.0.1";
	int MongoDB_PORT = 27017;
	String DB_NAME = "db";

	MongoClient mongoClient = new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
	DB db = mongoClient.getDB(DB_NAME);
	DBCollection userCollection = db.getCollection("User");
	DBCollection postsCollection = db.getCollection("Posts");

	public DBManager() {

		DBCursor userCursor = userCollection.find();
		DBCursor postsCursor = postsCollection.find();
		
		// count all users in DB
		userIndex = (int) userCollection.count();

		// count all posts in DB
		postsIndex = (int) postsCollection.count();

		recentIndex = (int) postsCollection.count();
		seeIndex = (int)postsCollection.count();
	}
	
	public ArrayList<Posts> refreshTimeLine() throws Exception{
		ArrayList<Posts> P = new ArrayList<Posts>();
		int dbIndex = (int) postsCollection.count();
		
		if(recentIndex < dbIndex){
			if(dbIndex-recentIndex <= 10){
				for(int i = 0;i<dbIndex-recentIndex;i++){
					recentIndex++;
					Posts posts = this.getPostsByIndex(recentIndex);
					posts.setIImage(this.getImageByIndex(recentIndex));					
					P.add(posts);
				}
			}
			else{
				for(int i =0;i<10;i++){
					recentIndex++;
					Posts posts = this.getPostsByIndex(recentIndex);
					posts.setIImage(this.getImageByIndex(recentIndex));					
					P.add(this.getPostsByIndex(recentIndex));
				}
			}
		}
		
		else{
			return null;
		}
		
		return P;
	}
	
	public ArrayList<Posts> getMorePosts() throws Exception{
		ArrayList<Posts> p = new ArrayList<Posts>();
		
		if(seeIndex < 10){
			for(int i=0;i<seeIndex;i++){
				seeIndex--;
				Posts posts = this.getPostsByIndex(seeIndex);
				posts.setIImage(this.getImageByIndex(seeIndex));
				p.add(posts);
			}
		}
		else{
			for(int i =0;i<10;i++){
				seeIndex--;
				Posts posts = this.getPostsByIndex(seeIndex);
				posts.setIImage(this.getImageByIndex(seeIndex));
				p.add(posts);
			}
		}
		
		return p;
	}
	
	
	public void insertUser(User user) {

		BasicDBObject document = new BasicDBObject();
		// index 파트 만들기, 넣기
		document.put("index", userIndex);
		userIndex++;
		// ID 파트 만들기, 넣기
		document.put("ID", user.getId());
		// PW 파트 만들기, 넣기
		document.put("PW", user.getPw());
		// LikeList 파트 만들기, 넣기
		document.put("LikeList", user.getLikeList());
		// MyList 파트 만들기, 넣기
		document.put("MyList", user.getMyList());

		// DB에 저장
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

		// 새로 받은 데이터로 업데이트
		userCollection.update(searchQuery, updateQuery);

	}

	// 비밀번호를 이용해 id리턴받기, id중복확인, 로그인에서 필요
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

	// 아이디를 이용해 유저 객체 받아오기.
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

	// 게시물 쓰면 부르는 메소드
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
	//새로받은 데이터로 게시물 업데이트
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

		BasicDBObject searchQuery = new BasicDBObject().append("index", posts.getPostsIndex());
		postsCollection.update(searchQuery, updateQuery);

	}
	
	// index를 이용해 게시물 리턴
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
				posts.setLocationInfo((Location) check.get("Location"));
				posts.setUrl((String) check.get("URL"));
				posts.setArtist((String) check.get("Artist"));
				posts.setSong((String) check.get("Song"));
				posts.setComment((String) check.get("Comment"));
				posts.setPostsID((int) check.get("PostsID"));
				posts.setLike((int) check.get("Like"));
				posts.setCreateTime((long) check.get("CreateTime"));
				return posts;
			}
		}
		return null;
	}
	
	
	
	//index를 이용해 게시물 이미지 불러오기
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

				try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

					GridFSDBFile imageForOutput = gfsPhoto.findOne(Integer.toString(index));
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
					bimage = ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));

					newImage = (Image) bimage;

					return newImage;

				} catch (IOException e) {
					throw new Exception("Cannot retrieve content of gridFsFile [" + index + "]", e);
				}

			}
		}

		return null;
	}

}