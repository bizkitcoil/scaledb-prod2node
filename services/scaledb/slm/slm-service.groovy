import java.util.concurrent.TimeUnit;

service {
	name "slm"
	icon "slm.jpg"
	elastic false
	numInstances 2
	minAllowedInstances 2
	maxAllowedInstances 2
	compute {
		template "SMALL_UBUNTU"
	}
		

	lifecycle {
/*	start {


                def fulladdress= context.privateAddress()
                def privateIP = fulladdress.split("/")[0]

		println "ip="+fulladdress+"pip="+privateIP
	}
*/
        
/*      		
		details {
				return [
					"return details"	
				]
		}	        		
*/
		startDetectionTimeoutSecs 900
		startDetection {
			if(context.attributes.thisApplication.scaledb_install_running == true)
				return false;

			println "slm-service.groovy(startDetection): checking for port 43306 ..."
			return ServiceUtils.isPortOccupied(System.getenv()["CLOUDIFY_AGENT_ENV_PRIVATE_IP"],43306)
			
		}	

		locator {
					def myPids = ServiceUtils.ProcessUtils.getPidsWithQuery("State.Name.ct=slm")
					println ":slm-service.groovy: current PIDs: ${myPids}"
					return myPids
        	}/*
        locator {
            //hack to avoid monitoring started processes by cloudify
            return [] as LinkedList
        }*/


	}
    
    // network {
       // port = 
      //  protocolDescription =
   // }
		
}
