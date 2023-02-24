package example.spring.web.mvc.convert;

public final class SocialSecurityNumber {

    private final String value;

    public SocialSecurityNumber(String value) {
        this.value = value;
    }

    public static SocialSecurityNumber valueOf(@MaskFormat("###-##-####") String value) {
        return new SocialSecurityNumber(value);
    }

    @MaskFormat("###-##-####")
    public String getValue() {
        return value;
    }

}
