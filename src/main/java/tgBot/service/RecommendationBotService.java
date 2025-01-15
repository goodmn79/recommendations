/*
Файл сервиса для получения рекомендаций пользователя в Телеграм-боте
Powered by ©AYE.team
 */
package tgBot.service;


import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.RecommendationData;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.exception.RecommendationNotFoundException;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.service.UserRecommendationService;
import tgBot.exceptions.BadMessageException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationBotService {

    private final UserRepository userRepository;
    private final UserRecommendationService userRecommendationService;

    public RecommendationBotService(UserRepository userRepository, UserRecommendationService userRecommendationService) {
        this.userRepository = userRepository;
        this.userRecommendationService = userRecommendationService;
    }

    /**
     * Метод для извлечения имени и фамилии пользователя из сообщения
     *
     * @param message Сообщение, полученное от пользователя
     * @return массив с именем и фамилией пользователя
     */

    public String[] extractNameFromMessage(String message) {
        if (message != null && message.startsWith("/recommend")) {
            String[] parts = message.split(" ");
            // Если после команды есть два аргумента (имя и фамилия), то возвращаем его
            if (parts.length == 3) {
                return new String[]{parts[1], parts[2]}; // parts[1] - firstName, parts[2] - lastName;
            }
        }
        throw new BadMessageException("Некорректный формат сообщения. Убедитесь, что сообщение содержит команду /recommend и Имя Фамилию).");
    }

    /**
     * Метод для использования значений из массива с Именем и Фамилией
     *
     * @param message сообщение полученное от пользователя
     * @return строка с ответом пользователю
     */

    public String processMessage(String message) {
        String firstName;
        String lastName;
        try {
            // Вызов метода для извлечения имени и фамилии из сообщения
            String[] names = extractNameFromMessage(message);

            // Извлекаем firstName и lastName из массива
            firstName = names[0];
            lastName = names[1];

            // Проверка валидности данных
            validData(firstName, lastName);

        } catch (BadMessageException e) {
            return "Ошибка: проверьте правильность написания сообщения";
        }
        return createResponse(firstName, lastName);
    }


    public void validData(String firstName, String lastName) {

        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new BadMessageException("Имя и фамилия не могут быть пустыми.");
        }

        if (!isCapitalized(firstName)) {
            throw new BadMessageException("Имя должно начинаться с заглавной буквы.");
        }

        if (!isCapitalized(lastName)) {
            throw new BadMessageException("Фамилия должна начинаться с заглавной буквы.");
        }
    }

    // Если строка не соответствует условиям, метод возвращает false (не капитализирована),
    // иначе — true (капитализирована)
    public boolean isCapitalized(String str) {
        return !str.isEmpty() && Character.isUpperCase(str.charAt(0)) &&
                str.substring(1).equals(str.substring(1).toLowerCase());
    }

    /**
     * Метод для получения имени и фамилии пользователя из сообщения
     *
     * @param firstName,lastName Имя и Фамилия полученное от пользователя
     * @return строка с описанием рекомендаций
     */

    public String getRecommendationByName(String firstName, String lastName) {
        Optional<UUID> userIdOptional = userRepository.getUserId(firstName, lastName);
        if (userIdOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        UUID userId = userIdOptional.get();
        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId);
        if (userRecommendation == null || userRecommendation.getRecommendations().isEmpty()) {
            throw new RecommendationNotFoundException();

        }
        // Получаем список рекомендаций
        List<RecommendationData> recommendations = userRecommendation.getRecommendations();

        // Возвращаем информацию о рекомендациях
        StringBuilder recommendationsInfo = new StringBuilder("Новые продукты для вас:\n");
        for (RecommendationData recommendation : recommendations) {
            recommendationsInfo.append(recommendation.getProductName())
                    .append("\n").append(recommendation.getProductText()).append("\n");
        }
        return recommendationsInfo.toString();
    }

    public String createResponse(String firstName, String lastName) {
        String hello = "Здравствуйте " + firstName + " " + lastName + "! ";
        return hello + getRecommendationByName(firstName, lastName);
    }
}
