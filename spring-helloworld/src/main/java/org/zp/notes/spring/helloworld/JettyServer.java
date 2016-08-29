package org.zp.notes.spring.helloworld;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
    private static final int PORT = 8095;
    private static final String CONTEXT = "/test";
    private static final String WEBAPP_PATH = "/src/main/webapp";
    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";

    public static void main(String[] args) {
        WebAppContext context = new WebAppContext();
        String projPath = System.getProperty("user.dir");
        context.setDescriptor(projPath + WEBAPP_PATH + WEB_XML_PATH);
        context.setResourceBase(projPath + WEBAPP_PATH);
        context.setContextPath(CONTEXT);
        context.setParentLoaderPriority(true);

        Server server = new Server(PORT);
        server.setHandler(context);
        try {
            server.start();
            System.out.println("服务器启动成功，URL为： http://localhost:" + PORT + CONTEXT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
