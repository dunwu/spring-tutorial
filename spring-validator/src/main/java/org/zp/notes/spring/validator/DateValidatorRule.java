package org.zp.notes.spring.validator;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.expression.ParseException;

@ValidRule
public class DateValidatorRule extends AbastractValidatorRule {
    @Override
    public boolean support(Annotation annotation) {
        return annotation instanceof DateString;
    }

    @Override
    public void validProperty(Annotation annotation, Object property, PostHandler postHandler) {
        DateString ds = (DateString) annotation;
        if (parse(ds.pattern(), (String) property) == null) {
            postHandler.postHanle(ds.errorCode(), ds.message());
        }
    }

    private Date parse(String pattern, String property) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(property);
        } catch (ParseException e) {
            // do noting
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
