package example.spring.web.mvc.convert;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MaskFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<MaskFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
        fieldTypes.add(String.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(MaskFormat annotation, Class<?> fieldType) {
        return new MaskFormatter(annotation.value());
    }

    @Override
    public Parser<?> getParser(MaskFormat annotation, Class<?> fieldType) {
        return new MaskFormatter(annotation.value());
    }

    private static class MaskFormatter implements Formatter<String> {

        private javax.swing.text.MaskFormatter delegate;

        public MaskFormatter(String mask) {
            try {
                this.delegate = new javax.swing.text.MaskFormatter(mask);
                this.delegate.setValueContainsLiteralCharacters(false);
            } catch (ParseException e) {
                throw new IllegalStateException("Mask could not be parsed " + mask, e);
            }
        }

        @Override
        public String print(String object, Locale locale) {
            try {
                return delegate.valueToString(object);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to print using mask " + delegate.getMask(), e);
            }
        }

        @Override
        public String parse(String text, Locale locale) throws ParseException {
            return (String) delegate.stringToValue(text);
        }

    }

}
