package net.yazeed44.cdpnebot;
import java.util.ArrayList;
import java.util.HashMap;

import org.telegram.BotConfig;
import org.telegram.SenderHelper;
import org.telegram.api.ForceReplyKeyboard;
import org.telegram.api.Message;
import org.telegram.api.Update;
import org.telegram.methods.SendMessage;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.UpdatesThread;





public class CdpneIntroBot implements UpdatesCallback {
	
    public static final String TOKEN = BotConfig.TOKEN_CDPNE;
    
    public static final SendMessage DEFAULT_MESSAGE = new SendMessage();
    public static final ForceReplyKeyboard DEFAULT_FORCE_REPLY = new ForceReplyKeyboard();
	
    public static final HashMap<Integer,Introduction> INTROS = new HashMap<>();
	
    public CdpneIntroBot(){
    	
    	SenderHelper.SendWebhook("", TOKEN);
    	new UpdatesThread(TOKEN, this);
	
        setup();
	}
	
	
	
	@Override
	public void onUpdateReceived(Update update) {
            
		if (update.getMessage().getNewChatParticipant() != null){
			sendWelcomeMessage(update.getMessage());
		}
		
		
		if (update.getMessage() != null && update.getMessage().hasText()){
			final Message message = update.getMessage();
			
			
			
			 if (hasCommandedToStart(message)){
				
				handleStartCommand(message);
				
			}
			
			else if (hasCommandedToDeleteIntro(message)){
				deleteIntro(message);
			}
			
			else if (hasCommandedToBrowseAllIntros(message)){
				queryAllIntrosAndShowThem(message);
			}
			
			else if (hasCommandedToSearchSpectifcIntro(message)){
				queryUserAndShowIntro(message);
			}
			
			
			
			else if (message.hasReplayMessage()){
				handleAnswers(message);
				
			}
			
		}
		
		
		
	}
	
	private void sendWelcomeMessage(final Message message) {
		DEFAULT_MESSAGE.setText("أهلا بك يا  " + message.getNewChatParticipant().getFirstName() + " " + CustomMessages.MESSAGE_WELCOME_NEW_MEMBER);
		replyWithoutForce(message);
	}

	private void setup(){
		DEFAULT_FORCE_REPLY.setForceReply(true);
		DEFAULT_FORCE_REPLY.setSelective(true);
		
		DEFAULT_MESSAGE.setReplayMarkup(DEFAULT_FORCE_REPLY);
	}


	
	
	private void queryAllIntrosAndShowThem(final Message message) {
		final ArrayList<Introduction> intros = DbUtil.getIntros();
		
		if (intros.isEmpty()){
			DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_THERE_IS_NO_INTROS);
			replyWithoutForce(message);
		}
		
		else {
			final StringBuilder introsTextBuilder = new StringBuilder();
			
			for(final Introduction intro : intros){
				
				introsTextBuilder.append(intro.generateIntroText())
				.append("\n \n")
				;
				
			}
			
			DEFAULT_MESSAGE.setText(introsTextBuilder.toString());
			replyWithoutForce(message);
			intros.clear();
		}
		
	}

	private boolean hasCommandedToBrowseAllIntros(final Message message) {
		
		return message.getText().startsWith(Commands.COMMAND_BROWSE_ALL_INTRODUCTIONS);
	}

	private void queryUserAndShowIntro(final Message message) {
		final String command = message.getText();
		final String username;
		
		 
		 if (command.contains(" ")){
			 username = command.split(" ")[1];
			 
			 if (!DbUtil.isUserInsertedInDb(username)){
				 DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_USER_HAS_NO_INTRO);
				 replyWithoutForce(message);
				 return;
			 }
			 
		 }
		 
		 else {
			 //By default the username is the caller username
			 if (DbUtil.isUserInsertedInDb(message.getFrom().getId())){
				 username = message.getFrom().getUserName();
				 
			 }
			 else {
				 handleStartCommand(message);
				 return;
			 }
		 }
		 
		 final Introduction intro = DbUtil.getIntro(username);
		 
		 DEFAULT_MESSAGE.setText(intro.generateIntroText());
		 replyWithoutForce(message);
		 
		 
	}

	private boolean hasCommandedToSearchSpectifcIntro(final Message message) {
		return message.getText().startsWith(Commands.COMMAND_SEARCH_FOR_SPECTIFC_INTRODUCTION);
	}

	private void handleStartCommand(final Message message){
		if (!DbUtil.isUserInsertedInDb(message.getFrom().getId())){
			//He isn't registerd in db
			
			if (message.getFrom().getUserName().isEmpty()){
				//Has no username
				tellCallerToCreateUsername(message);
				return;
			}
			
			else {
				askAboutCity(message);
			}
			
		}
		
		else {
			explainHowToDeleteIntro(message);
		}
	}
	
	
	private void tellCallerToCreateUsername(final Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_CALLER_HAS_NO_USERNAME);
		replyWithoutForce(message);
		
	}

	private void explainHowToDeleteIntro(final Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_YOU_ALREADY_CREATED_AN_INTRO);
		replyWithoutForce(message);
		
	}

	private void deleteIntro(Message message) {
		DbUtil.deleteIntro(message.getFrom().getId());
		DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_AFTER_DELETE_INTRO);
		replyWithoutForce(message);
		
	}

	private boolean hasCommandedToDeleteIntro(Message message) {
		return message.getText().startsWith(Commands.COMMAND_DELETE_INTRO);
	}

	private void handleAnswers(final Message message){
	    if (hasAnswerdAboutCity(message)){
	    	final Introduction intro = new Introduction(message.getFrom().getId(),message.getText());
	    	intro.setUsername(message.getFrom().getUserName());
	    	INTROS.put(message.getFrom().getId(), intro);
			askAboutHobbies(message);
		}
		
		else if (hasAnsweredAboutHobbies(message)){
			INTROS.get(message.getFrom().getId()).setHobbies(message.getText());
			askAboutPreferedTraitsInRoommate(message);
		}
		
		else if (hasAnsweredAboutPreferedTraitsInRoommate(message)){
			INTROS.get(message.getFrom().getId()).setPreferedTraitsInRoommate(message.getText());
			askAboutUnpreferedTraitsInRoomate(message);
		}
		
		else if (hasAnsweredAboutUnpreferedTraitsInRoommate(message)){
			final Introduction intro = INTROS.get(message.getFrom().getId());
			if (intro == null){
				return;
			}
			intro.setUnpreferedTraitsInRoommate(message.getText());
			DbUtil.insertIntro(intro);
			INTROS.remove(message.getFrom().getId());
			congratulateOnCompletation(message);
		}

	}
	
	private void congratulateOnCompletation(Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_CONGRATS_ON_COMPILATION);
		DEFAULT_MESSAGE.setReplayMarkup(null);
		replyAndSend(message);
		DEFAULT_MESSAGE.setReplayMarkup(DEFAULT_FORCE_REPLY);
		
	}

	private boolean hasAnsweredAboutUnpreferedTraitsInRoommate(final Message message) {
		return CustomMessages.QUESTION_UNPREFERED_TRAITS_IN_ROOMMATE.equals(message.getReplyToMessage().getText());
	}

	private void askAboutUnpreferedTraitsInRoomate(final Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_UNPREFERED_TRAITS_IN_ROOMMATE);
		replyAndSend(message);
	}

	private boolean hasAnsweredAboutPreferedTraitsInRoommate(final Message message) {
		
		return CustomMessages.QUESTION_PREFERED_TRAITS_IN_ROOMMATE.equals(message.getReplyToMessage().getText());
	}

	private void askAboutPreferedTraitsInRoommate(final Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_PREFERED_TRAITS_IN_ROOMMATE);
		replyAndSend(message);
		
	}

	private boolean hasAnsweredAboutHobbies(final Message message) {
		return CustomMessages.QUESTION_HOBBIES.equals(message.getReplyToMessage().getText());
	}

	private void askAboutHobbies(Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_HOBBIES);
		replyAndSend(message);
		
	}


	private boolean hasAnswerdAboutCity(Message message) {
		
		return CustomMessages.QUESTION_CITY.equals(message.getReplyToMessage().getText());
	}


	private boolean hasCommandedToStart(final Message message){
		return message.getText().startsWith(Commands.COMMAND_START);
	}
	
	private void askAboutCity(final Message message){
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_CITY);
		replyAndSend(message);
	}
	
	private void replyAndSend(final Message message){
		DEFAULT_MESSAGE.setChatId(message.getChatId());
		DEFAULT_MESSAGE.setReplayToMessageId(message.getMessageId());
		SenderHelper.SendMessage(DEFAULT_MESSAGE, TOKEN);
	}
	
	private void replyWithoutForce(final Message mesasge){
		DEFAULT_MESSAGE.setReplayMarkup(null);
		replyAndSend(mesasge);
		DEFAULT_MESSAGE.setReplayMarkup(DEFAULT_FORCE_REPLY);
	}


	

	

}
