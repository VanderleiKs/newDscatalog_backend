package dscatalog.services.validation;

import dscatalog.controllers.exceptions.FieldMessage;
import dscatalog.dto.UserInsertDTO;
import dscatalog.entities.User;
import dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO value, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(value.getEmail());

        if(user != null){
            list.add(new FieldMessage("Email", "Email already exist"));
        }

        for (FieldMessage f : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
