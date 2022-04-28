package ca.csfoy.servicesweb.camarchedoc.controller.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import ca.csfoy.servicesweb.camarchedoc.api.user.UserDtoForCreate;
import ca.csfoy.servicesweb.camarchedoc.domain.exception.InputValidationException;

public class UserCreateCustomValidator implements CustomValidator<UserDtoForCreate, String> {

    private final Validator defaultHibernateValidator;
    private List<String> errorMessages;
    
    public UserCreateCustomValidator(Validator defaultHibernateValidator) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void validate(UserDtoForCreate object) {
        Set<ConstraintViolation<UserDtoForCreate>> violations = defaultHibernateValidator.validate(object, Default.class);
        
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
    public void validate(String id, UserDtoForCreate object) {
        throw new UnsupportedOperationException();
    }

}
