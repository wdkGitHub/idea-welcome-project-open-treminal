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
public class ForkApp extends AnAction {
    private static final Logger LOG = Logger.getInstance(ForkApp.class);
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        try {
            Object recentProjectItem = ProjectInfo.getRecentProjectItem (event);
            event.getPresentation ().setEnabledAndVisible (
                    recentProjectItem != null
                            && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals (recentProjectItem.getClass ().getName ())
                            && ProjectInfo.isGitRepository (event)
            );
        } catch (Exception ex) {
            throw new RuntimeException ("项目信息对象获取错误");
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            String projectPath = ProjectInfo.getProjectPath (event);
            // 命令非全路径，报错如下。系统自带命令不会报错
            /*
            Cannot run program "fork" (in directory "/A/B/C"): error=2, No such file or directory
             */
            ProcessBuilder executeCommand = new ProcessBuilder ("/usr/local/bin/fork", ".").directory(new File(projectPath));
            executeCommand.start ();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException ("获取项目路径错误");
        }
    }
}
