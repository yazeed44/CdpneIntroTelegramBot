import java.util.List;

import org.telegram.BotConfig;
import org.telegram.BuildVars;
import org.telegram.SenderHelper;
import org.telegram.api.Update;
import org.telegram.updateshandlers.UpdatesCallback;
import org.telegram.updatesreceivers.UpdatesThread;
import org.telegram.updatesreceivers.Webhook;





public class CdpneIntroBot implements UpdatesCallback {
	
	private static final int webhookPort = 9990;
    private final Webhook webhook;
	
	public CdpneIntroBot(){
		if (BuildVars.useWebHook.booleanValue()) {
            webhook = new Webhook(this, webhookPort);
            SenderHelper.SendWebhook(webhook.getURL(), BotConfig.CDPNE_BOT_TOKEN);
        } else {
            webhook = null;
            SenderHelper.SendWebhook("", BotConfig.CDPNE_BOT_TOKEN);
            new UpdatesThread(BotConfig.CDPNE_BOT_TOKEN, this);
        }
	}


	public void onUpdateReceived(Update update) {
            
		
		final String text = update.getMessage().getText();     
		System.out.println(text);
	}


	public void onUpdatesReceived(List<Update> updates) {
		
		
		for(final Update update : updates){
			onUpdateReceived(update);
		}
		
		
	}

	

}
