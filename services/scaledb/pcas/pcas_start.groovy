import org.cloudifysource.utilitydomain.context.ServiceContextFactory
import java.util.concurrent.TimeUnit
import groovy.text.SimpleTemplateEngine


def context = ServiceContextFactory.getServiceContext()
def rem_dir=context.serviceDirectory
def homedir=""
def config = new ConfigSlurper().parse(new File("pcas-service.properties").toURL())
if(config.UNIX_USER == "root")
	homedir = "/home/root"
else
	homedir = "/home/$config.UNIX_USER"

//pemfile = System.getenv("KeyFileName")

if(rem_dir.endsWith("."))
	rem_dir = rem_dir.substring(0,rem_dir.length()-1)
// def env = System.getenv()
println "pcas_start.groovy: scaledb instanceid=${context.getInstanceId()}"
if(context.getInstanceId() <= 1) {
	println "pcas_start.groovy: scaledb is about to start"
//("PCAS",'',"PCASM",'',"SLM1",'',"SLM2",'',"MySQLN1",'',"MySQLN2",'');
	def servers = ["PCAS1","PCAS1M","SLM1","SLM2","MySQLN1","MySQLN2"]
	def f = new File("${rem_dir}/iscale.manifest")
	f.setText("unix_user scaledb\n")
	f << "install_dir mycluster\n"
	f << "SSHKEY ${config.pem_file} \n"
//	f << "SSHKEY ${pemfile}\n"
	f << "mysql_url http://downloads.mysql.com/archives/mysql-5.1/mysql-5.1.66-linux-x86_64-glibc23.tar.gz \n"
	f << "mariadb_url https://region-a.geo-1.objects.hpcloudsvc.com/v1/28974857097923/obj_pub1/mariadb-5.5.30-linux-x86_64.tar.gz \n"
	f << "scaledb_url      https://region-a.geo-1.objects.hpcloudsvc.com/v1/28974857097923/obj_pub1/scaledb-latest-mariadb-5.5.30.tar.gz \n"

	println "pcas_start.groovy: detecting pcas"
	def scaleDbService = context.waitForService("pcas", 400, TimeUnit.SECONDS);
	def scaleDbServices = scaleDbService.waitForInstances(scaleDbService.getNumberOfPlannedInstances(), 600, TimeUnit.SECONDS );
	def i=0
	println "pcas_start.groovy: pcas address is " + scaleDbServices[0].getHostAddress()
	f << servers[i] << " " << scaleDbServices[0].getHostAddress() << "\n"
	i++

	println "pcas_start.groovy: detecting pcas1m"
	scaleDbService = context.waitForService("pcas2", 400, TimeUnit.SECONDS);
	scaleDbServices = scaleDbService.waitForInstances(scaleDbService.getNumberOfPlannedInstances(), 600, TimeUnit.SECONDS );
	println "pcas_start.groovy: pcasm address is " + scaleDbServices[0].getHostAddress()
	f << servers[i] << " " << scaleDbServices[0].getHostAddress() << "\n"
	i++
	

	println "pcas_start.groovy: detecting slm"
	scaleDbService = context.waitForService("slm", 400, TimeUnit.SECONDS)
	scaleDbServices = scaleDbService.waitForInstances(scaleDbService.getNumberOfPlannedInstances(), 360, TimeUnit.SECONDS )
	def j=0
	while (j<2){
		j=0
		scaleDbServices = scaleDbService.waitForInstances(scaleDbService.getNumberOfPlannedInstances(), 360, TimeUnit.SECONDS )
		for(instance in scaleDbServices)
		{
			j++
		}
	}
	j=0
	for(instance in scaleDbServices)
	{
	
		println "pcas_start.groovy: slm address is " + instance.getHostAddress()
		f << servers[i] << " " << instance.getHostAddress() << "\n"
		i = i + 1
	}

	println "pcas_start.groovy: detecting mysql"
	scaleDbService = context.waitForService("mysql", 400, TimeUnit.SECONDS);
	
	j=0
	while (j<2){
		j=0
		scaleDbServices = scaleDbService.waitForInstances(scaleDbService.getNumberOfPlannedInstances(), 360, TimeUnit.SECONDS );
		for(instance in scaleDbServices)
		{
			j++
		}
	}
	for(instance in scaleDbServices)
	{
		println "pcas_start.groovy: mysql address is " + instance.getHostAddress()
		f << servers[i] << " " << instance.getHostAddress() << "\n"
		i = i + 1
	}


	if(context.attributes.thisApplication.scaledb_install_running == false)
	{	
		println("pcas_start.groovy: rerun - exiting")
		return false;
	}
	println("pcas_start.groovy: about to launch perl")
		
	new AntBuilder().sequential {
    	chmod(file:"${rem_dir}iscaledb", perm:"ugo+rx")
    	chmod(file:"${rem_dir}start.sh", perm:"ugo+rx")
		exec(executable:"${rem_dir}/start.sh", osfamily:"unix", dir:"${homedir}") {
			arg(value:"${homedir}")
			arg(value:rem_dir)
			arg(value:config.UNIX_USER)
		}
	}
	context.attributes.thisApplication.scaledb_install_running = false;

	println("Ran the command line perl ${rem_dir}/iscaledb")
} else
{
		println("pcas_start.groovy: I am not the manager")

}

println "pcas_start.groovy: Scaledb started"
