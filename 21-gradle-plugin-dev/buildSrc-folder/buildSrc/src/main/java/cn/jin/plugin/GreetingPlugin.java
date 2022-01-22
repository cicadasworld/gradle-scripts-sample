package cn.jin.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GreetingPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        GreetingPluginExt ext = project.getExtensions().create("greeting", GreetingPluginExt.class);
        project.task("hello").doLast(task -> {
            System.out.println("Hello, " + ext.getGreeter());
            System.out.println("I have a message for you: " + ext.getMessage());
        });
    }
}