package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ca.csfoy.servicesweb.camarchedoc.api.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class RatingCustomValidator implements CustomValidator<RatingDto, String> {
    
    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public RatingCustomValidator(Validator defaultHibernateValidator) {
        this.defaultHibernateValidator = defaultHibernateValidator;
        this.errorMessages = new ArrayList<>();
    }

    @Override
    public void verify(String genericStringMessage) {
        if (!errorMessages.isEmpty()) {
            throw new InputValidationException(genericStringMessage, this.errorMessages);
        }
    }
    
    @Override
    public void validateId(String id) {
        if (Objects.isNull(id)) {
            this.errorMessages.add("ID must not be null.");
        }
        
        if (!id.matches(RatingDto.ID_VALID_PATTERN)) {
            this.errorMessages.add("ID must be numbers only.");
        }
    }
    
    @Override
    public void validate(RatingDto object) {
        Set<ConstraintViolation<RatingDto>> violations = defaultHibernateValidator.validate(object, CreateGroupValidation.class);
        
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        
        if (object.note > 5 || object.note < 1) {
            this.errorMessages.add("Rating note must been between value 1 and 5");
        }
    }
    
    @Override
    public void validate(String id, RatingDto object) {
        throw new UnsupportedOperationException();
    }
}
