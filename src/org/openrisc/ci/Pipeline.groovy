package org.openrisc.ci

def shellExecute(command) {
    sh command
}

class Pipeline {

    def steps

    Pipeline(steps) {
        this.steps = steps
    }

// This method pulls standard dockerimage for openrisc related projects . 
    def dockerPull(){
        shellExecute('docker pull librecores/librecores-ci-openrisc')
        shellExecute('docker images')
    }

// This method runs or1k-tests against various enviroment variable
}
