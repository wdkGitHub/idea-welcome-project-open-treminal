package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import dekun.wang.utils.ExecuteCommand;
import dekun.wang.utils.ProjectInfo;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

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
        event.getPresentation().setEnabledAndVisible(ProjectInfo.isRecentProjectItem(event) && ProjectInfo.isGitRepository(event) && ProjectInfo.hasRemoteRepository(event));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        ExecuteCommand.GitOpen(ProjectInfo.getProjectPath(event));
    }
}
