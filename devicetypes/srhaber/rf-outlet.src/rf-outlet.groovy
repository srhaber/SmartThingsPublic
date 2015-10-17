metadata {	
    definition (name: "RF Outlet", namespace: "srhaber", author: "Shaun Haber") {
		capability "Switch"
        capability "Relay Switch"        
	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		}
		standardTile("on", "device.switch", decoration: "flat") {
			state "default", label: 'On', action: "on", backgroundColor: "#ffffff"
		}   
		standardTile("off", "device.switch", decoration: "flat") {
			state "default", label: 'Off', action: "off", backgroundColor: "#ffffff"
		}
        main "switch"
		details(["switch","on","off"])
	}
    
    preferences {
        input(name: "channel", title: "Channel", description: "Enter a valid channel, from 1 to 5.", type: "number", range: "1..5", required: true)    
    }
}

def parse(String description) {
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def on() {
	log.debug "$channel on()"
	sendEvent(name: "switch", value: "on")
    def result = new physicalgraph.device.HubAction(
        method: "PUT",
        path: "/$channel/1",
        headers: [
            HOST: '10.0.1.101:8080'
        ]
    )
    return result     
}

def off() {
	log.debug "$channel off()"
	sendEvent(name: "switch", value: "off")
    def result = new physicalgraph.device.HubAction(
        method: "PUT",
        path: "/$channel/0",
        headers: [
            HOST: '10.0.1.101:8080'
        ]
    )
    return result    
}

private getVersion() {
	"PUBLISHED"
}
