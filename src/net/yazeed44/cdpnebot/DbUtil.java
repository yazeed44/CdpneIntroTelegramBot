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
			st.bind(getUserIdIndex(st), intro.userId)
			.bind(getCityIndex(st), intro.city)
			.bind(getHobbiesIndex(st), intro.hobbies)
			.bind(getPreferedTraitsIndex(st), intro.preferedTraitsInRoommate)
			.bind(getUnpreferedTraitsIndex(st), intro.unpreferedTraitsInRoommate)
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
			
			System.out.println(st.getBindParameterCount());
			
			st.step();
			final Introduction intro = createIntro(st);
			st.dispose();
			return intro;
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static Introduction createIntro(SQLiteStatement st) throws SQLiteException {
		final Introduction intro = new Introduction(st.columnInt(getUserIdIndex(st)),st.columnString(getCityIndex(st)));
		intro.setHobbies(st.columnString(getHobbiesIndex(st)));
		intro.setPreferedTraitsInRoommate(st.columnString(getPreferedTraitsIndex(st)));
		final String unprefered = st.columnString(getUnpreferedTraitsIndex(st));
		intro.setUnpreferedTraitsInRoommate(unprefered);
		System.out.println(intro);
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
}
