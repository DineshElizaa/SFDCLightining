package com.infy.autoqa.karate.utility;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDBRepository<T> {

	public DBCollection _collection;

	public MongoDBRepository(String collectionName) {

		MongoClient objMongoClient = new MongoClient("localhost", 27017);
		@SuppressWarnings("deprecation")
		DB objDB = objMongoClient.getDB("CCPTestData");
		_collection = objDB.getCollection(collectionName);
	}

}
