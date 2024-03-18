package com.personalfinanceapp.model.personalfinanceapp.DateConverter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, String> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convertToDatabaseColumn(LocalDate date) {
        return (date == null ? null : date.format(DATE_FORMAT));
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        try {
            return (dbData == null ? null : LocalDate.parse(dbData, DATE_FORMAT));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dbData, e);
        }
    }
}
