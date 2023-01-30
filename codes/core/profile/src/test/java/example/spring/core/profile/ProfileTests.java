package example.spring.core.profile;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
public class ProfileTests {

    private String profiles;

    @BeforeEach
    public void before() {
        this.profiles = System.getProperty("spring.profiles.active");
    }

    @AfterEach
    public void after() {
        if (this.profiles != null) {
            System.setProperty("spring.profiles.active", this.profiles);
        } else {
            System.clearProperty("spring.profiles.active");
        }
    }

    @Test
    @DisplayName("测试环境变量指定默认 profile")
    public void testDefaultProfile(CapturedOutput capturedOutput) {
        System.setProperty("spring.profiles.active", "default");
        ProfileApplication.main(new String[0]);
        Assertions.assertThat(capturedOutput.toString()).contains("Development Begin!");
    }

    @Test
    @DisplayName("测试环境变量指定 dev profile")
    public void testDevProfile(CapturedOutput capturedOutput) {
        System.setProperty("spring.profiles.active", "dev");
        ProfileApplication.main(new String[0]);
        Assertions.assertThat(capturedOutput.toString()).contains("The app is running on profile dev.");
    }

    @Test
    @DisplayName("测试环境变量指定 test profile")
    public void testTestProfile(CapturedOutput capturedOutput) {
        System.setProperty("spring.profiles.active", "test");
        ProfileApplication.main(new String[0]);
        Assertions.assertThat(capturedOutput.toString()).contains("The app is running on profile test.");
    }

    @Test
    @DisplayName("测试环境变量指定 prod profile")
    public void testProdProfile(CapturedOutput capturedOutput) {
        System.setProperty("spring.profiles.active", "prod");
        ProfileApplication.main(new String[0]);
        Assertions.assertThat(capturedOutput.toString()).contains("The app is running on profile prod.");
    }

    @Test
    @DisplayName("测试命令行指定 prod profile")
    public void testProdProfileFromCommandline(CapturedOutput capturedOutput) {
        ProfileApplication.main(new String[] { "--spring.profiles.active=prod" });
        Assertions.assertThat(capturedOutput.toString()).contains("The app is running on profile prod.");
    }

}
