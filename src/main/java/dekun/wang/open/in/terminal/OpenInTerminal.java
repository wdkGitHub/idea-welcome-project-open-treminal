package dekun.wang.open.in.terminal;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


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
            Object recentProjectItem = getRecentProjectItem (event);
            event.getPresentation ().setEnabledAndVisible (recentProjectItem != null && "com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem".equals (recentProjectItem.getClass ().getName ()));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException |
                 InstantiationException ex) {
            String exceptionMessage = "项目信息对象获取错误";
            throw new RuntimeException (exceptionMessage);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            Object invoke = getRecentProjectItem (event);
            Method getProjectPath = invoke.getClass ().getMethod ("getProjectPath");
            String projectPath = String.valueOf (getProjectPath.invoke (invoke));
            ProcessBuilder executeCommand = new ProcessBuilder ("open", "-a", "/Applications/iTerm.app", projectPath);
            executeCommand.start ();
        } catch (Exception e) {
            throw new RuntimeException ("获取项目路径错误");
        }
    }

    private Object getRecentProjectItem(AnActionEvent event) throws RuntimeException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {

        Class<?> RecentProjectsWelcomeScreenActionBase$Companion = Class.forName ("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.RecentProjectsWelcomeScreenActionBase$Companion");
        Constructor<?> CompanionConstructor = RecentProjectsWelcomeScreenActionBase$Companion.getDeclaredConstructor ();
        CompanionConstructor.setAccessible (true);
        Object companion = CompanionConstructor.newInstance ();
        Method getSelectedItem$intellijPlatformIdeImpl = RecentProjectsWelcomeScreenActionBase$Companion.getMethod ("getSelectedItem$intellij_platform_ide_impl", AnActionEvent.class);
        return getSelectedItem$intellijPlatformIdeImpl.invoke (companion, event);

    }
}
