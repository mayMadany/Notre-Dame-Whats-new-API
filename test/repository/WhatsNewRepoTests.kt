package ca.estmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.config.common
import ca.etsmtl.applets.notre_dame.config.whatsNewRepo
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import kotlin.test.assertTrue

class WhatsNewRepoTests {
    val context = Kodein{
        import(common)
        import(whatsNewRepo)
    }

    @Test
    fun getByVersion() {
        val repository: WhatsNewRepo by context.instance("whatsNewRepo")
        val results = repository.getByVersion(1.0f)
        assertTrue { results.isNotEmpty() }
    }
}