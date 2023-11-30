package com.github.stasmalykhin.botHH.bot;

import com.github.stasmalykhin.botHH.config.BotConfig;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stanislav Malykhin
 */
@Component
@Getter
@Log4j
public class Bot extends TelegramWebhookBot {
    private final UpdateReceiver updateReceiver;
    private final String botUsername;
    private final String botToken;

    public Bot(UpdateReceiver updateReceiver, BotConfig botConfig, SetWebhook setWebhook) throws TelegramApiException {
        this.updateReceiver = updateReceiver;
        this.botToken = botConfig.getBotToken();
        this.botUsername = botConfig.getBotUsername();
        this.setWebhook(setWebhook);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handleUpdate(update);

        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                try {
                    if (response instanceof SendMessage message) {
                        execute(message);
                    }
                } catch (TelegramApiException e) {
                    log.error(e);
                }
            });
        }
        return null;
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
