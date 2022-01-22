package cn.jin.plugin;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.Test;
import static org.junit.Assert.*;

public class GreetingPluginTest {
    @Test 
    public void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("cn.jin.plugin.greeting");

        // Verify the result
        assertNotNull(project.getTasks().findByName("greeting"));
    }
}
