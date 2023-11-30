package com.github.stasmalykhin.botHH.bot;

import com.github.stasmalykhin.botHH.entity.AppUser;
import com.github.stasmalykhin.botHH.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateReceiver {
    private final UserService userService;

    public List<PartialBotApiMethod<? extends Serializable>> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final Message message = update.getMessage();
            final User telegramUser = message.getFrom();
            boolean userIsNew = userService.checkIfUserIsNew(telegramUser.getId());
            if (userIsNew) {
                userService.saveUser(AppUser.builder()
                        .telegramUserId(telegramUser.getId())
                        .username(telegramUser.getUserName())
                        .firstName(telegramUser.getFirstName())
                        .lastName(telegramUser.getLastName())
                        .dateOfPublicationOfLastVacancy(new Date())
                        .build());
                return List.of(SendMessage.builder()
                        .text("При появлении на сайте HH новых вакансий по Java в Москве и Санкт-Петербурге " +
                                "бот пришлет их в этот чат.")
                        .chatId(telegramUser.getId().toString())
                        .build());
            }
            return List.of(SendMessage.builder()
                    .text("Этот бот только оповещает о новых вакансиях.")
                    .chatId(telegramUser.getId().toString())
                    .build());
        }
        return Collections.emptyList();
    }
}
