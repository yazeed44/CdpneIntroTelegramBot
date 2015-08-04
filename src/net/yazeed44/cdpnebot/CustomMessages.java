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

	public static final String MESSAGE_THERE_IS_NO_INTROS = "لايوجد أي تعاريف مسجلة في قاعدة البيانات , لادخال تعريف جديد استخدم \n" + Commands.COMMAND_START;

	public static final String MESSAGE_USER_HAS_NO_INTRO = "هذا المستخدم ليس لديه تعريف";

	public static final String MESSAGE_CALLER_HAS_NO_USERNAME = "حسابك لايحتوي على اسم مستخدم " + " Username" ;

	public static final String MESSAGE_WELCOME_NEW_MEMBER = " . لإدخال تعريف بالنفس أستخدم أمر  " + "\n" + Commands.COMMAND_START + "\n"+ "سوف يتم ارسال فيديو لتوضيح طريقة ادخال تعريف";
}
