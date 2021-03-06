import java.util.concurrent.TimeUnit;

service {
	name "pcas"
	icon "pcas.jpg"
	elastic false
	numInstances 1
	minAllowedInstances 1
	maxAllowedInstances 1
	compute {
		template "SMALL_UBUNTU"
	}
		

	lifecycle {

		init {context.attributes.thisApplication.scaledb_install_running = true}
		install "pcas_install.groovy"
		start "pcas_start.groovy" 		

		 preStop "pcas_stop.groovy"			
        


		startDetectionTimeoutSecs 600
		startDetection {
			if(context.attributes.thisApplication.scaledb_install_running == true)
				return false;
			println "pcas-service.groovy(startDetection): checking for port 13306 on host " + System.getenv()["CLOUDIFY_AGENT_ENV_PRIVATE_IP"]
			return ServiceUtils.isPortOccupied(System.getenv()["CLOUDIFY_AGENT_ENV_PRIVATE_IP"],13306)

		}	

		locator {
					def myPids = ServiceUtils.ProcessUtils.getPidsWithQuery("State.Name.ct=cas.cnf")
					println "pcas-service.groovy: current PIDs: ${myPids}"
					return myPids
        	}
      /*  	
        locator {
            //hack to avoid monitoring started processes by cloudify
            return [] as LinkedList
        }*/
        	
	}
		



    
    // network {
	//	println ":scaledb-service.groovy: nameNode Http port: ${nameNodePort}"
       // port = nameNodePort
      //  protocolDescription ="NameNode"
   // }
		
}
