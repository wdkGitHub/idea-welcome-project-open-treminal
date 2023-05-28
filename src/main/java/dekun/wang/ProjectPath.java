package dekun.wang;

import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author WangDeKun
 *
 * <p>
 */
public class ProjectPath {

    public static boolean isGitRepository(AnActionEvent event) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        File file = new File (getProjectPath (event).replaceAll ("(.)/$", "$1") + "/.git");
        return file.exists () && file.isDirectory ();
    }

    public static Object getRecentProjectItem(AnActionEvent event) throws RuntimeException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
        Class<?> RecentProjectsWelcomeScreenActionBase$Companion = Class.forName ("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.RecentProjectsWelcomeScreenActionBase$Companion");
        Constructor<?> CompanionConstructor = RecentProjectsWelcomeScreenActionBase$Companion.getDeclaredConstructor ();
        CompanionConstructor.setAccessible (true);
        Object companion = CompanionConstructor.newInstance ();
        Method getSelectedItem$intellijPlatformIdeImpl = RecentProjectsWelcomeScreenActionBase$Companion.getMethod ("getSelectedItem$intellij_platform_ide_impl", AnActionEvent.class);
        return getSelectedItem$intellijPlatformIdeImpl.invoke (companion, event);

    }

    public static  String  getProjectPath(AnActionEvent event) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object invoke = getRecentProjectItem (event);
        Method getProjectPath = invoke.getClass ().getMethod ("getProjectPath");
        return String.valueOf (getProjectPath.invoke (invoke));
    }
}
