package example.spring.web.websocket.echo;

public class DefaultEchoService implements EchoService {

    private final String echoFormat;

    public DefaultEchoService(String echoFormat) {
        this.echoFormat = (echoFormat != null) ? echoFormat : "%s";
    }

    @Override
    public String getMessage(String message) {
        return String.format(this.echoFormat, message);
    }

}
