package com.github.noobymcroar.projectwriter

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

class WriteExternalDependenciesSpec extends Specification {

    @Rule
    TemporaryFolder temporaryFolder

    @Unroll
    def "Writes a dependency with scope '#scope' and dependency body '#dependency'"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create(scope)
            project.dependencies.add(scope, dependency)

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.text == new File("src/integrationTest/resources/dependencies/external/scopes/$expected").text

        where:
            scope            | dependency                                                      || expected
            'compile'        | 'org.ssinc.projectwriter:gradle-plugin:0.0.1'                   || 'compile.gradle'
            'testCompile'    | 'org.ssinc.projectwriter:library:0.0.1'                         || 'testCompile.gradle'
            'implementation' | 'org.apache.commons:commons-lang3:3.8.1'                        || 'implementation.gradle'
            'integTest'      | 'some.other.library:with-an-artifact-id:and.a.version-SNAPSHOT' || 'integTest.gradle'
    }

}
