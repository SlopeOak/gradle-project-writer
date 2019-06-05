package com.github.slopeoak

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class GradleWriterPluginSpec extends Specification {

    @Rule
    TemporaryFolder folder
    File buildFile

    def setup() {
        buildFile = folder.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'com.github.slopeoak.gradle-project-writer-plugin'
            }
        """
    }

    def "Plugin exists"() {
        expect:
            def project = GradleRunner.create()
                    .withPluginClasspath()
                    .withProjectDir(folder.root)
                    .withArguments('writeProject')
                    .build()
            project.task(':writeProject').outcome == TaskOutcome.SUCCESS
    }
}
