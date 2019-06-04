package org.ssinc.projectwriter

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

class WriteProjectDependenciesSpec extends Specification {

    @Rule
    TemporaryFolder temporaryFolder

    @Unroll
    def "Writes a project dependency with scope '#scope' and dependency path '#dependency'"() {
        given: 'there is a project'
            def project = ProjectBuilder.builder().build()

        and: 'the project has the dependency'
            project.configurations.create(scope)
            def otherDep = ProjectBuilder.builder()
                    .withName('other')
                    .build()
            project.dependencies.add(scope, otherDep)

        when: 'write is called with the project instance'
            ProjectWriter.write(project, temporaryFolder.root)

        then: 'there is a new build.gradle file in the target folder'
            def buildFile = new File(temporaryFolder.root, 'build.gradle')

        and: 'the build.gradle file matches the expected body'
            buildFile.text == new File("src/integrationTest/resources/dependencies/projects/scopes/$expected").text

        where:
            scope     || expected
            'compile' || 'projectOther.gradle'
    }
}
