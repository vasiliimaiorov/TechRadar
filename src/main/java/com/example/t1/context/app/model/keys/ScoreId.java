package com.example.t1.context.app.model.keys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ScoreId implements Serializable {

    private Long userId;
    private Long techSolutionId;

}
