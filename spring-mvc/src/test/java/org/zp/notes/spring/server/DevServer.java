package org.zp.notes.spring.server;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.zp.notes.spring.websocket.WebSocketEndpoint;

import com.google.common.collect.Lists;

/**
 * @author vicotr zhang
 */
@SuppressWarnings("unused")
public class DevServer {
    private static final int PORT = 7777;
    private static final String CONTEXT = "/";
    private static final String RESOURCE_BASE_PATH = "src/main/webapp";
    private static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web", "tiles" };
    private static final String WINDOWS_WEBDEFAULT_PATH = "src/test/resources/jetty/webdefault.xml";

    public static final int IDE_ECLIPSE = 0;
    public static final int IDE_INTELLIJ = 1;

    // private static int STARTUP_TYPE = JettyFactory.IDE_ECLIPSE;
    private static int STARTUP_TYPE = DevServer.IDE_INTELLIJ;

    public static void main(String[] args) throws Exception {
        // 设定 Spring 的 profile
        Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        // 配置 jetty 服务
        System.out.println("[INFO] Application loading");
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(CONTEXT);
        webAppContext.setResourceBase(getAbsolutePath() + RESOURCE_BASE_PATH);
        webAppContext.setDefaultsDescriptor(getAbsolutePath() + WINDOWS_WEBDEFAULT_PATH);
        server.setHandler(webAppContext);
        supportJspAndSetTldJarNames(server, TLD_JAR_NAMES);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webAppContext);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(WebSocketEndpoint.class);

            System.out.println("[INFO] Don't forget to set -XX:MaxPermSize=128m");
            System.out.println("Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[INFO] Hit Enter to reload the application quickly");

            server.start();
            server.join();

            // 等待用户输入回车重载应用
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    reloadWebAppContext(server);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void reloadWebAppContext(Server server) throws Exception {
        WebAppContext webAppContext = (WebAppContext) server.getHandler();
        System.out.println("[INFO] Application reloading");
        webAppContext.stop();
        WebAppClassLoader classLoader = new WebAppClassLoader(webAppContext);
        classLoader.addClassPath(getAbsolutePath() + "target/classes");
        classLoader.addClassPath(getAbsolutePath() + "target/test-classes");
        webAppContext.setClassLoader(classLoader);
        webAppContext.start();
        System.out.println("[INFO] Application reloaded");
    }

    private static String getAbsolutePath() {
        String path = null;
        String folderPath = DevServer.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        if (folderPath.indexOf("target") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("target"));
        }
        return path;
    }

    private static void supportJspAndSetTldJarNames(Server server, String... jarNames) {
        WebAppContext context = (WebAppContext) server.getHandler();
        // This webapp will use jsps and jstl. We need to enable the AnnotationConfiguration in
        // order to correctly set up the jsp container
        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");
        // Set the ContainerIncludeJarPattern so that jetty examines these container-path jars for
        // tlds, web-fragments etc.
        // If you omit the jar that contains the jstl .tlds, the jsp engine will scan for them
        // instead.
        ArrayList jarNameExprssions = Lists.newArrayList(".*/[^/]*servlet-api-[^/]*\\.jar$",
                ".*/javax.servlet.jsp.jstl-.*\\.jar$", ".*/[^/]*taglibs.*\\.jar$");

        for (String jarName : jarNames) {
            jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
        }

        context.setAttribute("org.eclipse.jetty.org.zp.notes.javaee.server.webapp.ContainerIncludeJarPattern",
                StringUtils.join(jarNameExprssions, '|'));
    }
}
