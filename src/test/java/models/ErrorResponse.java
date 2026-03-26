package models;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;
    private String message;
    private String description;
}
