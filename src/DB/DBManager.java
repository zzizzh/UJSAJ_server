package DB;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import ProblemDomain.*;

public class DBManager {

	public int userIndex;
	int postsIndex;
	String MongoDB_IP = "222.104.203.106";
	int MongoDB_PORT = 27017;
	String DB_NAME = "db";
	
	
	MongoClient mongoClient = new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
	DB db = mongoClient.getDB(DB_NAME);
	DBCollection userCollection = db.getCollection("User");
	DBCollection postsCollection = db.getCollection("Posts");
	
	public DBManager(){
		int userIndex=0;
		int postsIndex =0;
		
		DBCursor userCursor = userCollection.find();
		DBCursor postsCursor = postsCollection.find();
		
		while(userCursor.hasNext()){
			userCursor.next();
			userIndex++;
		}
		
		while(postsCursor.hasNext()){
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
		document.put("LikeList", null);
		document.put("MyList", null);
		
		// Insert Data01
		userCollection.insert(document);

	}
	
	public void updateList(User user){
		
		BasicDBObject updateQuery = new BasicDBObject();
		
		updateQuery.put("index",user.getUserIndex());
		updateQuery.put("ID",user.getId());
		updateQuery.put("PW",user.getPw());
		updateQuery.put("LikeList",user.getLikeList());
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

	public User getUserByID(String id){
	
		User user = new User();
		
		BasicDBObject idQuery = new BasicDBObject();
		idQuery.put("ID", id);
		
		DBCursor cursorId = userCollection.find(idQuery);
		
		while(cursorId.hasNext()){
			DBObject check = null;
			check = cursorId.next();
			if(check != null){
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
	
	public void insertPosts(Posts posts){
		
		
		
	}
}