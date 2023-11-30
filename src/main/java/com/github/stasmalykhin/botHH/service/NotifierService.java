package com.github.stasmalykhin.botHH.service;

import com.github.stasmalykhin.botHH.bot.Bot;
import com.github.stasmalykhin.botHH.entity.AppUser;
import com.github.stasmalykhin.botHH.model.Vacancy;
import com.github.stasmalykhin.botHH.util.DateConversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Malykhin
 */
@Service
@Log4j
@RequiredArgsConstructor
public class NotifierService extends APIConnectionService {
    private final Bot bot;
    private final UserService userService;
    private final HandlerAPIService handlerAPIService;
    private final DateConversion dateConversion;

    @Scheduled(fixedRateString = "${notifier.period}")
    private void informAboutNewVacancies() {
        log.info("Запущен поиск новых вакансий");
        List<AppUser> users = userService.findAllUser();
        for (AppUser user : users) {
            List<Vacancy> newVacancies
                    = handlerAPIService.getListWithNewVacancies(user.getDateOfPublicationOfLastVacancy());
            Collections.reverse(newVacancies);
            boolean newVacanciesExist = !newVacancies.isEmpty();
            if (newVacanciesExist) {
                for (Vacancy newVacancy : newVacancies) {
                    userService.updateDateOfPublicationAtUser(user, newVacancy.getPublishedAt());
                    createMessageWithVacancy(user, newVacancy);
                }
            }
            log.info("Для пользователя " + user.getUsername() +
                    " найдено: " + newVacancies.size() + "шт.");
        }
        log.info("Поиск вакансий закончен");
    }

    private void createMessageWithVacancy(AppUser user, Vacancy newVacancy) {
        String publishedAt =
                dateConversion.fromDateToString("yyyy-MM-dd HH:mm", newVacancy.getPublishedAt());
        StringBuilder message = new StringBuilder();
        message.append(newVacancy.getNameVacancy()).append("\n")
                .append(newVacancy.getNameEmployer()).append("\n")
                .append(newVacancy.getNameArea()).append("\n")
                .append(publishedAt).append("\n")
                .append(newVacancy.getUrl()).append("\n");
        bot.sendMessage(SendMessage.builder()
                .text(message.toString())
                .chatId(user.getTelegramUserId().toString())
                .build());
    }


}
