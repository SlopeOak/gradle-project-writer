package org.ssinc.projectwriter

import spock.lang.Specification

class HelloWorldSpec extends Specification {
    def "Hello world!"() {
        expect:
            'Hello, World!'.toLowerCase() == 'hello, world!'
    }
}
