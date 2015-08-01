package net.yazeed44.cdpnebot;

public final class CustomMessages {

	private CustomMessages(){
		
	}
	
	public final static String QUESTION_CITY = "من أي مدينة ؟";
	
	public final static String QUESTION_HOBBIES = "ماهي هواياتك ؟";
	
	public final static String QUESTION_PREFERED_TRAITS_IN_ROOMMATE = "الصفات المرغوبة في الروم ميت ؟";
	
	public final static String QUESTION_UNPREFERED_TRAITS_IN_ROOMMATE = "الصفات الغير محببة في الروم ميت ؟";
	
	public final static String MESSAGE_CONGRATS_ON_COMPILATION = "تم الانتهاء من التعريف بالنفس \n اذا كنت تريد أدخال تعريف جديد فاحذف التعريف القديم بأستخدام \n"
			+ Commands.COMMAND_DELETE_INTRO;

	public static final String MESSAGE_AFTER_DELETE_INTRO = "تم حذف التعريف بنجاح \n أستخدم أمر /start \n لإدخال تعريف جديد";

	public static final String MESSAGE_YOU_ALREADY_CREATED_AN_INTRO = "يوجد تعريف مرتبط بهذا الحساب , لحذف التعريف \n /deleteintro";
}
