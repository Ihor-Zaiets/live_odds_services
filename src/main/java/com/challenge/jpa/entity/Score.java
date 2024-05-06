package com.challenge.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Integer homeTeamScore = 0;
    private Integer awayTeamScore = 0;
}
