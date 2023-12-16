package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
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
public class GitOpenProject extends AnAction {
    private static final Logger logger = Logger.getInstance(ForkAppProject.class);


    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        if (CommandUtils.commandNotExists(CommandOrApp.Command.GitOpen_Command)) {
            event.getPresentation().setEnabledAndVisible(false);
        } else {
            event.getPresentation().setEnabledAndVisible(ProjectInfo.isGitRepository(event.getData(PlatformDataKeys.VIRTUAL_FILE)));
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        logger.info(event.toString());
        VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        assert virtualFile != null;
        if (virtualFile.getParent() != null) {
            String path = virtualFile.getPath();
            ExecuteCommand.execute(path, CommandOrApp.Command.GitOpen_Command);
        } else {
            logger.warn("virtualFile is null");
        }

    }
}
