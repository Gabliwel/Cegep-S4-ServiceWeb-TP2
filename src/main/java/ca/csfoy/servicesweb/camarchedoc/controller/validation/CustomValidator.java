package ca.csfoy.servicesweb.camarchedoc.controller.validation;

public interface CustomValidator<T, U> {
    
    void verify(String genericStringMessage);
    
    void validateId(U id);
    
    void validate(T object);
    
    void validate(U id, T object);

}
