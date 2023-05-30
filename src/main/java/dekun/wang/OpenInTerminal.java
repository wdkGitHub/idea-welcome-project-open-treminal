package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;


/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class OpenInTerminal extends AnAction {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        try {
            Object recentProjectItem = ProjectInfo.getRecentProjectItem (event);
            event.getPresentation ().setEnabledAndVisible (recentProjectItem != null
                    && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals (recentProjectItem.getClass ().getName ()));
        } catch (Exception ex) {
            throw new RuntimeException ("项目信息对象获取错误");
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            String projectPath = ProjectInfo.getProjectPath (event);
            ProcessBuilder executeCommand = new ProcessBuilder ("open", "-a", "/Applications/iTerm.app", projectPath);
            executeCommand.start ();
        } catch (Exception e) {
            throw new RuntimeException ("获取项目路径错误");
        }
    }

}
