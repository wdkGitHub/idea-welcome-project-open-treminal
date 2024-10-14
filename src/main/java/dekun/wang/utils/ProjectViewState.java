package dekun.wang.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author WangDeKun
 * <p>
 * Email :  wangdekunemail@gmail.com
 */


public class ProjectViewState {

    public static void projectViewStateModified(String dir) throws IOException {
        Path filePath = Path.of(dir, ".idea", "workspace.xml");
        if (!Files.exists(filePath)) {
            return;
        }
        List<String> modifiedLines = new ArrayList<>();
        List<String> projectViewState = new ArrayList<>();
        List<String> projectViewStateModified = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            boolean start = false;
            for (String line : lines.toList()) {
                if (!start) {
                    modifiedLines.add(line);
                    if (line.trim().startsWith("<component") && line.trim().contains("name=\"ProjectViewState\"")) {
                        start = true;
                    }
                } else {
                    if (line.trim().startsWith("</component>")) {
                        start = false;
                        boolean autoscrollFromSource = false;
                        boolean autoscrollToSource = false;
                        // boolean openDirectoriesWithSingleClick = false;
                        for (String opt : projectViewState) {
                            if (opt.trim().contains("name=\"autoscrollFromSource\"")) {
                                autoscrollFromSource = true;
                            }
                            if (opt.trim().contains("name=\"autoscrollToSource\"")) {
                                autoscrollToSource = true;
                            }
                            // if (opt.trim().contains("name=\"openDirectoriesWithSingleClick\"")) {
                            //     openDirectoriesWithSingleClick = true;
                            // }
                            projectViewStateModified.add(opt);
                        }
                        if (!autoscrollFromSource) {
                            projectViewStateModified.add("    <option name=\"autoscrollFromSource\" value=\"true\" />");
                        }
                        if (!autoscrollToSource) {
                            projectViewStateModified.add("    <option name=\"autoscrollToSource\" value=\"true\" />");
                        }
                        // if (!openDirectoriesWithSingleClick) {
                        //     projectViewStateModified.add("    <option name=\"openDirectoriesWithSingleClick\" value=\"true\" />");
                        // }
                        modifiedLines.addAll(projectViewStateModified);
                        modifiedLines.add(line);
                    } else {
                        projectViewState.add(line);
                    }
                }
            }
            Files.write(filePath, modifiedLines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

}
