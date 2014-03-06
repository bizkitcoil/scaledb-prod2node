import java.util.concurrent.TimeUnit;

service {
	name "mysql"
	icon "mysql.jpg"
	elastic false
	numInstances 2
	minAllowedInstances 2
	maxAllowedInstances 2
	compute {
		template "SMALL_UBUNTU"
	}
		

	lifecycle {

		startDetectionTimeoutSecs 900
		startDetection {
		    if(context.attributes.thisApplication.scaledb_install_running == true)
				return false;

			println "mysql-service.groovy(startDetection): checking for port 3306 ..."
			return ServiceUtils.isPortOccupied(System.getenv()["CLOUDIFY_AGENT_ENV_PRIVATE_IP"],3306)
			
		}	

		locator {
					def myPids = ServiceUtils.ProcessUtils.getPidsWithQuery("State.Name.ct=mysql")
					println ":mysql-service.groovy: current PIDs: ${myPids}"
					return myPids
        	}
/*
         locator {
            //hack to avoid monitoring started processes by cloudify
            return [] as LinkedList
        }
*/	
	}
		



    
    // network {
	//	println ":scaledb-service.groovy: nameNode Http port: ${nameNodePort}"
       // port = nameNodePort
      //  protocolDescription ="NameNode"
   // }
		
}
