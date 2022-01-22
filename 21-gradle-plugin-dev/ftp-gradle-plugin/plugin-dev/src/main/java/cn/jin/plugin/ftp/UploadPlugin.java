package cn.jin.plugin.ftp;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * upload plugin
 */
public class UploadPlugin implements Plugin<Project> {
    /**
     * 注册上传任务
     * @param project 构建项目
     */
    @Override
    public void apply(Project project) {
        project.getTasks().register("upload", UploadTask.class);
    }
}
