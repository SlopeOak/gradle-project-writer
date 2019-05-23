import org.gradle.api.DefaultTask
import org.gradle.api.Project

import javax.inject.Inject

class WriteProjectTask extends DefaultTask {

    @Inject
    WriteProjectTask(Project project) {

    }
}
