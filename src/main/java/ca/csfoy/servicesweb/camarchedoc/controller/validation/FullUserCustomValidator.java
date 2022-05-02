package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.stereotype.Component;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import ca.csfoy.servicesweb.camarchedoc.api.global.Const;
import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.api.validation.CreateGroupValidation;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

@Component
public class FullUserCustomValidator implements CustomValidator<FullUserDto, String> {

    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public FullUserCustomValidator(Validator defaultHibernateValidator) {
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
        if (id == null) {
            this.errorMessages.add("ID must not be null.");
        }
        
        if (Objects.isNull(id) || !id.matches(Const.ID_VALID_PATTERN)) {
            this.errorMessages.add("ID must be numbers only.");
        }
    }

    @Override
    public void validate(FullUserDto object) {
        Set<ConstraintViolation<FullUserDto>> violations = defaultHibernateValidator.validate(object, CreateGroupValidation.class);
        
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        
        if (object.password != null && object.password.trim().length() > 0) {
            Zxcvbn zxcvbn = new Zxcvbn();
            Strength strength = zxcvbn.measure(object.password);
            if (strength.getScore() < 3) {
                this.errorMessages.add("Password isn't strong enough.");
            }
        }
    }

    @Override
    public void validate(String id, FullUserDto object) {
        validateId(id);
        validate(object);
        Set<ConstraintViolation<FullUserDto>> violations = defaultHibernateValidator.validate(object, Default.class);
        if (!violations.isEmpty()) {
            violations.forEach(v -> this.errorMessages.add(v.getMessage()));
        }
        if (!object.id.equals(id)) {
            this.errorMessages.add("IDs must be equals." + id + object.id);
        }
    }
}
