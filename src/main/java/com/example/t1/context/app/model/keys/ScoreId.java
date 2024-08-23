package com.example.t1.context.app.model.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreId implements Serializable {

    private Long userId;
    private Long techSolutionId;

}
