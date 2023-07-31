package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import dekun.wang.utils.CommandOrApp;
import dekun.wang.utils.ExecuteCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * @author WangDeKun
 *
 * <p>
 * wangdekunemail@gmail.com
 */


public class ITerm2 extends AnAction {
    private static final Logger logger = Logger.getInstance(ITerm2.class);

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        logger.info(e.toString());
        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (file == null) {
            ExecuteCommand.execute(Objects.requireNonNull(e.getProject()).getBasePath(), CommandOrApp.App.getDefaultTerminalAppCommand());
        } else if (file.isDirectory()) {
            ExecuteCommand.execute(file.getPath(), CommandOrApp.App.getDefaultTerminalAppCommand());
        } else {
            ExecuteCommand.execute(file.getParent().getPath(), CommandOrApp.App.getDefaultTerminalAppCommand());
        }
    }

}
