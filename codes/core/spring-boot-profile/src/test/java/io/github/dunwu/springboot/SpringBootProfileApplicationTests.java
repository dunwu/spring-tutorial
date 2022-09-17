package io.github.dunwu.springboot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringBootProfileApplicationTests {

    @Rule
    public final OutputCapture output = new OutputCapture();

    private String profiles;

    @After
    public void after() {
        if (this.profiles != null) {
            System.setProperty("spring.profiles.active", this.profiles);
        } else {
            System.clearProperty("spring.profiles.active");
        }
    }

    @Before
    public void before() {
        this.profiles = System.getProperty("spring.profiles.active");
    }

    @Test
    public void testDefaultProfile() {
        System.setProperty("spring.profiles.active", "default");
        SpringBootProfileApplication.main(new String[0]);
        assertThat(this.output.toString()).contains("Development Begin!");
    }

    @Test
    public void testDevProfile() {
        System.setProperty("spring.profiles.active", "dev");
        SpringBootProfileApplication.main(new String[0]);
        assertThat(this.output.toString()).contains("The app is running on profile dev.");
    }

    @Test
    public void testProdProfile() {
        System.setProperty("spring.profiles.active", "prod");
        SpringBootProfileApplication.main(new String[0]);
        assertThat(this.output.toString()).contains("The app is running on profile prod.");
    }

    @Test
    public void testProdProfileFromCommandline() {
        SpringBootProfileApplication.main(new String[] { "--spring.profiles.active=prod" });
        assertThat(this.output.toString()).contains("The app is running on profile prod.");
    }

    @Test
    public void testTestProfile() {
        System.setProperty("spring.profiles.active", "test");
        SpringBootProfileApplication.main(new String[0]);
        assertThat(this.output.toString()).contains("The app is running on profile test.");
    }

}
