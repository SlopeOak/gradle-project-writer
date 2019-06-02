package org.ssinc.projectwriter

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import com.github.mustachejava.MustacheFactory
import org.gradle.api.Project
import org.ssinc.projectwriter.model.Dependency

class ProjectWriter {

    static write(Project project, File folder) {
        MustacheFactory mf = new DefaultMustacheFactory()
        Mustache m = mf.compile('dependencies.mustache')

        def buildGradle = new File(folder, 'build.gradle')
        def context = [dependencies: dependencies(project)]

        m.execute(buildGradle.newWriter(), context).flush()
    }

    static List<Dependency> dependencies(Project project) {
        def deps = []

        project.configurations.each { config ->
            config.dependencies.each { dep ->
                def dependency = new Dependency()
                dependency.configuration = config.name
                dependency.group = dep.group
                dependency.artifact = dep.name
                dependency.version = dep.version

                deps.add(dependency)
            }
        }

        deps
    }
}
