package com.example.t1.context.app.api;

import com.example.t1.context.app.dto.TechSolutionOnRadarDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechSolutionOnRadarResponse {
    private List<TechSolutionOnRadarDTO> entries;

}
