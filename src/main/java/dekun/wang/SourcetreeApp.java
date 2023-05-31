package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import dekun.wang.utils.CommandOrApp;
import dekun.wang.utils.CommandUtils;
import dekun.wang.utils.ExecuteCommand;
import dekun.wang.utils.ProjectInfo;
import org.jetbrains.annotations.NotNull;

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
        if (CommandUtils.commandNotExists(CommandOrApp.Command.SourceTree_Command)) {
            event.getPresentation().setEnabledAndVisible(false);
        } else {
            event.getPresentation().setEnabledAndVisible(ProjectInfo.isRecentProjectItem(event) && ProjectInfo.isGitRepository(event));
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        ExecuteCommand.execute(ProjectInfo.getProjectPath(event), CommandOrApp.Command.SourceTree_Command);
    }
}
