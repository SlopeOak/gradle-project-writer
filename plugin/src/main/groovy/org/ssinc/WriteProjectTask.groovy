package org.ssinc

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.ssinc.projectwriter.ProjectWriter

import javax.inject.Inject

class WriteProjectTask extends DefaultTask {

    private Project project

    @Inject
    WriteProjectTask(Project project) {
        description = 'Write the project to a build.gradle file'
        this.project = project
    }

    @TaskAction
    def writeProject() {
        project.logger.info("Writing build.gradle file to $project.rootDir")
        ProjectWriter.write(project, project.rootDir)
    }
}
