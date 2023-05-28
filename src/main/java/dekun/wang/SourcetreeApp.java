package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class SourcetreeApp extends AnAction {
    private static final Logger LOG = Logger.getInstance(SourcetreeApp.class);

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        try {
            Object recentProjectItem = ProjectPath.getRecentProjectItem(event);
            event.getPresentation().setEnabledAndVisible(recentProjectItem != null
                    && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals(recentProjectItem.getClass().getName())
                    && ProjectPath.isGitRepository(event)
            );
        } catch (Exception ex) {
            throw new RuntimeException("项目信息对象获取错误");
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            String projectPath = ProjectPath.getProjectPath(event);
            ProcessBuilder executeCommand = new ProcessBuilder ("/usr/local/bin/stree", ".").directory(new File(projectPath));
            executeCommand.start();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException("获取项目路径错误");
        }
    }
}
