package org.pum.shortly.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortURLRequest {

    @NotNull(message = "URL cannot be null")
    @NotEmpty(message = "URL cannot be empty")
    @URL(message = "Invalid URL format")
    private String url;

}
