package example.spring.core.aop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class AopTests {

    @Test
    public void testCommandLineOverrides(CapturedOutput capturedOutput) {
        AopApplication.main(new String[] { "--author.name=Spring Boot" });
        String output = capturedOutput.getOut();
        assertThat(output).contains(
            "call @Before, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @After, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @AfterReturning, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains("Welcome Spring Boot");
    }

    @Test
    public void testDefaultSettings(CapturedOutput capturedOutput) {
        AopApplication.main(new String[0]);
        String output = capturedOutput.getOut();
        assertThat(output).contains(
            "call @Before, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @After, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @AfterReturning, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains("Welcome Zhang Peng");
    }

    @Test
    public void testFailture(CapturedOutput capturedOutput) {
        try {
            AopApplication.main(new String[] { "--throw.exception=true" });
        } catch (Exception e) {
            e.printStackTrace();
        }

        String output = capturedOutput.getOut();
        assertThat(output).contains(
            "call @Before, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @After, joinPoint: execution(String example.spring.core.aop.WelcomeService.getMessage())");
        assertThat(output).contains(
            "call @AfterThrowing, joinPoint: execution(void example.spring.core.aop.WelcomeService.getException())");
        assertThat(output).contains("Welcome Zhang Peng");
    }

}
