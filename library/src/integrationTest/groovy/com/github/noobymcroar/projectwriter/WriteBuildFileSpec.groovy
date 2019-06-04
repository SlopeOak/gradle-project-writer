package com.github.noobymcroar.projectwriter

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class WriteBuildFileSpec extends Specification {
    @Rule
    TemporaryFolder temporaryFolder

    def "Writes a build gradle file when write is called with a project"() {
        given: 'there is a project instance'
            def project = ProjectBuilder.builder()
                    .withProjectDir(temporaryFolder.root)
                    .build()

        when: 'write is called with a project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'a new build.gradle file exists'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')
            buildFile != null && buildFile.exists()
    }
}
