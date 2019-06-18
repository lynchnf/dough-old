package name.mutant.dough.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RegexPatternValidator implements ConstraintValidator<RegexPattern, String> {
    @Override
    public void initialize(RegexPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s))
            return true;
        try {
            Pattern pattern = Pattern.compile(s);
            return true;
        } catch (Exception PatternSyntaxException) {
            return false;
        }
    }
}