package net.yazeed44.cdpnebot;

import java.io.File;

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
	
	public final static String TABLE_INTROS = "intros";
	
	public final static SQLiteConnection DB = new SQLiteConnection(new File("intros"));

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
		.append(") ")
		.append("VALUES(")
		.appendParams(5)
		.append(");");
		
		 System.out.println("Statement is  " + sqlParts.toString());
		 
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
			st.bind(1, intro.userId)
			.bind(2, intro.city)
			.bind(3, intro.hobbies)
			.bind(4, intro.preferedTraitsInRoommate)
			.bind(5, intro.unpreferedTraitsInRoommate)
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
	
}
