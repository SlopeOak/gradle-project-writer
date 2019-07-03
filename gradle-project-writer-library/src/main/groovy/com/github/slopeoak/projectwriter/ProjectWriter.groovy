package com.github.slopeoak.projectwriter

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.github.mustachejava.MustacheFactory
import org.gradle.api.Project

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

    static List<com.github.slopeoak.projectwriter.model.ExternalDependencyView> externalDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ExternalDependency }
                    .each { org.gradle.api.artifacts.ExternalDependency dep ->
                        def dependencyView = new com.github.slopeoak.projectwriter.model.ExternalDependencyView()
                        dependencyView.configuration = config.name
                        dependencyView.dependency = dep
                        dependencyView.verbose = dep.targetConfiguration != null && dep.targetConfiguration != 'jar'

                        deps.add(dependencyView)
                    }
        }

        deps
    }

    static List<com.github.slopeoak.projectwriter.model.ProjectDependencyView> projectDependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies
                    .findAll { it instanceof org.gradle.api.artifacts.ProjectDependency }
                    .each { org.gradle.api.artifacts.ProjectDependency dep ->
                def dependencyView = new com.github.slopeoak.projectwriter.model.ProjectDependencyView()
                dependencyView.configuration = config.name
                dependencyView.dependency = dep

                deps.add(dependencyView)
            }
        }

        deps
    }
}
