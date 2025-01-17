/*
Файл сервиса для получения рекомендаций пользователя в Телеграм-боте
Powered by ©AYE.team
 */
package pro.sky.recommendations.tgBot.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.RecommendationData;
import pro.sky.recommendations.dto.UserRecommendation;

import pro.sky.recommendations.service.UserService;
import pro.sky.recommendations.service.UserRecommendationService;
import pro.sky.recommendations.tgBot.exceptions.BadMessageException;

import java.util.List;

import java.util.UUID;

@Service
public class RecommendationBotService {

    private final UserService userService;
    private final UserRecommendationService userRecommendationService;
    ;
    private final CommandManager commandManager;
    private final TelegramService telegramService;

    @Autowired
    public RecommendationBotService(UserService userService, UserRecommendationService userRecommendationService, CommandManager commandManager, TelegramService telegramService) {
        this.userService = userService;
        this.userRecommendationService = userRecommendationService;
        this.commandManager = commandManager;
        this.telegramService = telegramService;
    }

    /**
     * Метод для использования значений из массива с Именем и Фамилией
     *
     * @param message сообщение полученное от пользователя
     * @return строка с ответом пользователю
     */

    public String processMessage(String message, Long chatId) {
        // Если команда начинается с /recommend, обрабатываем по-особенному
        if (message.startsWith("/recommend")) {
            return responseToRecommendCommand(message, chatId);
        }
        return commandManager.executeCommand(message, chatId);
    }

    public String responseToRecommendCommand(String message, Long chatId) {
        String firstName;
        String lastName;
        String formattedLastName = null;
        String formattedFirstName = null;
        try {
            // Вызов метода для извлечения имени и фамилии из сообщения
            String[] names = extractNameFromMessage(message);

            // Извлекаем firstName и lastName из массива
            firstName = names[0];
            lastName = names[1];

            // Проверка валидности данных
            boolean isValidData = validData(firstName, lastName);
            if (isValidData) {
                formattedFirstName = StringUtils.capitalize(StringUtils.lowerCase(firstName));
                formattedLastName = StringUtils.capitalize(StringUtils.lowerCase(lastName));
            }
            UUID userId = userService.userId(formattedFirstName, formattedLastName);
            if (userId == null) {
                String userNotFoundMessage = "Пользователь не найден";
                telegramService.sendMessage(chatId, userNotFoundMessage);
                return userNotFoundMessage;
            }

            String userFoundMessage = createResponse(formattedFirstName, formattedLastName, chatId);
            telegramService.sendMessage(chatId, userFoundMessage);
            return userFoundMessage;

        } catch (BadMessageException e) {
            String errorMessage = "Вы ввели некорректные данные";
            telegramService.sendMessage(chatId, errorMessage);
            return errorMessage;
        }

    }

    public boolean validData(String firstName, String lastName) {

        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new BadMessageException();
        }
        return true;
    }

    /**
     * Метод для извлечения имени и фамилии пользователя из сообщения
     *
     * @param userMessage Сообщение, полученное от пользователя
     * @return массив с именем и фамилией пользователя
     */

    public String[] extractNameFromMessage(String userMessage) {
        if (userMessage != null && userMessage.startsWith("/recommend")) {
            String[] parts = userMessage.split(" ");
            // Если после команды есть два аргумента (имя и фамилия), то возвращаем его
            if (parts.length == 3) {
                return new String[]{parts[1], parts[2]}; // parts[1] - firstName, parts[2] - lastName;
            }
        }
        throw new BadMessageException();
    }

    /**
     * Метод для получения имени и фамилии пользователя из сообщения
     *
     * @param formattedFirstName,formattedLastName Имя и Фамилия полученное от пользователя
     * @return строка с описанием рекомендаций
     */

    public String getRecommendationByName(String formattedFirstName, String formattedLastName, Long chatId) {

        UUID userId = userService.userId(formattedFirstName, formattedLastName);
        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId);
        if (userRecommendation == null || userRecommendation.getRecommendations().isEmpty()) {
            String recommendationsNotFoundMessage = "Доступных для Вас рекомендаций нет";
            telegramService.sendMessage(chatId, recommendationsNotFoundMessage);
            return recommendationsNotFoundMessage;
        }
        // Получаем список рекомендаций
        List<RecommendationData> recommendations = userRecommendation.getRecommendations();

        // Возвращаем информацию о рекомендациях
        StringBuilder recommendationsInfo = new StringBuilder("Новые продукты для вас:\n");
        for (RecommendationData recommendation : recommendations) {
            recommendationsInfo.append(recommendation.getProductName())
                    .append("\n").append(recommendation.getProductText()).append("\n");
        }
        telegramService.sendMessage(chatId, recommendationsInfo.toString());
        return recommendationsInfo.toString();
    }

    public String createResponse(String formattedFirstName, String formattedLastName, Long chatId) {
        String helloMessage = "Здравствуйте " + formattedFirstName + " " + formattedLastName + "! ";
        String recommendations = getRecommendationByName(formattedFirstName, formattedLastName, chatId);
        return helloMessage + recommendations;
    }

}
