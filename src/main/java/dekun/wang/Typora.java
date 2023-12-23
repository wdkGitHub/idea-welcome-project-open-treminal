package dekun.wang;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiFile;
import dekun.wang.utils.ExecuteCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * @author WangDeKun
 *
 * <p>
 * <p>
 * Email :  wangdekunemail@gmail.com
 * <p>
 */

public class Typora extends AnAction {
    private static final Logger logger = Logger.getInstance(Typora.class);

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(checkFileSuffix(event.getData(PlatformDataKeys.PSI_FILE)));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        logger.info(e.toString());
        PsiFile psiFile = e.getData(PlatformDataKeys.PSI_FILE);
        assert psiFile != null;
        if (psiFile.getParent() != null) {
            String path = psiFile.getParent().getVirtualFile().getPath();

            List<String> commandList = new ArrayList<>();
            commandList.add("open");
            commandList.add("-a");
            commandList.add("Typora");
            commandList.add(psiFile.getName());
            ExecuteCommand.execute(path, commandList);
        } else {
            logger.error("psiFile.getParent()==null");
        }


    }

    private boolean checkFileSuffix(PsiFile psiFile) {
        if (psiFile != null && !psiFile.isDirectory()) {
            String name = psiFile.getName();
            String suffix = name.replaceAll("^.*\\.(.*)$", "$1");
            return "md".equalsIgnoreCase(suffix);
        } else {
            return false;
        }
    }

}
