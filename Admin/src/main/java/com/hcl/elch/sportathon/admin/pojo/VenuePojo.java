package com.hcl.elch.sportathon.admin.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenuePojo {

    @NotNull(message = "Venue name cannot be null")
    @NotBlank(message = "Venue name cannot be blank")
    private String venueName;

    @NotNull(message = "Location cannot be null")
    @NotBlank(message = "Location name cannot be blank")
    private String location;
}
