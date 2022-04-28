package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class EventCustomValidator implements CustomValidator<EventDto, String> {
    
    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public EventCustomValidator(Validator defaultHibernateValidator) {
        this.defaultHibernateValidator = defaultHibernateValidator;
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public void verify(String genericStringMessage) {
        
        if (!errorMessages.isEmpty()) {
            throw new InputValidationException("Event cannot be created. Invalid Informations", this.errorMessages);
        }
    }

    @Override
    public void validateId(String id) {
        if (id == null) {
            this.errorMessages.add("ID must not be null.");
        }
        
        if (Objects.isNull(id) || !id.matches(Const.ID_VALID_PATTERN)) {
            this.errorMessages.add("ID must be numbers only.");
        }
        
    }

    @Override
    public void validate(EventDto dto) {
        Set<ConstraintViolation<EventDto>> violations = defaultHibernateValidator.validate(dto, CreateGroupValidation.class);
        
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
    }

    @Override
    public void validate(String id, EventDto dto) {
        Set<ConstraintViolation<EventDto>> violations = defaultHibernateValidator.validate(dto, Default.class);
        validateId(id);
        validate(dto);
        if (!dto.id.equals(id)) {
            this.errorMessages.add("IDs must be equals.");
        }
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        
    }

}
