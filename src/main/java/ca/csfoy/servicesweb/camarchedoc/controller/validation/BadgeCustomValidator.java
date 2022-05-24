package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Validator;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class BadgeCustomValidator implements CustomValidator<BadgeDto, String> {
    
    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public BadgeCustomValidator(Validator defaultHibernateValidator) {
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
        if (Objects.isNull(id) || id.isBlank()) {
            this.errorMessages.add("ID must not be null or blank.");
        } else if (!id.matches(Const.ID_VALID_PATTERN)) {
            this.errorMessages.add("ID must be numbers only.");
        }
    }

    @Override
    public void validate(BadgeDto object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void validate(String id, BadgeDto object) {
        throw new UnsupportedOperationException();
    }

}
