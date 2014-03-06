import org.cloudifysource.utilitydomain.context.ServiceContextFactory

import groovy.text.SimpleTemplateEngine
import java.util.concurrent.TimeUnit

def config = new ConfigSlurper().parse(new File("pcas-service.properties").toURL())
def context = ServiceContextFactory.getServiceContext()

println("pcas_install.groovy: manager attribute is ${context.attributes.thisService.manager}")

new AntBuilder().sequential {
   	chmod(file:"${context.serviceDirectory}/install.sh", perm:"ugo+rx")
	exec(executable:"${context.serviceDirectory}/install.sh", osfamily:"unix") {
		arg("value":context.serviceDirectory)						
		arg("value":config.UNIX_USER)		
		arg("value":config.pem_file)
	}				
}

println "pcas_install.groovy: Installing scaledb..."
