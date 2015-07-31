package net.yazeed44.cdpnebot;
import java.util.List;

import org.telegram.BotConfig;
import org.telegram.BuildVars;
import org.telegram.SenderHelper;
import org.telegram.api.ForceReply;
import org.telegram.api.Message;
import org.telegram.api.Update;
import org.telegram.methods.SendMessage;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.UpdatesThread;
import org.telegram.updatesreceivers.Webhook;





public class CdpneIntroBot implements UpdatesCallback {
	
	private static final int webhookPort = 9990;
    private final Webhook webhook;
    public static final String TOKEN = BotConfig.CDPNE_BOT_TOKEN;
    
    public static final SendMessage DEFAULT_MESSAGE = new SendMessage();
    public static final ForceReply DEFAULT_FORCE_REPLY = new ForceReply();
	
	public CdpneIntroBot(){
		if (BuildVars.useWebHook.booleanValue()) {
            webhook = new Webhook(this, webhookPort);
            SenderHelper.SendWebhook(webhook.getURL(), TOKEN);
        } else {
            webhook = null;
            SenderHelper.SendWebhook("", TOKEN);
            new UpdatesThread(TOKEN, this);
        }
		
		setup();
	}
	
	private void setup(){
		DEFAULT_FORCE_REPLY.setForceReply(true);
		DEFAULT_FORCE_REPLY.setSelective(true);
		
		DEFAULT_MESSAGE.setReplayMarkup(DEFAULT_FORCE_REPLY);
	}


	public void onUpdateReceived(Update update) {
            
		
		if (update.getMessage() != null && update.getMessage().hasText()){
			final Message message = update.getMessage();
			System.out.println(message.getText());
			
			
			if (hasCommandedToStart(message)){
				askAboutCity(message);
				
			}
			
			else if (!message.hasReplayMessage()){
				return;
			}
			
			else if (hasAnswerdAboutCity(message)){
				askAboutHobbies(message);
			}
			
			else if (hasAnsweredAboutHobbies(message)){
				askAboutPreferedTraitsInRoommate(message);
			}
			
			else if (hasAnsweredAboutPreferedTraitsInRoommate(message)){
				askAboutUnpreferedTraitsInRoomate(message);
			}
			
			else if (hasAnsweredAboutUnpreferedTraitsInRoommate(message)){
				congratulateOnCompletation(message);
			}
			
			
			
		}
		
		
		
	}
	
	private void congratulateOnCompletation(Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.MESSAGE_CONGRATS_ON_COMPILATION);
		replyAndSend(message);
		
	}

	private boolean hasAnsweredAboutUnpreferedTraitsInRoommate(final Message message) {
		return CustomMessages.QUESTION_UNPREFERED_TRAITS_IN_ROOMATE.equals(message.getReplyToMessage().getText());
	}

	private void askAboutUnpreferedTraitsInRoomate(final Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_UNPREFERED_TRAITS_IN_ROOMATE);
		replyAndSend(message);
	}

	private boolean hasAnsweredAboutPreferedTraitsInRoommate(Message message) {
		
		return CustomMessages.QUESTION_PREFERED_TRAITS_IN_ROOMMATE.equals(message.getReplyToMessage().getText());
	}

	private void askAboutPreferedTraitsInRoommate(Message message) {
		DEFAULT_MESSAGE.setText(CustomMessages.QUESTION_PREFERED_TRAITS_IN_ROOMMATE);
		replyAndSend(message);
		
	}

	private boolean hasAnsweredAboutHobbies(Message message) {
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


	public void onUpdatesReceived(List<Update> updates) {
		
		
		for(final Update update : updates){
			onUpdateReceived(update);
		}
		
		
	}

	

}
