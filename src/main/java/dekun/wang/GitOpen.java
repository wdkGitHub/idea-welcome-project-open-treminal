package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class GitOpen extends AnAction {
    private static final Logger LOG = Logger.getInstance(GitOpen.class);

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        try {
            Object recentProjectItem = ProjectInfo.getRecentProjectItem(event);
            event.getPresentation().setEnabledAndVisible(
                    recentProjectItem != null
                            && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals(recentProjectItem.getClass().getName())
                            && ProjectInfo.isGitRepository(event)
            );
            event.getPresentation().setEnabledAndVisible(ProjectInfo.hasRemoteRepository(event));
        } catch (Exception ex) {
            throw new RuntimeException("项目信息对象获取错误");
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            String projectPath = ProjectInfo.getProjectPath(event);
            File directory = new File(projectPath);
            String command = System.getProperty("user.home") + "/.oh-my-zsh/custom/plugins/git-open/git-open";
            LOG.info(command);
            ProcessBuilder executeCommand = new ProcessBuilder(command).directory(directory);
            executeCommand.start();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException("获取项目路径错误");
        }
    }
}
