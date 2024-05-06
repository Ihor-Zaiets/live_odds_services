package com.challenge.jpa.entity;

import com.challenge.jpa.enums.Country;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Match {

    @NonNull
    private Country homeTeam;
    @NonNull
    private Country awayTeam;
    private Score score = new Score();
}
