package com.github.stasmalykhin.botHH.controller;

import com.github.stasmalykhin.botHH.bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Stanislav Malykhin
 */
@RestController
@RequiredArgsConstructor
public class WebHookController {
    private final Bot bot;

    @PostMapping("/callback")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }
}
