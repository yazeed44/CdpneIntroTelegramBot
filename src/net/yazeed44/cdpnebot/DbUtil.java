package net.yazeed44.cdpnebot;

import java.io.File;
import java.util.ArrayList;

import com.almworks.sqlite4java.SQLParts;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public final class DbUtil {
	
	
	public final static String COLUMN_USER_ID = "userId";
	
	
	
	public final static String COLUMN_CITY = "city";
	
	
	
	public final static String COLUMN_HOBBIES = "hobbies";
	
	
	
	public final static String COLUMN_PREFERED_TRAITS_IN_ROOMMATE = "preferedTraitsInRoommate";
	
	
	
	public final static String COLUMN_UNPREFERED_TRAITS_IN_ROOMMATE = "unpreferedTraitsInRoommate";
	
	public final static String COLUMN_USERNAME = "username";
	
	
	
	
	
	public final static String TABLE_INTROS = "intros";
	
	public final static SQLiteConnection DB = new SQLiteConnection(new File("db/intros"));

	private DbUtil(){
		
		
	}
	
	public static void insertIntro(final Introduction intro){
		openDb();
		
		final SQLParts sqlParts = new SQLParts();
		
		sqlParts.append("INSERT INTO ")
		.append(TABLE_INTROS)
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
		.append(") ")
		.append("VALUES(")
		.appendParams(6)
		.append(");");
		
		 
		 try {
			final SQLiteStatement st = DB.prepare(sqlParts);
			bindIntroToStatement(st,intro);
			stepAndDispose(st);
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


	private static void bindIntroToStatement(SQLiteStatement st,Introduction intro) {
				
		try {
			st.bind(getUserIdIndex(st)+1, intro.userId)
			.bind(getCityIndex(st)+1, intro.city)
			.bind(getHobbiesIndex(st)+1, intro.hobbies)
			.bind(getPreferedTraitsIndex(st)+1, intro.preferedTraitsInRoommate)
			.bind(getUnpreferedTraitsIndex(st)+1, intro.unpreferedTraitsInRoommate)
			.bind(getUsernameIndex()+1, intro.username)
			;
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void stepAndDispose(final SQLiteStatement st) throws SQLiteException{
        
        try {
            st.step();
        }
        finally {
            st.dispose();
        }
    }
	
	private static void openDb(){
		if (!DB.isOpen()){
			try {
				DB.open(true);
			} catch (SQLiteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteIntro(final int id) {
		openDb();
		
		try {
			final SQLiteStatement st = DB.prepare("DELETE FROM " + TABLE_INTROS + " WHERE " + COLUMN_USER_ID +" = " + id);
			stepAndDispose(st);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Introduction getIntro(final int userId){
		openDb();
		
		try {
			final SQLiteStatement st = DB.prepare("SELECT * FROM " + TABLE_INTROS + " WHERE " + COLUMN_USER_ID + " = " + userId );
			
			st.step();
			final Introduction intro = createIntro(st);
			st.dispose();
			return intro;
		} catch (SQLiteException e) {
			System.out.println("There's no row ith userId  " + userId);
		}
		
		return null;
	}
	
	public static Introduction getIntro(String username){
		
		if (username.startsWith("@")){
			username = username.replace("@", "");
		}
		
		openDb();
		
		try {
			final SQLiteStatement st = DB.prepare("SELECT * FROM " + TABLE_INTROS + " WHERE " + COLUMN_USERNAME + " = " + "'" + username + "';" );
			
			st.step();
			final Introduction intro = createIntro(st);
			st.dispose();
			return intro;
		}
		
		catch(final SQLiteException e){
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
			final SQLiteStatement st = DB.prepare("SELECT * FROM " + TABLE_INTROS);
			
			while(st.step()){
				intros.add(createIntro(st));
			}
			
			st.dispose();
			
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		
		return intros;
	}

	private static Introduction createIntro(SQLiteStatement st) throws SQLiteException {
		final Introduction intro = new Introduction(st.columnInt(getUserIdIndex(st)),st.columnString(getCityIndex(st)));
		intro.setUsername(st.columnString(getUsernameIndex()));
		intro.setHobbies(st.columnString(getHobbiesIndex(st)));
		intro.setPreferedTraitsInRoommate(st.columnString(getPreferedTraitsIndex(st)));
		intro.setUnpreferedTraitsInRoommate(st.columnString(getUnpreferedTraitsIndex(st)));
		return intro;
	}
	
	
	private static int getUserIdIndex(final SQLiteStatement st) throws SQLiteException{
		return st.getBindParameterIndex(COLUMN_USER_ID);
	}
	
	private static int getCityIndex(final SQLiteStatement st) throws SQLiteException{
		return 1;
	}
	
	private static int getHobbiesIndex(final SQLiteStatement st) throws SQLiteException{
		return 2;
	}
	
	private static int getPreferedTraitsIndex(final SQLiteStatement st) throws SQLiteException{
		return 3;
	}
	
	private static int getUnpreferedTraitsIndex(final SQLiteStatement st) throws SQLiteException{
		return 4;
	}
	
	private static int getUsernameIndex() throws SQLiteException{
		return 5;
	}
}
