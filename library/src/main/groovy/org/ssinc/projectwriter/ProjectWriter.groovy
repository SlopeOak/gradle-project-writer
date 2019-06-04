package org.ssinc.projectwriter

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.github.mustachejava.MustacheFactory
import org.gradle.api.Project
import org.ssinc.projectwriter.model.ExternalDependencyView
import org.ssinc.projectwriter.model.ProjectDependencyView

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

    static List<ExternalDependencyView> externalDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ExternalDependency }
                    .each { org.gradle.api.artifacts.ExternalDependency dep ->
                        def dependencyView = new ExternalDependencyView()
                        dependencyView.configuration = config.name
                        dependencyView.dependency = dep

                        deps.add(dependencyView)
                    }
        }

        deps
    }

    static List<ProjectDependencyView> projectDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ProjectDependency }
                    .each { org.gradle.api.artifacts.ProjectDependency dep ->
                def dependencyView = new ProjectDependencyView()
                dependencyView.configuration = config.name
                dependencyView.dependency = dep

                deps.add(dependencyView)
            }
        }

        deps
    }
}
