package dekun.wang;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import dekun.wang.utils.ExecuteCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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
        List<String> commandList = new ArrayList<>();
        commandList.add("open");
        commandList.add("-a");
        commandList.add("/Applications/iTerm.app");
        commandList.add(".");
        logger.info(e.toString());
        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (file == null) {
            ExecuteCommand.execute(Objects.requireNonNull(e.getProject()).getBasePath(), commandList);
        } else if (file.isDirectory()) {
            ExecuteCommand.execute(file.getPath(), commandList);
        } else {
            ExecuteCommand.execute(file.getParent().getPath(), commandList);
        }
    }

}
