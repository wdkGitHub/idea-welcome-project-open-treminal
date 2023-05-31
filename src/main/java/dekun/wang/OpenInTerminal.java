package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import dekun.wang.utils.CommandOrApp;
import dekun.wang.utils.ExecuteCommand;
import dekun.wang.utils.ProjectInfo;
import org.jetbrains.annotations.NotNull;


/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class OpenInTerminal extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenInTerminal.class);

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
        ExecuteCommand.execute(ProjectInfo.getProjectPath(event), CommandOrApp.App.getDefaultTerminalAppCommand());
    }

}
