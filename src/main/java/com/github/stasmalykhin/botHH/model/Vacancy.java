package com.github.stasmalykhin.botHH.model;

import lombok.*;

import java.util.Date;

/**
 * @author Stanislav Malykhin
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private String headHunterId;
    private String nameVacancy;
    private String nameEmployer;
    private String nameArea;
    private Date publishedAt;
    private String url;
}
