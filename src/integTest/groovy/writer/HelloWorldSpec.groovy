package writer

import spock.lang.Specification

class HelloWorldSpec extends Specification {

    def "Hello World"() {
        expect:
            'hello world' == 'Hello World'.toLowerCase()
    }
}
