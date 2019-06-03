package org.ssinc.projectwriter

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.github.mustachejava.MustacheFactory
import org.gradle.api.Project
import org.ssinc.projectwriter.model.ExternalDependency
import org.ssinc.projectwriter.model.ProjectDependency

class ProjectWriter {

    static write(Project project, File folder) {
        MustacheFactory mf = new DefaultMustacheFactory()
        Mustache m = mf.compile('dependencies.mustache')

        def buildGradle = new File(folder, 'build.gradle')
        def context = [
                externalDependencies: externalDependencies(project),
                projectDependencies : projectDependencies(project)
        ]

        m.execute(buildGradle.newWriter(), context).flush()
    }

    static List<ExternalDependency> externalDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ExternalDependency }
                    .each { org.gradle.api.artifacts.ExternalDependency dep ->
                        def dependency = new ExternalDependency()
                        dependency.configuration = config.name
                        dependency.group = dep.group
                        dependency.artifact = dep.name
                        dependency.version = dep.version

                        deps.add(dependency)
                    }
        }

        deps
    }

    static List<ProjectDependency> projectDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ProjectDependency }
                    .each { org.gradle.api.artifacts.ProjectDependency dep ->
                def dependency = new ProjectDependency()
                dependency.configuration = config.name
                dependency.projectname = dep.name

                deps.add(dependency)
            }
        }

        deps
    }
}
