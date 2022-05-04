package no.fintlabs.consumer

import no.fintlabs.cache.CacheManager
import no.fintlabs.consumer.admin.AdminController
import no.fintlabs.consumer.config.ConsumerProps
import spock.lang.Specification

class AdminControllerSpec extends Specification {
    def "Check given assets"() {
        given:
        ConsumerProps consumerProps = Mock() {
            orgId >> "MittFylke.no"
        }
        def adminController = new AdminController(consumerProps, Mock(CacheManager))

        when:
        String result = adminController.getAssets()

        then:
        result.contains("MittFylke.no")
        !result.contains(",")
    }
}
