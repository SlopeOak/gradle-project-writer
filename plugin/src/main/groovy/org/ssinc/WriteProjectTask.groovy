package org.ssinc

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

class WriteProjectTask extends DefaultTask {

    @Inject
    WriteProjectTask(Project project) {
        description = 'Write the project to a build.gradle file'
    }

    @TaskAction
    def writeProject() {

    }
}
