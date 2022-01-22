package cn.jin.plugin.ftp;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * upload plugin
 */
public class UploadPlugin implements Plugin<Project> {
    /**
     * ע���ϴ�����
     * @param project ������Ŀ
     */
    @Override
    public void apply(Project project) {
        project.getTasks().register("upload", UploadTask.class);
    }
}
