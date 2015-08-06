package net.yazeed44.cdpnebot;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.telegram.database.ConectionDB;

public final class DbUtil {
	
	
	public final static String COLUMN_USER_ID = "userId";
	
	
	
	public final static String COLUMN_CITY = "city";
	
	
	
	public final static String COLUMN_HOBBIES = "hobbies";
	
	
	
	public final static String COLUMN_PREFERED_TRAITS_IN_ROOMMATE = "preferedTraitsInRoommate";
	
	
	
	public final static String COLUMN_UNPREFERED_TRAITS_IN_ROOMMATE = "unpreferedTraitsInRoommate";
	
	public final static String COLUMN_USERNAME = "username";
	
	
	
	
	
	public final static String TABLE_INTROS = "intros";
	
	public static final ConectionDB DB = new ConectionDB();
	
	

	private DbUtil(){
		
		
	}
	
	
	private static void openDb(){
		
	}
	
	public static boolean insertIntro(final Introduction intro){
		openDb();
		int updatedRows = 0;
		
		try {
			final PreparedStatement insertStatement = DB.getPreparedStatement(createSqlInsert());
			
			bindIntroToStatement(insertStatement,intro);
		
			updatedRows = insertStatement.executeUpdate();
		}
		
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		 
		 
		 return updatedRows > 0;
	}
	
	private static String createSqlInsert(){
		final StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
		
		return insertBuilder.append(TABLE_INTROS)
		.append("(")
		.append(COLUMN_USER_ID)
		.append(",")
		.append(COLUMN_CITY)
		.append(",")
		.append(COLUMN_HOBBIES)
		.append(",")
		.append(COLUMN_PREFERED_TRAITS_IN_ROOMMATE)
		.append(",")
		.append(COLUMN_UNPREFERED_TRAITS_IN_ROOMMATE)
		.append(",")
		.append(COLUMN_USERNAME)
		.append(") VALUES(?,?,?,?,?,?);")
		.toString();
		
	}
	
	


	private static void bindIntroToStatement(final PreparedStatement st,final Introduction intro) throws SQLException {
		
		st.setInt(getUserIdIndex()+1, intro.getUserId());
		st.setString(getCityIndex()+1, intro.getCity());
		st.setString(getHobbiesIndex()+1, intro.getHobbies());
		st.setString(getPreferedTraitsIndex()+1, intro.getPreferedTraitsInRoommate());
		st.setString(getUnpreferedTraitsIndex()+1, intro.getUnpreferedTraitsInRoommate());
		st.setString(getUsernameIndex()+1, intro.getUsername());
		
			
			
		
	}

	
	
	

	public static boolean deleteIntro(final int id) {
		openDb();
		int updatedRows = 0;
		
		
		try {
			final PreparedStatement deleteStatement = DB.getPreparedStatement("DELETE FROM " + TABLE_INTROS + " WHERE " + COLUMN_USER_ID + " = " + id);
			updatedRows = deleteStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updatedRows > 0;
	}
	
	public static Introduction getIntro(final int userId){
		openDb();
		
		
		try {
			final PreparedStatement queryStatement = DB.getPreparedStatement("SELECT * FROM " + TABLE_INTROS + " WHERE " + COLUMN_USER_ID + " = ?;");
			queryStatement.setInt(1, userId);
			System.out.println("Query  " + queryStatement.toString());
			
			final ResultSet queryResult = queryStatement.executeQuery();
			queryResult.next();
			
			return createIntro(queryResult);
			
		} catch (SQLException e) {
			System.out.println("There's no row ith userId  " + userId);
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Introduction getIntro(String username){
		
		if (username.startsWith("@")){
			username = username.replace("@", "");
		}
		
		openDb();
		
		try {
			final PreparedStatement queryStatement = DB.getPreparedStatement("SELECT * FROM " + TABLE_INTROS + " WHERE " + COLUMN_USERNAME +" = " + "'" + username + "';");
			final ResultSet queryResult = queryStatement.executeQuery();
			queryResult.next();
			

			return createIntro(queryResult);
		}
		
		catch(final SQLException e){
			System.out.println("There's no username with  " + username);
		}
		
		return null;
		
		
	}
	

	public static boolean isUserInsertedInDb(final int userId){
		return DbUtil.getIntro(userId) != null;
	}
	
	public static boolean isUserInsertedInDb(final String username){
		return DbUtil.getIntro(username) != null;
	}
	
	public static ArrayList<Introduction> getIntros(){
		openDb();
		final ArrayList<Introduction> intros = new ArrayList<>();
		try {
			final PreparedStatement queryStatement = DB.getPreparedStatement("SELECT * FROM " + TABLE_INTROS);
			final ResultSet queryResult = queryStatement.executeQuery();
			
			while(queryResult.next()){
				intros.add(createIntro(queryResult));
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return intros;
	}

	private static Introduction createIntro(final ResultSet queryResult) throws SQLException {
		final Introduction intro = new Introduction(queryResult.getInt(COLUMN_USER_ID),queryResult.getString(COLUMN_CITY));
		intro.setUsername(queryResult.getString(COLUMN_USERNAME));
		intro.setHobbies(queryResult.getString(COLUMN_HOBBIES));
		intro.setPreferedTraitsInRoommate(queryResult.getString(COLUMN_PREFERED_TRAITS_IN_ROOMMATE));
		intro.setUnpreferedTraitsInRoommate(queryResult.getString(COLUMN_UNPREFERED_TRAITS_IN_ROOMMATE));
		System.out.println(intro.toString());
		return intro;
	}
	
	
	private static int getUserIdIndex(){
		return 0;
	}
	
	private static int getCityIndex(){
		return 1;
	}
	
	private static int getHobbiesIndex(){
		return 2;
	}
	
	private static int getPreferedTraitsIndex(){
		return 3;
	}
	
	private static int getUnpreferedTraitsIndex(){
		return 4;
	}
	
	private static int getUsernameIndex(){
		return 5;
	}
}
