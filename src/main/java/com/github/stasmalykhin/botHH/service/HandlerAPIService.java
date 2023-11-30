package com.github.stasmalykhin.botHH.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stasmalykhin.botHH.model.Vacancy;
import com.github.stasmalykhin.botHH.util.DateConversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class HandlerAPIService extends APIConnectionService {
    private final DateConversion dateConversion;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${headhunter.endpoint.searchForVacancy}")
    private String searchForVacancyURI;
    private static final String TEXT_OF_SEARCH_QUERY = "Java";
    private static final int ID_OF_MOSCOW_FOR_SEARCH_QUERY = 1;
    private static final int ID_OF_SAINT_PETERSBURG_FOR_SEARCH_QUERY = 2;

    public List<Vacancy> getListWithNewVacancies(Date dateOfPublicationOfLastVacancy) {
        HttpEntity<HttpHeaders> request = createRequestWithHeaders();
        List<Vacancy> vacancies = new ArrayList<>();
        int numberOfPages = getNumberOfPages(request);
        for (int i = 0; i <= numberOfPages; i++) {
            JsonNode jsonNode = getPageWithVacancies(request, i);
            boolean jsonNodeContainsVacancies = jsonNode != null && !jsonNode.get("items").isEmpty();
            if (jsonNodeContainsVacancies) {
                JsonNode vacanciesOnCurrentPage = jsonNode.get("items");
                for (int j = 0; j < vacanciesOnCurrentPage.size(); j++) {
                    JsonNode vacancy = vacanciesOnCurrentPage.get(j);
                    Date publishedAt = dateConversion.fromStringToDate("yyyy-MM-dd'T'HH:mm:ss",
                            vacancy.get("published_at").asText());
                    boolean currentVacancyIsNotNew = !publishedAt.after(dateOfPublicationOfLastVacancy);
                    if (currentVacancyIsNotNew) {
                        return vacancies;
                    }
                    vacancies.add(convertIntoVacancy(vacancy, publishedAt));
                }
            }
        }
        return vacancies;
    }

    private Vacancy convertIntoVacancy(JsonNode vacancy, Date publishedAt) {
        return Vacancy.builder()
                .headHunterId(vacancy.get("id").asText())
                .nameVacancy(vacancy.get("name").asText())
                .nameEmployer(vacancy.get("employer").get("name").asText())
                .nameArea(vacancy.get("area").get("name").asText())
                .publishedAt(publishedAt)
                .url(vacancy.get("alternate_url").asText())
                .build();
    }

    private int getNumberOfPages(HttpEntity<HttpHeaders> request) {
        JsonNode firstPageWithVacancies = getPageWithVacancies(request, 0);
        String numberOfPages = firstPageWithVacancies.get("found").asText();
        return Integer.parseInt(numberOfPages) / 100;
    }

    private JsonNode getPageWithVacancies(HttpEntity<HttpHeaders> request, int pageNumber) {
        String uri = getUri(pageNumber);

        var response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class).getBody();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.error(e);
        }
        return jsonNode;
    }

    private String getUri(int pageNumber) {
        String uri = searchForVacancyURI;
        return UriComponentsBuilder
                .fromUriString(uri)
                .queryParam("page", String.valueOf(pageNumber))
                .queryParam("per_page", "100")
                .queryParam("text", TEXT_OF_SEARCH_QUERY)
                .queryParam("search_field", "name")
                .queryParam("area", ID_OF_MOSCOW_FOR_SEARCH_QUERY,
                        ID_OF_SAINT_PETERSBURG_FOR_SEARCH_QUERY)
                .queryParam("period", "1")
                .queryParam("order_by", "publication_time")
                .build().toUriString();
    }
}
