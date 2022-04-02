package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class TrailCustomValidator implements CustomValidator<TrailDto, String> {
    
    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public TrailCustomValidator(Validator defaultHibernateValidator) {
        this.defaultHibernateValidator = defaultHibernateValidator;
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public void verify(String genericStringMessage) {
        
        if (!errorMessages.isEmpty()) {
            throw new InputValidationException("Trail cannot be created. Invalid Informations", this.errorMessages);
        }
    }

    @Override
    public void validateId(String id) {
        if (Objects.isNull(id)) {
            this.errorMessages.add("ID must not be null.");
        }
        
        if (Objects.isNull(id) || !id.matches(TrailDto.ID_VALID_PATTERN)) {
            this.errorMessages.add("ID must be numbers only.");
        }
        
    }

    @Override
    public void validate(TrailDto dto) {
        Set<ConstraintViolation<TrailDto>> violations = defaultHibernateValidator.validate(dto, CreateGroupValidation.class);
        
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        
        validateCommonAttributes(dto);
    }

    @Override
    public void validate(String id, TrailDto dto) {
        validateId(id);
        Set<ConstraintViolation<TrailDto>> violations = defaultHibernateValidator.validate(dto, Default.class);
        
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        validateCommonAttributes(dto);
        
    }
    
    private void validateCommonAttributes(TrailDto dto) {
        
        if (dto.openingDate != null && dto.lastMaintenanceDate != null && dto.lastMaintenanceDate.isBefore(dto.openingDate)) {
            this.errorMessages.add("Last maintenance date cannot be before opening date");
        }
    }

}
