package dekun.wang.open.in.terminal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * @author wdk
 * @see <a href=""></a>
 * <p>
 */
public class OpenInTerminal extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        try {
//            // 参考： 复制路径的Class
//            Class<?> copyProjectPath = Class.forName ("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.CopyProjectPathAction");
//            Object object = copyProjectPath.getDeclaredConstructor ().newInstance ();
//            System.out.println (object.getClass ().getName ());
//            //获取 父类
//            Class<?> superclass = copyProjectPath.getSuperclass ();
//            //获取 父类：内部类
//            Class<?>[] declaredClasses = superclass.getDeclaredClasses ();
//            for (Class<?> declaredClass : declaredClasses) {
//                System.out.println ("父类---内部类：" + declaredClass.getName ());
//            }

            //获取可以获取 project 的内部类
            Class<?> RecentProjectsWelcomeScreenActionBase$Companion = Class.forName ("com.intellij.openapi.wm.impl.welcomeScreen.projectActions.RecentProjectsWelcomeScreenActionBase$Companion");
            //构造器
            Constructor<?> CompanionConstructor = RecentProjectsWelcomeScreenActionBase$Companion.getDeclaredConstructor ();
            //修改访问权限 private==》public
            CompanionConstructor.setAccessible (true);
            //new
            Object companion = CompanionConstructor.newInstance ();

//            //遍历内部类方法
//            Method[] companionMethods = RecentProjectsWelcomeScreenActionBase$Companion.getDeclaredMethods ();
//            // 内部类 方法｜类型
//            for (Method method : companionMethods) {
//                String typeClassName = "";
//                Class<?>[] parameterTypes = method.getParameterTypes ();
//                for (Class<?> parameterType : parameterTypes) {
//                    typeClassName += parameterType.getName ();
//                }
//                System.out.println ("获取内部类--- 方法名｜类型：" + method.getName () + "  |  " + ("".equals (typeClassName) ? "无参" : typeClassName));
//            }

            //获取可以获取 project 的内部类---  具体方法
            Method getSelectedItem$intellijPlatformIdeImpl = RecentProjectsWelcomeScreenActionBase$Companion.getMethod ("getSelectedItem$intellij_platform_ide_impl", AnActionEvent.class);

            // 获取 RecentProjectItem （koltin 对象），继续用反射获取想用的参数
            Object invoke = getSelectedItem$intellijPlatformIdeImpl.invoke (companion, e);
            // 获取项目本地路径方法
            Method getProjectPath = invoke.getClass ().getMethod ("getProjectPath");
            // 获取路径
            String projectPath = String.valueOf (getProjectPath.invoke (invoke));
//            System.out.println ("路径：" + projectPath);

            //<editor-fold desc="执行Mac命令">
            ProcessBuilder executeCommand = new ProcessBuilder ("open", "-a", "/Applications/iTerm.app", projectPath);
            executeCommand.start ();
            //</editor-fold>

        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

}
