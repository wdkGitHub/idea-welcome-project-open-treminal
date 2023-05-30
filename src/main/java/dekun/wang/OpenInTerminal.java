package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import dekun.wang.utils.ExecuteCommand;
import dekun.wang.utils.ProjectInfo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


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
        event.getPresentation().setEnabledAndVisible(ProjectInfo.isRecentProjectItem(event));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        ExecuteCommand.OpenInTerminal(ProjectInfo.getProjectPath(event));
    }

}
